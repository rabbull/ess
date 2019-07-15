import command.Command;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class CommandTest {

    private InputStream testInputStream;

    @org.junit.Before
    public void setUp() throws Exception {
        File file = new File("/home/karl/Java/ess/src/test/java/cmd_test_file");
        testInputStream = new FileInputStream(file);
    }

    @org.junit.Test
    public void getOneCommandFromInputStream() throws Exception {
        Command command = Command.getOneCommandFromInputStream(testInputStream);
        System.out.println(command.toString());
    }
}
