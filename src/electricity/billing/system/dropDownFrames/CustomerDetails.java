package electricity.billing.system.dropDownFrames;

import electricity.billing.system.frames.AdminFrame;
import electricity.billing.system.server.Database;
import net.proteanit.sql.DbUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

public class CustomerDetails extends JFrame implements ActionListener {

    public Database db = new Database();

    public final JComboBox<String> meterNumValue;

    public final JTextField nameValue;

    public JButton searchButton, printButton, closeButton;

    public JTable table;

    public CustomerDetails(){
        super("Customer Details");
        getContentPane().setBackground(new Color(212, 173, 252)); //light purple

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int frameWidth = screenSize.width/2;
        int frameHeight = screenSize.height/2;

        JLabel meterNumber = new JLabel("Search by Meter Number :");
        meterNumber.setBounds(frameWidth/20, frameHeight/15, frameWidth/3, frameHeight/20);
        add(meterNumber);

        String[] options = {};
        try{
            String getMeterNumsQuery = "SELECT meterno FROM MeterInfo;";
            ResultSet meterNums = db.statement.executeQuery(getMeterNumsQuery);

            int resultRows;
            meterNums.last(); //goto last row, get row count and jump back to first row
            resultRows = meterNums.getRow();
            meterNums.beforeFirst();

            options = new String[resultRows];
            int i = 0;
            while(meterNums.next()){
                String value = meterNums.getString("meterno");
                options[i] = value;
                i++;
            }

            meterNums.close();
        } catch(Exception e){
            System.out.println("Error in meter number fetching");
        }
        meterNumValue = new JComboBox<>(options);
        meterNumValue.setBounds(frameWidth/5+50, frameHeight/15, frameWidth/6, frameHeight/20);
        meterNumValue.setMaximumRowCount(5);
        add(meterNumValue);

        JLabel name = new JLabel("Search by Name :");
        name.setBounds(frameWidth/2+frameWidth/8, frameHeight/15, frameWidth/6, frameHeight/20);
        add(name);

        nameValue = new JTextField(20);
        nameValue.setBounds(frameWidth/2+frameWidth/4+20, frameHeight/15, frameWidth/6, frameHeight/20);
        add(nameValue);

        searchButton = new JButton("Search");
        searchButton.setBounds(frameWidth/20, frameHeight/15+40, frameWidth/10, frameHeight/20);
        searchButton.addActionListener(this);
        add(searchButton);

        printButton = new JButton("Print");
        printButton.setBounds(frameWidth/20+frameWidth/8, frameHeight/15+40, frameWidth/10, frameHeight/20);
        printButton.addActionListener(this);
        add(printButton);

        closeButton = new JButton("Close");
        closeButton.setBounds(frameWidth/3+frameWidth/2, frameHeight/15+40, frameWidth/10, frameHeight/20);
        closeButton.addActionListener(this);
        add(closeButton);

        table = new JTable();
        try{
            String getTableQuery = "SELECT * FROM NewCustomer;";
            ResultSet results = db.statement.executeQuery(getTableQuery);
            table.setModel(DbUtils.resultSetToTableModel(results));
        } catch(Exception e){
            System.out.println(e.toString());
            System.out.println("Table error");
        }

        //if table contents overflow, it needs to scroll
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(0, frameHeight/15+80, frameWidth, frameHeight);
        scrollPane.setBackground(Color.white);
        add(scrollPane);

        //frame parameters
        setResizable(false);
        setSize(frameWidth, frameHeight);
        setLocationRelativeTo(null);
        setLayout(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if(event.getSource() == closeButton){
            dispose();
            new AdminFrame("Admin", meterNumValue.getSelectedItem().toString());
        }
        else if(event.getSource() == searchButton){
            String searchQuery;
            if(nameValue.getText().isEmpty()){
                searchQuery = "SELECT * FROM NewCustomer WHERE meterno = '%s';"
                        .formatted(meterNumValue.getSelectedItem());
            }
            else{
                searchQuery = "SELECT * FROM NewCustomer WHERE name LIKE '"+nameValue.getText()+"%';";
            }

            try {
                ResultSet resultSet = db.statement.executeQuery(searchQuery);
                table.setModel(DbUtils.resultSetToTableModel(resultSet));
            } catch (Exception e) {
                System.out.println(e.toString());
                System.out.println("search table error");
            }
        }
        else if(event.getSource() == printButton){
            try {
                table.print();
            } catch (Exception e) {
                System.out.println(e.toString());
                System.out.println("error in printing");
            }
        }
    }

    public static void main(String[] args) {
        new CustomerDetails();
    }
}
