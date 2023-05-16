package electricity.billing.system.dropDownFrames;

import electricity.billing.system.frames.AdminFrame;
import electricity.billing.system.server.Database;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

public class ViewInformation extends JFrame implements ActionListener {

    public JLabel nameValue, meterNumValue, addressValue, cityValue, stateValue, emailValue, phoneValue;

    public String meterNumber;

    public JButton closeButton;

    public ViewInformation(String meterNumber){
        super("View Information");
        this.meterNumber = meterNumber;

        getContentPane().setBackground(Color.WHITE);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int frameWidth = screenSize.width/2 + screenSize.width/5;
        int frameHeight = screenSize.height/2 + screenSize.height/5;

        //heading
        JLabel heading = new JLabel("View Customer Information");
        heading.setBounds(frameWidth/4+50, frameHeight/60, frameWidth, frameHeight/10);
        heading.setFont(new Font("Arial Black", Font.BOLD, frameWidth/40));
        add(heading);

        //labels
        JLabel name = new JLabel("Name: ");
        name.setBounds(frameWidth/10, frameHeight/5, frameWidth/8, frameHeight/20);
        name.setFont(new Font("Arial", Font.PLAIN, frameWidth/70));
        add(name);

        nameValue = new JLabel();
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

        addressValue = new JLabel();
        addressValue.setBounds(frameWidth/10+frameWidth/9, frameHeight/5+120, frameWidth/6, frameHeight/20);
        addressValue.setFont(new Font("Arial", Font.PLAIN, frameWidth/70));
        add(addressValue);

        JLabel city = new JLabel("City: ");
        city.setBounds(frameWidth/10, frameHeight/5+180, frameWidth/8, frameHeight/20);
        city.setFont(new Font("Arial", Font.PLAIN, frameWidth/70));
        add(city);

        cityValue = new JLabel();
        cityValue.setBounds(frameWidth/10+frameWidth/9, frameHeight/5+180, frameWidth/6, frameHeight/20);
        cityValue.setFont(new Font("Arial", Font.PLAIN, frameWidth/70));
        add(cityValue);

        JLabel state = new JLabel("State: ");
        state.setBounds(frameWidth/10+frameWidth/2+30, frameHeight/5, frameWidth/8, frameHeight/20);
        state.setFont(new Font("Arial", Font.PLAIN, frameWidth/70));
        add(state);

        stateValue = new JLabel();
        stateValue.setBounds(frameWidth/10+frameWidth/2+frameWidth/10, frameHeight/5, frameWidth/6, frameHeight/20);
        stateValue.setFont(new Font("Arial", Font.PLAIN, frameWidth/70));
        add(stateValue);

        JLabel email = new JLabel("Email: ");
        email.setBounds(frameWidth/10+frameWidth/2+30, frameHeight/5+60, frameWidth/8, frameHeight/20);
        email.setFont(new Font("Arial", Font.PLAIN, frameWidth/70));
        add(email);

        emailValue = new JLabel();
        emailValue.setBounds(frameWidth/10+frameWidth/2+frameWidth/10, frameHeight/5+60, frameWidth/6, frameHeight/20);
        emailValue.setFont(new Font("Arial", Font.PLAIN, frameWidth/70));
        add(emailValue);

        JLabel phone = new JLabel("Phone: ");
        phone.setBounds(frameWidth/10+frameWidth/2+30, frameHeight/5+120, frameWidth/8, frameHeight/20);
        phone.setFont(new Font("Arial", Font.PLAIN, frameWidth/70));
        add(phone);

        phoneValue = new JLabel();
        phoneValue.setBounds(frameWidth/10+frameWidth/2+frameWidth/10, frameHeight/5+120, frameWidth/6, frameHeight/20);
        phoneValue.setFont(new Font("Arial", Font.PLAIN, frameWidth/70));
        add(phoneValue);

        //button
        closeButton = new JButton("Close");
        closeButton.setBounds(frameWidth/10+frameWidth/4, frameHeight/5+220, frameWidth/13, frameHeight/25);
        closeButton.addActionListener(this);
        add(closeButton);

        //bottom image
        ImageIcon bottomImage = new ImageIcon(ClassLoader.getSystemResource("images/view_info.png"));
        ImageIcon cropped = new ImageIcon(bottomImage.getImage().getScaledInstance(frameWidth/2+100, frameHeight/2+100, Image.SCALE_SMOOTH));

        JLabel imageLabel = new JLabel(cropped);
        imageLabel.setBounds(0, frameHeight/3+100, frameWidth, frameHeight/2+100);
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

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == closeButton){
            dispose();
            new AdminFrame("Customer", meterNumber);
        }
    }

    public static void main(String[] args) {
        new ViewInformation("");
    }
}
