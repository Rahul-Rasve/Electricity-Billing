package electricity.billing.system.frames;

import electricity.billing.system.dropDownFrames.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminFrame extends JFrame implements ActionListener {

    public String meterNumber;

    String accountType;

    public AdminFrame(String accountType, String meterNumber){
        super("Electricity Billing System");
        this.meterNumber = meterNumber;

        this.accountType = accountType;

        //set a flowlayout
        setLayout(new FlowLayout());

        //get screen size
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        //constant parameters
        Font menuBarTextFont = new Font("Arial Black", Font.PLAIN, screenSize.width/95);
        Font menuTextFont = new Font("Arial Nova Light", Font.PLAIN, screenSize.width/98);

        //set the frame size to max
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        //set the image to frame
        ImageIcon splashImage = new ImageIcon(ClassLoader.getSystemResource("images/background.jpg"));
        //crop image
        ImageIcon croppedImage = new ImageIcon(splashImage.getImage().getScaledInstance(screenSize.width, screenSize.height, Image.SCALE_SMOOTH));
        //create label for the frame
        JLabel splashLabel = new JLabel(croppedImage);
        add(splashLabel); //add the image to frame

        //menu bar at top of page
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        //set menubar items
        JMenu menu = new JMenu("Menu");
        menu.setFont(menuBarTextFont);  menu.setVisible(true);

        JMenu info = new JMenu("Information");
        info.setFont(menuBarTextFont);  info.setVisible(true);

        JMenu user = new JMenu("User");
        user.setFont(menuBarTextFont);  user.setVisible(true);

        JMenu bill = new JMenu("Bill");
        bill.setFont(menuBarTextFont);  bill.setVisible(true);

        JMenu utilities = new JMenu("Utilities");
        utilities.setFont(menuBarTextFont);

        JMenu exit = new JMenu("EXIT");
        exit.setForeground(Color.red);
        exit.setFont(menuBarTextFont);

        //add items to the menu drop-down
        JMenuItem newCustomer = new JMenuItem("New Customer");
        newCustomer.setFont(menuTextFont);
        newCustomer.addActionListener(this);
        menu.add(newCustomer);
        JMenuItem customerDetail = new JMenuItem("Customer Details");
        customerDetail.setFont(menuTextFont);
        customerDetail.addActionListener(this);
        menu.add(customerDetail);
        JMenuItem depositDetails = new JMenuItem("Deposit Details");
        depositDetails.setFont(menuTextFont);
        depositDetails.addActionListener(this);
        menu.add(depositDetails);
        JMenuItem calculateBill = new JMenuItem("Calculate Bill");
        calculateBill.setFont(menuTextFont);
        calculateBill.addActionListener(this);
        menu.add(calculateBill);

        //add items to info drop-down
        JMenuItem updateInfo = new JMenuItem("Update Information");
        updateInfo.setFont(menuTextFont);
        updateInfo.addActionListener(this);
        info.add(updateInfo);
        JMenuItem viewInfo = new JMenuItem("View Information");
        viewInfo.setFont(menuTextFont);
        viewInfo.addActionListener(this);
        info.add(viewInfo);

        //add items to user drop-down
        JMenuItem payBill = new JMenuItem("Pay Bill");
        payBill.setFont(menuTextFont);
        payBill.addActionListener(this);
        user.add(payBill);
        JMenuItem billDetails = new JMenuItem("Bill Details");
        billDetails.setFont(menuTextFont);
        billDetails.addActionListener(this);
        user.add(billDetails);

        //add items to bill drop-down
        JMenuItem generateBill = new JMenuItem("Generate Bill");
        generateBill.setFont(menuTextFont);
        generateBill.addActionListener(this);
        bill.add(generateBill);

        //add items to utilities drop-down
        JMenuItem calculator = new JMenuItem("Calculator");
        calculator.setFont(menuTextFont);
        calculator.addActionListener(this);
        utilities.add(calculator);
        JMenuItem notepad = new JMenuItem("Notepad");
        notepad.setFont(menuTextFont);
        notepad.addActionListener(this);
        utilities.add(notepad);

        //add items to exit drop-down
        JMenuItem logout = new JMenuItem("Logout");
        logout.setFont(menuTextFont);
        logout.addActionListener(this);
        exit.add(logout);

        //menubar items
        if(accountType.equals("Admin")){
            menuBar.add(menu);
        }
        else {
            menuBar.add(user);
            menuBar.add(info);
            menuBar.add(bill);
        }
        menuBar.add(utilities);
        menuBar.add(exit);

        //user cannot resize the frame
        setResizable(false);

        //center the splash screen
        setLocationRelativeTo(null);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        String action = event.getActionCommand();
        if(action.equals("New Customer")){
            dispose();
            new NewCustomer();
        }
        if(action.equals("Customer Details")){
            dispose();
            new CustomerDetails();
        }
        if(action.equals("Deposit Details")){
            dispose();
            new DepositDetails();
        }
        if(action.equals("Calculate Bill")){
            dispose();
            new CalculateBill();
        }
        if(action.equals("Update Information")){
            dispose();
            new UpdateInformation(meterNumber);
        }
        if(action.equals("View Information")){
            dispose();
            new ViewInformation(meterNumber);
        }
        if(action.equals("Pay Bill")){
            dispose();
            new PayBill(meterNumber);
        }
        if(action.equals("Bill Details")){
            dispose();
            new BillDetails(meterNumber);
        }
        if(action.equals("Generate Bill")){
            dispose();
        }
        if(action.equals("Calculator")){
            try{
                Runtime.getRuntime().exec("calc.exe");
            } catch (Exception e){
                System.out.println(e.toString());
                System.out.println("error in calculator");
            }
        }
        if(action.equals("Notepad")){
            try{
                Runtime.getRuntime().exec("notepad.exe");
            } catch (Exception e){
                System.out.println(e.toString());
                System.out.println("error in notepad");
            }
        }
        if(action.equals("Logout")){
            dispose();
            new LoginFrame();
        }
    }

    public static void main(String[] args) {
        new AdminFrame("", "");
    }
}
