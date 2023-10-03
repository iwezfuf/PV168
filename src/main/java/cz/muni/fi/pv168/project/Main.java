package cz.muni.fi.pv168.project;

import com.formdev.flatlaf.FlatLightLaf;
import cz.muni.fi.pv168.project.ui.MainWindow;

import javax.swing.UIManager;
import java.awt.EventQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    public static void main(String[] args) {
        initNimbusLookAndFeel();
        EventQueue.invokeLater(() -> new MainWindow().show());
    }

    private static void initNimbusLookAndFeel() {
        try {
            UIManager.setLookAndFeel( new FlatLightLaf() );
            /*for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                System.out.println(info.getName());
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }*/
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, "Nimbus layout initialization failed", ex);
        }
    }
}
