package command.processors;

public class MessageCommandProcessor implements CommandProcessor {
    @Override
    public Object run(String[] args) {
        for (String arg : args) {
            System.out.println(arg);
        }
        System.out.println();
        return null;
    }
}
