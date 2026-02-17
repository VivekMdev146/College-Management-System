package college.management.system;

import net.proteanit.sql.DbUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

public class StudentDetails extends JFrame implements ActionListener {

    Choice choice;
    JTable table;
    JButton search,print, update, add, cancel, delete;
    StudentDetails(){
        getContentPane().setBackground(new Color(43, 145, 141, 255));

        JLabel heading = new JLabel("Search by College Id");
        heading.setBounds(20,20,150,20);
        add(heading);

        choice = new Choice();
        choice.setBounds(180,20,150,20);
        add(choice);

        try {
            Conn c = new Conn();
            ResultSet resultSet = c.statement.executeQuery("select * from student");
            while (resultSet.next()){
                choice.add(resultSet.getString("id"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        table = new JTable();
        try {
            Conn c = new Conn();
            ResultSet resultSet = c.statement.executeQuery("select * from student");
            table.setModel(DbUtils.resultSetToTableModel(resultSet));
        }catch (Exception e){
            e.printStackTrace();
        }
        JScrollPane js = new JScrollPane(table);
        js.setBounds(10,100,1400,500);
        add(js);

        search = new JButton("Search");
        search.setBounds(20,70,80,20);
        search.addActionListener(this);
        add(search);

        print = new JButton("Print");
        print.setBounds(120,70,80,20);
        print.addActionListener(this);
        add(print);

        add = new JButton("Add");
        add.setBounds(220,70,80,20);
        add.addActionListener(this);
        add(add);

        update = new JButton("Update");
        update.setBounds(320,70,80,20);
        update.addActionListener(this);
        add(update);

        delete = new JButton("Delete");
        delete.setBounds(420,70,80,20);
        delete.setBackground(Color.red);
        delete.setForeground(Color.white);
        delete.addActionListener(this);
        add(delete);

        cancel = new JButton("Cancel");
        cancel.setBounds(520,70,80,20);
        cancel.addActionListener(this);
        add(cancel);

        setLayout(null);
        setSize(900,700);
        setLocation(300,100);
        setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == search){
            String q = "select * from student where id = '"+choice.getSelectedItem()+"'";
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
        } else if (e.getSource() == add) {
            setVisible(false);
            new AddStudent();
        } else if (e.getSource() == update) {
            setVisible(false);
            new updateStudent();
        } else if (e.getSource() == delete) {
            String studentId = choice.getSelectedItem();
            
            // Confirmation dialog
            int result = JOptionPane.showConfirmDialog(this, 
                "Are you sure you want to delete student with ID: " + studentId + "?\n" +
                "This will permanently delete all records related to this student.",
                "Confirm Delete", 
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);
                
            if (result == JOptionPane.YES_OPTION) {
                try {
                    Conn c = new Conn();
                    
                    // First delete any related records (to maintain referential integrity)
                    // Delete from studentLeave - fix column name from 'rollno' to 'id'
                    c.statement.executeUpdate("delete from studentLeave where id = '" + studentId + "'");
                    
                    // Delete from marks
                    c.statement.executeUpdate("delete from marks where id = '" + studentId + "'");
                    
                    // Delete from feecollege
                    c.statement.executeUpdate("delete from feecollege where id = '" + studentId + "'");
                    
                    // Finally delete the student record
                    c.statement.executeUpdate("delete from student where id = '" + studentId + "'");
                    
                    JOptionPane.showMessageDialog(null, "Student Deleted Successfully");
                    
                    // Refresh the table and choice
                    setVisible(false);
                    new StudentDetails();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error deleting student: " + ex.getMessage());
                }
            }
        } else {
            setVisible(false);
        }
    }

    public static void main(String[] args) {
        new StudentDetails();
    }
}
