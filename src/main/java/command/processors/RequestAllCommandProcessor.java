package command.processors;

import com.alibaba.fastjson.JSON;
import command.Command;
import models.sheets.ExpertGetAllSheet;
import models.sheets.ProjectGetAllSheet;
import mappers.*;
import models.Model;
import models.entities.*;
import org.apache.ibatis.session.SqlSession;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class RequestAllCommandProcessor implements CommandProcessor {

    private SqlSession session;

    public RequestAllCommandProcessor(SqlSession session) {
        this.session = session;
    }

    public Command run(String[] args) {
        String className = args[0];
        if (className.equals("Expert")) {
            ExpertMapper expertMapper = session.getMapper(ExpertMapper.class);
            CompanyMapper companyMapper = session.getMapper(CompanyMapper.class);
            Collection<Model> experts, companies;
            try {
                experts = expertMapper.getAll();
                companies = companyMapper.getAll();
            } catch (Exception e) {
                return new Command("InternalError");
            }
            ArrayList<ExpertGetAllSheet> results = new ArrayList<>();
            for (Model expertObj : experts) {
                Expert expert = (Expert) expertObj;
                int companyId = expert.getCompany();
                Company company = null;
                for (Model companyObj : companies) {
                    if (companyObj.getId() == companyId) {
                        company = (Company) companyObj;
                        break;
                    }
                }
                if (company == null) {
                    return new Command("CorruptDatabase");
                }
                ExpertGetAllSheet sheet = new ExpertGetAllSheet(expert, company);
                results.add(sheet);
            }
            return new Command("Object", Collections.singleton(JSON.toJSONString(results)));
        } else if (className.equals("Project")) {
            ProjectMapper projectMapper = session.getMapper(ProjectMapper.class);
            BiddingTypeMapper biddingTypeMapper = session.getMapper(BiddingTypeMapper.class);
            IndustryTypeMapper industryTypeMapper = session.getMapper(IndustryTypeMapper.class);
            BiddingMethodMapper biddingMethodMapper = session.getMapper(BiddingMethodMapper.class);
            OrgTypeMapper orgTypeMapper = session.getMapper(OrgTypeMapper.class);

            Collection<Model> projects, biddingTypes, industryTypes, biddingMethods, orgTypes;
            try {
                projects = projectMapper.getAll();
                biddingMethods = biddingMethodMapper.getAll();
                industryTypes = industryTypeMapper.getAll();
                biddingTypes = biddingTypeMapper.getAll();
                orgTypes = orgTypeMapper.getAll();
            } catch (Exception e) {
                return new Command("InternalError");
            }
            ArrayList<ProjectGetAllSheet> results = new ArrayList<>();
            for (Model projectObj : projects) {
                Project project = (Project) projectObj;
                BiddingMethod biddingMethod = null;
                BiddingType biddingType = null;
                IndustryType industryType = null;
                OrgType orgType = null;
                for (Model biddingMethodObj : biddingMethods) {
                    if (biddingMethodObj.getId().equals(project.getBiddingMethod())) {
                        biddingMethod = (BiddingMethod) biddingMethodObj;
                        break;
                    }
                }
                for (Model biddingTypeObj : biddingTypes) {
                    if (biddingTypeObj.getId().equals(project.getBiddingType())) {
                        biddingType = (BiddingType) biddingTypeObj;
                        break;
                    }
                }
                for (Model industryTypeObj : industryTypes) {
                    if (industryTypeObj.getId().equals(project.getIndustryType())) {
                        industryType = (IndustryType) industryTypeObj;
                        break;
                    }
                }
                for (Model orgTypeObj : orgTypes) {
                    if (orgTypeObj.getId().equals(project.getOrgType())) {
                        orgType = (OrgType) orgTypeObj;
                        break;
                    }
                }
                if (biddingMethod == null || biddingType == null || industryType == null || orgType == null) {
                    return new Command("CorruptDatabase");
                }
                ProjectGetAllSheet sheet = new ProjectGetAllSheet(project, biddingType, biddingMethod, industryType, orgType);
                results.add(sheet);
            }
            return new Command("Object", Collections.singleton(JSON.toJSONString(results)));
        } else {
            Class modelClass;
            Class mapperClass;
            try {
                modelClass = Class.forName("models.entities." + className);
            } catch (ClassNotFoundException e) {
                try {
                    modelClass = Class.forName("models.relationships." + className);
                } catch (ClassNotFoundException ex) {
                    ex.printStackTrace();
                    return new Command("Error");
                }
            }
            try {
                mapperClass = Class.forName("mappers." + className + "Mapper");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                return new Command("Error");
            }
            GetAllable mapper = (GetAllable) session.getMapper(mapperClass);
            Collection<Model> result = null;
            try {
                result = mapper.getAll();
            } catch (Exception e) {
                e.printStackTrace();
                return new Command("Error");
            }
            result.forEach(modelClass::cast);
            return new Command("Object", Collections.singleton(JSON.toJSONString(result)));
        }
    }
}
