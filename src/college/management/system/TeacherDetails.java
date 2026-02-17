package college.management.system;
import net.proteanit.sql.DbUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.util.jar.JarFile;

public class TeacherDetails extends JFrame implements ActionListener {

    Choice choice;
    JTable table;
    JButton search,print, update, add, cancel, delete;

    TeacherDetails(){
        getContentPane().setBackground(new Color(192,164,252));

        JLabel heading = new JLabel("Search By Teacher ID");
        heading.setBounds(20,20,150,20);
        add(heading);

        choice = new Choice();

        choice.setBounds(180,20,150,20);
        add(choice);

        try {
            Conn c = new Conn();
            ResultSet resultSet = c.statement.executeQuery("select * from teacher");
            while (resultSet.next()){
                choice.add(resultSet.getString("tecid"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        table = new JTable();
        try {
            Conn c = new Conn();
            ResultSet resultSet = c.statement.executeQuery("select * from teacher");
            table.setModel(DbUtils.resultSetToTableModel(resultSet));
        }catch (Exception e){
            e.printStackTrace();
        }
        JScrollPane js = new JScrollPane(table);
        js.setBounds(0,100,900,600);
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
            String q = "select * from teacher where tecid = '"+choice.getSelectedItem()+"'";
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
            new AddFaculty();
        } else if (e.getSource() == update) {
            setVisible(false);
            new UpdateTeacher();
        } else if (e.getSource() == delete) {
            String teacherId = choice.getSelectedItem();
            
            // Confirmation dialog
            int result = JOptionPane.showConfirmDialog(this, 
                "Are you sure you want to delete teacher with ID: " + teacherId + "?\n" +
                "This will permanently delete all records related to this teacher.",
                "Confirm Delete", 
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);
                
            if (result == JOptionPane.YES_OPTION) {
                try {
                    Conn c = new Conn();
                    
                    // First delete any related records (to maintain referential integrity)
                    // Delete from teacherLeave - fixing column name from 'rollno' to 'tecid'
                    c.statement.executeUpdate("delete from teacherLeave where tecid = '" + teacherId + "'");
                    
                    // Finally delete the teacher record
                    c.statement.executeUpdate("delete from teacher where tecid = '" + teacherId + "'");
                    
                    JOptionPane.showMessageDialog(null, "Teacher Deleted Successfully");
                    
                    // Refresh the table and choice
                    setVisible(false);
                    new TeacherDetails();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error deleting teacher: " + ex.getMessage());
                }
            }
        } else {
            setVisible(false);
        }
    }

    public static void main(String[] args) {
        new TeacherDetails();
    }
}
