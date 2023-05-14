package electricity.billing.system.dropDownFrames;

import electricity.billing.system.frames.AdminFrame;
import electricity.billing.system.server.Database;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CalculateBill extends JFrame implements ActionListener {

    private Database db = new Database();

    private JComboBox<String> monthValue, meterNumValue;

    private JLabel nameValue, addressValue;

    private JTextField unitConValue;

    private JButton submitButton, cancelButton;

    public CalculateBill(){
        super("Calculate Bill");
        getContentPane().setBackground(new Color(229, 255, 204));

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int frameHeight = screenSize.height/2;
        int frameWidth = screenSize.width/2;

        ImageIcon image = new ImageIcon(ClassLoader.getSystemResource("images/calcBill.png"));
        ImageIcon cropped = new ImageIcon(image.getImage().getScaledInstance(frameWidth/2, frameHeight, Image.SCALE_SMOOTH));
        JLabel imageLabel = new JLabel(cropped);
        imageLabel.setBounds(0, 0, frameWidth/2, frameHeight);
        add(imageLabel);

        //heading
        JLabel title = new JLabel("Calculate Bill");
        title.setFont(new Font("Arial Black", Font.PLAIN, frameHeight/15));
        title.setBounds(frameWidth/3+frameWidth/4+30, frameHeight/40, frameWidth/2, frameHeight/15);
        add(title);

        //labels and fields
        JLabel meterNum = new JLabel("Meter Number:");
        meterNum.setBounds(frameWidth/2+50, frameHeight/7, frameWidth/8, frameHeight/20);
        add(meterNum);

        String[] meterNumOptions = {};
        try {
            String getMeterNumsQuery = "SELECT meterno FROM MeterInfo;";
            ResultSet meterNums = db.statement.executeQuery(getMeterNumsQuery);

            int resultRows = 0;
            meterNums.last(); //goto last row, get row count and jump back to first row
            resultRows = meterNums.getRow();
            meterNums.beforeFirst();

            meterNumOptions = new String[resultRows];
            int i = 0;
            while(meterNums.next()){
                String value = meterNums.getString("meterno");
                meterNumOptions[i] = value;
                i++;
            }

            meterNums.close();
        } catch (Exception e) {
            System.out.println(e.toString());
            System.out.println("error in meter text");
        }
        meterNumValue = new JComboBox<>(meterNumOptions);
        meterNumValue.setMaximumRowCount(4);
        meterNumValue.setBounds(frameWidth/2+frameWidth/4, frameHeight/7, frameWidth/6, frameHeight/20);
        add(meterNumValue);

        JLabel name = new JLabel("Name:");
        name.setBounds(frameWidth/2+50, frameHeight/7+60, frameWidth/8, frameHeight/20);
        add(name);

        nameValue = new JLabel("Name");
        nameValue.setBounds(frameWidth/2+frameWidth/4, frameHeight/7+60, frameWidth/6, frameHeight/20);
        add(nameValue);

        JLabel address = new JLabel("Address:");
        address.setBounds(frameWidth/2+50, frameHeight/7+100, frameWidth/8, frameHeight/20);
        add(address);

        addressValue = new JLabel("Address");
        addressValue.setBounds(frameWidth/2+frameWidth/4, frameHeight/7+100, frameWidth/6, frameHeight/20);
        add(addressValue);

        //change name and address values with new item selection
        meterNumValue.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent event) {
                try {
                    String nameQuery = "SELECT name,address FROM NewCustomer WHERE meterno=%s;".formatted(meterNumValue.getSelectedItem());
                    ResultSet result = db.statement.executeQuery(nameQuery);
                    if(result.next()){
                        nameValue.setText(result.getString("name"));
                        addressValue.setText(result.getString("address"));
                    }
                } catch (Exception e) {
                    System.out.println(e.toString());
                    System.out.println("error in name text");
                }
            }
        });

        JLabel unitCon = new JLabel("Units Consumed:");
        unitCon.setBounds(frameWidth/2+50, frameHeight/7+140, frameWidth/6, frameHeight/20);
        add(unitCon);

        unitConValue = new JTextField(10);
        unitConValue.setBounds(frameWidth/2+frameWidth/4, frameHeight/7+140, frameWidth/6, frameHeight/20);
        add(unitConValue);

        JLabel month = new JLabel("Month:");
        month.setBounds(frameWidth/2+50, frameHeight/7+180, frameWidth/8, frameHeight/20);
        add(month);

        String[] monthList = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "July", "Aug", "Sep", "Oct", "Nov", "Dec"};
        monthValue = new JComboBox<>(monthList);
        monthValue.setMaximumRowCount(3);
        monthValue.setBounds(frameWidth/2+frameWidth/4, frameHeight/7+180, frameWidth/6, frameHeight/20);
        add(monthValue);

        //buttons
        submitButton = new JButton("Submit");
        submitButton.addActionListener(this);
        submitButton.setBounds(frameWidth/2+70, frameHeight/7+270, frameWidth/10, frameHeight/20);
        submitButton.setBackground(Color.black);
        submitButton.setForeground(Color.white);
        add(submitButton);

        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(this);
        cancelButton.setBounds(frameWidth/2+frameWidth/4+40, frameHeight/7+270, frameWidth/10, frameHeight/20);
        cancelButton.setBackground(Color.black);
        cancelButton.setForeground(Color.white);
        add(cancelButton);

        //frame parameters
        setResizable(false);
        setSize(frameWidth, frameHeight);
        setLocationRelativeTo(null);
        setLayout(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == cancelButton) {
            dispose();
            new AdminFrame("Admin");
        }
        else if(event.getSource() == submitButton){
            int totalBill = 0;
            int units = Integer.parseInt(unitConValue.getText());
            String taxQuery = "SELECT * FROM Tax";
            try {
                ResultSet taxValues = db.statement.executeQuery(taxQuery);
                if (taxValues.next()) {
                    totalBill += units * Integer.parseInt(taxValues.getString("costPerUnit"));
                    totalBill += Integer.parseInt(taxValues.getString("meterRent"));
                    totalBill += Integer.parseInt(taxValues.getString("serviceCharge"));
                    totalBill += Integer.parseInt(taxValues.getString("serviceTax"));
                }
            } catch (Exception e) {
                System.out.println(e.toString());
            }

            String billQuery = "INSERT INTO Bill VALUES ('%s', '%s', '%s', '%s', 'Not Paid');"
                    .formatted(meterNumValue.getSelectedItem(),
                            monthValue.getSelectedItem(),
                            unitConValue.getText(),
                            totalBill);
            try {
                db.statement.executeUpdate(billQuery);

                JOptionPane.showMessageDialog(null, "Bill Stored Successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } catch (Exception e) {
                System.out.println(e.toString());
            }
        }
    }

    public static void main(String[] args) {
        new CalculateBill();
    }
}
