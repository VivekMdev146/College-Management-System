package college.management.system;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import com.toedter.calendar.JDateChooser;

public class updateStudent extends JFrame implements ActionListener {
    JTextField textAddress,textPhone,textemail,textAadhar,textfather,textM10,textM12;
    JComboBox courseBox, departmentBox;
    JLabel idText, textName;
    JDateChooser cdob;

    JButton submit, cancel;
    Choice cid;
    updateStudent(){
        getContentPane().setBackground(new Color(230,210,252));

        JLabel heading = new JLabel("Update Student Details");
        heading.setBounds(50,10,500,50);
        heading.setFont(new Font("serif",Font.BOLD,35));
        add(heading);

        JLabel id = new JLabel("Select Colllege Id ");
        id.setBounds(50,100,200,20);
        id.setFont(new Font("serif", Font.PLAIN,20));
        add(id);

        cid = new Choice();
        cid.setBounds(250,100,200,20);
        add(cid);

        try{
            Conn c = new Conn();
            ResultSet rs = c.statement.executeQuery("select * from student");
            while (rs.next()){
                cid.add(rs.getString("id"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }


        JLabel name = new JLabel("Name");
        name.setBounds(50,150,100,30);
        name.setFont(new Font("serif",Font.BOLD,20));
        add(name);

        textName = new JLabel();
        textName.setBounds(200,150,150,30);
        add(textName);

        JLabel fname = new JLabel("Father Name");
        fname.setBounds(400,150,200,30);
        fname.setFont(new Font("serif",Font.BOLD,20));
        add(fname);

        textfather = new JTextField();
        textfather.setBounds(600,150,150,30);
        add(textfather);

        JLabel collegeId = new JLabel("College Id");
        collegeId.setBounds(50,200,200,30);
        collegeId.setFont(new Font("serif",Font.BOLD,20));
        add(collegeId);

        idText = new JLabel();
        idText.setBounds(200,200,150,30);
        idText.setFont(new Font("serif",Font.BOLD,20));
        add(idText);

        JLabel dob = new JLabel("Date of Birth");
        dob.setBounds(400,200,200,30);
        dob.setFont(new Font("serif",Font.BOLD,20));
        add(dob);

        cdob = new JDateChooser();
        cdob.setBounds(600,200,150,30);
        add(cdob);

        JLabel address = new JLabel("Address");
        address.setBounds(50,250,200,30);
        address.setFont(new Font("serif",Font.BOLD,20));
        add(address);

        textAddress = new JTextField();
        textAddress.setBounds(200,250,150,30);
        add(textAddress);

        JLabel phone = new JLabel("Phone");
        phone.setBounds(400,250,200,30);
        phone.setFont(new Font("serif",Font.BOLD,20));
        add(phone);

        textPhone = new JTextField();
        textPhone.setBounds(600,250,150,30);
        add(textPhone);

        JLabel email = new JLabel("Email");
        email.setBounds(50,300,200,30);
        email.setFont(new Font("serif",Font.BOLD,20));
        add(email);

        textemail = new JTextField();
        textemail.setBounds(200,300,150,30);
        add(textemail);

        JLabel M10 = new JLabel("Class X (%)");
        M10.setBounds(400,300,200,30);
        M10.setFont(new Font("serif",Font.BOLD,20));
        add(M10);

        textM10 = new JTextField();
        textM10.setBounds(600,300,150,30);
        add(textM10);

        JLabel M12 = new JLabel("Class XII (%)");
        M12.setBounds(50,350,200,30);
        M12.setFont(new Font("serif",Font.BOLD,20));
        add(M12);

        textM12 = new JTextField();
        textM12.setBounds(200,350,150,30);
        add(textM12);

        JLabel AadharNo = new JLabel("Aadhar Number");
        AadharNo.setBounds(400,350,200,30);
        AadharNo.setFont(new Font("serif",Font.BOLD,20));
        add(AadharNo);

        textAadhar = new JTextField();
        textAadhar.setBounds(600,350,150,30);
        add(textAadhar);

        JLabel Qualification = new JLabel("Course");
        Qualification.setBounds(50,400,200,30);
        Qualification.setFont(new Font("serif",Font.BOLD,20));
        add(Qualification);

        String course[] = {"BBA-CA","BCA","BBA","BSC","BCom","MSC","MBA","MCA","MCom","MA","BA"};
        courseBox = new JComboBox(course);
        courseBox.setBounds(200,400,150,30);
        courseBox.setBackground(Color.white);
        courseBox.setForeground(Color.black);
        add(courseBox);

        JLabel Department = new JLabel("Branch");
        Department.setBounds(400,400,200,30);
        Department.setFont(new Font("serif",Font.BOLD,20));
        add(Department);

        String department[] = {"Computer Application","Computer Science","Commerce","IT","Arts"};
        departmentBox = new JComboBox(department);
        departmentBox.setBounds(600,400,150,30);
        departmentBox.setBackground(Color.white);
        departmentBox.setForeground(Color.black);
        add(departmentBox);

        try{
            Conn c = new Conn();
            String query = "select * from student where id = '"+cid.getSelectedItem()+"'";
            ResultSet resultSet = c.statement.executeQuery(query);
            while (resultSet.next()){
                textName.setText(resultSet.getString("name"));
                textfather.setText(resultSet.getString("fname"));
                try {
                    String dobString = resultSet.getString("dob");
                    // Try different date formats
                    java.util.Date date = null;
                    try {
                        // Try yyyy-MM-dd format
                        date = new SimpleDateFormat("yyyy-MM-dd").parse(dobString);
                    } catch (Exception e1) {
                        try {
                            // Try d MMM yyyy format
                            date = new SimpleDateFormat("d MMM yyyy").parse(dobString);
                        } catch (Exception e2) {
                            try {
                                // Try dd MMM yyyy format
                                date = new SimpleDateFormat("dd MMM yyyy").parse(dobString);
                            } catch (Exception e3) {
                                // If all parse attempts fail, just set current date
                                date = new java.util.Date();
                            }
                        }
                    }
                    cdob.setDate(date);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                textAddress.setText(resultSet.getString("address"));
                textPhone.setText(resultSet.getString("phone"));
                textemail.setText(resultSet.getString("email"));
                textM10.setText(resultSet.getString("M10"));
                textM12.setText(resultSet.getString("M12"));
                textAadhar.setText(resultSet.getString("aadhar"));
                idText.setText(resultSet.getString("id"));
                courseBox.setSelectedItem(resultSet.getString("course"));
                departmentBox.setSelectedItem(resultSet.getString("department"));
            }

        }catch (Exception E){
            E.printStackTrace();
        }

        cid.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                try {
                    Conn c = new Conn();
                    String query = "select * from student where id = '"+cid.getSelectedItem()+"'";
                    ResultSet resultSet = c.statement.executeQuery(query);
                    while (resultSet.next()) {
                        textName.setText(resultSet.getString("name"));
                        textfather.setText(resultSet.getString("fname"));
                        try {
                            String dobString = resultSet.getString("dob");
                            // Try different date formats
                            java.util.Date date = null;
                            try {
                                // Try yyyy-MM-dd format
                                date = new SimpleDateFormat("yyyy-MM-dd").parse(dobString);
                            } catch (Exception e1) {
                                try {
                                    // Try d MMM yyyy format
                                    date = new SimpleDateFormat("d MMM yyyy").parse(dobString);
                                } catch (Exception e2) {
                                    try {
                                        // Try dd MMM yyyy format
                                        date = new SimpleDateFormat("dd MMM yyyy").parse(dobString);
                                    } catch (Exception e3) {
                                        // If all parse attempts fail, just set current date
                                        date = new java.util.Date();
                                    }
                                }
                            }
                            cdob.setDate(date);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                        textAddress.setText(resultSet.getString("address"));
                        textPhone.setText(resultSet.getString("phone"));
                        textemail.setText(resultSet.getString("email"));
                        textM10.setText(resultSet.getString("M10"));
                        textM12.setText(resultSet.getString("M12"));
                        textAadhar.setText(resultSet.getString("aadhar"));
                        idText.setText(resultSet.getString("id"));
                        courseBox.setSelectedItem(resultSet.getString("course"));
                        departmentBox.setSelectedItem(resultSet.getString("department"));
                    }
                }catch (Exception E){
                    E.printStackTrace();
                }
            }
        });



        submit = new JButton("Update");
        submit.setBounds(250,550,120,30);
        submit.setBackground(Color.black);
        submit.setForeground(Color.white);
        submit.addActionListener(this);
        add(submit);

        cancel = new JButton("Cancel");
        cancel.setBounds(450,550,120,30);
        cancel.setBackground(Color.black);
        cancel.setForeground(Color.white);
        cancel.addActionListener(this);
        add(cancel);

        setSize(900,700);
        setLocation(350,50);
        setLayout(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submit){
            String id = idText.getText();
            String fname = textfather.getText();
            String dob = ((JTextField) cdob.getDateEditor().getUiComponent()).getText();
            String address = textAddress.getText();
            String phone = textPhone.getText();
            String email = textemail.getText();
            String M10 = textM10.getText();
            String M12 = textM12.getText();
            String aadhar = textAadhar.getText();
            String course = (String) courseBox.getSelectedItem();
            String department = (String) departmentBox.getSelectedItem();

            // Validate input fields
            String validationMessage = ValidationUtils.getValidationMessage(phone, email, aadhar);
            if (!validationMessage.isEmpty()) {
                JOptionPane.showMessageDialog(null, validationMessage);
                return;
            }

            try {
                String query = "update student set fname = '"+fname+"', dob = '"+dob+"', address = '"+address+"', phone = '"+phone+"', email = '"+email+"', M10 = '"+M10+"', M12 = '"+M12+"', aadhar = '"+aadhar+"', course = '"+course+"', department = '"+department+"' where id = '"+cid.getSelectedItem()+"'";
                Conn c = new Conn();
                c.statement.executeUpdate(query);
                JOptionPane.showMessageDialog(null, "Student Details Updated Successfully");
                setVisible(false);
            }catch (Exception E){
                E.printStackTrace();
            }
        }else {
            setVisible(false);
        }
    }

    public static void main(String[] args) {
        new updateStudent();
    }
}
