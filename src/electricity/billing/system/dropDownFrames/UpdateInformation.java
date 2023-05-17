package electricity.billing.system.dropDownFrames;

import electricity.billing.system.frames.AdminFrame;
import electricity.billing.system.server.Database;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

public class UpdateInformation extends JFrame implements ActionListener {

    public JTextField nameValue, addressValue, cityValue, stateValue, emailValue, phoneValue;

    public JLabel meterNumValue;

    public String meterNumber;

    public JButton submitButton, cancelButton;

    public UpdateInformation(String meterNumber){
        super("Update Information");
        this.meterNumber = meterNumber;

        getContentPane().setBackground(Color.WHITE);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int frameWidth = screenSize.width/2 + screenSize.width/5;
        int frameHeight = screenSize.height/2 + screenSize.height/5;

        //heading
        JLabel heading = new JLabel("Update Customer Information");
        heading.setBounds(frameWidth/4+50, frameHeight/60, frameWidth, frameHeight/10);
        heading.setFont(new Font("Arial Black", Font.BOLD, frameWidth/40));
        add(heading);

        //labels
        JLabel name = new JLabel("Name: ");
        name.setBounds(frameWidth/10, frameHeight/5, frameWidth/8, frameHeight/20);
        name.setFont(new Font("Arial", Font.PLAIN, frameWidth/70));
        add(name);

        nameValue = new JTextField();
        nameValue.setBounds(frameWidth/10+frameWidth/9, frameHeight/5, frameWidth/6, frameHeight/20);
        nameValue.setFont(new Font("Arial", Font.PLAIN, frameWidth/70));
        add(nameValue);

        JLabel meterNum = new JLabel("Meter Number: ");
        meterNum.setBounds(frameWidth/10, frameHeight/5+60, frameWidth/8, frameHeight/20);
        meterNum.setFont(new Font("Arial", Font.PLAIN, frameWidth/70));
        add(meterNum);

        meterNumValue = new JLabel();
        meterNumValue.setBounds(frameWidth/10+frameWidth/9, frameHeight/5+60, frameWidth/6, frameHeight/20);
        meterNumValue.setFont(new Font("Arial", Font.PLAIN, frameWidth/70));
        add(meterNumValue);

        JLabel address = new JLabel("Address: ");
        address.setBounds(frameWidth/10, frameHeight/5+120, frameWidth/8, frameHeight/20);
        address.setFont(new Font("Arial", Font.PLAIN, frameWidth/70));
        add(address);

        addressValue = new JTextField();
        addressValue.setBounds(frameWidth/10+frameWidth/9, frameHeight/5+120, frameWidth/6, frameHeight/20);
        addressValue.setFont(new Font("Arial", Font.PLAIN, frameWidth/70));
        add(addressValue);

        JLabel city = new JLabel("City: ");
        city.setBounds(frameWidth/10, frameHeight/5+180, frameWidth/8, frameHeight/20);
        city.setFont(new Font("Arial", Font.PLAIN, frameWidth/70));
        add(city);

        cityValue = new JTextField();
        cityValue.setBounds(frameWidth/10+frameWidth/9, frameHeight/5+180, frameWidth/6, frameHeight/20);
        cityValue.setFont(new Font("Arial", Font.PLAIN, frameWidth/70));
        add(cityValue);

        JLabel state = new JLabel("State: ");
        state.setBounds(frameWidth/10+frameWidth/2+30, frameHeight/5, frameWidth/8, frameHeight/20);
        state.setFont(new Font("Arial", Font.PLAIN, frameWidth/70));
        add(state);

        stateValue = new JTextField();
        stateValue.setBounds(frameWidth/10+frameWidth/2+frameWidth/10, frameHeight/5, frameWidth/6, frameHeight/20);
        stateValue.setFont(new Font("Arial", Font.PLAIN, frameWidth/70));
        add(stateValue);

        JLabel email = new JLabel("Email: ");
        email.setBounds(frameWidth/10+frameWidth/2+30, frameHeight/5+60, frameWidth/8, frameHeight/20);
        email.setFont(new Font("Arial", Font.PLAIN, frameWidth/70));
        add(email);

        emailValue = new JTextField();
        emailValue.setBounds(frameWidth/10+frameWidth/2+frameWidth/10, frameHeight/5+60, frameWidth/6, frameHeight/20);
        emailValue.setFont(new Font("Arial", Font.PLAIN, frameWidth/70));
        add(emailValue);

        JLabel phone = new JLabel("Phone: ");
        phone.setBounds(frameWidth/10+frameWidth/2+30, frameHeight/5+120, frameWidth/8, frameHeight/20);
        phone.setFont(new Font("Arial", Font.PLAIN, frameWidth/70));
        add(phone);

        phoneValue = new JTextField();
        phoneValue.setBounds(frameWidth/10+frameWidth/2+frameWidth/10, frameHeight/5+120, frameWidth/6, frameHeight/20);
        phoneValue.setFont(new Font("Arial", Font.PLAIN, frameWidth/70));
        add(phoneValue);

        //button
        submitButton = new JButton("Submit");
        submitButton.setBounds(frameWidth/10+frameWidth/4+40, frameHeight/5+220, frameWidth/13, frameHeight/25);
        submitButton.addActionListener(this);
        add(submitButton);

        cancelButton = new JButton("Cancel");
        cancelButton.setBounds(frameWidth/10+frameWidth/3+80, frameHeight/5+220, frameWidth/13, frameHeight/25);
        cancelButton.addActionListener(this);
        add(cancelButton);

        //bottom image
        ImageIcon bottomImage = new ImageIcon(ClassLoader.getSystemResource("images/view_info.png"));
        ImageIcon cropped = new ImageIcon(bottomImage.getImage().getScaledInstance(frameWidth/2+100, frameHeight/2+100, Image.SCALE_SMOOTH));

        JLabel imageLabel = new JLabel(cropped);
        imageLabel.setBounds(0, frameHeight/3+120, frameWidth, frameHeight/2+100);
        add(imageLabel);

        try {
            //get data from database
            Database db = new Database();
            String query = "SELECT * FROM NewCustomer WHERE meterno = '%s';"
                    .formatted(meterNumber);
            ResultSet resultSet = db.statement.executeQuery(query);
            if(resultSet.next()){
                nameValue.setText(resultSet.getString("name"));
                meterNumValue.setText(resultSet.getString("meterno"));
                addressValue.setText(resultSet.getString("address"));
                cityValue.setText(resultSet.getString("city"));
                stateValue.setText(resultSet.getString("state"));
                emailValue.setText(resultSet.getString("email"));
                phoneValue.setText(resultSet.getString("phone"));
            }
        } catch (Exception e) {
            System.out.println(e.toString());
            System.out.println("error in getting info");
        }

        //frame parameters
        setResizable(false);
        setSize(frameWidth, frameHeight);
        setLocationRelativeTo(null);
        setLayout(null);
        setVisible(true);
    }

    public boolean nullCheck(){
        return !nameValue.getText().equals("") &&
                !addressValue.getText().equals("") &&
                !cityValue.getText().equals("") &&
                !stateValue.getText().equals("") &&
                !emailValue.getText().equals("") &&
                !phoneValue.getText().equals("");
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == cancelButton){
            dispose();
            new AdminFrame("Customer", meterNumber);
        }
        else if(event.getSource() == submitButton){
            try {
                if(nullCheck()) {
                    Database db = new Database();
                    String updateQuery = "UPDATE NewCustomer SET name='%s', address='%s', city='%s', state='%s', email='%s', phone='%s' WHERE meterno = '%s'"
                            .formatted(nameValue.getText(), addressValue.getText(), cityValue.getText(), stateValue.getText(), emailValue.getText(), phoneValue.getText(), meterNumber);
                    int result = db.statement.executeUpdate(updateQuery);
                    if (result > 0) {
                        JOptionPane.showMessageDialog(null, "Update Successful", "Success", JOptionPane.INFORMATION_MESSAGE);
                        dispose();
                        new AdminFrame("Customer", meterNumber);
                    }
                }
                else {
                    JOptionPane.showMessageDialog(null, "Check for empty fields of Internet Connection", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                System.out.println(e.toString());
                System.out.println("error in submit button");
            }
        }
    }

    public static void main(String[] args) {
        new UpdateInformation("42822");
    }
}
