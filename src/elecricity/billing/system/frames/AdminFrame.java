package elecricity.billing.system.frames;

import javax.swing.*;
import java.awt.*;

public class AdminFrame extends JFrame {

    AdminFrame(){
        //get screen size
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        //set the image to frame
        ImageIcon splashImage = new ImageIcon(ClassLoader.getSystemResource("images/background.jpg"));

        //crop image
        ImageIcon croppedImage = new ImageIcon(splashImage.getImage().getScaledInstance(screenSize.width, screenSize.height, Image.SCALE_SMOOTH));

        //create label for the frame
        JLabel splashLabel = new JLabel(croppedImage);
        add(splashLabel); //add the image to frame

        //add title
        setTitle("Electricity Billing [Admin]");

        //user cannot resize the frame
        setResizable(false);

        //set the frame size to max
        setExtendedState(MAXIMIZED_BOTH);

        //center the splash screen
        setLocationRelativeTo(null);

        setVisible(true);
    }

    public static void main(String[] args) {
        new AdminFrame();
    }
}
