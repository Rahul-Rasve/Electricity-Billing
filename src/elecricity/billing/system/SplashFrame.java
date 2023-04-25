package elecricity.billing.system;

import javax.swing.*;
import java.awt.*;

public class SplashFrame extends JFrame {

    SplashFrame(){
        super("Loading...");

        //get screen size
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        //set the image to frame
        ImageIcon splashImage = new ImageIcon(ClassLoader.getSystemResource("images/splashImage.jpg"));

        //set responsive screenSize and visibility
        setSize(screenSize.width/2, screenSize.height/2);

        //crop image
        ImageIcon croppedImage = new ImageIcon(splashImage.getImage().getScaledInstance(screenSize.width/2, screenSize.height/2, Image.SCALE_SMOOTH));

        //create label for the frame
        JLabel splashLabel = new JLabel(croppedImage);
        add(splashLabel); //add the image to frame

        //user cannot resize the frame
        setResizable(false);

        //center the splash screen
        setLocationRelativeTo(null);

        setVisible(true);

        //close this frame after 3 sec
        try{
            Thread.sleep(3000);
            dispose();
            new LoginFrame();
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        new SplashFrame();
    }
}
