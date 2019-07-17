import javax.swing.*;
import java.awt.*;

public class CenterPane extends JPanel {

        public  JTabbedPane mainTabPane;
        public CenterPane(){
            super(new BorderLayout());

            mainTabPane = new JTabbedPane();
            setVisible(true);

            mainTabPane.addTab("主界面", new MainPane());
            mainTabPane.addTab("抽取专家信息", new JScrollPane(new SelectingExp()));
            mainTabPane.addTab("注册专家信息", new RegistrationPane());

            this.add(mainTabPane, BorderLayout.CENTER);

    }
}
