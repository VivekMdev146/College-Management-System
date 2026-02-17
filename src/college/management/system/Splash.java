package college.management.system;
import javax.swing.*;
import java.awt.*;

public class Splash extends JFrame implements Runnable {
    Thread t;
    JLabel Text;

    // Add Image
    Splash(){
        setTitle("College Management System");
        
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icon/first.png"));
        Image i2 = i1.getImage().getScaledInstance(1200, 900, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel img = new JLabel(i3);
        add(img);

        // Adding text
        Text = new JLabel("Welcome VMD Technical College", JLabel.CENTER);
        Text.setFont(new Font("Serif", Font.BOLD, 40));
        Text.setForeground(new Color(255, 255, 255, 0)); // Initially Transparent
        Text.setBounds(500, 400, 200, 50);
        img.add(Text);

        t = new Thread(this);
        t.start();
        
        // Set to full screen mode
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        // Animate Screen
        int x = 1;
        for (int i = 2; i <= 600; i += 4, x += 1) {
            setLocation(600 - ((i + x) / 2), 350 - (i / 2));
            setSize(i + 3 * x, i + x / 2);
            try {
                Thread.sleep(3);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // Animate Text
        animateText();
    }

    private void animateText() {
        for (int alpha = 0; alpha <= 255; alpha += 5) {
            // Gradually increase opacity
            Text.setForeground(new Color(255, 255, 255, alpha));
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void run() {
        try {
            Thread.sleep(5000);
            setVisible(false);
            // Redirect to Login instead of main_class for proper flow
            new Login();
            dispose(); // Close the splash screen properly
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        new Splash();
    }
}