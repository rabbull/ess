//import com.sun.org.apache.xpath.internal.operations.Bool;
import command.Command;
import models.entities.Company;
import models.entities.Expert;
import models.entities.Profession;
import org.apache.commons.math3.analysis.function.Exp;
import org.omg.IOP.IOR;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


public class RegistrationPane extends JPanel{
    private   String[] expertInfoItems = {"姓名","性别","电话号码","公司"};  //录入专家信息的表的labels

    private   JTextField[] textFields = new JTextField[expertInfoItems.length];

    private Socket comIn;

    private Socket comOut;

    public  RegistrationPane() {
        Box jresult = Box.createVerticalBox();
        JLabel[] labels = new JLabel[expertInfoItems.length];
        Box[] boxes = new Box[expertInfoItems.length];
        JPanel Forum = new JPanel(false);

        for (int i = 0; i < expertInfoItems.length; i++) {
            textFields[i] = new JTextField();
            labels[i] = new JLabel(expertInfoItems[i]);
            boxes[i] = Box.createHorizontalBox();
            boxes[i].add(labels[i]);
            boxes[i].add(textFields[i]);
            Forum.add(boxes[i]);
        }

        Forum.setLayout(new GridLayout(expertInfoItems.length, 1));

        Forum.setPreferredSize(new Dimension(500, 400));
        Forum.setMaximumSize(new Dimension(500, 400));
        Forum.setMinimumSize(new Dimension(500, 400));
//        jresult.add(Forum);
        this.add(Forum);
        JButton jButton = new JButton("提交录入");
        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StringBuilder conformationString = new StringBuilder();
                for (int i = 0; i < expertInfoItems.length; i++) {
                    if (textFields[i].getText().equals("")) {
                        JOptionPane.showMessageDialog(null, "信息不全请继续录入", "提示信息", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    conformationString.append(expertInfoItems[i]);
                    conformationString.append(":");
                    conformationString.append(textFields[i].getText());
                    conformationString.append("\n");
                }
                conformationString.append("\n 请确认上述信息是否无误 \n是否录入？");
                int response = JOptionPane.showConfirmDialog(null, conformationString.toString(), "确认信息", JOptionPane.YES_NO_OPTION);
                if (response == 0) {
                    try{
                        List<String> reg_info = new ArrayList<>();
                        for(int k = 0;k < textFields.length;k ++){
                            reg_info.add(expertInfoItems[k] + " " + textFields[k].getText().replaceAll("\\s*",""));
                        }
                        Command submit_reg = new Command("submit_reg",reg_info);
                        SwingNovice.comOut.write(submit_reg.serialize());
                    }
                    catch (IOException ioe){
                        System.out.println(ioe.getMessage());
                    }
                }

            }
        });
        jButton.setPreferredSize(new Dimension(150, 80));
        jButton.setMinimumSize(new Dimension(150, 80));
//        jresult.add(jButton);
        this.add(jButton);
//        return jresult;
    }
}
