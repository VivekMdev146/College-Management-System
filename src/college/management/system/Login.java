package college.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.ResultSet;


public class Login extends JFrame implements ActionListener {
    JTextField textFieldName;
    JPasswordField passwordField;
    JButton login, back;
    JCheckBox showPasswordCheckbox;
    
    Login(){
        setTitle("College Management System - Login");
        
        // Get screen dimensions to center the login panel
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int loginPanelWidth = 500;
        int loginPanelHeight = 400;
        int loginPanelX = (screenSize.width - loginPanelWidth) / 2;
        int loginPanelY = (screenSize.height - loginPanelHeight) / 2;
        
        // Main panel to hold everything
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBounds(0, 0, screenSize.width, screenSize.height);
        add(mainPanel);
        
        // Set up the login panel on top of the background
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(null);
        loginPanel.setBounds(loginPanelX, loginPanelY, loginPanelWidth, loginPanelHeight);
        loginPanel.setBackground(new Color(240, 240, 240, 220));
        loginPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(100, 100, 100), 1),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        mainPanel.add(loginPanel);

        JLabel heading = new JLabel("Login");
        heading.setBounds(200, 30, 100, 40);
        heading.setFont(new Font("Serif", Font.BOLD, 30));
        loginPanel.add(heading);

        JLabel labelName = new JLabel("Username");
        labelName.setBounds(100, 100, 100, 25);
        labelName.setFont(new Font("Serif", Font.PLAIN, 18));
        loginPanel.add(labelName);

        textFieldName = new JTextField();
        textFieldName.setBounds(220, 100, 200, 30);
        loginPanel.add(textFieldName);

        JLabel labelpass = new JLabel("Password");
        labelpass.setBounds(100, 160, 100, 25);
        labelpass.setFont(new Font("Serif", Font.PLAIN, 18));
        loginPanel.add(labelpass);

        passwordField = new JPasswordField();
        passwordField.setBounds(220, 160, 200, 30);
        loginPanel.add(passwordField);
        
        // Add show password checkbox
        showPasswordCheckbox = new JCheckBox("Show Password");
        showPasswordCheckbox.setBounds(220, 200, 150, 20);
        showPasswordCheckbox.setBackground(new Color(240, 240, 240, 220));
        showPasswordCheckbox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    // Show password as plain text
                    passwordField.setEchoChar((char) 0);
                } else {
                    // Hide password with default bullet character
                    passwordField.setEchoChar('•');
                }
            }
        });
        loginPanel.add(showPasswordCheckbox);

        login = new JButton("Login");
        login.setBounds(120, 250, 120, 35);
        login.setBackground(Color.black);
        login.setForeground(Color.yellow);
        login.setFont(new Font("Serif", Font.BOLD, 16));
        login.addActionListener(this);
        loginPanel.add(login);

        back = new JButton("Back");
        back.setBounds(270, 250, 120, 35);
        back.setBackground(Color.black);
        back.setForeground(Color.red);
        back.setFont(new Font("Serif", Font.BOLD, 16));
        back.addActionListener(this);
        loginPanel.add(back);
        
        // Background image for the whole screen
        ImageIcon backgroundIcon = new ImageIcon(ClassLoader.getSystemResource("icon/loginback.png"));
        

        Image img = backgroundIcon.getImage().getScaledInstance(screenSize.width, screenSize.height, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(img);
        JLabel background = new JLabel(scaledIcon);
        background.setBounds(0, 0, screenSize.width, screenSize.height);
        mainPanel.add(background);

        // Set to full screen
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == login){
            String username = textFieldName.getText();
            String password = new String(passwordField.getPassword());

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Username or Password cannot be empty");
                return;
            }
            
            String query = "select * from login where username='"+username+"' and password = '"+password+"'";
            try {
                Conn c = new Conn();
                ResultSet resultSet = c.statement.executeQuery(query);
                if (resultSet.next()){
                    // Store username in session
                    SessionManager.setCurrentUsername(username);
                    System.out.println("User successfully logged in: " + username);
                    
                    // Close this window and open main class
                    setVisible(false);
                    new main_class();
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null,"Invalid username or password");
                }
            } catch (Exception E){
                E.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error connecting to database: " + E.getMessage());
            }
        } else if (e.getSource() == back) {
            // Make sure any session is terminated when going back
            SessionManager.logout();
            System.out.println("Session terminated - returning to splash screen");
            
            // Go back to splash screen - create Splash screen first, then hide this one
            new Splash(); // Create new Splash screen first
            setVisible(false); // Hide this login window
            dispose(); // Release resources after new window is created
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        new Login();
    }
}
