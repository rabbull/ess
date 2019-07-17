import com.alibaba.fastjson.JSON;
import command.Command;
import command.exceptions.InvalidCommandFormatException;
import models.entities.Expert;
import models.entities.Profession;
import models.relations.Invites;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class conditions extends JPanel {
    private  String[] selection_condition_inputs = new String[5];

    private  String Biding_type;

    private  String Biding_method;

    private  String industry_type;

    private  String organ_type;

    private  Date start_date;

    private  Date end_date;

    private  JComboBox<String> maj_1_main;

    private  JComboBox<String> maj_2_main;

    private  JComboBox<String> maj_3_main;

    private  JComboBox<String> maj_1_aux;

    private  JComboBox<String> maj_2_aux;

    private  JComboBox<String> maj_3_aux;

    private  JTextField total_personel;

    private  JLabel total_per_lable;

    private  List<Profession> pro_chunks;

    private List<String> company_subs;

    private  String[] maj_1_m = new String[100];

    private  String[] maj_2_m = new String[100];

    private  String[] maj_3_m = new String[100];

    private  String[] maj_2_a = new String[100];

    private  String[] maj_3_a = new String[100];

    private  List<Profession> maj_2 = new ArrayList<>();

    private  List<Profession> maj_3 = new ArrayList<>();

    private  Expert[] exp_selected;

    private  ArrayList<String> aux_pros;

    private  ArrayList<String> main_pros;

    private Integer left_experts;

    private DataInputStream comIn;

    private DataOutputStream comOut;

    public conditions(String[] selection_condition_inputs, String biding_type, String biding_method, String industry_type, String organ_type, Date biding_time_start_inputs, Date biding_time_end_inputs, List<String> company_subs,Integer left_experts) {
        this.selection_condition_inputs = selection_condition_inputs;
        Biding_type = biding_type;
        Biding_method = biding_method;
        this.industry_type = industry_type;
        this.organ_type = organ_type;
        this.start_date = biding_time_start_inputs;
        this.start_date = biding_time_end_inputs;
        this.company_subs = company_subs;
        this.left_experts = left_experts;
//那么到底传进来的是不是引用呢

//        JPanel maj_selecting = new JPanel();
//        maj_selecting.setLayout(new GridLayout(2, 1));
//        maj_selecting.setBorder(new TitledBorder("专家信息填写"));
        this.setLayout(new GridLayout(2, 1));
        this.setBorder(new TitledBorder("专家信息填写"));
        JPanel maj_inputs = new JPanel();
        maj_inputs.setLayout(new GridLayout(5, 1));
//--------------------------------------------------------------------------------
        try{
            Command get_pros = new Command("Get_professions", Collections.singletonList("all"));
            comOut.write(get_pros.serialize());
        }
        catch (IOException ioe){
            System.out.println(ioe.getMessage());
        }
        try{
            Command pros = Command.getOneCommandFromInputStream(comIn);
            if(pros.getCmd().equals("Object")) {
                String pros_str = String.join(" ",pros.getArgs());
                pro_chunks = JSON.parseArray(pros_str, Profession.class);
            }
        }
        catch (InvalidCommandFormatException icfe){
            System.out.println(icfe.getMessage());
        }
//--------------------------------------------------------------------------------
        Box pro_1_box = Box.createHorizontalBox();
        pro_1_box.add(new JLabel("拟抽取专业"));
        java.util.List<Profession> maj_1 = new ArrayList<>(20);
        for(Profession m:pro_chunks){
            if(m.getFather() == null){
                maj_1.add(m);
            }
        }
        maj_1_m = new String[maj_1.size()];
        for(int l = 0;l < maj_1.size();l ++){
            maj_1_m[l] = maj_1.get(l).getCode() + "/" + maj_1.get(l).getName();
        }
        maj_1_main = new JComboBox<>(maj_1_m);
        maj_1_main.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (!maj_1_main.getSelectedItem().equals("全部")) {

                    Profession selected = (Profession) maj_1_main.getSelectedItem();
                    for(Profession pros:pro_chunks){
                        if(pros.getFather().equals(selected)){
                            maj_2.add(pros);
                        }
                    }
                    for(int r = 0; r < maj_2.size();r ++){
                        maj_2_m[r] = maj_2.get(r).getCode() + "/" + maj_2.get(r).getName();
                    }
                    maj_2_main.setVisible(true);

                } else {
                    maj_2_main.setVisible(false);
                    maj_3_main.setVisible(false);
                }
            }
        });
        pro_1_box.add(maj_1_main);

        maj_2_main = new JComboBox<>(maj_2_m);
        maj_2_main.setVisible(false);
        maj_2_main.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (maj_2_main.getSelectedItem().equals("全部")) {
                    Profession selected = (Profession) maj_2_main.getSelectedItem();
                    for(Profession pros:pro_chunks){
                        if(pros.getFather().equals(selected)){
                            maj_3.add(pros);
                        }
                    }
                    for(int r = 0; r < maj_3.size();r ++){
                        maj_3_m[r] = maj_3.get(r).getCode() + "/" + maj_3.get(r).getName();
                    }
                    maj_3_main.setVisible(true);
                } else {
                    maj_3_main.setVisible(false);
                }
            }
        });
        pro_1_box.add(maj_2_main);
        maj_3_main = new JComboBox<>(maj_3_m);
        maj_3_main.setVisible(false);
        pro_1_box.add(maj_3_main);

        Box pro_2_box = Box.createHorizontalBox();
        pro_2_box.add(new JLabel("替补专业"));
//        String[] maj_1 = {"全部","一级专业1","一级专业二","一级专业三"};
        //maj_1.clear();
        maj_2.clear();
        maj_3.clear();

        maj_1_aux = new JComboBox<>(maj_1_m);
        maj_1_aux.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (maj_1_aux.getSelectedItem().equals("全部")) {
                    Profession selected = (Profession) maj_1_aux.getSelectedItem();
                    for(Profession pros:pro_chunks){
                        if(pros.getFather().equals(selected)){
                            maj_2.add(pros);
                        }
                    }
                    for(int r = 0; r < maj_2.size();r ++){
                        maj_2_a[r] = maj_2.get(r).getCode() + maj_2.get(r).getName();
                    }
                    maj_2_aux.setVisible(true);
                } else {
                    maj_2_aux.setVisible(false);
                    maj_3_aux.setVisible(false);
                }
            }
        });
        pro_2_box.add(maj_1_aux);
//        String[] maj_2 = {"全部","2级专业1","2级专业二","2级专业三"};
        maj_2_aux = new JComboBox<>(maj_2_a);
        maj_2_aux.setVisible(false);
        maj_2_aux.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (maj_2_aux.getSelectedItem().equals("全部")) {
                    Profession selected = (Profession) maj_2_aux.getSelectedItem();
                    for(Profession pros:pro_chunks){
                        if(pros.getFather().equals(selected)){
                            maj_3.add(pros);
                        }
                    }
                    for(int r = 0; r < maj_3.size();r ++){
                        maj_3_a[r] = maj_3.get(r).getCode() + "/" + maj_3.get(r).getName();
                    }
                    maj_3_aux.setVisible(true);
                } else {
                    maj_3_aux.setVisible(false);
                }
            }
        });
        pro_2_box.add(maj_2_aux);
//        String[] maj_3 = {"全部","3级专业1","3级专业二","3级专业三"};
        maj_3_aux = new JComboBox<>(maj_3_a);
        maj_3_aux.setVisible(false);
        pro_2_box.add(maj_3_aux);

        maj_inputs.add(pro_1_box);
        maj_inputs.add(pro_2_box);

        Box capabilities = Box.createHorizontalBox();
        ButtonGroup bg = new ButtonGroup();
        JRadioButton no_need = new JRadioButton("不需要");
        JRadioButton need = new JRadioButton("需要");
        bg.add(no_need);
        bg.add(need);
        capabilities.add(new JLabel("是否需要电子评标能力:"));
        capabilities.add(no_need);
        capabilities.add(need);
        maj_inputs.add(capabilities);

        total_personel = new JTextField();
        Box total_box = Box.createHorizontalBox();
        total_box.setPreferredSize(new Dimension(150, 0));
        total_box.add(new JLabel("该条件拟抽取专家数"));
        total_box.add(total_personel);
        total_per_lable = new JLabel("还需要" + left_experts.toString());
//        if(isRerolling) total_per_lable.setText("还需要" + (p.getNumExpertExpected() - p.getNumExpertReal()) + "人");
        total_box.add(total_per_lable);
        maj_inputs.add(total_box);

        JTable conditions = new JTable();
        DefaultTableModel cond_m = (DefaultTableModel) conditions.getModel();
        Object[] c_ids = {"评标专业", "专业等级", "人数"};
        cond_m.setColumnIdentifiers(c_ids);
        cond_m.setRowCount(0);
        conditions.setModel(cond_m);
        conditions.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        conditions.getColumn("评标专业").setPreferredWidth(300);
        conditions.getColumn("专业等级").setPreferredWidth(300);
        conditions.getColumn("人数").setPreferredWidth(100);


        Box final_btns = Box.createHorizontalBox();
        JScrollPane jsp_cond = new JScrollPane(conditions);



        JButton save = new JButton("保存已经填写的信息");
        JButton init = new JButton("开始抽签");
        init.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JPanel out_panel = new JPanel();
                out_panel.setBorder(new TitledBorder("抽签信息"));
                out_panel.setPreferredSize(new Dimension(400, 400));
                out_panel.setLayout(new GridLayout(2, 1));
                TextArea Rolling_process = new TextArea();
                Rolling_process.append("（抽签信息）");
                out_panel.add(Rolling_process);

//                String[][] exp_selected = {{"王八", "四川大学", "17883430983"}, {"小土豆", "贵州大学", "14629382734"}};
                try{
//                    java.util.List<String> company_sub  = new ArrayList<>(30);
                    Sheet_selection subs;
//                    DefaultTableModel mkout = (DefaultTableModel) company_table.getModel();
//                    for(int t = 0;t < company_table.getRowCount();t ++){
//                        String str_out = "Avoid_company" + "/" + (String)mkout.getValueAt(t,0) + "/" + (String)mkout.getValueAt(t,1);
//                        company_sub.add(str_out);
//                    }
//                    Date starting_date = new Date(Integer.parseInt(biding_time_start_inputs[0].getText()),Integer.parseInt(biding_time_start_inputs[1].getText()),Integer.parseInt(biding_time_start_inputs[2].getText()),Integer.parseInt(biding_time_start_inputs[3].getText()),Integer.parseInt(biding_time_start_inputs[4].getText()),0);
//                    Date ending_date = new Date(Integer.parseInt(biding_time_end_inputs[0].getText()),Integer.parseInt(biding_time_end_inputs[1].getText()),Integer.parseInt(biding_time_end_inputs[2].getText()),Integer.parseInt(biding_time_end_inputs[3].getText()),Integer.parseInt(biding_time_end_inputs[4].getText()),0);
                    subs = new Sheet_selection(selection_condition_inputs[0].replaceAll("\\s*",""),
                            Long.parseLong(selection_condition_inputs[1].replaceAll("\\s*","")),
                            selection_condition_inputs[2].replaceAll("\\s*",""),
                            Long.parseLong(selection_condition_inputs[3].replaceAll("\\s*","")),
                            selection_condition_inputs[4].replaceAll("\\s*",""),
                            (String)Biding_type,
                            (String)Biding_method,
                            (String)industry_type,
                            (String)organ_type,
                            start_date,end_date,company_subs,main_pros,aux_pros);
                    Command Roll = new Command("submit",Collections.singletonList(subs.toString()));
                    comOut.write(Roll.serialize());
                }
                catch(IOException ioe){
                    System.out.println(ioe.getMessage());
                }
                try{
                    Command roll_res = Command.getOneCommandFromInputStream(comIn);
                    if(roll_res.getCmd().equals("Object")) {
                        String res_str = String.join(" ",roll_res.getArgs());
                        java.util.List<Invites> exp_res = JSON.parseArray(res_str,Invites.class);
                        exp_selected = new Expert[exp_res.size()];
                        for(int o = 0;o < exp_selected.length;o ++){
                            exp_selected[o] = exp_res.get(o).getExpert();
                        }
                    }
                }
                catch(InvalidCommandFormatException icfe){
                    System.out.println(icfe.getMessage());
                }


                JCheckBox[] exp_checks = new JCheckBox[exp_selected.length];
                JTextField[] exp_reason = new JTextField[exp_selected.length];
                JPanel exps = new JPanel();
                exps.setLayout(new GridLayout(exp_selected.length + 1, 1));
                exps.setPreferredSize(new Dimension(600, 35 * (exp_selected.length + 1)));
                JScrollPane jsp_exps = new JScrollPane(exps);
                jsp_exps.setBorder(new TitledBorder("抽取专家结果"));
                JPanel ids = new JPanel();
                ids.setLayout(new GridLayout(1, 5));
                ids.add(new JLabel(""));
                ids.add(new JLabel("姓名"));
                ids.add(new JLabel("单位"));
                ids.add(new JLabel("联系方式"));
                exps.add(ids);
                for (int i = 0; i < exp_selected.length; i++) {
                    JPanel temp = new JPanel();
                    temp.setLayout(new GridLayout(1, 5));
                    exp_checks[i] = new JCheckBox();
                    exp_checks[i].setVisible(false);
                    exp_reason[i] = new JTextField();
                    exp_reason[i].setVisible(false);
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
                    temp.add(new JLabel(exp_selected[i].getName()));
                    temp.add(new JLabel(exp_selected[i].getSex().toString()));
                    temp.add(new JLabel(exp_selected[i].getPhoneNumber()));
                    temp.add(exp_reason[i]);
                    temp.setBorder(new EtchedBorder());
                    exps.add(temp);
                }
                out_panel.add(jsp_exps);


                JOptionPane.showMessageDialog(null, out_panel);

            }
        });
        JButton add_new_exps = new JButton("添加条件");
        add_new_exps.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(maj_1_main.getSelectedItem().equals(null) || maj_1_aux.getSelectedItem().equals(null)  || total_personel.getText().equals("")){
                    JOptionPane.showMessageDialog(null,"信息尚未完整");
                    return;
                }
                DefaultTableModel m = (DefaultTableModel) conditions.getModel();
                java.util.List<Object> out_main = new ArrayList<Object>();

                out_main.add(maj_1_main.getSelectedItem());

                out_main.add(maj_2_main.getSelectedItem());

                out_main.add(maj_3_main.getSelectedItem());
                Object[] new_row = new Object[3];
                if(out_main.get(0).equals("全部")){
                    new_row[0] = out_main.get(0);
                    new_row[1] = 1;
                    main_pros.add(((String)out_main.get(0)).split("/")[0]);
                }
                else if(out_main.get(1).equals("全部")){
                    new_row[0] = out_main.get(1);
                    new_row[1] = 2;
                    main_pros.add(((String)out_main.get(1)).split("/")[0]);
                }
                else {
                    new_row[0] = out_main.get(2);
                    new_row[1] = 3;
                    main_pros.add(((String) out_main.get(2)).split("/")[0]);
                }
                new_row[2] = total_personel.getText();

                List<String> out_aux = new ArrayList<>();
                out_aux.add((String) maj_1_aux.getSelectedItem());
                out_aux.add((String) maj_2_aux.getSelectedItem());
                out_aux.add((String) maj_3_aux.getSelectedItem());
                if(out_aux.get(0).equals("全部")){
                    aux_pros.add(out_aux.get(0).split("/")[0]);
                }
                else if(out_aux.get(1).equals("全部")){
                    aux_pros.add(out_aux.get(1).split("/")[0]);
                }
                else {
                    aux_pros.add(out_aux.get(2).split("/")[0]);
                }

                m.addRow(new_row);
                conditions.setModel(m);
            }
        });
        JButton del_exps = new JButton("删除条件");
        del_exps.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultTableModel m = (DefaultTableModel) conditions.getModel();
                int selrow = conditions.getSelectedRow();
                if(selrow == -1){
                    JOptionPane.showMessageDialog(null,"暂未选择任何行");
                }
                m.removeRow(selrow);
                aux_pros.remove(selrow);
                main_pros.remove(selrow);
            }
        });
        final_btns.add(add_new_exps);
        final_btns.add(del_exps);
        final_btns.add(save);
        final_btns.add(init);
        maj_inputs.add(final_btns);

//        maj_selecting.add(maj_inputs);
//        maj_selecting.add(jsp_cond);
        this.add(maj_inputs);
        this.add(jsp_cond);
    }
}

