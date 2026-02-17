package college.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.ResultSet;

public class UpdateFeeStructure extends JFrame implements ActionListener {
    
    JComboBox<String> courseBox;
    JTextField semester1Field, semester2Field, semester3Field, semester4Field, 
               semester5Field, semester6Field, semester7Field, semester8Field;
    JButton updateButton, resetButton, backButton;
    JLabel statusLabel;
    
    public UpdateFeeStructure() {
        setTitle("Update Fee Structure");
        getContentPane().setBackground(new Color(230, 250, 230));
        setLayout(null);
        
        // Heading
        JLabel heading = new JLabel("Update Fee Structure");
        heading.setBounds(300, 30, 500, 40);
        heading.setFont(new Font("Tahoma", Font.BOLD, 30));
        add(heading);
        
        // Course selection
        JLabel courseLabel = new JLabel("Select Course:");
        courseLabel.setBounds(100, 100, 150, 30);
        courseLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
        add(courseLabel);
        
        String[] courses = {"BBA-CA", "BCA", "BBA", "BSC", "BCom", "MSC", "MBA", "MCA", "MCom", "MA", "BA"};
        courseBox = new JComboBox<>(courses);
        courseBox.setBounds(250, 100, 150, 30);
        courseBox.setBackground(Color.WHITE);
        add(courseBox);
        
        // Add ItemListener to update fields when course changes
        courseBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    loadFeeDetails();
                }
            }
        });
        
        // Fee fields for each semester
        JLabel semesterLabel = new JLabel("Fee Amount by Semester:");
        semesterLabel.setBounds(100, 150, 250, 30);
        semesterLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
        add(semesterLabel);
        
        // Semester 1
        JLabel sem1Label = new JLabel("Semester 1:");
        sem1Label.setBounds(100, 200, 100, 25);
        add(sem1Label);
        
        semester1Field = new JTextField();
        semester1Field.setBounds(250, 200, 150, 25);
        add(semester1Field);
        
        // Semester 2
        JLabel sem2Label = new JLabel("Semester 2:");
        sem2Label.setBounds(100, 240, 100, 25);
        add(sem2Label);
        
        semester2Field = new JTextField();
        semester2Field.setBounds(250, 240, 150, 25);
        add(semester2Field);
        
        // Semester 3
        JLabel sem3Label = new JLabel("Semester 3:");
        sem3Label.setBounds(100, 280, 100, 25);
        add(sem3Label);
        
        semester3Field = new JTextField();
        semester3Field.setBounds(250, 280, 150, 25);
        add(semester3Field);
        
        // Semester 4
        JLabel sem4Label = new JLabel("Semester 4:");
        sem4Label.setBounds(100, 320, 100, 25);
        add(sem4Label);
        
        semester4Field = new JTextField();
        semester4Field.setBounds(250, 320, 150, 25);
        add(semester4Field);
        
        // Semester 5
        JLabel sem5Label = new JLabel("Semester 5:");
        sem5Label.setBounds(450, 200, 100, 25);
        add(sem5Label);
        
        semester5Field = new JTextField();
        semester5Field.setBounds(600, 200, 150, 25);
        add(semester5Field);
        
        // Semester 6
        JLabel sem6Label = new JLabel("Semester 6:");
        sem6Label.setBounds(450, 240, 100, 25);
        add(sem6Label);
        
        semester6Field = new JTextField();
        semester6Field.setBounds(600, 240, 150, 25);
        add(semester6Field);
        
        // Semester 7
        JLabel sem7Label = new JLabel("Semester 7:");
        sem7Label.setBounds(450, 280, 100, 25);
        add(sem7Label);
        
        semester7Field = new JTextField();
        semester7Field.setBounds(600, 280, 150, 25);
        add(semester7Field);
        
        // Semester 8
        JLabel sem8Label = new JLabel("Semester 8:");
        sem8Label.setBounds(450, 320, 100, 25);
        add(sem8Label);
        
        semester8Field = new JTextField();
        semester8Field.setBounds(600, 320, 150, 25);
        add(semester8Field);
        
        // Status label
        statusLabel = new JLabel("");
        statusLabel.setBounds(100, 370, 600, 25);
        statusLabel.setForeground(Color.BLUE);
        add(statusLabel);
        
        // Buttons
        updateButton = new JButton("Update");
        updateButton.setBounds(200, 420, 100, 35);
        updateButton.setBackground(new Color(0, 128, 0));
        updateButton.setForeground(Color.WHITE);
        updateButton.addActionListener(this);
        add(updateButton);
        
        resetButton = new JButton("Reset");
        resetButton.setBounds(350, 420, 100, 35);
        resetButton.setBackground(Color.ORANGE);
        resetButton.setForeground(Color.WHITE);
        resetButton.addActionListener(this);
        add(resetButton);
        
        backButton = new JButton("Back");
        backButton.setBounds(500, 420, 100, 35);
        backButton.setBackground(Color.GRAY);
        backButton.setForeground(Color.WHITE);
        backButton.addActionListener(this);
        add(backButton);
        
        // Load fee details for the default selected course
        loadFeeDetails();
        
        // Set frame properties
        setSize(850, 550);
        setLocation(250, 100);
        setVisible(true);
    }
    
    // Method to load fee details for the selected course
    private void loadFeeDetails() {
        try {
            String selectedCourse = (String) courseBox.getSelectedItem();
            Conn c = new Conn();
            String query = "SELECT * FROM fee WHERE course = '" + selectedCourse + "'";
            ResultSet rs = c.statement.executeQuery(query);
            
            // Clear all fields first
            clearFields();
            
            if (rs.next()) {
                // Populate fields with existing fee values
                semester1Field.setText(rs.getString("semester-1"));
                semester2Field.setText(rs.getString("semester-2"));
                semester3Field.setText(rs.getString("semester-3"));
                semester4Field.setText(rs.getString("semester-4"));
                semester5Field.setText(rs.getString("semester-5"));
                semester6Field.setText(rs.getString("semester-6"));
                semester7Field.setText(rs.getString("semester-7"));
                semester8Field.setText(rs.getString("semester-8"));
                
                statusLabel.setText("Loaded fee details for " + selectedCourse);
            } else {
                statusLabel.setText("No fee details found for " + selectedCourse + ". You can add new details.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            statusLabel.setText("Error loading fee details: " + e.getMessage());
        }
    }
    
    // Clear all text fields
    private void clearFields() {
        semester1Field.setText("");
        semester2Field.setText("");
        semester3Field.setText("");
        semester4Field.setText("");
        semester5Field.setText("");
        semester6Field.setText("");
        semester7Field.setText("");
        semester8Field.setText("");
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == updateButton) {
            // Validate inputs
            if (!validateInputs()) {
                statusLabel.setText("Please enter valid fee amounts (numbers only).");
                return;
            }
            
            String selectedCourse = (String) courseBox.getSelectedItem();
            String sem1 = semester1Field.getText();
            String sem2 = semester2Field.getText();
            String sem3 = semester3Field.getText();
            String sem4 = semester4Field.getText();
            String sem5 = semester5Field.getText();
            String sem6 = semester6Field.getText();
            String sem7 = semester7Field.getText();
            String sem8 = semester8Field.getText();
            
            try {
                Conn c = new Conn();
                
                // First check if course exists in fee table
                ResultSet rs = c.statement.executeQuery("SELECT * FROM fee WHERE course = '" + selectedCourse + "'");
                
                String query;
                if (rs.next()) {
                    // Update existing record
                    query = "UPDATE fee SET `semester-1` = '" + sem1 + "', `semester-2` = '" + sem2 + "', " +
                            "`semester-3` = '" + sem3 + "', `semester-4` = '" + sem4 + "', " +
                            "`semester-5` = '" + sem5 + "', `semester-6` = '" + sem6 + "', " +
                            "`semester-7` = '" + sem7 + "', `semester-8` = '" + sem8 + "' " +
                            "WHERE course = '" + selectedCourse + "'";
                } else {
                    // Insert new record
                    query = "INSERT INTO fee VALUES('" + selectedCourse + "', '" + sem1 + "', '" + sem2 + "', " +
                            "'" + sem3 + "', '" + sem4 + "', '" + sem5 + "', '" + sem6 + "', " +
                            "'" + sem7 + "', '" + sem8 + "')";
                }
                
                c.statement.executeUpdate(query);
                statusLabel.setText("Fee structure for " + selectedCourse + " has been updated successfully!");
                JOptionPane.showMessageDialog(this, "Fee structure updated successfully!");
                
            } catch (Exception ex) {
                ex.printStackTrace();
                statusLabel.setText("Error updating fee structure: " + ex.getMessage());
                JOptionPane.showMessageDialog(this, "Error updating fee structure: " + ex.getMessage(), 
                                             "Error", JOptionPane.ERROR_MESSAGE);
            }
            
        } else if (e.getSource() == resetButton) {
            clearFields();
            statusLabel.setText("Fields have been reset. Enter new values.");
            
        } else if (e.getSource() == backButton) {
            setVisible(false);
        }
    }
    
    // Validate that all inputs are numbers
    private boolean validateInputs() {
        try {
            if (!semester1Field.getText().isEmpty()) Integer.parseInt(semester1Field.getText());
            if (!semester2Field.getText().isEmpty()) Integer.parseInt(semester2Field.getText());
            if (!semester3Field.getText().isEmpty()) Integer.parseInt(semester3Field.getText());
            if (!semester4Field.getText().isEmpty()) Integer.parseInt(semester4Field.getText());
            if (!semester5Field.getText().isEmpty()) Integer.parseInt(semester5Field.getText());
            if (!semester6Field.getText().isEmpty()) Integer.parseInt(semester6Field.getText());
            if (!semester7Field.getText().isEmpty()) Integer.parseInt(semester7Field.getText());
            if (!semester8Field.getText().isEmpty()) Integer.parseInt(semester8Field.getText());
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    public static void main(String[] args) {
        new UpdateFeeStructure();
    }
} 