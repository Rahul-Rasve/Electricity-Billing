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
import java.time.LocalDate;

public class PayBill extends JFrame implements ActionListener {

    public Database db = new Database();

    public String meterNumber;

    public JComboBox<String> monthValue;

    public JButton cancelButton, payButton;

    public JLabel nameValue, unitValue, totalBillValue, statusValue;

    public PayBill(String meterNumber){
        super("Pay Bill");
        this.meterNumber = meterNumber;

        getContentPane().setBackground(new Color(160, 216, 179));

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int frameWidth = screenSize.width/3;
        int frameHeight = screenSize.height/2+100;

        //heading
        JLabel heading = new JLabel("Bill Payment");
        heading.setBounds(frameWidth/4+30, 5, frameWidth, frameHeight/10);
        heading.setFont(new Font("Arial Black", Font.PLAIN, frameWidth/20));
        add(heading);

        //labels
        JLabel meterNum = new JLabel("Meter Number :");
        meterNum.setBounds(frameWidth/5, frameHeight/10, frameWidth/4, frameHeight/10);
        add(meterNum);

        JLabel meterValue = new JLabel(meterNumber);
        meterValue.setBounds(frameWidth/5+frameWidth/4+50, frameHeight/10, frameWidth/4, frameHeight/10);
        add(meterValue);

        JLabel name = new JLabel("Name :");
        name.setBounds(frameWidth/5, frameHeight/10+60, frameWidth/4, frameHeight/10);
        add(name);

        nameValue = new JLabel();
        nameValue.setBounds(frameWidth/5+frameWidth/4+50, frameHeight/10+60, frameWidth/4, frameHeight/10);
        add(nameValue);

        //get name
        String nameQuery = "SELECT name FROM Users WHERE userid = '%s';".formatted(meterNumber);
        try {
            ResultSet result = db.statement.executeQuery(nameQuery);
            if (result.next()) {
                nameValue.setText(result.getString("name"));
            }
        } catch (Exception e) {
            System.out.println(e.toString());
            System.out.println("error in name query");
        }

        JLabel month = new JLabel("Month :");
        month.setBounds(frameWidth/5, frameHeight/10+120, frameWidth/4, frameHeight/10);
        add(month);

        String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "July", "Aug", "Sep", "Oct", "Nov", "Dec"};
        monthValue = new JComboBox<>(months);
        monthValue.setMaximumRowCount(4);
        monthValue.setBounds(frameWidth/5+frameWidth/4+50, frameHeight/10+130, frameWidth/4, frameHeight/20);
        add(monthValue);

        JLabel unit = new JLabel("Units :");
        unit.setBounds(frameWidth/5, frameHeight/10+180, frameWidth/4, frameHeight/10);
        add(unit);

        unitValue = new JLabel();
        unitValue.setBounds(frameWidth/5+frameWidth/4+50, frameHeight/10+180, frameWidth/4, frameHeight/10);
        add(unitValue);

        JLabel totalBill = new JLabel("Total Bill :");
        totalBill.setBounds(frameWidth/5, frameHeight/10+240, frameWidth/4, frameHeight/10);
        add(totalBill);

        totalBillValue = new JLabel();
        totalBillValue.setBounds(frameWidth/5+frameWidth/4+50, frameHeight/10+240, frameWidth/4, frameHeight/10);
        add(totalBillValue);

        JLabel status = new JLabel("Status :");
        status.setBounds(frameWidth/5, frameHeight/10+300, frameWidth/4, frameHeight/10);
        add(status);

        statusValue = new JLabel();
        statusValue.setBounds(frameWidth/5+frameWidth/4+50, frameHeight/10+300, frameWidth/4, frameHeight/10);
        add(statusValue);

        //buttons
        payButton = new JButton("Pay Bill");
        payButton.setBounds(frameWidth/4, frameHeight/10+380, frameWidth/6, frameHeight/20);
        payButton.addActionListener(this);
        add(payButton);

        cancelButton = new JButton("Cancel");
        cancelButton.setBounds(frameWidth/2+20, frameHeight/10+380, frameWidth/6, frameHeight/20);
        cancelButton.addActionListener(this);
        add(cancelButton);

        //month selected
        monthValue.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                try {
                    String billQuery = "SELECT * FROM Bill WHERE meterno = '%s' AND month = '%s' AND year = '%s';"
                            .formatted(meterNumber, monthValue.getSelectedItem(), LocalDate.now().getYear());
                    ResultSet resultSet = db.statement.executeQuery(billQuery);
                    if(resultSet.next()){
                        unitValue.setText(resultSet.getString("unit"));
                        totalBillValue.setText(resultSet.getString("totalbill"));
                        String status = resultSet.getString("status");
                        if(status.equals("Not Paid")){
                            statusValue.setForeground(Color.RED);
                        }
                        else {
                            statusValue.setForeground(Color.BLUE);
                            payButton.setEnabled(false);
                        }
                        statusValue.setText(status);
                    }
                } catch (Exception ex) {
                    System.out.println(e.toString());
                    System.out.println("error in bill query");
                }
            }
        });

        //frame parameters
        setSize(frameWidth, frameHeight);
        setResizable(false);
        setLayout(null);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == cancelButton){
            dispose();
            new AdminFrame("Customer", meterNumber);
        }
        else if(e.getSource() == payButton){
            try {
                String payQuery = "UPDATE Bill SET status = 'Paid' WHERE meterno = '%s';"
                        .formatted(meterNumber);
                int result = db.statement.executeUpdate(payQuery);
                if(result > 0){
                    JOptionPane.showMessageDialog(null, "Bill Paid Successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                    new AdminFrame("Customer", meterNumber);
                }
                else{
                    JOptionPane.showMessageDialog(null, "Payment Failed.", "Failure", JOptionPane.WARNING_MESSAGE);
                }
            } catch (Exception ex) {
                System.out.println(e.toString());
                System.out.println("error in pay query");
            }
        }
    }

    public static void main(String[] args) {
        new PayBill("42822");
    }
}
