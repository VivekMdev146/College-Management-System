package college.management.system;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.ResultSet;

public class TeacherLeave extends JFrame implements ActionListener {
    Choice choicetecID, choTime;
    JDateChooser selDate;
    JButton submit, cancel;
    JLabel teacherNameLabel;
    TeacherLeave(){
        getContentPane().setBackground(new Color(210,232,252));

        JLabel heading = new JLabel("Apply Leave (Teacher)");
        heading.setBounds(40,50,300,30);
        heading.setFont(new Font("Tahoma", Font.BOLD,20));
        add(heading);

        JLabel tecIDSE = new JLabel("Search By Teacher ID");
        tecIDSE.setBounds(60,100,200,20);
        tecIDSE.setFont(new Font("Tahoma", Font.PLAIN,18));
        add(tecIDSE);

        choicetecID = new Choice();
        choicetecID.setBounds(60,130,200,20);
        add(choicetecID);

        // Label to display teacher name
        teacherNameLabel = new JLabel("Teacher Name: ");
        teacherNameLabel.setBounds(300,130,300,20);
        teacherNameLabel.setFont(new Font("Tahoma", Font.PLAIN,16));
        add(teacherNameLabel);

        try{
            Conn c = new Conn();
            ResultSet resultSet = c.statement.executeQuery("select * from teacher");
            while (resultSet.next()){
                choicetecID.add(resultSet.getString("tecID"));
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        // Add item listener to display name when ID is selected
        choicetecID.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                try {
                    Conn c = new Conn();
                    String selectedId = choicetecID.getSelectedItem();
                    ResultSet rs = c.statement.executeQuery("select name from teacher where tecID='" + selectedId + "'");
                    if (rs.next()) {
                        teacherNameLabel.setText("Teacher Name: " + rs.getString("name"));
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
            String tecID = choicetecID.getSelectedItem();
            String date = ((JTextField) selDate.getDateEditor().getUiComponent()).getText();
            String time = choTime.getSelectedItem();

            String Q = "insert into teacherleave values('"+tecID+"','"+date+"','"+time+"')";
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
        new TeacherLeave();
    }
}
