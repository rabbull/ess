import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.net.Socket;
import java.util.*;
import java.util.List;

import com.alibaba.fastjson.JSON;
import command.Command;
import command.exceptions.InvalidCommandFormatException;
import models.entities.Expert;
import models.entities.Profession;
import models.entities.Project;
import models.relations.Invites;


public class SelectingExp extends JPanel{
    //抽签需要填写的信息
    private  String[] s_conditions = {"招标项目名称", "招标金额（单位：万元）", "评标地点", "评标时段", "招标人名称"};

    private  JTextField[] selection_condition_inputs = new JTextField[5];

    private  JComboBox<String> Biding_type;

    private  JComboBox<String> Biding_method;

    private  JComboBox<String> industry_type;

    private  JComboBox<String> organ_type;

    private  String[] Biding_options = {"招标类型——1", "招标类型——2", "招标类型——3", "招标类型——4"};

    private  String[] method_options = {"招标方式——1", "招标方式——2", "招标方式——3", "招标方式——4",};

    private  String[] industry_options = {"行业类型1", "行业类型2", "行业类型3"};

    private  String[] organ_options = {"组织形式1", "组织形式2", "组织形式3"};

    private  JTextField[] biding_time_start_inputs = new JTextField[5];

    private  JTextField[] biding_time_end_inputs = new JTextField[5];

    private  JTextField avoid_company_name, avoid_reason;

    private  Object[] ids = {"公司名称", "回避原因"};

    private Socket comIn;

    private Socket comOut;

//    private  JComboBox<String> maj_1_main;
//
//    private  JComboBox<String> maj_2_main;
//
//    private  JComboBox<String> maj_3_main;
//
//    private  JComboBox<String> maj_1_aux;
//
//    private  JComboBox<String> maj_2_aux;
//
//    private  JComboBox<String> maj_3_aux;
//
//    private  JTextField total_personel;
//
//    private  JLabel total_per_lable;
//
//    private  List<Profession> pro_chunks;
//
//    private  String[] maj_1_m = new String[100];
//
//    private  String[] maj_2_m = new String[100];
//
//    private  String[] maj_3_m = new String[100];
//
//    private  String[] maj_2_a = new String[100];
//
//    private  String[] maj_3_a = new String[100];
//
//    private  List<Profession> maj_2 = new ArrayList<>();
//
//    private  List<Profession> maj_3 = new ArrayList<>();
//
//    private  Expert[] exp_selected;
//
//    private  ArrayList<String> aux_pros;
//
//    private  ArrayList<String> main_pros;

    Integer left_exps = new Integer(0);

    public  SelectingExp() {
        try{
            Command get_method_options = new Command("requestall BiddingMethod");
            SwingNovice.comOut.write(get_method_options.serialize());
        }
        catch(IOException ioe){
            System.out.println(ioe.getMessage());
        }

        try{
            Command methods = Command.getOneCommandFromInputStream(SwingNovice.comIn);
            if(methods.getCmd().equals("Object")){
                method_options = methods.getArgs();
                //只需要一些字符串怎么办？？
            }
        }
        catch (InvalidCommandFormatException e){
            System.out.println(e.getMessage());
        }
//---------------------------------------------------------
        try{
            Command get_biding_options = new Command("requestall BiddingType");
            SwingNovice.comOut.write(get_biding_options.serialize());
        }
        catch(IOException ioe){
            System.out.println(ioe.getMessage());
        }
        try{
            Command bidings = Command.getOneCommandFromInputStream(SwingNovice.comIn);
            if(bidings.getCmd().equals("Object")){
                Biding_options = bidings.getArgs();
                //只需要一些字符串怎么办？？
            }
        }
        catch (InvalidCommandFormatException e){
            System.out.println(e.getMessage());
        }
//----------------------------------------------------------------------------------
        try{
            Command get_industry_options = new Command("requestall IndustryType");
            SwingNovice.comOut.write(get_industry_options.serialize());
        }
        catch(IOException ioe){
            System.out.println(ioe.getMessage());
        }
        try{
            Command industrys = Command.getOneCommandFromInputStream(SwingNovice.comIn);
            if(industrys.getCmd().equals("Object")){
                industry_options = industrys.getArgs();
                //只需要一些字符串怎么办？？
            }
        }
        catch (InvalidCommandFormatException e){
            System.out.println(e.getMessage());
        }

        //-----------------------------------------------------------------------

        try{
            Command get_organ_options = new Command("requestall OrgType");
            SwingNovice.comOut.write(get_organ_options.serialize());
        }
        catch(IOException ioe){
            System.out.println(ioe.getMessage());
        }
        try{
            Command organs = Command.getOneCommandFromInputStream(SwingNovice.comIn);
            if(organs.getCmd().equals("Object")){
                organ_options = organs.getArgs();
                //只需要一些字符串怎么办？？
            }
        }
        catch (InvalidCommandFormatException e){
            System.out.println(e.getMessage());
        }


//-------------------------------------------------------------------------------------



//        JPanel result = new JPanel(false);
//        result.setLayout(new GridLayout(3, 1));
//        result.setPreferredSize(new Dimension(700, 1200));
        this.setLayout(new GridLayout(3, 1));
        this.setPreferredSize(new Dimension(700, 1200));
//        JScrollPane jsp_outtermost = new JScrollPane(result);
        JPanel outter_hori_box = new JPanel();
        outter_hori_box.setBorder(new TitledBorder("基本信息填写"));
        outter_hori_box.setLayout(new GridLayout(12, 1));
        outter_hori_box.setPreferredSize(new Dimension(700, 50 * 12));
        JScrollPane jsp_2 = new JScrollPane(outter_hori_box);


        Box[] wrappers = new Box[s_conditions.length];
//        Box selection_conditions = Box.createVerticalBox();
//        selection_conditions.setPreferredSize(new Dimension(200, 300));
//        Box inputwrapper = Box.createVerticalBox();
        for (int i = 0; i < s_conditions.length; i++) {
            wrappers[i] = Box.createHorizontalBox();
            JLabel temp = new JLabel(s_conditions[i]);
            selection_condition_inputs[i] = new JTextField();
            wrappers[i].add(temp);
            wrappers[i].add(selection_condition_inputs[i]);
            outter_hori_box.add(wrappers[i]);
        }

        Box person_box = Box.createHorizontalBox();
        person_box.add(new JLabel("项目计划抽取专家人数"));
        JTextField person_input = new JTextField();
        person_input.addCaretListener(new CaretListener() {
            @Override
            public void caretUpdate(CaretEvent e) {
                try {
                    int number = Integer.parseInt(person_input.getText());
                    left_exps = number;
                } catch (NumberFormatException nfe) {
                    JOptionPane.showMessageDialog(null, "输入人数格式错误");
                }
            }
        });
        person_box.add(person_input);
        outter_hori_box.add(person_box);


        Box bid_1 = Box.createHorizontalBox();
        bid_1.add(new JLabel("    招标类型"));
        Biding_type = new JComboBox<>(Biding_options);
        bid_1.add(Biding_type);
        outter_hori_box.add(bid_1);

        Box bid_2 = Box.createHorizontalBox();
        bid_2.add(new JLabel("    招标方式"));
        Biding_method = new JComboBox<>(method_options);
        bid_2.add(Biding_method);
        outter_hori_box.add(bid_2);

        Box bid_3 = Box.createHorizontalBox();
        bid_3.add(new JLabel("    行业类型"));
        industry_type = new JComboBox<>(industry_options);
        bid_3.add(industry_type);
        outter_hori_box.add(bid_3);

        Box bid_4 = Box.createHorizontalBox();
        bid_4.add(new JLabel("  招标组织形式"));
        organ_type = new JComboBox<>(organ_options);
        bid_4.add(organ_type);
        outter_hori_box.add(bid_4);

        JPanel biding_time_start = new JPanel();
        biding_time_start.setLayout(new GridLayout(1, 10));
        biding_time_start.setBorder(new TitledBorder("评标开始时间"));
        for (int i = 0; i < 5; i++) {
            biding_time_start_inputs[i] = new JTextField();
        }
        biding_time_start.add(biding_time_start_inputs[0]);
        biding_time_start.add(new JLabel("年"));
        biding_time_start.add(biding_time_start_inputs[1]);
        biding_time_start.add(new JLabel("月"));
        biding_time_start.add(biding_time_start_inputs[2]);
        biding_time_start.add(new JLabel("日"));
        biding_time_start.add(biding_time_start_inputs[3]);
        biding_time_start.add(new JLabel("时"));
        biding_time_start.add(biding_time_start_inputs[4]);
        biding_time_start.add(new JLabel("分"));

        outter_hori_box.add(biding_time_start);

        JPanel biding_time_end = new JPanel();
        biding_time_end.setLayout(new GridLayout(1, 10));
        biding_time_end.setBorder(new TitledBorder("评标结束时间"));
        for (int i = 0; i < 5; i++) {
            biding_time_end_inputs[i] = new JTextField();
        }
        biding_time_end.add(biding_time_end_inputs[0]);
        biding_time_end.add(new JLabel("年"));
        biding_time_end.add(biding_time_end_inputs[1]);
        biding_time_end.add(new JLabel("月"));
        biding_time_end.add(biding_time_end_inputs[2]);
        biding_time_end.add(new JLabel("日"));
        biding_time_end.add(biding_time_end_inputs[3]);
        biding_time_end.add(new JLabel("时"));
        biding_time_end.add(biding_time_end_inputs[4]);
        biding_time_end.add(new JLabel("分"));

        outter_hori_box.add(biding_time_end);

//        result.add(jsp_2);
        this.add(jsp_2);

        JPanel avoid_comp_selection = new JPanel();
        avoid_comp_selection.setBorder(new TitledBorder("回避公司选择"));
        avoid_comp_selection.setLayout(new GridLayout(2, 1));
        JPanel setup = new JPanel();

        JTable company_table = new JTable();
//        company_table.setBorder(new TitledBorder("已经选择的公司"));
        DefaultTableModel model = (DefaultTableModel) company_table.getModel();
        model.setColumnIdentifiers(ids);
        model.setRowCount(0);
        company_table.setModel(model);
        company_table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        company_table.setRowHeight(35);
        company_table.getColumn("公司名称").setPreferredWidth(200);
        company_table.getColumn("回避原因").setPreferredWidth(500);
        JScrollPane jsp_com_table = new JScrollPane(company_table);

        setup.setLayout(new GridLayout(3, 1));
        Box com_name_box = Box.createHorizontalBox();
        com_name_box.add(new JLabel("公司名称"));
        avoid_company_name = new JTextField();
        com_name_box.add(avoid_company_name);
        setup.add(com_name_box);

        Box com_reason_box = Box.createHorizontalBox();
        com_reason_box.add(new JLabel("回避原因"));
        avoid_reason = new JTextField();
        com_reason_box.add(avoid_reason);
        setup.add(com_reason_box);

        Box btns = Box.createHorizontalBox();
        JButton add_comp = new JButton("添加新的规避公司");
        add_comp.setPreferredSize(new Dimension(150, 50));
        add_comp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (avoid_reason.getText().equals("") || avoid_reason.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "信息尚未完整请重新填写");
                    return;
                }
                DefaultTableModel m = (DefaultTableModel) company_table.getModel();
                Object[] row2bein = {avoid_company_name.getText(), avoid_reason.getText()};
                m.addRow(row2bein);
                company_table.setModel(m);
                avoid_company_name.setText("");
                avoid_reason.setText("");
                JOptionPane.showMessageDialog(null, "添加成功");
            }
        });
        JButton deleteCompany = new JButton("删除选中的公司");
        deleteCompany.setPreferredSize(new Dimension(150, 50));
        deleteCompany.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultTableModel model = (DefaultTableModel) company_table.getModel();    //获得表格模型
                int selectedRow = company_table.getSelectedRow();
                StringBuilder info = new StringBuilder();

                info.append("当前选中的纪录如下：\n\n");

                //有数据库之后从数据库获取完整的信息
                for (int i = 0; i < ids.length; i++) {
                    info.append(ids[i]);
                    info.append(":");
                    info.append(model.getValueAt(selectedRow, i));
                    info.append("\n");
                }
                info.append("确认要移除这一条纪录么？");
                int response = JOptionPane.showConfirmDialog(null, info.toString(), "详细信息", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (response == 0) {
                    model.removeRow(selectedRow);
                    company_table.setModel(model);
                    JOptionPane.showMessageDialog(null, "删除成功", "消息", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        btns.add(add_comp);
        btns.add(deleteCompany);

        setup.add(btns);

        avoid_comp_selection.add(setup);
        avoid_comp_selection.add(jsp_com_table);

//        result.add(avoid_comp_selection);
        this. add(avoid_comp_selection);

        List<String> company_sub  = new ArrayList<>(30);
        DefaultTableModel mkout = (DefaultTableModel) company_table.getModel();
        for(int t = 0;t < company_table.getRowCount();t ++){
            String str_out = "Avoid_company" + "/" + (String)mkout.getValueAt(t,0) + "/" + (String)mkout.getValueAt(t,1);
            company_sub.add(str_out);
        }
        Date starting_date = new Date(Integer.parseInt(biding_time_start_inputs[0].getText()),Integer.parseInt(biding_time_start_inputs[1].getText()),Integer.parseInt(biding_time_start_inputs[2].getText()),Integer.parseInt(biding_time_start_inputs[3].getText()),Integer.parseInt(biding_time_start_inputs[4].getText()),0);
        Date ending_date = new Date(Integer.parseInt(biding_time_end_inputs[0].getText()),Integer.parseInt(biding_time_end_inputs[1].getText()),Integer.parseInt(biding_time_end_inputs[2].getText()),Integer.parseInt(biding_time_end_inputs[3].getText()),Integer.parseInt(biding_time_end_inputs[4].getText()),0);

        String[] s_inputs = new String[5];
        for(int g = 0;g < s_inputs.length;g ++){
            s_inputs[g] = selection_condition_inputs[g].getText();
        }

        this.add(new conditions(s_inputs,
                (String)Biding_type.getSelectedItem(),
                (String)Biding_method.getSelectedItem(),
                (String)industry_type.getSelectedItem(),
                (String)organ_type.getSelectedItem(),
                starting_date,
                ending_date,
                company_sub,
                left_exps));
    }
}