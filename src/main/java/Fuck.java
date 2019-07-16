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
        Reader reader = null;
        try {
            reader = Resources.getResourceAsReader("mybatis.cfg.xml");
        } catch (IOException e) {
            e.printStackTrace();
        }
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(reader);
        SqlSession session = factory.openSession();

        RequestAllCommandProcessor processor = new RequestAllCommandProcessor(session);
        Command ret = processor.run(new String[]{"Log"});
        System.out.println(ret.getArgs()[0]);
    }
}
