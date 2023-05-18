package electricity.billing.system.dropDownFrames;

import electricity.billing.system.frames.AdminFrame;
import electricity.billing.system.server.Database;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class GenerateBill extends JFrame implements ActionListener {

    public String meterNumber;

    public JComboBox<String> monthValue;

    public JScrollPane pane;

    public JTextArea area;

    public JButton generateButton, closeButton;

    public GenerateBill(String meterNumber){
        super("Generated Bill");
        this.meterNumber = meterNumber;

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int frameWidth = screenSize.width/3;
        int frameHeight = screenSize.height/4*3;

        setLayout(new BorderLayout());
        JPanel panel = new JPanel();

        JLabel generateBill = new JLabel("Generate Bill for Month :");

        String[] monthList = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        monthValue = new JComboBox<>(monthList);

        area = new JTextArea(50, 15);
        area.setText("\n\n\n\n\n            -----------------Select Month-----------------\n" +
                "           -----------------Click Generate----------------");
        area.setFont(new Font("Sanserif", Font.ITALIC, 20));

        pane = new JScrollPane(area);

        generateButton = new JButton("Generate");
        generateButton.addActionListener(this);

        closeButton = new JButton("Close");
        closeButton.addActionListener(this);

        add(pane);

        panel.add(generateBill);
        panel.add(monthValue);
        panel.add(closeButton, BorderLayout.NORTH);

        add(panel, "North");
        add(generateButton, "South");

        //frame parameters
        setSize(frameWidth, frameHeight);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if(event.getSource() == generateButton){
            int year = LocalDate.now().getYear();
            try {
                Database db = new Database();

                area.setText("Energy House ltd. \nElectricity Bill for month of %s, %s \n\n"
                        .formatted(monthValue.getSelectedItem(), Integer.toString(year)));

                ResultSet customerResult = db.statement.executeQuery("SELECT * FROM NewCustomer WHERE meterno = '%s';".formatted(meterNumber));
                if(customerResult.next()){
                    area.append("\nCUSTOMER INFORMATION ->");
                    area.append("\n    Name         : " + customerResult.getString("name"));
                    area.append("\n    Meter Number : " + customerResult.getString("meterno"));
                    area.append("\n    Address      : " + customerResult.getString("address"));
                    area.append("\n    City         : " + customerResult.getString("city"));
                    area.append("\n    State        : " + customerResult.getString("state"));
                    area.append("\n    Email        : " + customerResult.getString("email"));
                    area.append("\n    Phone        : " + customerResult.getString("phone"));
                    area.append("\n");
                }

                ResultSet meterInfoResult = db.statement.executeQuery("SELECT * FROM MeterInfo WHERE meterno = '%s';".formatted(meterNumber));
                if(meterInfoResult.next()){
                    area.append("\nMETER INFORMATION ->");
                    area.append("\n    Meter Location : " + meterInfoResult.getString("meterloc"));
                    area.append("\n    Energy Type    : " + meterInfoResult.getString("energytype"));
                    area.append("\n    Phase Code     : " + meterInfoResult.getString("phasecode"));
                    area.append("\n    Bill Type      : " + meterInfoResult.getString("billtype"));
                    area.append("\n");
                }

                ResultSet taxResult = db.statement.executeQuery("SELECT * FROM Tax;");
                if(taxResult.next()){
                    area.append("\nTAX INFORMATION ->");
                    area.append("\n    Cost Per Unit  : " + taxResult.getString("costPerUnit"));
                    area.append("\n    Meter Rent     : " + taxResult.getString("meterRent"));
                    area.append("\n    Service Charge : " + taxResult.getString("serviceCharge"));
                    area.append("\n    Service Tax    : " + taxResult.getString("serviceTax"));
                    area.append("\n");
                }

                ResultSet billResult = db.statement.executeQuery("SELECT * FROM Bill WHERE meterno = '%s' AND month = '%s' AND year = '%s';"
                        .formatted(meterNumber, monthValue.getSelectedItem().toString().substring(0, 3), Integer.toString(year)));
                if(billResult.next()){
                    area.append("\nBILL INFORMATION ->");
                    area.append("\n    Month            : " + billResult.getString("month"));
                    area.append("\n    Units Consumed   : " + billResult.getString("unit"));
                    area.append("\n    Total Charges    : " + billResult.getString("totalbill"));
                    area.append("\nTotal Payable Amount : " + billResult.getString("totalbill"));
                    area.append("\n");
                } else {
                    throw new SQLException("No bill Generated for this month");
                }
            } catch (Exception e) {
                area.setText("\n\n\n\nEnergy House ltd. \n\nElectricity Bill for month of %s, %s\n is not Generated.\n\n"
                        .formatted(monthValue.getSelectedItem(), Integer.toString(year)));
            }
        }
        else if(event.getSource() == closeButton){
            dispose();
            new AdminFrame("Customer", meterNumber);
        }
    }

    public static void main(String[] args) {
        new GenerateBill("42822");
    }
}
