package electricity.billing.system.frames;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminFrame extends JFrame implements ActionListener {

    public AdminFrame(){
        super("Electricity Billing System");

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
        menuBar.add(menu);
        JMenu info = new JMenu("Information");
        info.setFont(menuBarTextFont);  info.setVisible(false);
        menuBar.add(info);
        JMenu user = new JMenu("User");
        user.setFont(menuBarTextFont);  user.setVisible(false);
        menuBar.add(user);
        JMenu bill = new JMenu("Bill");
        bill.setFont(menuBarTextFont);  bill.setVisible(false);
        menuBar.add(bill);
        JMenu utilities = new JMenu("Utilities");
        utilities.setFont(menuBarTextFont);
        menuBar.add(utilities);
        JMenu exit = new JMenu("EXIT");
        exit.setForeground(Color.red);
        exit.setFont(menuBarTextFont);
        menuBar.add(exit);

        //add items to the menu drop-down
        JMenuItem newCustomer = new JMenuItem("New Customer");
        newCustomer.setFont(menuTextFont);
        menu.add(newCustomer);
        JMenuItem customerDetail = new JMenuItem("Customer Details");
        customerDetail.setFont(menuTextFont);
        menu.add(customerDetail);
        JMenuItem depositDetails = new JMenuItem("Deposit Details");
        depositDetails.setFont(menuTextFont);
        menu.add(depositDetails);
        JMenuItem calculateBill = new JMenuItem("Calculate Bill");
        calculateBill.setFont(menuTextFont);
        menu.add(calculateBill);

        //add items to info drop-down
        JMenuItem updateInfo = new JMenuItem("Update Information");
        updateInfo.setFont(menuTextFont);
        info.add(updateInfo);
        JMenuItem viewInfo = new JMenuItem("View Information");
        viewInfo.setFont(menuTextFont);
        info.add(viewInfo);

        //add items to user drop-down
        JMenuItem payBill = new JMenuItem("Pay Bill");
        payBill.setFont(menuTextFont);
        user.add(payBill);
        JMenuItem billDetails = new JMenuItem("Bill Details");
        billDetails.setFont(menuTextFont);
        user.add(billDetails);

        //add items to bill drop-down
        JMenuItem generateBill = new JMenuItem("Generate Bill");
        generateBill.setFont(menuTextFont);
        bill.add(generateBill);

        //add items to utilities drop-down
        JMenuItem calculator = new JMenuItem("Calculator");
        calculator.setFont(menuTextFont);
        utilities.add(calculator);
        JMenuItem notepad = new JMenuItem("Notepad");
        notepad.setFont(menuTextFont);
        utilities.add(notepad);

        //add items to exit drop-down
        JMenuItem logout = new JMenuItem("Logout");
        logout.setFont(menuTextFont);
        exit.add(logout);

        //user cannot resize the frame
        setResizable(false);

        //center the splash screen
        setLocationRelativeTo(null);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
    }

    public static void main(String[] args) {
        new AdminFrame();
    }
}
