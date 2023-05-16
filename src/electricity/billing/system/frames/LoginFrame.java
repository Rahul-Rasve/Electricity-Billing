package electricity.billing.system.frames;

import electricity.billing.system.server.Database;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.util.Objects;

public class LoginFrame extends JFrame implements ActionListener {

    private final JTextField usernameText;
    private final JTextField passwordText;
    private final JComboBox<String> userTypeOption;

    private final JButton loginButton, cancelButton, signupButton;

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

        JLabel signupMsg = new JLabel("Don't have an account? SignUp here.");
        signupMsg.setBounds(frameWidth/2+frameWidth/8 ,frameHeight/8+230, frameWidth/2, frameHeight/20);
        add(signupMsg);

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

    public boolean checkForNull(){
        return !usernameText.getText().equals("") &&
                !passwordText.getText().equals("");
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if(event.getSource() == signupButton){
            dispose();
            new SignUpFrame();
        }
        else if(event.getSource() == cancelButton){
            dispose();
        }
        else if(event.getSource() == loginButton){
            if(checkForNull()){
                String user = userTypeOption.getSelectedItem().toString();
                try {
                    Database db = new Database();
                    String searchQuery = "SELECT * FROM Users WHERE username = '%s'; "
                            .formatted(usernameText.getText());
                    ResultSet searchResult = db.statement.executeQuery(searchQuery);

                    if(searchResult.next()) {
                        String username = searchResult.getString("username");
                        String password = searchResult.getString("password");
                        String userChoice = searchResult.getString("usertype");
                        String meterNum = searchResult.getString("userid");

                        if (usernameText.getText().equals(username) &&
                                passwordText.getText().equals(password) &&
                                userTypeOption.getSelectedItem().toString().equals(userChoice)
                        ) {
                            JOptionPane.showMessageDialog(null, "Login Successful", "Success", JOptionPane.INFORMATION_MESSAGE);
                            dispose();
                            new AdminFrame(userChoice, meterNum);
                        }
                        else{
                            JOptionPane.showMessageDialog(null, "Invalid Credentials. Try again!", "Failed", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "No User Found!", "Failed", JOptionPane.ERROR_MESSAGE);
                    }
                } catch(Exception e){
                    JOptionPane.showMessageDialog(null, "Network Failure. Try again!", "Error", JOptionPane.ERROR_MESSAGE);
                    System.out.println(e.toString());
                }
            }
            else{
                JOptionPane.showMessageDialog(null, "Something is missing. Try again!", "Failed", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        new LoginFrame();
    }
}