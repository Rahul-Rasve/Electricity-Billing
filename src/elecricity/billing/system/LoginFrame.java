package elecricity.billing.system;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {

    LoginFrame() {
        super("Login Page");
        getContentPane().setBackground(new Color(176, 196, 222)); //blue grey color

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int frameWidth = screenSize.width/2;
        int frameHeight = screenSize.height/2;


        ImageIcon avatar = new ImageIcon(ClassLoader.getSystemResource("images/avatar.png"));
        ImageIcon croppedImage = new ImageIcon(avatar.getImage().getScaledInstance(frameWidth/2, frameHeight, Image.SCALE_SMOOTH));

        JLabel avatarLabel = new JLabel(croppedImage);
        avatarLabel.setBounds(0,0, frameWidth/2, frameHeight);
        add(avatarLabel);

        //input fields
        JLabel username = new JLabel("Username:");
        username.setBounds(frameWidth/2+70, frameHeight/8, frameWidth/8, frameHeight/20);
        add(username);

        JTextField usernameText = new JTextField(20);
        usernameText.setBounds(frameWidth/2+frameWidth/5 ,frameHeight/8, frameWidth/6, frameHeight/20);
        add(usernameText);

        JLabel password = new JLabel("Password:");
        password.setBounds(frameWidth/2+70, frameHeight/8+60, frameWidth/8, frameHeight/20);
        add(password);

        JPasswordField passwordText = new JPasswordField(20);
        passwordText.setBounds(frameWidth/2+frameWidth/5 ,frameHeight/8+60, frameWidth/6, frameHeight/20);
        add(passwordText);

        JLabel userType = new JLabel("Login As:");
        userType.setBounds(frameWidth/2+70, frameHeight/8+120, frameWidth/8, frameHeight/20);
        add(userType);

        //frame parameters
        setResizable(false);
        setSize(frameWidth, frameHeight);
        setLocationRelativeTo(null);
        setLayout(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        new LoginFrame();
    }
}