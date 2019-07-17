
import models.entities.Company;
import models.entities.Expert;
import models.entities.Profession;
import models.entities.Project;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.util.List;


public class SwingNovice extends JPanel implements ClientInterface {

    public static String[] expertInfoItems = {"姓名","性别","电话号码","公司"};  //录入专家信息的表的labels

    public static JTextField[] textFields = new JTextField[expertInfoItems.length];

    public static JTabbedPane mainTabPane;

    public static List<Company> companies;
    public static List<Profession> professions;
    public static List<Expert> experts;
    public static List<Project> projects;

    public static Socket com;

    public static DataInputStream comIn;

    public static DataOutputStream comOut;

    public SwingNovice() {
        super(new BorderLayout());

        mainTabPane = new JTabbedPane();
        setVisible(true);

        mainTabPane.addTab("主界面", MainPane.AdminPanelDeliver());
        mainTabPane.addTab("抽取专家信息", SelectingExp.SelectingExDeliver(false,null));
        mainTabPane.addTab("注册专家信息", RegistrationPane.RegistrationPanelDeliver());

        this.add(mainTabPane, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        JFrame j = new JFrame("专家信息管理系统");
        j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        j.add(new SwingNovice(), BorderLayout.CENTER);
        j.setVisible(true);
        j.setSize(750, 600);
    }

    public void run() {
        JFrame j = new JFrame("专家信息管理系统");
        j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        j.add(new SwingNovice(), BorderLayout.CENTER);
        j.setVisible(true);
        j.setSize(750, 600);
    }

    public void registerSocket(Socket socket){
        com = socket;
        try {
            comIn = new DataInputStream(com.getInputStream());
            comOut = new DataOutputStream(com.getOutputStream());
        }
        catch (IOException ioe){
            System.out.print("socket流读取错误");
        }

    }
}
