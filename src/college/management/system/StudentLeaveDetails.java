package college.management.system;

import net.proteanit.sql.DbUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

public class StudentLeaveDetails extends JFrame implements ActionListener {

    Choice choiceid;
    JTable table;
    JButton search ,cancel,print;
    StudentLeaveDetails(){
        getContentPane().setBackground(new Color(250,172,206));

        JLabel heading = new JLabel("Search by College ID");
        heading.setBounds(20,20,150,20);
        add(heading);

        choiceid = new Choice();
        choiceid.setBounds(180,20,150,20);
        add(choiceid);

        try {
            Conn c = new Conn();
            ResultSet resultSet = c.statement.executeQuery("select * from student");
            while (resultSet.next()){
                choiceid.add(resultSet.getString("id"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        table = new JTable();
        try{
            Conn c = new Conn();
            ResultSet resultSet = c.statement.executeQuery("select * from studentleave");
            table.setModel(DbUtils.resultSetToTableModel(resultSet));
        }catch (Exception e){
            e.printStackTrace();
        }

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(0,100,900,600);
        add(scrollPane);


        search = new JButton("Search");
        search.setBounds(20,70,80,20);
        search.addActionListener(this);
        add(search);

        print = new JButton("Print");
        print.setBounds(120,70,80,20);
        print.addActionListener(this);
        add(print);

        cancel = new JButton("Cancel");
        cancel.setBounds(220,70,80,20);
        cancel.addActionListener(this);
        add(cancel);


        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(null);
        setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == search){
            String q = "select * from studentleave where id = '"+choiceid.getSelectedItem()+"'";
            try {
                Conn c = new Conn();
                ResultSet resultSet = c.statement.executeQuery(q);
                table.setModel(DbUtils.resultSetToTableModel(resultSet));
            }catch (Exception E){
                E.printStackTrace();
            }
        }else if (e.getSource() == print){
            try {
                table.print();
            }catch (Exception E){
                E.printStackTrace();
            }
        }else {
            setVisible(false);
        }
    }

    public static void main(String[] args) {
        new StudentLeaveDetails();
    }
}
