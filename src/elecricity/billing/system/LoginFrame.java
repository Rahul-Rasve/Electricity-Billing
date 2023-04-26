package elecricity.billing.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class LoginFrame extends JFrame implements ActionListener {

    private JTextField usernameText, passwordText;
    private JComboBox<String> userTypeOption;

    private JButton loginButton, cancelButton, signupButton;

    public String getUsernameText() {
        return usernameText.getText();
    }

    public String getPasswordText() {
        return passwordText.getText();
    }

    public String getUserTypeOption() {
        return Objects.requireNonNull(userTypeOption.getSelectedItem()).toString();
    }

    LoginFrame() {
        super("Login");
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

        usernameText = new JTextField(20);
        usernameText.setBounds(frameWidth/2+frameWidth/5 ,frameHeight/8, frameWidth/6, frameHeight/20);
        add(usernameText);

        JLabel password = new JLabel("Password:");
        password.setBounds(frameWidth/2+70, frameHeight/8+60, frameWidth/8, frameHeight/20);
        add(password);

        passwordText = new JPasswordField(20);
        passwordText.setBounds(frameWidth/2+frameWidth/5 ,frameHeight/8+60, frameWidth/6, frameHeight/20);
        add(passwordText);

        JLabel userType = new JLabel("Login As:");
        userType.setBounds(frameWidth/2+70, frameHeight/8+120, frameWidth/8, frameHeight/20);
        add(userType);

        String[] options = {"Admin", "Customer"};
        userTypeOption = new JComboBox<>(options);
        userTypeOption.setBounds(frameWidth/2+frameWidth/5 ,frameHeight/8+120, frameWidth/6, frameHeight/20);
        add(userTypeOption);

        loginButton = new JButton("Login");
        loginButton.setBounds(frameWidth/2+frameWidth/9 ,frameHeight/8+180, frameWidth/8, frameHeight/20);
        loginButton.addActionListener(this);
        add(loginButton);

        signupButton = new JButton("SignUp");
        signupButton.setBounds(frameWidth/2+frameWidth/5 ,frameHeight/8+250, frameWidth/8, frameHeight/20);
        signupButton.addActionListener(this);
        add(signupButton);

        cancelButton = new JButton("Cancel");
        cancelButton.setBounds(frameWidth/2+frameWidth/4+20 ,frameHeight/8+180, frameWidth/8, frameHeight/20);
        cancelButton.addActionListener(this);
        add(cancelButton);

        //frame parameters
        setResizable(false);
        setSize(frameWidth, frameHeight);
        setLocationRelativeTo(null);
        setLayout(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == signupButton){
            dispose();
            new SignUpFrame();
        }
        else if(e.getSource() == cancelButton){
            dispose();
        }
        else{

        }
    }

    public static void main(String[] args) {
        new LoginFrame();
    }
}