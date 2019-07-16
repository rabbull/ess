import dao.ExpertDAO;
import dao.ProjectDAO;
import command.Command;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;


public class MainPane {

    public static Dimension buttonSize = new Dimension(150, 50);


    public static Object[][] expertInformation = {{"asdasd", "asdawr", "asdarsafwfe", "asdarsr", "asdawdsd", "adwsad", "adsaw", "asdawd"}, {"1231231", "qwe2w2wrawe", "asdqwrw", "aef3efw", "asadwd", "asdawds", "asdawsa", "asdawd"}};

    public static Object[][] projectInformation = {{"1000", "asd", "qwdq"}, {"qwd", "asd", "asd"}};

    public static Object[] expertTableTitles = ExpertDAO.readableTitle;

    public static String[] filterConditions = ExpertDAO.readableTitle;

    public static Object[] ProjectTableTitles = ProjectDAO.readableTitle;

    public static JComponent AdminPanelDeliver() {
        JPanel result = new JPanel(false);
        result.setLayout(new GridLayout(2, 2, 6, 6));

        Box box_1 = Box.createVerticalBox();
        box_1.add(new JLabel("专家数据库"));
        box_1.setPreferredSize(new Dimension(200, 140));
        JTable expertTable = new JTable();
        DefaultTableModel m = (DefaultTableModel) expertTable.getModel();
        m.setRowCount(0);
        m.setColumnIdentifiers(expertTableTitles);

        //向后段索取需要的专家数据
        Command require_all_exp = new Command("require_exp all");
        try {
            SwingNovice.comOut.writeUTF(require_all_exp.toString().getBytes().length + " " +require_all_exp.toString());
            int n = SwingNovice.comIn.readInt();

        }
        catch(IOException ioe){
            System.out.println(ioe.fillInStackTrace());
        }


        for (Object[] exp : expertInformation) {
            m.addRow(exp);
        }
        expertTable.setModel(m);
        expertTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        expertTable.setRowHeight(30);
        expertTable.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);

        JScrollPane jsp = new JScrollPane(expertTable);
        jsp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        jsp.setPreferredSize(new Dimension(500, 400));
        box_1.add(jsp);
        result.add(box_1);

        JPanel Table_man = new JPanel();
        Table_man.setLayout(new GridLayout(3, 1));
        Table_man.setBorder(new TitledBorder("人员数据操作数据操作"));

        JPanel btn_ctner = new JPanel();
        btn_ctner.setLayout(new GridLayout(3, 1));
        btn_ctner.setPreferredSize(new Dimension(130, 250));
        JButton view_record = new JButton("查看选中纪录");
        view_record.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TableModel model = expertTable.getModel();    //获得表格模型
                int selectedRow = expertTable.getSelectedRow();
                if (selectedRow == -1) {
                    return;
                }
                StringBuilder info = new StringBuilder();

                //有数据库之后从数据库获取完整的信息
                for (int i = 0; i < expertTableTitles.length; i++) {
                    info.append(expertTableTitles[i]);
                    info.append(":");
                    info.append(model.getValueAt(selectedRow, i));
                    info.append("\n");
                }
                JOptionPane.showMessageDialog(null, info.toString(), "详细信息", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        view_record.setSize(new Dimension(150, 50));
        btn_ctner.add(view_record);

        JButton deleteExpert = new JButton("删除当前选中纪录");
        deleteExpert.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultTableModel model = (DefaultTableModel) expertTable.getModel();    //获得表格模型
                int selectedRow = expertTable.getSelectedRow();
                if (selectedRow == -1) {
                    return;
                }
                StringBuilder info = new StringBuilder();

                info.append("当前选中的纪录如下：\n\n");

                //有数据库之后从数据库获取完整的信息
                for (int i = 0; i < expertTableTitles.length; i++) {
                    info.append(expertTableTitles[i]);
                    info.append(":");
                    info.append(model.getValueAt(selectedRow, i));
                    info.append("\n");
                }
                info.append("确认要移除这一条纪录么？");
                int response = JOptionPane.showConfirmDialog(null, info.toString(), "详细信息", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (response == 0) {
                    model.removeRow(selectedRow);
                    expertTable.setModel(model);
                    JOptionPane.showMessageDialog(null, "删除成功", "消息", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        deleteExpert.setPreferredSize(buttonSize);
        btn_ctner.add(deleteExpert);

        JButton take_absence = new JButton("当前专家请假");
        take_absence.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //
            }
        });
        take_absence.setPreferredSize(buttonSize);
        btn_ctner.add(take_absence);

        //搜索组合面板
        JPanel Filter = new JPanel(false);
        Filter.setPreferredSize(new Dimension(100, 100));

        JLabel search_title = new JLabel("搜索:");
        JTextField search_tx = new JTextField();

        search_tx.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //搜索的输入处理
                String search_result = search_tx.getText();

            }
        });
        Box search_wrapper = Box.createHorizontalBox();
        search_wrapper.setPreferredSize(new Dimension(200, 30));
        search_wrapper.add(search_title);
        search_wrapper.add(search_tx);
        Filter.add(search_wrapper);

        JLabel con_tx = new JLabel("搜索方式:");
        Filter.add(con_tx);

        ButtonGroup group = new ButtonGroup();
        JRadioButton[] btns = new JRadioButton[filterConditions.length];
        btns[0] = new JRadioButton(filterConditions[0], true);
        group.add(btns[0]);
        Filter.add(btns[0]);
        for (int i = 1; i < btns.length; i++) {
            btns[i] = new JRadioButton(filterConditions[i]);
            group.add(btns[i]);
            Filter.add(btns[i]);
        }


        JButton init_search = new JButton("搜索");
        init_search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //启动搜索
            }
        });

        Table_man.add(btn_ctner);
        Table_man.add(Filter);
        Table_man.add(init_search);

        result.add(Table_man);

        Box Existing_proj = Box.createVerticalBox();
        Existing_proj.add(new JLabel("项目列表"));
        JTable Proj_table = new JTable();
        DefaultTableModel m2 = (DefaultTableModel) Proj_table.getModel();
        m2.setColumnIdentifiers(ProjectTableTitles);
        //导入数据
        for (int i = 0; i < projectInformation.length; i++) {
            m2.addRow(projectInformation[i]);
        }
        Proj_table.setModel(m2);
        Proj_table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        JScrollPane jsp2 = new JScrollPane(Proj_table);
        Existing_proj.add(jsp2);
        result.add(Existing_proj);

        JPanel btns2 = new JPanel();
        btns2.setBorder(new TitledBorder("项目操作"));
        btns2.setLayout(new GridLayout(3, 1));
        JLabel invisible = new JLabel("");
        invisible.setPreferredSize(new Dimension(150, 50));
        invisible.setVisible(false);
        btns2.add(invisible);

        JButton Complement_Rolling = new JButton("对当前项目补充抽取人员");
        Complement_Rolling.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StringBuilder out = new StringBuilder();
                DefaultTableModel model = (DefaultTableModel) Proj_table.getModel();
                int selected_r = Proj_table.getSelectedRow();
                out.append("选择的项目信息如下：\n");
                for (int i = 0; i < ProjectTableTitles.length; i++) {
                    out.append(model.getValueAt(selected_r, i));
                    out.append("\n");  //显示打印项目信息
                }
                JPanel out_p = new JPanel();
                out_p.setLayout(new GridLayout(2, 1));
                JScrollPane jsp_out = new JScrollPane(out_p);
                out_p.setPreferredSize(new Dimension(400, 400));
                out_p.add(new JLabel(out.toString()));
                out_p.add(SelectingExp.SelectingExDeliver(true));
                int response = JOptionPane.showConfirmDialog(null, jsp_out, "补充抽取", JOptionPane.YES_NO_OPTION);
                if (response == 0) {
                    //重新抽取
                    JOptionPane.showMessageDialog(null, "已补充专家");
                }
            }
        });
        Complement_Rolling.setPreferredSize(new Dimension(150, 50));
        btns2.add(Complement_Rolling);

        JButton scoring = new JButton("对当前项目打分");
        scoring.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] exp_namelist = {"张三", "李四", "王五", "渣男", "渣女"};//有数据库则用数据库获取当前项目的相关专家
                JTextField[] score_input = new JTextField[exp_namelist.length];//用于对于每一个专家打分
                StringBuilder out = new StringBuilder();
                DefaultTableModel model = (DefaultTableModel) Proj_table.getModel();
                int selected_r = Proj_table.getSelectedRow();
                out.append("选择的项目信息如下：\n");
                for (int i = 0; i < ProjectTableTitles.length; i++) {
                    out.append(model.getValueAt(selected_r, i));
                    out.append("\n");  //显示打印项目信息
                }
                Box out_panel = Box.createVerticalBox();
                out_panel.add(new JLabel(out.toString()));
                for (int i = 0; i < exp_namelist.length; i++) {
                    Box bx = Box.createHorizontalBox();
                    bx.add(new JLabel(exp_namelist[i]));
                    score_input[i] = new JTextField();
                    bx.add(score_input[i]);
                    out_panel.add(bx);
                }
                int result = JOptionPane.showConfirmDialog(null, out_panel, "项目人员打分", JOptionPane.OK_CANCEL_OPTION);
                if (result == 0) {
                    JOptionPane.showMessageDialog(null, "提交成功!");
                }
            }
        });
        scoring.setPreferredSize(new Dimension(150, 50));
        btns2.add(scoring);
        result.add(btns2);
        return result;
    }
}
