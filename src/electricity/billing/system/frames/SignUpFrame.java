package electricity.billing.system.frames;

import electricity.billing.system.server.Database;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class SignUpFrame extends JFrame implements ActionListener {

    private final JComboBox<String> optionValue;

    private final JButton signupButton, backButton;

    private final JTextField userId, userNameText, nameText, passwordText, confirmPasswordText;

    SignUpFrame(){
        super("Sign-Up");
        getContentPane().setBackground(new Color(176, 196, 222));

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int frameWidth = screenSize.width/2;
        int frameHeight = screenSize.height/2;

        ImageIcon avatar = new ImageIcon(ClassLoader.getSystemResource("images/avatar.png"));
        ImageIcon croppedImage = new ImageIcon(avatar.getImage().getScaledInstance(frameWidth/2, frameHeight, Image.SCALE_SMOOTH));

        JLabel avatarLabel = new JLabel(croppedImage);
        avatarLabel.setBounds(0,0, frameWidth/2, frameHeight);
        add(avatarLabel);

        //frame title
        JLabel frameTitle = new JLabel("Create Account");
        frameTitle.setFont(new Font("Arial", Font.PLAIN, 18));
        frameTitle.setBounds(frameWidth/2+frameWidth/6, frameHeight/40, frameWidth/5, frameHeight/16);
        add(frameTitle);

        //text inputs and labels
        JLabel userType = new JLabel("Signup As:");
        userType.setBounds(frameWidth/2+70, frameHeight/8, frameWidth/8, frameHeight/20);
        add(userType);

        String[] options = new String[]{"Admin", "Customer"};
        optionValue = new JComboBox<>(options);
        optionValue.setBounds(frameWidth/2+frameWidth/4, frameHeight/8, frameWidth/6, frameHeight/20);
        add(optionValue);

        JLabel meterNumber = new JLabel("Meter Number:");
        meterNumber.setBounds(frameWidth/2+70, frameHeight/10+80, frameWidth/8, frameHeight/20);
        meterNumber.setVisible(false);
        add(meterNumber);

        JLabel employeeId = new JLabel("Employee Id:");
        employeeId.setBounds(frameWidth/2+70, frameHeight/10+80, frameWidth/8, frameHeight/20);
        employeeId.setVisible(true);
        add(employeeId);

        userId = new JTextField(5);
        userId.setBounds(frameWidth/2+frameWidth/4, frameHeight/10+80, frameWidth/6, frameHeight/20);
        userId.setVisible(true);
        add(userId);

        JLabel userName = new JLabel("Username:");
        userName.setBounds(frameWidth/2+70, frameHeight/10+120, frameWidth/8, frameHeight/20);
        add(userName);

        userNameText = new JTextField(20);
        userNameText.setBounds(frameWidth/2+frameWidth/4, frameHeight/10+120, frameWidth/6, frameHeight/20);
        add(userNameText);

        JLabel name = new JLabel("Name:");
        name.setBounds(frameWidth/2+70, frameHeight/10+160, frameWidth/8, frameHeight/20);
        add(name);

        nameText = new JTextField(20);
        nameText.setBounds(frameWidth/2+frameWidth/4, frameHeight/10+160, frameWidth/6, frameHeight/20);
        add(nameText);

        JLabel password = new JLabel("Password:");
        password.setBounds(frameWidth/2+70, frameHeight/10+200, frameWidth/8, frameHeight/20);
        add(password);

        passwordText = new JPasswordField(20);
        passwordText.setBounds(frameWidth/2+frameWidth/4, frameHeight/10+200, frameWidth/6, frameHeight/20);
        add(passwordText);

        JLabel confirmPassword = new JLabel("Confirm Password:");
        confirmPassword.setBounds(frameWidth/2+70, frameHeight/10+240, frameWidth/8, frameHeight/20);
        add(confirmPassword);

        confirmPasswordText = new JPasswordField(20);
        confirmPasswordText.setBounds(frameWidth/2+frameWidth/4, frameHeight/10+240, frameWidth/6, frameHeight/20);
        add(confirmPasswordText);

        signupButton = new JButton("Sign-Up");
        signupButton.setBounds(frameWidth/2+frameWidth/9, frameHeight/10+300, frameWidth/8, frameHeight/20);
        signupButton.addActionListener(this);
        add(signupButton);

        backButton = new JButton("Back");
        backButton.setBounds(frameWidth/2+frameWidth/4, frameHeight/10+300, frameWidth/8, frameHeight/20);
        backButton.addActionListener(this);
        add(backButton);

        optionValue.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(optionValue.getSelectedItem() == "Admin"){
                    meterNumber.setVisible(false);
                    employeeId.setVisible(true);
                }
                else{
                    employeeId.setVisible(false);
                    meterNumber.setVisible(true);
                }
            }
        });

        //frame parameters
        setResizable(false);
        setSize(frameWidth, frameHeight);
        setLocationRelativeTo(null);
        setLayout(null);
        setVisible(true);
    }

    public boolean checkForNull(){
        return !userId.getText().equals("") &&
                !userNameText.getText().equals("") &&
                !nameText.getText().equals("") &&
                !passwordText.getText().equals("") &&
                !confirmPasswordText.getText().equals("");
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if(event.getSource() == signupButton){
            if(passwordText.getText().equals(confirmPasswordText.getText())){
                if(!checkForNull()){
                    JOptionPane.showMessageDialog(null, "Something is missing. Try again!", "Failed", JOptionPane.WARNING_MESSAGE);
                }
                else {
                    String user = optionValue.getSelectedItem().toString();
                    try {
                        Database db = new Database();
                        String insertQuery = "INSERT INTO Users VALUES ('%s', '%s', '%s', '%s', '%s');"
                                .formatted(userId.getText(), userNameText.getText(), nameText.getText(), passwordText.getText(), user);
                        db.statement.executeUpdate(insertQuery);

                        JOptionPane.showMessageDialog(null, "Sign-Up Successful", "Success", JOptionPane.INFORMATION_MESSAGE);

                        dispose();
                        new LoginFrame();
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, "Sign-Up Failed", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
            else{
                JOptionPane.showMessageDialog(null, "Passwords don't match. Try again!", "Failed", JOptionPane.ERROR_MESSAGE);
            }
        }
        else if(event.getSource() == backButton){
            dispose();
            new LoginFrame();
        }
    }

    public static void main(String[] args) {
        new SignUpFrame();
    }
}
