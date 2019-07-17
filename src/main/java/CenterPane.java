import javax.swing.*;
import java.awt.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;

public class CenterPane extends JPanel {

        public  JTabbedPane mainTabPane;
        public CenterPane(DataInputStream comIn ,DataOutputStream comOut){
            super(new BorderLayout());

            mainTabPane = new JTabbedPane();
            setVisible(true);

            mainTabPane.addTab("主界面", new MainPane(comIn,comOut));
            mainTabPane.addTab("抽取专家信息", new JScrollPane(new SelectingExp(comIn,comOut)));
            mainTabPane.addTab("注册专家信息", new RegistrationPane(comIn,comOut));

            this.add(mainTabPane, BorderLayout.CENTER);

    }
}
