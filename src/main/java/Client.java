
import models.entities.Company;
import models.entities.Expert;
import models.entities.Profession;
import models.entities.Project;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.util.List;


public class Client implements ClientInterface {

    public static Socket com;

    public  DataInputStream comIn;

    public  DataOutputStream comOut;

    public Client() {

    }

    public static void main(String[] args) {
        JFrame j = new JFrame("专家信息管理系统");
        j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        j.add(new CenterPane(), BorderLayout.CENTER);
        j.setVisible(true);
        j.setSize(750, 600);
    }
    public void run(){
        JFrame j = new JFrame("专家信息管理系统");
        j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        j.add(new CenterPane(), BorderLayout.CENTER);
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
