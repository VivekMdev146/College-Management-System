package college.management.system;

import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.ResultSet;

public class StudentLeave extends JFrame implements ActionListener {
    Choice choiceid, choTime;
    JDateChooser selDate;
    JButton submit, cancel;
    JLabel studentNameLabel;
    StudentLeave(){

        getContentPane().setBackground(new Color(210,232,252));

        JLabel heading = new JLabel("Apply Leave (Student)");
        heading.setBounds(40,50,300,30);
        heading.setFont(new Font("Tahoma", Font.BOLD,20));
        add(heading);

        JLabel idSE = new JLabel("Search by Roll Number");
        idSE.setBounds(60,100,200,20);
        idSE.setFont(new Font("Tahoma", Font.PLAIN,18));
        add(idSE);

        choiceid = new Choice();
        choiceid.setBounds(60,130,200,20);
        add(choiceid);

        // Label to display student name
        studentNameLabel = new JLabel("Student Name: ");
        studentNameLabel.setBounds(300,130,300,20);
        studentNameLabel.setFont(new Font("Tahoma", Font.PLAIN,16));
        add(studentNameLabel);

        try{
            Conn c = new Conn();
            ResultSet resultSet = c.statement.executeQuery("select * from student");
            while (resultSet.next()){
                choiceid.add(resultSet.getString("id"));
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        // Add item listener to display name when ID is selected
        choiceid.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                try {
                    Conn c = new Conn();
                    String selectedId = choiceid.getSelectedItem();
                    ResultSet rs = c.statement.executeQuery("select name from student where id='" + selectedId + "'");
                    if (rs.next()) {
                        studentNameLabel.setText("Student Name: " + rs.getString("name"));
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        JLabel lbldate = new JLabel("Date");
        lbldate.setBounds(60,180,200,20);
        lbldate.setFont(new Font("Tahoma", Font.PLAIN,18));
        add(lbldate);

        selDate = new JDateChooser();
        selDate.setBounds(60,210,200,25);
        add(selDate);

        JLabel time = new JLabel("Time Duration");
        time.setBounds(60,260,200,20);
        time.setFont(new Font("Tahoma", Font.PLAIN,18));
        add(time);

        choTime = new Choice();
        choTime.setBounds(60,290,200,20);
        choTime.add("Full Day");
        choTime.add("Half Day");
        add(choTime);

        submit = new JButton("Submit");
        submit.setBounds(60,350,100,25);
        submit.setBackground(Color.black);
        submit.setForeground(Color.white);
        submit.addActionListener(this);
        add(submit);

        cancel = new JButton("Cancel");
        cancel.setBounds(200,350,100,25);
        cancel.setBackground(Color.black);
        cancel.setForeground(Color.white);
        cancel.addActionListener(this);
        add(cancel);



        setSize(500,550);
        setLocation(550,100);
        setLayout(null);
        setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submit){
            String rollno = choiceid.getSelectedItem();
            String date = ((JTextField) selDate.getDateEditor().getUiComponent()).getText();
            String time = choTime.getSelectedItem();

            String Q = "insert into studentLeave values('"+rollno+"','"+date+"','"+time+"')";
            try {
                Conn c = new Conn();
                c.statement.executeUpdate(Q);
                JOptionPane.showMessageDialog(null, "Leave Confirmed");
                setVisible(false);
            }catch (Exception E){
                E.printStackTrace();
            }
        }else {
            setVisible(false);
        }
    }

    public static void main(String[] args) {
        new StudentLeave();
    }
}
