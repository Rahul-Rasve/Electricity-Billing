package electricity.billing.system.dropDownFrames;

import electricity.billing.system.frames.AdminFrame;
import electricity.billing.system.server.Database;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MeterInfo extends JFrame implements ActionListener {

    private final String meterNoValue;

    private final JButton submitButton;

    private final JComboBox<String> meterLoc, energyTypeValue, phaseCodeValue, billTypeValue;

    MeterInfo(String meterNo){
        super("New Customer");
        this.meterNoValue = meterNo;

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
        ImageIcon avatar = new ImageIcon(ClassLoader.getSystemResource("images/meterDetails.png"));
        ImageIcon croppedImage = new ImageIcon(avatar.getImage().getScaledInstance(frameWidth/2, frameHeight+frameHeight/8, Image.SCALE_SMOOTH));
        JLabel image = new JLabel(croppedImage);
        image.setBounds(0, 0, frameWidth/2, frameHeight+frameHeight/8);
        add(image);

        //heading
        JLabel heading = new JLabel("Meter Information");
        heading.setFont(new Font("Arial Black", Font.PLAIN, frameWidth/40));
        heading.setBounds(2*frameWidth/3, frameHeight/70, frameWidth/3, frameHeight/10);
        add(heading);

        //labels and fields
        JLabel meterNum = new JLabel("Meter Number:");
        meterNum.setBounds(labelX, frameHeight/20+40, labelWidth, labelFieldHeight);
        add(meterNum);

        JLabel meterNumValue = new JLabel(meterNo);
        meterNumValue.setBounds(fieldX, frameHeight/20+40, fieldWidth, labelFieldHeight);
        add(meterNumValue);

        JLabel meterLocation = new JLabel("Meter Location:");
        meterLocation.setBounds(labelX, frameHeight/20+90, labelWidth, labelFieldHeight);
        add(meterLocation);

        String[] options = {"Outside", "Inside"};
        meterLoc = new JComboBox<>(options);
        meterLoc.setBounds(fieldX, frameHeight/20+90, fieldWidth, labelFieldHeight);
        add(meterLoc);

        JLabel energyType = new JLabel("Energy Type:");
        energyType.setBounds(labelX, frameHeight/20+140, labelWidth, labelFieldHeight);
        add(energyType);

        String[] energyOptions = {"Coal Energy", "Solar Energy"};
        energyTypeValue = new JComboBox<>(energyOptions);
        energyTypeValue.setBounds(fieldX, frameHeight/20+140, fieldWidth, labelFieldHeight);
        add(energyTypeValue);

        JLabel phaseCode = new JLabel("Phase Code:");
        phaseCode.setBounds(labelX, frameHeight/20+190, labelWidth, labelFieldHeight);
        add(phaseCode);

        String[] phaseOptions = {"011", "022", "033", "044", "055", "066", "077", "088", "099"};
        phaseCodeValue = new JComboBox<>(phaseOptions);
        phaseCodeValue.setBounds(fieldX, frameHeight/20+190, fieldWidth, labelFieldHeight);
        phaseCodeValue.setMaximumRowCount(4);
        add(phaseCodeValue);

        JLabel billType = new JLabel("Bill Type:");
        billType.setBounds(labelX, frameHeight/20+240, labelWidth, labelFieldHeight);
        add(billType);

        String[] billOptions = {"Standard", "Industrial"};
        billTypeValue = new JComboBox<>(billOptions);
        billTypeValue.setBounds(fieldX, frameHeight/20+240, fieldWidth, labelFieldHeight);
        add(billTypeValue);

        JLabel time = new JLabel("Billing Time:");
        time.setBounds(labelX, frameHeight/20+290, labelWidth, labelFieldHeight);
        add(time);

        JLabel timeText = new JLabel("30 Days");
        timeText.setBounds(fieldX, frameHeight/20+290, fieldWidth, labelFieldHeight);
        add(timeText);

        //buttons
        submitButton = new JButton("Submit");
        submitButton.setBounds(labelX+70, frameHeight/20+360, frameWidth/8, labelFieldHeight);
        submitButton.setBackground(Color.BLACK);
        submitButton.setForeground(Color.white);
        submitButton.addActionListener(this);
        add(submitButton);

        setResizable(false);
        setSize(frameWidth, frameHeight + frameHeight/8);
        setLocationRelativeTo(null);
        setLayout(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == submitButton){
            try{
                Database db = new Database();
                String insertQuery = "INSERT INTO MeterInfo VALUES ('%s', '%s', '%s', '%s', '%s')"
                        .formatted(meterNoValue, meterLoc.getSelectedItem(), energyTypeValue.getSelectedItem(), phaseCodeValue.getSelectedItem(), billTypeValue.getSelectedItem());
                db.statement.executeUpdate(insertQuery);

                JOptionPane.showMessageDialog(null, "Meter Information Added Successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);

                dispose();
                new AdminFrame();
            } catch(Exception e){
                JOptionPane.showMessageDialog(null, "Some Error Occurred.", "Error", JOptionPane.ERROR_MESSAGE);
                System.out.println(e.toString());
            }
        }
    }

    public static void main(String[] args) {
    }
}
