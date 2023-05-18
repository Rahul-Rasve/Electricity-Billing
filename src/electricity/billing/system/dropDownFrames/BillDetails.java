package electricity.billing.system.dropDownFrames;

import electricity.billing.system.server.Database;
import net.proteanit.sql.DbUtils;

import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;

public class BillDetails extends JFrame {

    public JLabel nameValue, meterValue;

    public String meterNumber;

    public Database db = new Database();

    public BillDetails(String meterNumber){
        super("Bill Details");
        this.meterNumber = meterNumber;

        getContentPane().setBackground(new Color(225, 212, 187));

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int frameWidth = screenSize.width/2;
        int frameHeight = screenSize.height/2+200;

        JLabel heading = new JLabel("Bill Details");
        heading.setFont(new Font("Arial Black", Font.PLAIN, frameWidth/30));
        heading.setBounds(frameWidth/5*2, 10, frameWidth/2, frameHeight/20);
        add(heading);

        JLabel name = new JLabel("Customer Name :");
        name.setBounds(frameWidth/10, 50, frameWidth/6, frameHeight/20);
        name.setFont(new Font("Arial", Font.PLAIN, 15));
        add(name);

        nameValue = new JLabel();
        nameValue.setBounds(frameWidth/10+frameWidth/8+30, 50, frameWidth/3, frameHeight/20);
        nameValue.setFont(new Font("Arial", Font.BOLD, 15));
        add(nameValue);

        JLabel meter = new JLabel("Meter Number :");
        meter.setBounds(frameWidth/10, 75, frameWidth/6, frameHeight/20);
        meter.setFont(new Font("Arial", Font.PLAIN, 15));
        add(meter);

        meterValue = new JLabel(meterNumber);
        meterValue.setBounds(frameWidth/10+frameWidth/8+30, 75, frameWidth/8, frameHeight/20);
        meterValue.setFont(new Font("Arial", Font.BOLD, 15));
        add(meterValue);

        try {
            String nameQuery = "SELECT name FROM Users WHERE userid = '%s';".formatted(meterNumber);
            ResultSet nameResult = db.statement.executeQuery(nameQuery);
            if (nameResult.next()) {
                nameValue.setText(nameResult.getString("name"));
            }
        } catch (Exception e) {
            System.out.println(e.toString());
            System.out.println("error in name query");
        }

        JTable table= new JTable();

        try {
            String query = "SELECT month, unit, totalbill, status, year FROM Bill WHERE meterno = '%s'".formatted(meterNumber);
            ResultSet result = db.statement.executeQuery(query);
            table.setModel(DbUtils.resultSetToTableModel(result));
        } catch (Exception e) {
            System.out.println(e.toString());
            System.out.println("error in table");
        }

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(0, frameHeight/7+20, frameWidth, frameHeight);
        add(scrollPane);

        //frame parameters
        setSize(frameWidth, frameHeight);
        setResizable(false);
        setLocationRelativeTo(null);
        setLayout(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        new BillDetails("42822");
    }
}
