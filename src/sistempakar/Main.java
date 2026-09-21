package sistempakar;

import java.awt.Font;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * Entry point aplikasi Sistem Pakar Diagnosa Kerusakan Sepeda Motor Matik.
 */
public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                        if ("Nimbus".equals(info.getName())) {
                            UIManager.setLookAndFeel(info.getClassName());
                            break;
                        }
                    }
                    UIManager.put("defaultFont", new Font("Segoe UI", Font.PLAIN, 14));
                    UIManager.put("Panel.background", Ui.PAGE_BG);
                    UIManager.put("OptionPane.background", Ui.CARD_BG);
                    UIManager.put("OptionPane.messageForeground", Ui.TEXT_MAIN);
                    javax.swing.UIDefaults def = UIManager.getDefaults();
                    def.put("Panel.windowBackground", Ui.PAGE_BG);
                    def.put("TextArea.background", Ui.CARD_BG);
                    def.put("RadioButton.background", Ui.CARD_BG);
                } catch (Exception ignored) {
                    // fallback ke look and feel bawaan
                }
                new LoginFrame().setVisible(true);
            }
        });
    }
}