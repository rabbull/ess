package command.processors;

import com.alibaba.fastjson.JSON;
import command.Command;
import mappers.GetAllable;
import org.apache.ibatis.session.SqlSession;

import java.util.Collection;
import java.util.Collections;

public class RequestAllCommandProcessor implements CommandProcessor {

    private SqlSession session;

    public RequestAllCommandProcessor(SqlSession session) {
        this.session = session;
    }

    public Command run(String[] args) {
        String className = args[0];
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
        Collection<Object> result = null;
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
