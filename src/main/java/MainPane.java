import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import command.exceptions.InvalidCommandFormatException;
import common.Serializable;

import command.Command;
import javax.swing.*;
import java.awt.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.*;
import java.util.Collections;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.CaretListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import models.entities.Expert;
import models.entities.Project;
import models.relations.Invites;
import models.sheets.ExpertGetAllSheet;
import models.sheets.ProjectGetAllSheet;


public class MainPane extends JPanel{

    public Dimension buttonSize = new Dimension(150, 50);


//    public static Object[][] expertInformation = {{"asdasd", "asdawr", "asdarsafwfe", "asdarsr", "asdawdsd", "adwsad", "adsaw", "asdawd"}, {"1231231", "qwe2w2wrawe", "asdqwrw", "aef3efw", "asadwd", "asdawds", "asdawsa", "asdawd"}};

    private Object[][] expertInformation;

//    private static Object[][] projectInformation = {{"1000", "asd", "qwdq"}, {"qwd", "asd", "asd"}};

    private Object[][] projectInformation;

    private Object[] expertTableTitles = {"ID","姓名","性别","电话号码","公司"};

    private String[] filterConditions = {"ID","姓名","电话号码","公司代码"};

    private Object[] ProjectTableTitles = {"项目编号","项目名称","招标金额","招标类型","招标方式","行业类型"};

    private List<ProjectGetAllSheet> Proj_chunk;

    private Project p;

    private List<ExpertGetAllSheet> Exper_chunk;

    private DataInputStream comIn;

    private DataOutputStream comOut;

    private List<Invites> score_exps;

    public  MainPane(DataInputStream com1, DataOutputStream com2) {
        this.comIn = com1;
        this.comOut = com2;
        this.setLayout(new GridLayout(2,2,6,6));
//        JPanel result = new JPanel(false);
//        result.setLayout(new GridLayout(2, 2, 6, 6));

        Box box_1 = Box.createVerticalBox();
        box_1.add(new JLabel("专家数据库"));
        box_1.setPreferredSize(new Dimension(200, 140));
        JTable expertTable = new JTable();
        DefaultTableModel m = (DefaultTableModel) expertTable.getModel();
        m.setRowCount(0);
        m.setColumnIdentifiers(expertTableTitles);

        this.get_table_data();

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
//        result.add(box_1);
        this.add(box_1);

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
                    Expert exp2d = new Expert(model.getValueAt(selectedRow,0).toString(),model.getValueAt(selectedRow,1).toString().equals("男"),model.getValueAt(selectedRow,2).toString());
                    model.removeRow(selectedRow);
                    expertTable.setModel(model);

                    Command del_exp = new Command("delete",Collections.singletonList(exp2d.toString()));
                    Command del_suc = new Command("");
                    try {
                        comOut.write(del_exp.serialize());
                    }
                    catch(IOException ioe){
                        System.out.println(ioe.fillInStackTrace());
                    }
                    try {
                        del_suc = Command.getOneCommandFromInputStream(comIn);
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
                    Integer IDout = new Integer(0);
//                    Expert abs_exp = new Expert(model.getValueAt(selrow,0).toString(),model.getValueAt(selrow,1).toString().equals("男"),model.getValueAt(selrow,2).toString());
                    for(ExpertGetAllSheet exp:Exper_chunk){
                        if(exp.getExpert().getId().equals(model.getValueAt(selrow,0))){
                            IDout = exp.getExpert().getId();
                        }
                    }
                    Command absent_command = new Command("requestprobyexpnumber",Collections.singletonList(IDout.toString()));
                    comOut.write(absent_command.serialize());
                }
                catch(IOException ioe){
                    System.out.println(ioe.fillInStackTrace());
                }
                try{
                    Command proj_in = Command.getOneCommandFromInputStream(comIn);
                    if(proj_in.getCmd().equals("Object")) {
                        JPanel out_panel = new JPanel(new GridLayout(1,1));
                        out_panel.setPreferredSize(new Dimension(400, 400));
                        String proj_str = String.join(" ",proj_in.getArgs());
                        List<Invites> invs = JSON.parseArray(proj_str,Invites.class);
//                        ArrayList<Invites> invites = new ArrayList<>(10);
//                        for(Invites inv:invs){
//                            invites.add(inv);
//                        }
                        //TODO 需要等invitessheet改了之后改这里
                        JCheckBox[] exp_checks = new JCheckBox[invs.size()];
                        JTextField[] exp_reason = new JTextField[invs.size()];
                        JPanel exps = new JPanel();
                        exps.setLayout(new GridLayout(invs.size() + 1, 1));
                        exps.setPreferredSize(new Dimension(600, 35 * (invs.size() + 1)));
                        JScrollPane jsp_exps = new JScrollPane(exps);
                        jsp_exps.setBorder(new TitledBorder("与专家相关的项目"));
                        JPanel ids = new JPanel();
                        ids.setLayout(new GridLayout(1, 5));
                        ids.add(new JLabel(""));
                        ids.add(new JLabel("姓名"));
                        ids.add(new JLabel("单位"));
                        ids.add(new JLabel("联系方式"));
                        exps.add(ids);
                        for (int i = 0; i < invs.size(); i++) {
                            JPanel temp = new JPanel();
                            temp.setLayout(new GridLayout(1, 5));
                            exp_checks[i] = new JCheckBox();
                            exp_checks[i].setVisible(false);
                            exp_reason[i] = new JTextField();
                            exp_reason[i].setVisible(true);
                            exp_reason[i].setEnabled(true);
                            JCheckBox check_temp = exp_checks[i];
                            JTextField reason_temp = exp_reason[i];
                            temp.add(exp_checks[i]);
//                    exp_checks[i].addItemListener(new ItemListener() {
//                        @Override
//                        public void itemStateChanged(ItemEvent e) {
//                            if (check_temp.isSelected()) {
//                                reason_temp.setEnabled(true);
//                            } else {
//                                reason_temp.setEnabled(false);
//                                reason_temp.setText("");
//                            }
//                        }
//                    });
                            temp.add(new JLabel(invs.get(i).getId().toString()));
                            temp.add(new JLabel(invs.get(i).getProject().getName()));
                            temp.add(exp_reason[i]);
                            temp.setBorder(new EtchedBorder());
                            exps.add(temp);
                        }
                        out_panel.add(jsp_exps);


                        int response = JOptionPane.showConfirmDialog(null, out_panel);
                        if(response != 0){
                            try{
                                List<String> out = new ArrayList<>(10);
                                for(int o = 0;o < exp_reason.length;o ++){
                                    invs.get(o).setReason(exp_reason[o].getText().replaceAll("\\s*",""));
                                    out.add(invs.get(o).getProject().getId() + "/" + invs.get(o).getReason());
                                }
                                Command submit_abs = new Command("submit_abs", out);
                                comOut.write(submit_abs.serialize());
                            }
                            catch(IOException ioe){
                                System.out.println(ioe.getMessage());
                            }
                        }
                    }
                }
                catch(InvalidCommandFormatException icfe){
                    System.out.println(icfe.getMessage());
                }
            }
        });
        take_absence.setPreferredSize(buttonSize);
        btn_ctner.add(take_absence);

        //搜索组合面板
        JPanel Filter = new JPanel(false);
        Filter.setPreferredSize(new Dimension(100, 100));
//TODO 本地搜索
        //搜索本地

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
                String search_text = search_tx.getText().replaceAll("\\s*","");
                for(int o = 0;o < search_text.length();o ++){
                    if(search_text.charAt(o) == ' '){
                        JOptionPane.showMessageDialog(null,"输入中包含空格，请重新输入");
                        search_tx.setText("");
                        return;
                    }
                }
                Object[][] result = new Object[200][5];
                int i = 0;
                if(btns[0].isSelected()){
                    for(ExpertGetAllSheet ega:Exper_chunk){
                        if(ega.getExpert().getId().equals(search_text)) {
                            result[i][0] = ega.getExpert().getId();
                            result[i][1] = ega.getExpert().getName();
                            result[i][2] = ega.getExpert().getSex();
                            result[i][3] = ega.getExpert().getPhoneNumber();
                            result[i][4] = ega.getExpert().getCompany();
                            i ++;
                        }
                    }
                }
                else if(btns[1].isSelected()){
                    for(ExpertGetAllSheet ega:Exper_chunk){
                        if(ega.getExpert().getName().equals(search_text)) {
                            result[i][0] = ega.getExpert().getId();
                            result[i][1] = ega.getExpert().getName();
                            result[i][2] = ega.getExpert().getSex();
                            result[i][3] = ega.getExpert().getPhoneNumber();
                            result[i][4] = ega.getExpert().getCompany();
                            i ++;
                        }
                    }
                }
                else if(btns[2].isSelected()){
                    for(ExpertGetAllSheet ega:Exper_chunk){
                        if(ega.getExpert().getPhoneNumber().equals(search_text)) {
                            result[i][0] = ega.getExpert().getId();
                            result[i][1] = ega.getExpert().getName();
                            result[i][2] = ega.getExpert().getSex();
                            result[i][3] = ega.getExpert().getPhoneNumber();
                            result[i][4] = ega.getExpert().getCompany();
                            i ++;
                        }
                    }
                }
                else if(btns[3].isSelected()){
                    for(ExpertGetAllSheet ega:Exper_chunk){
                        if(ega.getExpert().getCompany().equals(Integer.parseInt(search_text))) {
                            result[i][0] = ega.getExpert().getId();
                            result[i][1] = ega.getExpert().getName();
                            result[i][2] = ega.getExpert().getSex();
                            result[i][3] = ega.getExpert().getPhoneNumber();
                            result[i][4] = ega.getExpert().getCompany();
                            i ++;
                        }
                    }
                }
                DefaultTableModel res = new DefaultTableModel();
                for(int k = 0;k < i + 1;k ++){
                    res.addRow(result[k]);
                }
                expertTable.setModel(res);

            }
        });

        Table_man.add(btn_ctner);
        Table_man.add(Filter);
        Table_man.add(init_search);

//        result.add(Table_man);
        this.add(Table_man);

        Box Existing_proj = Box.createVerticalBox();
        Existing_proj.add(new JLabel("项目列表"));
        JTable Proj_table = new JTable();
        DefaultTableModel m2 = (DefaultTableModel) Proj_table.getModel();
        m2.setColumnIdentifiers(ProjectTableTitles);

        for (int i = 0; i < projectInformation.length; i++) {
            m2.addRow(projectInformation[i]);
        }
        Proj_table.setModel(m2);
        Proj_table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        JScrollPane jsp2 = new JScrollPane(Proj_table);
        Existing_proj.add(jsp2);
//        result.add(Existing_proj);
        this.add(Existing_proj);

        JPanel btns2 = new JPanel();
        btns2.setBorder(new TitledBorder("项目操作"));
        btns2.setLayout(new GridLayout(3, 1));
        JButton update = new JButton("刷新专家与项目表单");
        update.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultTableModel exp_model = new DefaultTableModel();
                DefaultTableModel proj_model = new DefaultTableModel();
                get_table_data();
                for (int i = 0; i < projectInformation.length; i++) {
                    exp_model.addRow(projectInformation[i]);
                }
                for (Object[] exp : expertInformation) {
                    proj_model.addRow(exp);
                }
                expertTable.setModel(exp_model);
                Proj_table.setModel(proj_model);
                JOptionPane.showMessageDialog(null,"刷新成功");
            }
        });
        btns2.add(update);

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
                        for(Project proj:Proj_chunk) {
                            if(proj.getId().equals((Integer) model.getValueAt(selected_r,0))) {

                            }
                        }
                        int response = JOptionPane.showConfirmDialog(null, jsp_out, "补充抽取", JOptionPane.YES_NO_OPTION);
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
//                Project p = new Project(p_model.getValueAt(selrow,0).toString(),p_model.getValueAt(selrow,1).toString(),Long.parseLong((String)p_model.getValueAt(selrow,2)),p_model.getValueAt(selrow,3).toString(),p_model.getValueAt(selrow,4).toString(),p_model.getValueAt(selrow,5).toString(),p_model.getValueAt(selrow,6).toString());

                for(ProjectGetAllSheet pr:Proj_chunk){
                    if(pr.getProject().getId().equals((Integer)p_model.getValueAt(selrow,0))){
                        p = pr.getProject();
                        break;
                    }
                }
                try{
                    Command get_pro_exp = new Command("requestprojexpert",Collections.singletonList(p.getId().toString()));
                    comOut.write(get_pro_exp.serialize());
                }
                catch(IOException ioe){
                    System.out.println(ioe.fillInStackTrace());
                }

                try{
                    Command exps = Command.getOneCommandFromInputStream(comIn);
                    if(exps.getCmd().equals("Object")){
                        String exps_str = String.join(" ",exps.getArgs());
                        score_exps = JSON.parseArray(exps_str,Invites.class);

                    }
                }
                catch (InvalidCommandFormatException icfe){
                    System.out.println(icfe.fillInStackTrace());
                }

                JTextField[] score_input = new JTextField[score_exps.size()];//用于对于每一个专家打分
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
                for (int i = 0; i < score_exps.size(); i++) {
                    Box bx = Box.createHorizontalBox();
                    bx.add(new JLabel(score_exps.get(i).getExpert().getName()));
                    bx.add(new JLabel(score_exps.get(i).getExpert().getSex().toString()));
                    bx.add(new JLabel(score_exps.get(i).getExpert().getPhoneNumber()));
                    score_input[i] = new JTextField();
                    bx.add(score_input[i]);
                    out_panel.add(bx);
                }
                int result = JOptionPane.showConfirmDialog(null, out_panel, "项目人员打分", JOptionPane.OK_CANCEL_OPTION);
                if (result == 0) {
                    try{
                        List<String> score_sub = new ArrayList<>(10);
                        for(int k = 0 ;k < score_exps.size();k ++){
                            score_exps.get(k).setScore(Integer.parseInt(score_input[k].getText()));
                            score_sub.add(score_exps.get(k).getExpert().getId() + "/" + score_exps.get(k).getScore());
                        }
                        Command submit_grades = new Command("Submit",score_sub);
                        comOut.write(submit_grades.serialize());
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
//        result.add(btns2);
        this.add(btns2);
//        return result;
//        super(result);
    }
    public void get_table_data(){
        //向后段索取需要的专家数据
        List<String> clargs  = new ArrayList<>();
        clargs.add("all");
        Command require_all_exp = new Command("require_exp",clargs);
        try {
            comOut.write(require_all_exp.serialize());
        }
        catch(IOException ioe){
            System.out.println(ioe.fillInStackTrace());
        }
        try {
            Command js = Command.getOneCommandFromInputStream(comIn);
            if(js.getCmd().equals("Object")){
                String processed = String.join(" ", js.getArgs());
                Exper_chunk = JSON.parseArray(processed,ExpertGetAllSheet.class);
                expertInformation = new Object[Exper_chunk.size()][5];
                for(int i = 0;i < Exper_chunk.size();i ++){
                    expertInformation[i][0] = Exper_chunk.get(i).getExpert().getId();
                    expertInformation[i][1] = Exper_chunk.get(i).getExpert().getName();
                    expertInformation[i][2] = Exper_chunk.get(i).getExpert().getSex();
                    expertInformation[i][3] = Exper_chunk.get(i).getExpert().getPhoneNumber();
                    expertInformation[i][4] = Exper_chunk.get(i).getExpert().getCompany();
                }
            }
            else{
                System.out.println("接收错误");
            }
        }
        catch(InvalidCommandFormatException icfe){
            System.out.println(icfe.fillInStackTrace());
        }

        //导入数据 倒入项目信息
        List<String> poargs  = new ArrayList<String>();
        poargs.add("all");
        Command require_all_proj = new Command("require_proj",poargs);
        try {
            comOut.write(require_all_proj.serialize());
        }
        catch(IOException ioe){
            System.out.println(ioe.fillInStackTrace());
        }
        try {
            Command pjs = Command.getOneCommandFromInputStream(comIn);
            if(pjs.getCmd().equals("Object")){
                String processed = String.join(" ", pjs.getArgs());
                Proj_chunk = JSON.parseArray(processed,ProjectGetAllSheet.class);
                projectInformation = new Object[Proj_chunk.size()][6];
                for(int i = 0;i < Proj_chunk.size();i ++){
                    projectInformation[i][0] = Proj_chunk.get(i).getProject().getId();
                    projectInformation[i][1] = Proj_chunk.get(i).getProject().getName();
                    projectInformation[i][2] = Proj_chunk.get(i).getProject().getAmount();
                    projectInformation[i][3] = Proj_chunk.get(i).getProject().getBiddingType();
                    projectInformation[i][4] = Proj_chunk.get(i).getProject().getBiddingMethod();
                    projectInformation[i][5] = Proj_chunk.get(i).getProject().getIndustryType();
                }
            }
            else{
                System.out.println("接收错误");
            }
        }
        catch(InvalidCommandFormatException icfe){
            System.out.println(icfe.fillInStackTrace());
        }

    }
}
