package college.management.system;

import net.proteanit.sql.DbUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;

public class ExaminationDetails extends JFrame implements ActionListener {

    Choice studentIdChoice;
    JLabel studentNameDisplay;
    JButton result, back;
    JTable table;
    JScrollPane scrollPane;
    
    ExaminationDetails(){

        getContentPane().setBackground(new Color(241,252,210));

        JLabel heading = new JLabel("Check Result");
        heading.setBounds(350,15,400,50);
        heading.setFont(new Font("Tahoma",Font.BOLD,24));
        add(heading);

        JLabel idLabel = new JLabel("Select Student ID:");
        idLabel.setBounds(80,70,150,20);
        idLabel.setFont(new Font("Tahoma", Font.PLAIN,16));
        add(idLabel);

        studentIdChoice = new Choice();
        studentIdChoice.setBounds(80,90,200,30);
        studentIdChoice.setFont(new Font("Tahoma", Font.PLAIN,16));
        add(studentIdChoice);
        
        JLabel nameLabel = new JLabel("Student Name:");
        nameLabel.setBounds(80,120,150,20);
        nameLabel.setFont(new Font("Tahoma", Font.PLAIN,16));
        add(nameLabel);
        
        studentNameDisplay = new JLabel();
        studentNameDisplay.setBounds(220,120,300,20);
        studentNameDisplay.setFont(new Font("Tahoma", Font.BOLD,16));
        studentNameDisplay.setForeground(Color.BLUE);
        add(studentNameDisplay);

        result = new JButton("Result");
        result.setBounds(300,90,120,30);
        result.setBackground(Color.black);
        result.setForeground(Color.white);
        result.addActionListener(this);
        add(result);

        back = new JButton("Back");
        back.setBounds(440,90,120,30);
        back.setBackground(Color.black);
        back.setForeground(Color.white);
        back.addActionListener(this);
        add(back);

        table = new JTable();
        scrollPane = new JScrollPane(table);
        scrollPane.setBounds(0,150,getWidth(),310);
        add(scrollPane);

        try {
            // Load students into the dropdown
            Conn c = new Conn();
            ResultSet resultSet = c.statement.executeQuery("select * from student");
            System.out.println("Loading student data from database...");
            
            // Populate the dropdown with student IDs
            while (resultSet.next()) {
                studentIdChoice.add(resultSet.getString("id"));
            }
            
            // Reset resultSet to display in table
            resultSet = c.statement.executeQuery("select * from student");
            table.setModel(DbUtils.resultSetToTableModel(resultSet));
            
            // Print column names
            System.out.println("\nTable columns:");
            for (int i = 0; i < table.getColumnCount(); i++) {
                System.out.println("Column " + i + ": " + table.getColumnName(i));
            }
            
            // Print first row data
            if (table.getRowCount() > 0) {
                System.out.println("\nFirst row data:");
                for (int i = 0; i < table.getColumnCount(); i++) {
                    System.out.println("Column " + i + ": " + table.getValueAt(0, i));
                }
            } else {
                System.out.println("WARNING: No students found in the database!");
                JOptionPane.showMessageDialog(null, "No students found in the database!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error loading student data: " + e.getMessage());
        }
        
        // Add item listener to update student name when ID is selected
        studentIdChoice.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                updateStudentName();
            }
        });

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                if (row >= 0) {  // Make sure a row is selected
                    // Print all columns to verify the data
                    System.out.println("\nRow clicked: " + row);
                    for (int i = 0; i < table.getColumnCount(); i++) {
                        System.out.println("Column " + i + ": " + table.getModel().getValueAt(row, i));
                    }
                    
                    // The ID is in column 3 (index 2)
                    Object idValue = table.getModel().getValueAt(row, 2);  // This is correct as the ID is in the third column
                    System.out.println("Raw ID value: " + idValue);
                    
                    if (idValue != null) {
                        String studentId = idValue.toString().trim();
                        if (!studentId.isEmpty()) {
                            // Update the dropdown selection instead of text field
                            studentIdChoice.select(studentId);
                            updateStudentName();
                            System.out.println("Selected student ID: " + studentId);
                        } else {
                            System.out.println("Student ID is empty");
                            JOptionPane.showMessageDialog(null, "Invalid student ID: ID is empty");
                        }
                    } else {
                        System.out.println("Student ID is null");
                        JOptionPane.showMessageDialog(null, "Invalid student ID: ID is null");
                    }
                } else {
                    System.out.println("No row selected");
                    JOptionPane.showMessageDialog(null, "Please select a student from the table");
                }
            }
        });

        // Initialize student name display for first selected ID
        if (studentIdChoice.getItemCount() > 0) {
            updateStudentName();
        }

        // Add component listener to adjust table width when window is resized
        addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                // Update the table width to match the window width
                scrollPane.setBounds(0, 150, getWidth(), 310);
                revalidate();
                repaint();
            }
        });

        // Set window to full screen
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(null);
        setVisible(true);
    }
    
    // Helper method to update student name when ID is selected
    private void updateStudentName() {
        try {
            Conn c = new Conn();
            String selectedId = studentIdChoice.getSelectedItem();
            String query = "select name from student where id = '" + selectedId + "'";
            ResultSet rs = c.statement.executeQuery(query);
            if (rs.next()) {
                studentNameDisplay.setText(rs.getString("name"));
            } else {
                studentNameDisplay.setText("Name not found");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            studentNameDisplay.setText("Error fetching name");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == result){
            String studentId = studentIdChoice.getSelectedItem().trim();
            System.out.println("\nResult button clicked");
            System.out.println("Selected student ID: '" + studentId + "'");
            
            if (studentId.isEmpty()) {
                System.out.println("No student selected");
                JOptionPane.showMessageDialog(null, "Please select a student first");
                return;
            }
            
            // First check if the student has marks and subjects in the database
            try {
                Conn c = new Conn();
                
                // Check if subject data exists
                ResultSet subjectRS = c.statement.executeQuery("select * from subject where id = '" + studentId + "'");
                boolean hasSubjects = subjectRS.next();
                
                // Check if marks data exists
                ResultSet marksRS = c.statement.executeQuery("select * from marks where id = '" + studentId + "'");
                boolean hasMarks = marksRS.next();
                
                if (!hasSubjects || !hasMarks) {
                    String errorMsg = "Cannot show result for student ID: " + studentId + ". ";
                    errorMsg += !hasSubjects ? "No subjects found. " : "";
                    errorMsg += !hasMarks ? "No marks found. " : "";
                    errorMsg += "Please enter subjects and marks first.";
                    
                    System.out.println(errorMsg);
                    JOptionPane.showMessageDialog(null, errorMsg);
                    return;
                }
                
                System.out.println("Opening marks for student ID: " + studentId);
            setVisible(false);
                new Marks(studentId);
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error checking student data: " + ex.getMessage());
            }
        } else {
            setVisible(false);
        }
    }

    public static void main(String[] args) {
        new ExaminationDetails();
    }
}
