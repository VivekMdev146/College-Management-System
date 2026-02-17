package college.management.system;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

public class Marks extends JFrame implements ActionListener {
    String id;
    JButton cancel;
    Marks(String id){
        this.id = id;
        
        System.out.println("Opening Marks for student ID: " + id);
        if (id == null || id.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Invalid student ID");
            dispose();
            return;
        }

        setSize(600, 650);
        setLocation(500, 100);
        setLayout(null);

        // Set to full screen mode
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        getContentPane().setBackground(new Color(210, 252, 248));

        JLabel heading = new JLabel("VivekMdev Technical University");
        heading.setBounds(100, 10, 500, 25);
        heading.setFont(new Font("Tahoma", Font.BOLD, 20));
        add(heading);

        JLabel subheading = new JLabel("Result of Examination 2025");
        subheading.setBounds(150, 50, 500, 20);
        subheading.setFont(new Font("Tahoma", Font.BOLD, 18));
        add(subheading);

        JLabel lblid = new JLabel("College ID: " + id);
        lblid.setBounds(60, 100, 500, 20);
        lblid.setFont(new Font("Tahoma", Font.PLAIN, 18));
        add(lblid);
        
        JLabel lblname = new JLabel("Student Name: ");
        lblname.setBounds(60, 130, 500, 20);
        lblname.setFont(new Font("Tahoma", Font.PLAIN, 18));
        add(lblname);
        
        JLabel lblcourse = new JLabel("Course: ");
        lblcourse.setBounds(60, 160, 500, 20);
        lblcourse.setFont(new Font("Tahoma", Font.PLAIN, 18));
        add(lblcourse);

        JLabel lblsemester = new JLabel("Semester: ");
        lblsemester.setBounds(60, 190, 500, 20);
        lblsemester.setFont(new Font("Tahoma", Font.PLAIN, 18));
        add(lblsemester);
        
        // Add subject header labels
        JLabel subjectHeader = new JLabel("SUBJECT");
        subjectHeader.setBounds(100, 230, 200, 20);
        subjectHeader.setFont(new Font("Tahoma", Font.BOLD, 18));
        add(subjectHeader);
        
        JLabel marksHeader = new JLabel("MARKS");
        marksHeader.setBounds(320, 230, 100, 20);
        marksHeader.setFont(new Font("Tahoma", Font.BOLD, 18));
        add(marksHeader);
        
        JLabel statusHeader = new JLabel("STATUS");
        statusHeader.setBounds(430, 230, 100, 20);
        statusHeader.setFont(new Font("Tahoma", Font.BOLD, 18));
        add(statusHeader);
        
        // Add a separator line
        JPanel separator = new JPanel();
        separator.setBounds(60, 255, 480, 2);
        separator.setBackground(Color.BLACK);
        add(separator);

        JLabel sub1 = new JLabel();
        sub1.setBounds(100, 270, 200, 20);
        sub1.setFont(new Font("Tahoma", Font.PLAIN, 18));
        add(sub1);

        JLabel sub2 = new JLabel();
        sub2.setBounds(100, 300, 200, 20);
        sub2.setFont(new Font("Tahoma", Font.PLAIN, 18));
        add(sub2);

        JLabel sub3 = new JLabel();
        sub3.setBounds(100, 330, 200, 20);
        sub3.setFont(new Font("Tahoma", Font.PLAIN, 18));
        add(sub3);

        JLabel sub4 = new JLabel();
        sub4.setBounds(100, 360, 200, 20);
        sub4.setFont(new Font("Tahoma", Font.PLAIN, 18));
        add(sub4);

        JLabel sub5 = new JLabel();
        sub5.setBounds(100, 390, 200, 20);
        sub5.setFont(new Font("Tahoma", Font.PLAIN, 18));
        add(sub5);
        
        // Marks display labels
        JLabel mark1 = new JLabel();
        mark1.setBounds(320, 270, 100, 20);
        mark1.setFont(new Font("Tahoma", Font.PLAIN, 18));
        add(mark1);
        
        JLabel mark2 = new JLabel();
        mark2.setBounds(320, 300, 100, 20);
        mark2.setFont(new Font("Tahoma", Font.PLAIN, 18));
        add(mark2);
        
        JLabel mark3 = new JLabel();
        mark3.setBounds(320, 330, 100, 20);
        mark3.setFont(new Font("Tahoma", Font.PLAIN, 18));
        add(mark3);
        
        JLabel mark4 = new JLabel();
        mark4.setBounds(320, 360, 100, 20);
        mark4.setFont(new Font("Tahoma", Font.PLAIN, 18));
        add(mark4);
        
        JLabel mark5 = new JLabel();
        mark5.setBounds(320, 390, 100, 20);
        mark5.setFont(new Font("Tahoma", Font.PLAIN, 18));
        add(mark5);
        
        // Status labels
        JLabel status1 = new JLabel();
        status1.setBounds(430, 270, 100, 20);
        status1.setFont(new Font("Tahoma", Font.BOLD, 18));
        add(status1);
        
        JLabel status2 = new JLabel();
        status2.setBounds(430, 300, 100, 20);
        status2.setFont(new Font("Tahoma", Font.BOLD, 18));
        add(status2);
        
        JLabel status3 = new JLabel();
        status3.setBounds(430, 330, 100, 20);
        status3.setFont(new Font("Tahoma", Font.BOLD, 18));
        add(status3);
        
        JLabel status4 = new JLabel();
        status4.setBounds(430, 360, 100, 20);
        status4.setFont(new Font("Tahoma", Font.BOLD, 18));
        add(status4);
        
        JLabel status5 = new JLabel();
        status5.setBounds(430, 390, 100, 20);
        status5.setFont(new Font("Tahoma", Font.BOLD, 18));
        add(status5);

        // Add another separator line
        JPanel separator2 = new JPanel();
        separator2.setBounds(60, 425, 480, 2);
        separator2.setBackground(Color.BLACK);
        add(separator2);

        // Add total marks and percentage labels
        JLabel totalMarks = new JLabel();
        totalMarks.setBounds(100, 440, 500, 20);
        totalMarks.setFont(new Font("Tahoma", Font.BOLD, 18));
        add(totalMarks);

        JLabel percentage = new JLabel();
        percentage.setBounds(100, 470, 500, 20);
        percentage.setFont(new Font("Tahoma", Font.BOLD, 18));
        add(percentage);

        JLabel result = new JLabel();
        result.setBounds(100, 500, 500, 30);
        result.setFont(new Font("Tahoma", Font.BOLD, 22));
        add(result);

        try {
            Conn c = new Conn();

            // First get student details
            ResultSet studentRS = c.statement.executeQuery("select * from student where id = '" + id + "'");
            if (studentRS.next()) {
                lblname.setText("Student Name: " + studentRS.getString("name"));
                lblcourse.setText("Course: " + studentRS.getString("course"));
            } else {
                throw new Exception("Student not found with ID: " + id);
            }

            System.out.println("Querying subjects for ID: " + id);
            String subjectQuery = "select * from subject where id = '"+id+"'";
            System.out.println("Subject query: " + subjectQuery);
            
            ResultSet rs1 = c.statement.executeQuery(subjectQuery);
            boolean hasSubjects = false;
            String semester = "";
            
            while(rs1.next()) {
                hasSubjects = true;
                System.out.println("Found subject record for ID: " + id);
                semester = rs1.getString("semester");
                sub1.setText(rs1.getString("subject1"));
                sub2.setText(rs1.getString("subject2"));
                sub3.setText(rs1.getString("subject3"));
                sub4.setText(rs1.getString("subject4"));
                sub5.setText(rs1.getString("subject5"));
                lblsemester.setText("Semester: " + semester);
            }
            
            if (!hasSubjects) {
                System.out.println("No subjects found for ID: " + id);
                throw new Exception("No subjects found for this student with ID: " + id);
            }

            System.out.println("Querying marks for ID: " + id);
            String marksQuery = "select * from marks where id = '"+id+"'";
            System.out.println("Marks query: " + marksQuery);
            
            ResultSet rs2 = c.statement.executeQuery(marksQuery);
            boolean hasMarks = false;
            int total = 0;
            
            while(rs2.next()) {
                hasMarks = true;
                System.out.println("Found marks record for ID: " + id);
                
                // Get marks and calculate pass/fail status
                int marks1 = Integer.parseInt(rs2.getString("marks1"));
                int marks2 = Integer.parseInt(rs2.getString("marks2"));
                int marks3 = Integer.parseInt(rs2.getString("marks3"));
                int marks4 = Integer.parseInt(rs2.getString("marks4"));
                int marks5 = Integer.parseInt(rs2.getString("marks5"));
                
                // Set marks and status for each subject
                mark1.setText(String.valueOf(marks1));
                mark2.setText(String.valueOf(marks2));
                mark3.setText(String.valueOf(marks3));
                mark4.setText(String.valueOf(marks4));
                mark5.setText(String.valueOf(marks5));
                
                // Set pass/fail for each subject
                boolean pass1 = marks1 >= 35;
                boolean pass2 = marks2 >= 35;
                boolean pass3 = marks3 >= 35;
                boolean pass4 = marks4 >= 35;
                boolean pass5 = marks5 >= 35;
                
                status1.setText(pass1 ? "PASS" : "FAIL");
                status2.setText(pass2 ? "PASS" : "FAIL");
                status3.setText(pass3 ? "PASS" : "FAIL");
                status4.setText(pass4 ? "PASS" : "FAIL");
                status5.setText(pass5 ? "PASS" : "FAIL");
                
                status1.setForeground(pass1 ? new Color(0, 100, 0) : Color.RED);
                status2.setForeground(pass2 ? new Color(0, 100, 0) : Color.RED);
                status3.setForeground(pass3 ? new Color(0, 100, 0) : Color.RED);
                status4.setForeground(pass4 ? new Color(0, 100, 0) : Color.RED);
                status5.setForeground(pass5 ? new Color(0, 100, 0) : Color.RED);
                
                // Calculate total and percentage
                total = marks1 + marks2 + marks3 + marks4 + marks5;
                double percentageValue = (total / 500.0) * 100;
                
                // Set total marks and percentage
                totalMarks.setText("Total Marks: " + total + " / 500");
                percentage.setText("Percentage: " + String.format("%.2f", percentageValue) + "%");
                
                // Set overall result
                boolean overallPass = percentageValue >= 35 && pass1 && pass2 && pass3 && pass4 && pass5;
                result.setText("Result: " + (overallPass ? "PASS" : "FAIL"));
                result.setForeground(overallPass ? new Color(0, 100, 0) : Color.RED);
            }
            
            if (!hasMarks) {
                System.out.println("No marks found for ID: " + id);
                throw new Exception("No marks found for this student with ID: " + id);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
            dispose();
            return;
        }

        cancel = new JButton("Back");
        cancel.setBounds(250, 550, 120, 25);
        cancel.setBackground(Color.BLACK);
        cancel.setForeground(Color.WHITE);
        cancel.addActionListener(this);
        cancel.setFont(new Font("Tahoma", Font.BOLD, 15));
        add(cancel);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        setVisible(false);
        new ExaminationDetails();
    }

    public static void main(String[] args) {
        new Marks("");
    }
}
