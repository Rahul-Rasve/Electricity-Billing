package electricity.billing.system.dropDownFrames;

import electricity.billing.system.frames.AdminFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NewCustomer extends JFrame implements ActionListener {

    private int meterNumberValue = (int) (Math.random() * 90000) + 10000;

    private JButton nextButton, cancelButton;

    private JTextField newCustomerText, addressText, cityText, stateText, emailText, phoneText;

    public NewCustomer(){
        super("New Customer");

        getContentPane().setBackground(Color.ORANGE);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int frameWidth = screenSize.width/2;
        int frameHeight = screenSize.height/2;
        int labelX = frameWidth/2+70;
        int fieldX = frameWidth/2+frameWidth/4;
        int labelWidth = frameWidth/8;
        int fieldWidth = frameWidth/6;
        int labelFieldHeight = frameHeight/20;

        //left image
        ImageIcon avatar = new ImageIcon(ClassLoader.getSystemResource("images/avatar.png"));
        ImageIcon croppedImage = new ImageIcon(avatar.getImage().getScaledInstance(frameWidth/2, frameHeight+frameHeight/8, Image.SCALE_SMOOTH));
        JLabel image = new JLabel(croppedImage);
        image.setBounds(0, 0, frameWidth/2, frameHeight+frameHeight/8);
        add(image);

        //heading
        JLabel heading = new JLabel("New Customer");
        heading.setFont(new Font("Arial Black", Font.PLAIN, frameWidth/40));
        heading.setBounds(2*frameWidth/3, frameHeight/70, frameWidth/3, frameHeight/10);
        add(heading);

        //labels and fields
        JLabel newCustomer = new JLabel("New Customer:");
        newCustomer.setBounds(labelX, frameHeight/20+30, labelWidth, labelFieldHeight);
        add(newCustomer);

        newCustomerText = new JTextField(20);
        newCustomerText.setBounds(fieldX, frameHeight/20+30, fieldWidth, labelFieldHeight);
        add(newCustomerText);

        JLabel meterNumber = new JLabel("Meter Number:");
        meterNumber.setBounds(labelX, frameHeight/20+80, labelWidth, labelFieldHeight);
        add(meterNumber);

        JLabel meterNumText = new JLabel("%s".formatted(meterNumberValue));
        meterNumText.setBounds(fieldX, frameHeight/20+80, fieldWidth, labelFieldHeight);
        add(meterNumText);

        JLabel address = new JLabel("Address:");
        address.setBounds(labelX, frameHeight/20+130, labelWidth, labelFieldHeight);
        add(address);

        addressText = new JTextField(40);
        addressText.setBounds(fieldX, frameHeight/20+130, fieldWidth, labelFieldHeight);
        add(addressText);

        JLabel city = new JLabel("City:");
        city.setBounds(labelX, frameHeight/20+180, labelWidth, labelFieldHeight);
        add(city);

        cityText = new JTextField(20);
        cityText.setBounds(fieldX, frameHeight/20+180, fieldWidth, labelFieldHeight);
        add(cityText);

        JLabel state = new JLabel("State:");
        state.setBounds(labelX, frameHeight/20+230, labelWidth, labelFieldHeight);
        add(state);

        stateText = new JTextField(20);
        stateText.setBounds(fieldX, frameHeight/20+230, fieldWidth, labelFieldHeight);
        add(stateText);

        JLabel email = new JLabel("Email:");
        email.setBounds(labelX, frameHeight/20+280, labelWidth, labelFieldHeight);
        add(email);

        emailText = new JTextField(20);
        emailText.setBounds(fieldX, frameHeight/20+280, fieldWidth, labelFieldHeight);
        add(emailText);

        JLabel phone = new JLabel("Phone:");
        phone.setBounds(labelX, frameHeight/20+330, labelWidth, labelFieldHeight);
        add(phone);

        phoneText = new JTextField(20);
        phoneText.setBounds(fieldX, frameHeight/20+330, fieldWidth, labelFieldHeight);
        add(phoneText);

        //buttons
        nextButton = new JButton("Next");
        nextButton.setBounds(labelX+20, frameHeight/20+380, frameWidth/8, labelFieldHeight);
        nextButton.setBackground(Color.BLACK);
        nextButton.setForeground(Color.white);
        nextButton.addActionListener(this);
        add(nextButton);

        cancelButton = new JButton("Cancel");
        cancelButton.setBounds(labelX+150, frameHeight/20+380, frameWidth/8, labelFieldHeight);
        cancelButton.setBackground(Color.BLACK);
        cancelButton.setForeground(Color.white);
        cancelButton.addActionListener(this);
        add(cancelButton);

        setResizable(false);
        setSize(frameWidth, frameHeight + frameHeight/8);
        setLocationRelativeTo(null);
        setLayout(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if(event.getSource() == cancelButton){
            dispose();
            new AdminFrame();
        }
        else{
            dispose();
        }
    }

    public static void main(String[] args) {
        new NewCustomer();
    }
}
