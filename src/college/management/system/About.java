package college.management.system;

import javax.swing.*;
import java.awt.*;

public class About extends JFrame {
    About(){

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icon/about.png"));
        Image i2 = i1.getImage().getScaledInstance(300, 200, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel img = new JLabel(i3);
        img.setBounds(400,0,300,200);
        add(img);

        JLabel heading = new JLabel("<html> VivekMdev Technical </br> College </html>");
        heading.setBounds(70,20,300,130);
        heading.setFont(new Font("Tahoma", Font.BOLD,30));
        add(heading);

        JLabel name = new JLabel("Vivek Kumar Sahni");
        name.setBounds(60,260,550,40);
        name.setFont(new Font("Tahoma", Font.BOLD,30));
        add(name);

        JLabel contact = new JLabel("viveksahni2087@gmail.com");
        contact.setBounds(70,340,550,40);
        contact.setFont(new Font("Tahoma", Font.BOLD,30));
        add(contact);

        JLabel Mobile = new JLabel("+91 7520107704");
        Mobile.setBounds(90,390,550,40);
        Mobile.setFont(new Font("Tahoma", Font.BOLD,30));
        add(Mobile);

        setSize(700,500);
        setLocation(400,150);
        getContentPane().setBackground(new Color(252,228,210));
        setLayout(null);
        setVisible(true);

    }
    public static void main(String[] args) {
        new About();
    }
}
