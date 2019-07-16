package command.processors;

import com.alibaba.fastjson.JSON;
import command.Command;
import mappers.GetAllable;
import mappers.LogMapper;
import mappers.ProjectMapper;
import models.entities.Log;
import org.apache.ibatis.session.SqlSession;
import sun.misc.Request;

import java.util.Collection;
import java.util.Collections;

public class RequestAllCommandProcessor implements CommandProcessor {

    private SqlSession session;

    public RequestAllCommandProcessor(SqlSession session) {
        this.session = session;
    }

    public Command run(String[] args) {
        String className = args[0];
        GetAllable mapper;
        switch(className) {
            case "Log":
                mapper = session.getMapper(LogMapper.class);
                break;

            case "Project":
                mapper = session.getMapper(ProjectMapper.class);
                break;

            default:
                return new Command("Error", Collections.emptySet());
        }
        Collection<Object> result = null;
        try {
            result = mapper.getAll();
        } catch (Exception e) {
            e.printStackTrace();
            return new Command("Error", Collections.emptySet());
        }
        return new Command("Object", Collections.singleton(JSON.toJSONString(result)));
    }
}
