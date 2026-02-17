package college.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.ResultSet;

public class EnterMarks extends JFrame implements ActionListener {
    Choice choiceid;
    JComboBox comboBox;
    JTextField sub1, sub2, sub3, sub4, sub5, mrk1, mrk2, mrk3, mrk4, mrk5;
    JLabel studentNameDisplay, courseDisplay;
    JButton submit,cancel;
    
    // Map of course-specific subjects by semester
    private final java.util.Map<String, String[][]> courseSubjects = new java.util.HashMap<>();
    
    // Pre-defined subjects for each semester
    private final String[][] semesterSubjects = {
        // 1st Semester
        {"Business Communication", "Principles of Management", "C Programming", "Business Statistics", "DBMS"},
        // 2nd Semester
        {"Financial Accounts", "Business Mathematics", "RDMS", "Web Technology", "HR Management"},
        // 3rd Semester
        {"Digital Marketing", "Big Data", "Software Engineering", "Data Structure Algorithm", "PHP"},
        // 4th Semester
        {"Networking", "C++", "Operating Systems", "Advance PHP", "Project"},
        // 5th Semester
        {"Cyber Security", "OOSE", "Core JAVA", "Python", "Project"},
        // 6th Semester
        {"Recent Trends in IT", "Software Testing", "Advance JAVA", "Android Programming", "Project"},
        // 7th Semester (placeholder)
        {"Subject 1", "Subject 2", "Subject 3", "Subject 4", "Subject 5"},
        // 8th Semester (placeholder)
        {"Subject 1", "Subject 2", "Subject 3", "Subject 4", "Subject 5"}
    };
    
    EnterMarks(){
        // Initialize course subjects
        initializeCourseSubjects();

        getContentPane().setBackground(new Color(252,245,210));

        // Add JVM warning suppression flag
        try {
            System.setProperty("illegal-access", "permit");
        } catch (Exception e) {
            System.out.println("Warning: Could not set system property for JVM warnings");
        }

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icon/exam.png"));
        Image i2 = i1.getImage().getScaledInstance(400,300,Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel img = new JLabel(i3);
        img.setBounds(500,40,400,300);
        add(img);

        JLabel heading = new JLabel("Enter Marks of Student");
        heading.setBounds(50,0,500,50);
        heading.setFont(new Font("Tahoma",Font.BOLD,20));
        add(heading);

        JLabel id = new JLabel("Select Student Id");
        id.setBounds(50,70,150,20);
        add(id);

        choiceid = new Choice();
        choiceid.setBounds(200,70,150,20);
        add(choiceid);
        
        JLabel nameLabel = new JLabel("Student Name:");
        nameLabel.setBounds(50,90,150,20);
        add(nameLabel);
        
        studentNameDisplay = new JLabel();
        studentNameDisplay.setBounds(200,90,200,20);
        studentNameDisplay.setFont(new Font("Tahoma", Font.BOLD, 14));
        studentNameDisplay.setForeground(Color.BLUE);
        add(studentNameDisplay);

        JLabel courseLabel = new JLabel("Course:");
        courseLabel.setBounds(50,110,150,20);
        add(courseLabel);
        
        courseDisplay = new JLabel();
        courseDisplay.setBounds(200,110,200,20);
        courseDisplay.setFont(new Font("Tahoma", Font.BOLD, 14));
        courseDisplay.setForeground(Color.BLUE);
        add(courseDisplay);

        try {
            Conn c = new Conn();
            ResultSet resultSet = c.statement.executeQuery("select * from student");
            while (resultSet.next()){
                choiceid.add(resultSet.getString("id"));
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        
        // Add item listener to fetch student name when ID is selected
        choiceid.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    try {
                        Conn c = new Conn();
                        String selectedId = choiceid.getSelectedItem();
                        String query = "select name, course from student where id = '" + selectedId + "'";
                        ResultSet rs = c.statement.executeQuery(query);
                        if (rs.next()) {
                            studentNameDisplay.setText(rs.getString("name"));
                            courseDisplay.setText(rs.getString("course"));
                            
                            // Populate subjects immediately after course is updated
                            SwingUtilities.invokeLater(() -> populateSubjects());
                        } else {
                            studentNameDisplay.setText("Name not found");
                            courseDisplay.setText("Course not found");
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        studentNameDisplay.setText("Error fetching data");
                        courseDisplay.setText("Error fetching data");
                    }
                }
            }
        });

        JLabel sem = new JLabel("Select Semester");
        sem.setBounds(50,150,150,20);
        add(sem);

        String semester[] = {"1st Semester","2nd Semester","3rd Semester","4th Semester","5th Semester","6th Semester"};
        comboBox = new JComboBox(semester);
        comboBox.setBounds(200,150,150,20);
        comboBox.setBackground(Color.WHITE);
        add(comboBox);
        
        // Add item listener to populate subjects when semester is selected
        comboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    populateSubjects();
                }
            }
        });

        JLabel entersub = new JLabel("Enter Subject");
        entersub.setBounds(100,190,200,40);
        add(entersub);

        JLabel entermarks = new JLabel("Enter Marks");
        entermarks.setBounds(320,190,200,40);
        add(entermarks);

        sub1 = new JTextField();
        sub1.setBounds(50,240,200,20);
        add(sub1);

        sub2 = new JTextField();
        sub2.setBounds(50,270,200,20);
        add(sub2);

        sub3 = new JTextField();
        sub3.setBounds(50,300,200,20);
        add(sub3);

        sub4 = new JTextField();
        sub4.setBounds(50,330,200,20);
        add(sub4);

        sub5 = new JTextField();
        sub5.setBounds(50,360,200,20);
        add(sub5);

        mrk1 = new JTextField();
        mrk1.setBounds(250,240,200,20);
        add(mrk1);

        mrk2 = new JTextField();
        mrk2.setBounds(250,270,200,20);
        add(mrk2);

        mrk3 = new JTextField();
        mrk3.setBounds(250,300,200,20);
        add(mrk3);

        mrk4 = new JTextField();
        mrk4.setBounds(250,330,200,20);
        add(mrk4);

        mrk5 = new JTextField();
        mrk5.setBounds(250,360,200,20);
        add(mrk5);

        submit = new JButton("Submit");
        submit.setBounds(70,400,150,25);
        submit.setBackground(Color.black);
        submit.setForeground(Color.WHITE);
        submit.addActionListener(this);
        add(submit);

        cancel = new JButton("Cancel");
        cancel.setBounds(280,400,150,25);
        cancel.setBackground(Color.black);
        cancel.setForeground(Color.WHITE);
        cancel.addActionListener(this);
        add(cancel);
        
        // Populate subjects for initially selected semester
        if (choiceid.getItemCount() > 0) {
            fetchAndDisplayStudentName(choiceid.getSelectedItem());
            // Double-check subjects are populated
            SwingUtilities.invokeLater(() -> populateSubjects());
        }

        setSize(1000,500);
        setLayout(null);
        setLocation(300,150);
        
        // Set to full screen mode
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        
        setVisible(true);
    }
    
    // Initialize subject lists for different courses
    private void initializeCourseSubjects() {
        // BBA-CA (Bachelor of Business Administration in Computer Applications)
        courseSubjects.put("BBA-CA", new String[][] {
            // 1st Semester
            {"Business Communication", "Principles of Management", "C Programming", "Business Statistics", "DBMS"},
            // 2nd Semester
            {"Financial Accounts", "Business Mathematics", "RDMS", "Web Technology", "HR Management"},
            // 3rd Semester
            {"Digital Marketing", "Big Data", "Software Engineering", "Data Structure Algorithm", "PHP"},
            // 4th Semester
            {"Networking", "C++", "Operating Systems", "Advance PHP", "Project"},
            // 5th Semester
            {"Cyber Security", "OOSE", "Core JAVA", "Python", "Project"},
            // 6th Semester
            {"Recent Trends in IT", "Software Testing", "Advance JAVA", "Android Programming", "Project"}
        });
        
        // BCA (Bachelor of Computer Applications)
        courseSubjects.put("BCA", new String[][] {
            // 1st Semester
            {"Introduction to Programming", "Computer Fundamentals", "Digital Logic", "Mathematics I", "Communication Skills"},
            // 2nd Semester
            {"Data Structures", "Object-Oriented Programming", "DBMS", "Mathematics II", "Web Design"},
            // 3rd Semester
            {"Computer Networks", "Operating Systems", "Software Engineering", "Java Programming", "Computer Graphics"},
            // 4th Semester
            {"Data Communication", "Python Programming", "Web Technologies", "Mobile Application Development", "Project I"},
            // 5th Semester
            {"Artificial Intelligence", "Cloud Computing", "Information Security", "Data Mining", "Project II"},
            // 6th Semester
            {"Internet of Things", "Machine Learning", "Full Stack Development", "Software Testing", "Major Project"}
        });
        
        // BBA (Bachelor of Business Administration)
        courseSubjects.put("BBA", new String[][] {
            // 1st Semester
            {"Principles of Management", "Business Communication", "Financial Accounting", "Business Economics", "Marketing Management"},
            // 2nd Semester
            {"Human Resource Management", "Organizational Behavior", "Business Law", "Management Accounting", "Financial Management"},
            // 3rd Semester
            {"Operations Management", "Strategic Management", "Entrepreneurship Development", "International Business", "Research Methodology"},
            // 4th Semester
            {"Corporate Finance", "Business Ethics", "Business Analytics", "Digital Marketing", "Project Management"},
            // 5th Semester
            {"Supply Chain Management", "Management Information Systems", "Risk Management", "Consumer Behavior", "Business Policy"},
            // 6th Semester
            {"Leadership Development", "Corporate Governance", "Change Management", "E-Commerce", "Business Simulation"}
        });
        
        // BSC (Bachelor of Science - Computer Science)
        courseSubjects.put("BSC", new String[][] {
            // 1st Semester
            {"Programming in C", "Digital Computer Fundamentals", "Mathematics I", "Physics", "English"},
            // 2nd Semester
            {"Programming in C++", "Data Structures", "Mathematics II", "Electronics", "Environmental Science"},
            // 3rd Semester
            {"Database Management Systems", "Operating Systems", "Software Engineering", "Computer Networks", "Web Technologies"},
            // 4th Semester
            {"Java Programming", "Artificial Intelligence", "Computer Graphics", "System Analysis and Design", "Mobile Computing"},
            // 5th Semester
            {"Data Mining", "Cloud Computing", "Information Security", "Internet of Things", "Project I"},
            // 6th Semester
            {"Machine Learning", "Big Data Analytics", "Software Testing", "E-Commerce", "Project II"}
        });
        
        // BCom (Bachelor of Commerce)
        courseSubjects.put("BCom", new String[][] {
            // 1st Semester
            {"Financial Accounting", "Business Economics", "Business Mathematics and Statistics", "Business Organization", "Business Communication"},
            // 2nd Semester
            {"Corporate Accounting", "Cost Accounting", "Business Law", "Business Environment", "Marketing Management"},
            // 3rd Semester
            {"Income Tax", "Auditing", "Banking and Insurance", "Human Resource Management", "E-Commerce"},
            // 4th Semester
            {"Indirect Taxes", "Financial Management", "International Business", "Business Ethics", "Research Methodology"},
            // 5th Semester
            {"Corporate Tax Planning", "Investment Management", "Entrepreneurship Development", "Digital Marketing", "Project Management"},
            // 6th Semester
            {"Strategic Management", "Risk Management", "Corporate Governance", "Financial Markets", "Business Analytics"}
        });
        
        // MSC (Master of Science - Computer Science)
        courseSubjects.put("MSC", new String[][] {
            // 1st Semester
            {"Advanced Database Management Systems", "Advanced Operating Systems", "Software Project Management", "Data Mining and Warehousing", "Cloud Computing"},
            // 2nd Semester
            {"Mobile Computing", "Machine Learning", "Big Data Analytics", "Internet of Things", "Advanced Computer Networks"},
            // 3rd Semester
            {"Artificial Intelligence", "Cyber Security", "Data Science", "Distributed Systems", "Research Methodology"},
            // 4th Semester
            {"Dissertation", "Project Work", "Seminar", "Comprehensive Viva", "Research Paper"}
        });
        
        // MBA (Master of Business Administration)
        courseSubjects.put("MBA", new String[][] {
            // 1st Semester
            {"Managerial Economics", "Financial Management", "Marketing Management", "Human Resource Management", "Operations and Supply Chain Management"},
            // 2nd Semester
            {"Strategic Management", "Business Research Methods", "Organizational Behavior", "Entrepreneurship Development", "International Business"},
            // 3rd Semester
            {"Business Analytics", "Leadership and Change Management", "Corporate Governance", "Project Management", "Elective I"},
            // 4th Semester
            {"Business Ethics", "Strategic Marketing", "Financial Markets", "Elective II", "Dissertation"}
        });
        
        // MCA (Master of Computer Applications)
        courseSubjects.put("MCA", new String[][] {
            // 1st Semester
            {"Data Structures and Algorithms", "Object-Oriented Programming", "Database Management Systems", "Operating Systems", "Computer Networks"},
            // 2nd Semester
            {"Software Engineering", "Web Technologies", "Mobile Application Development", "Artificial Intelligence", "Cloud Computing"},
            // 3rd Semester
            {"Data Mining", "Machine Learning", "Internet of Things", "Cyber Security", "Advanced Java Programming"},
            // 4th Semester
            {"Project Work", "Seminar", "Comprehensive Viva", "Technical Writing", "Research Paper"}
        });
        
        // MCom (Master of Commerce)
        courseSubjects.put("MCom", new String[][] {
            // 1st Semester
            {"Advanced Financial Management", "Advanced Marketing Management", "Research Methodology", "Business Environment", "Managerial Economics"},
            // 2nd Semester
            {"Strategic Management", "Advanced Cost Accounting", "Corporate Tax Planning", "Financial Markets", "E-Commerce"},
            // 3rd Semester
            {"Investment Management", "International Finance", "Corporate Governance", "Business Analytics", "Elective I"},
            // 4th Semester
            {"Risk Management", "International Business", "Elective II", "Dissertation", "Comprehensive Viva"}
        });
        
        // MA (Master of Arts)
        courseSubjects.put("MA", new String[][] {
            // 1st Semester
            {"Research Methodology", "Literary Theory", "British Literature", "American Literature", "Indian Writing in English"},
            // 2nd Semester
            {"World Literature", "Postcolonial Literature", "Gender Studies", "Cultural Studies", "Linguistics"},
            // 3rd Semester
            {"Contemporary Literature", "Film Studies", "Literary Criticism", "Comparative Literature", "Elective I"},
            // 4th Semester
            {"Dissertation", "Seminar", "Elective II", "Comprehensive Viva", "Research Paper"}
        });
        
        // BA (Bachelor of Arts)
        courseSubjects.put("BA", new String[][] {
            // 1st Semester
            {"Microeconomics", "Macroeconomics", "Indian Economy", "Public Finance", "International Economics"},
            // 2nd Semester
            {"Development Economics", "Statistical Methods for Economics", "Econometrics", "Money and Banking", "Environmental Economics"},
            // 3rd Semester
            {"Economic History", "Political Economy", "Labor Economics", "Agricultural Economics", "Industrial Economics"},
            // 4th Semester
            {"Public Economics", "Game Theory", "Welfare Economics", "Financial Economics", "Urban Economics"},
            // 5th Semester
            {"International Trade", "Economic Development", "Demographic Economics", "Health Economics", "Project I"},
            // 6th Semester
            {"Behavioral Economics", "Experimental Economics", "Economics of Education", "Natural Resource Economics", "Project II"}
        });
    }
    
    // Helper method to populate subject fields based on selected semester and student's course
    private void populateSubjects() {
        try {
            // Get student's course
            String studentCourse = courseDisplay.getText();
            int semesterIndex = comboBox.getSelectedIndex();
            
            System.out.println("Populating subjects for course: '" + studentCourse + "', semester: " + (semesterIndex + 1));
            
            // Default to BBA-CA subjects if course not found or empty
            if (studentCourse == null || studentCourse.isEmpty()) {
                System.out.println("Course is empty, using default subjects");
                if (semesterIndex >= 0 && semesterIndex < semesterSubjects.length) {
                    String[] subjects = semesterSubjects[semesterIndex];
                    sub1.setText(subjects[0]);
                    sub2.setText(subjects[1]);
                    sub3.setText(subjects[2]);
                    sub4.setText(subjects[3]);
                    sub5.setText(subjects[4]);
                }
                return;
            }
            
            // Check if course exists in our map
            if (!courseSubjects.containsKey(studentCourse)) {
                System.out.println("Course '" + studentCourse + "' not found in map, available courses: " + 
                                    String.join(", ", courseSubjects.keySet()));
                
                // Try to find a close match (in case of case difference)
                String matchedCourse = null;
                for (String course : courseSubjects.keySet()) {
                    if (course.equalsIgnoreCase(studentCourse)) {
                        matchedCourse = course;
                        break;
                    }
                }
                
                if (matchedCourse != null) {
                    studentCourse = matchedCourse;
                    System.out.println("Found case-insensitive match: " + matchedCourse);
                } else {
                    // Fall back to default subjects
                    if (semesterIndex >= 0 && semesterIndex < semesterSubjects.length) {
                        String[] subjects = semesterSubjects[semesterIndex];
                        sub1.setText(subjects[0]);
                        sub2.setText(subjects[1]);
                        sub3.setText(subjects[2]);
                        sub4.setText(subjects[3]);
                        sub5.setText(subjects[4]);
                    }
                    return;
                }
            }
            
            // Get subject list for this course and semester
            String[][] subjectsForCourse = courseSubjects.get(studentCourse);
            
            // Make sure we have subjects for this semester
            if (semesterIndex >= 0 && semesterIndex < subjectsForCourse.length) {
                String[] subjects = subjectsForCourse[semesterIndex];
                System.out.println("Setting subjects for " + studentCourse + ", semester " + (semesterIndex + 1) + 
                                  ": " + String.join(", ", subjects));
                sub1.setText(subjects[0]);
                sub2.setText(subjects[1]);
                sub3.setText(subjects[2]);
                sub4.setText(subjects[3]);
                sub5.setText(subjects[4]);
            } else {
                // If semester not found, clear the fields
                System.out.println("Semester index " + semesterIndex + " out of bounds for course " + studentCourse);
                sub1.setText("");
                sub2.setText("");
                sub3.setText("");
                sub4.setText("");
                sub5.setText("");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error in populateSubjects: " + e.getMessage());
        }
    }

    // Helper method to fetch and display student name
    private void fetchAndDisplayStudentName(String studentId) {
        try {
            Conn c = new Conn();
            String query = "select name, course from student where id = '" + studentId + "'";
            ResultSet rs = c.statement.executeQuery(query);
            if (rs.next()) {
                studentNameDisplay.setText(rs.getString("name"));
                courseDisplay.setText(rs.getString("course"));
            } else {
                studentNameDisplay.setText("Name not found");
                courseDisplay.setText("Course not found");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            studentNameDisplay.setText("Error fetching data");
            courseDisplay.setText("Error fetching data");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submit){
            try {
                Conn c = new Conn();
                String studentId = choiceid.getSelectedItem();
                String semester = (String) comboBox.getSelectedItem();
                
                // Get the subject names
                String[] subjectInputs = {sub1.getText(), sub2.getText(), sub3.getText(), sub4.getText(), sub5.getText()};
                
                // Check if any subjects are empty
                String errorMessage = "";
                for (int i = 0; i < subjectInputs.length; i++) {
                    if (subjectInputs[i].trim().isEmpty()) {
                        errorMessage = "Subject " + (i+1) + " cannot be empty.";
                        break;
                    }
                }
                
                if (!errorMessage.isEmpty()) {
                    JOptionPane.showMessageDialog(null, errorMessage, "Validation Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                // Validate marks - ensure they are numbers between 0 and 100
                String[] markInputs = {mrk1.getText(), mrk2.getText(), mrk3.getText(), mrk4.getText(), mrk5.getText()};
                
                for (int i = 0; i < markInputs.length; i++) {
                    String markText = markInputs[i].trim();
                    String subjectName = subjectInputs[i].trim();
                    
                    // Check if empty
                    if (markText.isEmpty()) {
                        errorMessage = "Marks for '" + subjectName + "' cannot be empty.";
                        break;
                    }
                    
                    try {
                        int mark = Integer.parseInt(markText);
                        if (mark < 0) {
                            errorMessage = "Marks for '" + subjectName + "' cannot be negative.";
                            break;
                        }
                        if (mark > 100) {
                            errorMessage = "Marks for '" + subjectName + "' cannot be greater than 100.";
                            break;
                        }
                    } catch (NumberFormatException nfe) {
                        errorMessage = "Marks for '" + subjectName + "' must be a valid number.";
                        break;
                    }
                }
                
                if (!errorMessage.isEmpty()) {
                    JOptionPane.showMessageDialog(null, errorMessage, "Validation Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                System.out.println("Inserting data for student ID: " + studentId);
                System.out.println("Semester: " + semester);
                
                String Q1 = "insert into subject values('"+studentId+"', '"+semester+"','"+sub1.getText()+"','"+sub2.getText()+"', '"+sub3.getText()+"', '"+sub4.getText()+"', '"+sub5.getText()+"')";
                String Q2 = "insert into marks values('"+studentId+"', '"+semester+"','"+mrk1.getText()+"','"+mrk2.getText()+"', '"+mrk3.getText()+"', '"+mrk4.getText()+"', '"+mrk5.getText()+"')";

                System.out.println("Subject query: " + Q1);
                System.out.println("Marks query: " + Q2);

                c.statement.executeUpdate(Q1);
                c.statement.executeUpdate(Q2);
                JOptionPane.showMessageDialog(null,"Marks Inserted Successfully");
                setVisible(false);

            }catch (Exception E){
                E.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error: " + E.getMessage());
            }
        } else if (e.getSource() == cancel) {
            setVisible(false);
        }
    }

    public static void main(String[] args) {
        new EnterMarks();
    }
}
