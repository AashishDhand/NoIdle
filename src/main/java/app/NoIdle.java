package app;

import java.awt.Image;
import java.awt.Robot;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.TrayIcon.MessageType;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * Launch Windows No Idle Utility. <br><b>Note:</b> Set the variables adMethod,
 * KEYEVENT_INT
 *
 * @author ADHAND
 */
class NoIdle implements Runnable {

    /**
     * Available values <br>noIdle <br>adNotification <br> adTimer
     */
    static int KEYEVENT_INT;
    String adMethod = "";

    int timer = 0;
    static long startTime;

    @Override
    public void run() {
        try {
            if (adMethod.equals("noIdle")) {
                this.noIdle();
            } else if (adMethod.equals("adNotification")) {
                this.adNotification();
            } else if (adMethod.equals("adTimer")) {
                this.adTimer();
            }
        } catch (Exception ex) {
            System.out.println("Exception occurred during adMethod: " + adMethod);
        }
    }

    void noIdle() throws Exception {
        long secondsLong = Long.parseLong(Launcher.SECONDS);
        long millisecondsLong = secondsLong * 1000;

        Robot r = new Robot();
        r.keyPress(KEYEVENT_INT);
        r.keyRelease(KEYEVENT_INT);
        Thread.sleep(millisecondsLong);
        this.run();
    }

    void adTimer() throws Exception {

        long elapsedTime = System.currentTimeMillis() - startTime;
        long elapsedSeconds = elapsedTime / 1000;
        long secondsDisplay = elapsedSeconds % 60;
        long elapsedMinutes = elapsedSeconds / 60;
        Launcher.jLabelStatus.setText("Status: Running since " + elapsedMinutes + " mins");
        Thread.sleep(60000);
        this.run();
    }

    void adNotification() throws Exception {
        //Obtain only one instance of the SystemTray object
        SystemTray tray = SystemTray.getSystemTray();

        //If the icon is a file
        Image image = Toolkit.getDefaultToolkit().createImage("icon.png");
        //Alternative (if the icon is on the classpath):
        //Image image = Toolkit.getDefaultToolkit().createImage(getClass().getResource("icon.png"));

        TrayIcon trayIcon = new TrayIcon(image, "Tray Demo");
        //Let the system resize the image if needed
        trayIcon.setImageAutoSize(true);
        //Set tooltip text for the tray icon
        trayIcon.setToolTip("System tray icon demo");

        Thread.sleep(5000);//30 minutes sleep (1800000 milliseconds)
        tray.add(trayIcon);
        trayIcon.displayMessage("No Idle is running", "", MessageType.INFO);
        this.run();
    }

    void lookAndFeel() {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        }
    }
}
