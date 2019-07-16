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
import command.Command;


public class SelectingExp {
    //抽签需要填写的信息
    public static String[] s_conditions = {"招标项目名称", "招标金额（单位：万元）", "评标地点", "评标时段", "招标人名称"};

    public static JTextField[] selection_condition_inputs = new JTextField[s_conditions.length];

    public static JComboBox<String> Biding_type;

    public static JComboBox<String> Biding_method;

    public static JComboBox<String> industry_type;

    public static JComboBox<String> organ_type;

    public static String[] Biding_options = {"招标类型——1", "招标类型——2", "招标类型——3", "招标类型——4"};

    public static String[] method_options = {"招标方式——1", "招标方式——2", "招标方式——3", "招标方式——4",};

    public static String[] industry_options = {"行业类型1", "行业类型2", "行业类型3"};

    public static String[] organ_options = {"组织形式1", "组织形式2", "组织形式3"};

    public static JTextField[] biding_time_start_inputs = new JTextField[5];

    public static JTextField[] biding_time_end_inputs = new JTextField[5];

    public static JTextField avoid_company_name, avoid_reason;

    public static Object[] ids = {"公司名称", "回避原因"};

    public static JComboBox<String> maj_1_main;

    public static JComboBox<String> maj_2_main;

    public static JComboBox<String> maj_3_main;

    public static JComboBox<String> maj_1_aux;

    public static JComboBox<String> maj_2_aux;

    public static JComboBox<String> maj_3_aux;

    public static JTextField total_personel;

    public static JLabel total_per_lable;

    public static JComponent SelectingExDeliver(boolean isRerolling) {
        JPanel result = new JPanel(false);
        result.setLayout(new GridLayout(3, 1));
        result.setPreferredSize(new Dimension(700, 1200));
        JScrollPane jsp_outtermost = new JScrollPane(result);
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
                    total_per_lable.setText("还需要设置" + number + "人");
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

        result.add(jsp_2);

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

        result.add(avoid_comp_selection);

        JPanel maj_selecting = new JPanel();
        maj_selecting.setLayout(new GridLayout(2, 1));
        maj_selecting.setBorder(new TitledBorder("专家信息填写"));
        JPanel maj_inputs = new JPanel();
        maj_inputs.setLayout(new GridLayout(5, 1));

        Box pro_1_box = Box.createHorizontalBox();
        pro_1_box.add(new JLabel("拟抽取专业"));
        String[] maj_1 = {"全部", "一级专业1", "一级专业二", "一级专业三"};
        maj_1_main = new JComboBox<>(maj_1);
        maj_1_main.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (maj_1_main.getSelectedIndex() != 0) {
                    maj_2_main.setVisible(true);
                } else {
                    maj_2_main.setVisible(false);
                    maj_3_main.setVisible(false);
                }
            }
        });
        pro_1_box.add(maj_1_main);
        String[] maj_2 = {"全部", "2级专业1", "2级专业二", "2级专业三"};
        maj_2_main = new JComboBox<>(maj_2);
        maj_2_main.setVisible(false);
        maj_2_main.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (maj_2_main.getSelectedIndex() != 0) {
                    maj_3_main.setVisible(true);
                } else {
                    maj_3_main.setVisible(false);
                }
            }
        });
        pro_1_box.add(maj_2_main);
        String[] maj_3 = {"全部", "3级专业1", "3级专业二", "3级专业三"};
        maj_3_main = new JComboBox<>(maj_3);
        maj_3_main.setVisible(false);
        pro_1_box.add(maj_3_main);

        Box pro_2_box = Box.createHorizontalBox();
        pro_2_box.add(new JLabel("替补专业"));
//        String[] maj_1 = {"全部","一级专业1","一级专业二","一级专业三"};
        maj_1_aux = new JComboBox<>(maj_1);
        maj_1_aux.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (maj_1_aux.getSelectedIndex() != 0) {
                    maj_2_aux.setVisible(true);
                } else {
                    maj_2_aux.setVisible(false);
                    maj_3_aux.setVisible(false);
                }
            }
        });
        pro_2_box.add(maj_1_aux);
//        String[] maj_2 = {"全部","2级专业1","2级专业二","2级专业三"};
        maj_2_aux = new JComboBox<>(maj_2);
        maj_2_aux.setVisible(false);
        maj_2_aux.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (maj_2_aux.getSelectedIndex() != 0) {
                    maj_3_aux.setVisible(true);
                } else {
                    maj_3_aux.setVisible(false);
                }
            }
        });
        pro_2_box.add(maj_2_aux);
//        String[] maj_3 = {"全部","3级专业1","3级专业二","3级专业三"};
        maj_3_aux = new JComboBox<>(maj_3);
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
        total_per_lable = new JLabel("尚未指定人数");
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

                String[][] exp_selected = {{"王八", "四川大学", "17883430983"}, {"小土豆", "贵州大学", "14629382734"}};
                JCheckBox[] exp_checks = new JCheckBox[exp_selected.length];
                JTextField[] exp_reason = new JTextField[exp_selected.length];
                JPanel exps = new JPanel();
                exps.setLayout(new GridLayout(exp_selected.length + 1, 1));
                exps.setPreferredSize(new Dimension(600, 35 * (exp_selected.length + 1)));
                JScrollPane jsp_exps = new JScrollPane(exps);
                jsp_exps.setBorder(new TitledBorder("请勾选无法参加评标的专家并输入原因"));
                JPanel ids = new JPanel();
                ids.setLayout(new GridLayout(1, 5));
                ids.add(new JLabel(""));
                ids.add(new JLabel("姓名"));
                ids.add(new JLabel("单位"));
                ids.add(new JLabel("联系方式"));
                ids.add(new JLabel("缺席原因"));
                exps.add(ids);
                for (int i = 0; i < exp_selected.length; i++) {
                    JPanel temp = new JPanel();
                    temp.setLayout(new GridLayout(1, 5));
                    exp_checks[i] = new JCheckBox();
                    exp_reason[i] = new JTextField();
                    exp_reason[i].setEnabled(false);
                    JCheckBox check_temp = exp_checks[i];
                    JTextField reason_temp = exp_reason[i];
                    temp.add(exp_checks[i]);
                    exp_checks[i].addItemListener(new ItemListener() {
                        @Override
                        public void itemStateChanged(ItemEvent e) {
                            if (check_temp.isSelected()) {
                                reason_temp.setEnabled(true);
                            } else {
                                reason_temp.setEnabled(false);
                                reason_temp.setText("");
                            }
                        }
                    });
                    temp.add(new JLabel(exp_selected[i][0]));
                    temp.add(new JLabel(exp_selected[i][1]));
                    temp.add(new JLabel(exp_selected[i][2]));
                    temp.add(exp_reason[i]);
                    temp.setBorder(new EtchedBorder());
                    exps.add(temp);
                }
                out_panel.add(jsp_exps);

                JButton[] bs = new JButton[3];
                bs[0] = new JButton("确定");
                bs[0].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        //
                    }
                });
                bs[1] = new JButton("打印抽签结果");
                bs[1].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        //
                    }
                });
                bs[2] = new JButton("取消");
                bs[2].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        //
                    }
                });
                JOptionPane.showOptionDialog(null, out_panel, "选择", 1, 3, null, bs, bs[0]);

            }
        });
        JButton add_new_exps = new JButton("添加条件");
        JButton del_exps = new JButton("删除条件");
        final_btns.add(add_new_exps);
        final_btns.add(del_exps);
        final_btns.add(save);
        final_btns.add(init);
        maj_inputs.add(final_btns);

        maj_selecting.add(maj_inputs);
        maj_selecting.add(jsp_cond);

        if (isRerolling) {
            return maj_selecting;
        }

        result.add(maj_selecting);

        return jsp_outtermost;
    }
}
