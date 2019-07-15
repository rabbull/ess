import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;

public class Fuck {
    public static void main(String[] args) {
        String en = "123";
        byte[] bytes = en.getBytes(StandardCharsets.UTF_8);
        System.out.println(Arrays.toString(bytes));
    }
}
