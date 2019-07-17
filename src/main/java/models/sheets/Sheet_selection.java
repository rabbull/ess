package models.sheets;

import Front_classes.Project_FE;
import com.alibaba.fastjson.JSON;
import models.entities.Profession;
import models.entities.Project;
import java.util.Date;
import java.util.List;

public class Sheet_selection {
    private Project_FE project;

    private List<String> Avoid_companies;

    private List<String> main_Conditions;

    private List<String> aux_Conditions;

    public Sheet_selection(Project_FE project, List<String> avoid_companies, List<String> main_Conditions, List<String> aux_Conditions) {
        this.project = project;
        Avoid_companies = avoid_companies;
        this.main_Conditions = main_Conditions;
        this.aux_Conditions = aux_Conditions;
    }

    public Project_FE getProject() {
        return project;
    }

    public void setProject(Project_FE project) {
        this.project = project;
    }

    public List<String> getAvoid_companies() {
        return Avoid_companies;
    }

    public void setAvoid_companies(List<String> avoid_companies) {
        Avoid_companies = avoid_companies;
    }

    public List<String> getMain_Conditions() {
        return main_Conditions;
    }

    public void setMain_Conditions(List<String> main_Conditions) {
        this.main_Conditions = main_Conditions;
    }

    public List<String> getAux_Conditions() {
        return aux_Conditions;
    }

    public void setAux_Conditions(List<String> aux_Conditions) {
        this.aux_Conditions = aux_Conditions;
    }
}
