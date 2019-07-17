import com.alibaba.fastjson.JSON;
import command.Command;
import command.processors.RequestAllCommandProcessor;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;

public class Fuck {

    static class A {
        public String name;
        public String nickname;
        public B b;
    }

    static class B {
        public String name;
    }

    public static void main(String[] args) {
        Object a = new A();
        ((A) a).name = "shit";
        System.out.println(JSON.toJSONString(a));
    }
}
