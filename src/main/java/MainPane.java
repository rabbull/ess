import com.alibaba.fastjson.JSON;
import command.exceptions.InvalidCommandFormatException;
import common.Serializable;
import duplicated.dao.ExpertDAO;
import duplicated.dao.ProjectDAO;
import command.Command;
import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.Collections;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import command.Command;
import duplicated.models.Company;
import duplicated.models.Expert;
import duplicated.models.Project;
import org.apache.commons.math3.analysis.function.Exp;


public class MainPane {

    public static Dimension buttonSize = new Dimension(150, 50);


//    public static Object[][] expertInformation = {{"asdasd", "asdawr", "asdarsafwfe", "asdarsr", "asdawdsd", "adwsad", "adsaw", "asdawd"}, {"1231231", "qwe2w2wrawe", "asdqwrw", "aef3efw", "asadwd", "asdawds", "asdawsa", "asdawd"}};

    public static Object[][] expertInformation;

//    public static Object[][] projectInformation = {{"1000", "asd", "qwdq"}, {"qwd", "asd", "asd"}};

    public static Object[][] projectInformation;

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
        List<String> clargs  = new ArrayList<>();
        clargs.add("all");
        Command require_all_exp = new Command("require_exp",clargs);
        try {
            SwingNovice.comOut.write(require_all_exp.serialize());
        }
        catch(IOException ioe){
            System.out.println(ioe.fillInStackTrace());
        }
        try {
            Command js = Command.getOneCommandFromInputStream(SwingNovice.comIn);
            if(js.getCmd().equals("Object")){
                String processed = String.join(" ", js.getArgs());
                List<Expert> out = JSON.parseArray(processed,Expert.class);
                expertInformation = new Object[out.size()][4];
                for(int i = 0;i < out.size();i ++){
                    expertInformation[i][0] = out.get(i).getName();
                    expertInformation[i][1] = out.get(i).getGender();
                    expertInformation[i][2] = out.get(i).getPhone();
                    expertInformation[i][3] = out.get(i).getCompany().getName();
                }
            }
            else{
                System.out.println("接收错误");
            }
        }
        catch(InvalidCommandFormatException icfe){
            System.out.println(icfe.fillInStackTrace());
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
                    Expert exp2d = new Expert(model.getValueAt(selectedRow,0).toString(),model.getValueAt(selectedRow,1).toString(),model.getValueAt(selectedRow,2).toString(),new Company(model.getValueAt(selectedRow,3).toString()));
                    model.removeRow(selectedRow);
                    expertTable.setModel(model);

                    Command del_exp = new Command("delete",Collections.singletonList(exp2d.toString()));
                    Command del_suc = new Command("");
                    try {
                        SwingNovice.comOut.write(del_exp.serialize());
                    }
                    catch(IOException ioe){
                        System.out.println(ioe.fillInStackTrace());
                    }
                    try {
                        del_suc = Command.getOneCommandFromInputStream(SwingNovice.comIn);
                    }
                    catch (InvalidCommandFormatException icfe){
                        System.out.println(icfe.fillInStackTrace());
                    }
                    finally {
                        if(del_suc.getCmd().equals("success")) {
                            JOptionPane.showMessageDialog(null, "删除成功", "消息", JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                }
            }
        });
        deleteExpert.setPreferredSize(buttonSize);
        btn_ctner.add(deleteExpert);

        JButton take_absence = new JButton("当前专家请假");
        take_absence.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    DefaultTableModel model = (DefaultTableModel) expertTable.getModel();
                    int selrow = expertTable.getSelectedRow();
                    Expert abs_exp = new Expert(model.getValueAt(selrow,0).toString(),model.getValueAt(selrow,1).toString(),model.getValueAt(selrow,2).toString(),new Company(model.getValueAt(selrow,3).toString()));
                    Command absent_command = new Command("absent",Collections.singletonList(abs_exp.toString()));
                    SwingNovice.comOut.write(absent_command.serialize());
                }
                catch(IOException ioe){
                    System.out.println(ioe.fillInStackTrace());
                }
            }
        });
        take_absence.setPreferredSize(buttonSize);
        btn_ctner.add(take_absence);

        //搜索组合面板
        JPanel Filter = new JPanel(false);
        Filter.setPreferredSize(new Dimension(100, 100));

        JLabel search_title = new JLabel("搜索:");
        JTextField search_tx = new JTextField();
        JRadioButton[] btns = new JRadioButton[filterConditions.length];
        btns[0] = new JRadioButton(filterConditions[0], true);


        Box search_wrapper = Box.createHorizontalBox();
        search_wrapper.setPreferredSize(new Dimension(200, 30));
        search_wrapper.add(search_title);
        search_wrapper.add(search_tx);
        Filter.add(search_wrapper);

        JLabel con_tx = new JLabel("搜索方式:");
        Filter.add(con_tx);

        ButtonGroup group = new ButtonGroup();
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
                //搜索的输入处理
                String search_text = search_tx.getText();
                for(int o = 0;o < search_text.length();o ++){
                    if(search_text.charAt(o) == ' '){
                        JOptionPane.showMessageDialog(null,"输入中包含空格，请重新输入");
                        search_tx.setText("");
                        return;
                    }
                }
                String search_type = new String();
                for(JRadioButton j:btns){
                    if(j.isSelected()) search_type = j.getText();
                }

                Command sear = new Command("search " + search_type + " " + search_text );

                try {
                    SwingNovice.comOut.write(sear.serialize());
                }
                catch(IOException ioe){
                    System.out.println(ioe.fillInStackTrace());
                }

                try{
                    Command search_res = Command.getOneCommandFromInputStream(SwingNovice.comIn);
                    if(search_res.getCmd().equals("Object")){
                        String processed = String.join(" ", search_res.getArgs());
                        List<Expert> out = JSON.parseArray(processed,Expert.class);
                        expertInformation = new Object[out.size()][4];
                        for(int i = 0;i < out.size();i ++){
                            expertInformation[i][0] = out.get(i).getName();
                            expertInformation[i][1] = out.get(i).getGender();
                            expertInformation[i][2] = out.get(i).getPhone();
                            expertInformation[i][3] = out.get(i).getCompany().getName();
                        }
                    }
                    DefaultTableModel or_model = (DefaultTableModel) expertTable.getModel();
                    for(Object[] j:expertInformation){
                        or_model.addRow(j);
                    }
                    expertTable.setModel(or_model);
                }
                catch(InvalidCommandFormatException icfe){
                    System.out.println(icfe.fillInStackTrace());
                }
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
        //导入数据 倒入项目信息
        List<String> poargs  = new ArrayList<String>();
        poargs.add("all");
        Command require_all_proj = new Command("require_proj",poargs);
        try {
            SwingNovice.comOut.write(require_all_proj.serialize());
        }
        catch(IOException ioe){
            System.out.println(ioe.fillInStackTrace());
        }
        try {
            Command pjs = Command.getOneCommandFromInputStream(SwingNovice.comIn);
            if(pjs.getCmd().equals("Object")){
                String processed = String.join(" ", pjs.getArgs());
                List<Project> pout = JSON.parseArray(processed,Project.class);
                projectInformation = new Object[pout.size()][7];
                for(int i = 0;i < pout.size();i ++){
                    projectInformation[i][0] = pout.get(i).getCode();
                    projectInformation[i][1] = pout.get(i).getName();
                    projectInformation[i][2] = pout.get(i).getAmount();
                    projectInformation[i][3] = pout.get(i).getMethod();
                    projectInformation[i][4] = pout.get(i).getIndustry();
                    projectInformation[i][5] = pout.get(i).getOrganizationForm();
                    projectInformation[i][6] = pout.get(i).getCategory();
                }
            }
            else{
                System.out.println("接收错误");
            }
        }
        catch(InvalidCommandFormatException icfe){
            System.out.println(icfe.fillInStackTrace());
        }

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

                //根据数据判断还需要还需要抽取的人数
                Project p = new Project(model.getValueAt(selected_r,0).toString(),model.getValueAt(selected_r,1).toString(),Long.parseLong((String)model.getValueAt(selected_r,2)),model.getValueAt(selected_r,3).toString(),model.getValueAt(selected_r,4).toString(),model.getValueAt(selected_r,5).toString(),model.getValueAt(selected_r,6).toString());
                try{
                    //发送请求返回一个models里面的project实例，含有需要的抽取专家人数。
                    Command get_expect = new Command("Get_proj",Collections.singletonList(p.toString()));
                    SwingNovice.comOut.write(get_expect.serialize());
                }
                catch(IOException ioe){
                    System.out.println(ioe.fillInStackTrace());
                }
                try{
                    Command expected_proj = Command.getOneCommandFromInputStream(SwingNovice.comIn);
                    if(expected_proj.getCmd().equals("Object")){
                        String jsin = String.join(" ",expected_proj.getArgs());
                        models.entities.Project expected_p = JSON.parseObject(jsin,models.entities.Project.class);
                        out_p.add(SelectingExp.SelectingExDeliver(true,expected_p));
                        int response = JOptionPane.showConfirmDialog(null, jsp_out, "补充抽取", JOptionPane.YES_NO_OPTION);

                    }
                }
                catch(InvalidCommandFormatException icfe){
                    System.out.println(icfe.fillInStackTrace());
                }
                //传入pro然后判断逮抽取人数
            }
        });
        Complement_Rolling.setPreferredSize(new Dimension(150, 50));
        btns2.add(Complement_Rolling);

        JButton scoring = new JButton("对当前项目打分");
        scoring.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultTableModel p_model = (DefaultTableModel) Proj_table.getModel();
                int selrow = Proj_table.getSelectedRow();
                Project p = new Project(p_model.getValueAt(selrow,0).toString(),p_model.getValueAt(selrow,1).toString(),Long.parseLong((String)p_model.getValueAt(selrow,2)),p_model.getValueAt(selrow,3).toString(),p_model.getValueAt(selrow,4).toString(),p_model.getValueAt(selrow,5).toString(),p_model.getValueAt(selrow,6).toString());



                try{
                    Command get_pro_exp = new Command("Get_proj_exp",Collections.singletonList(p.toString()));
                    SwingNovice.comOut.write(get_pro_exp.serialize());
                }
                catch(IOException ioe){
                    System.out.println(ioe.fillInStackTrace());
                }
                String[] exp_namelist = new String[1];
                try{
                    Command exps = Command.getOneCommandFromInputStream(SwingNovice.comIn);
                    if(exps.getCmd().equals("Object")){
                        String exps_str = String.join(" ",exps.getArgs());
                        List<Expert> experts = JSON.parseArray(exps_str,Expert.class);
                        exp_namelist = new String[experts.size()];
                        for(int i = 0 ;i < experts.size();i ++){
                            exp_namelist[i] = experts.get(i).getName();
                        }
                    }
                }
                catch (InvalidCommandFormatException icfe){
                    System.out.println(icfe.fillInStackTrace());
                }

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
                    try{
                        Collection<String> grades = new ArrayList<>(20);
                        for(int k = 0 ;k < exp_namelist.length;k ++){
                            grades.add(exp_namelist[k] + " " + score_input[k]);
                        }
                        Command submit_grades = new Command("Submit",grades);
                        SwingNovice.comOut.write(submit_grades.serialize());
                    }
                    catch (IOException ioe){
                        System.out.println(ioe.fillInStackTrace());
                    }
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
