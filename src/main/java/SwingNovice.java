import dao.ExpertDAO;
import dao.ProfessionDAO;
import dao.ProjectDAO;
import dao.CompanyDAO;
import database.Database;
import models.Company;
import models.Expert;
import models.Profession;
import models.Project;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class SwingNovice extends JPanel implements ClientInterface{

    public static String[] expertInfoItems = {"asd","asdasd"};  //录入专家信息的表的labels
    public static JTextField[] textFields = new JTextField[2];

    public static JTabbedPane mainTabPane;

    public static List<Company> companies;
    public static List<Profession> professions;
    public static List<Expert> experts;
    public static List<Project> projects;

//    public static CompanyDAO companyDAO;
//    public static ProfessionDAO professionDAO;
//    public static ExpertDAO expertDAO;
//    public static ProjectDAO projectDAO;

    public static Socket com;

    public static DataInputStream comIn;

    public static DataOutputStream comOut;

    public SwingNovice() {
        super(new BorderLayout());

        mainTabPane = new JTabbedPane();
        setVisible(true);

        mainTabPane.addTab("主界面", MainPane.AdminPanelDeliver());
        mainTabPane.addTab("抽取专家信息", SelectingExp.SelectingExDeliver(false));
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
    public void run(){
        return;
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
