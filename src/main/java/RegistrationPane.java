//import com.sun.org.apache.xpath.internal.operations.Bool;
import models.entities.Company;
import models.entities.Expert;
import models.entities.Profession;
import org.apache.commons.math3.analysis.function.Exp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;


public class RegistrationPane {
    public static JComponent RegistrationPanelDeliver() {
        Box jresult = Box.createVerticalBox();
        JLabel[] labels = new JLabel[SwingNovice.expertInfoItems.length];
        Box[] boxes = new Box[SwingNovice.expertInfoItems.length];
        JPanel Forum = new JPanel(false);

        for (int i = 0; i < SwingNovice.expertInfoItems.length; i++) {
            SwingNovice.textFields[i] = new JTextField();
            labels[i] = new JLabel(SwingNovice.expertInfoItems[i]);
            boxes[i] = Box.createHorizontalBox();
            boxes[i].add(labels[i]);
            boxes[i].add(SwingNovice.textFields[i]);
            Forum.add(boxes[i]);
        }

        Forum.setLayout(new GridLayout(SwingNovice.expertInfoItems.length, 1));

        Forum.setPreferredSize(new Dimension(500, 400));
        Forum.setMaximumSize(new Dimension(500, 400));
        Forum.setMinimumSize(new Dimension(500, 400));
        jresult.add(Forum);
        JButton jButton = new JButton("提交录入");
        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StringBuilder conformationString = new StringBuilder();
                for (int i = 0; i < SwingNovice.expertInfoItems.length; i++) {
                    if (SwingNovice.textFields[i].getText().equals("")) {
                        JOptionPane.showMessageDialog(null, "信息不全请继续录入", "提示信息", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    conformationString.append(SwingNovice.expertInfoItems[i]);
                    conformationString.append(":");
                    conformationString.append(SwingNovice.textFields[i].getText());
                    conformationString.append("\n");
                }
                conformationString.append("\n 请确认上述信息是否无误 \n是否录入？");
                int response = JOptionPane.showConfirmDialog(null, conformationString.toString(), "确认信息", JOptionPane.YES_NO_OPTION);
                if (response != 0) {
                    return;
                }

            }
        });
        jButton.setPreferredSize(new Dimension(150, 80));
        jButton.setMinimumSize(new Dimension(150, 80));
        jresult.add(jButton);

        return jresult;
    }
}
