package sistempakar;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 * Form Login dengan layout split: panel kiri branding, panel kanan form.
 * Ukuran window ditentukan secara presisi dari konten (pack) sehingga
 * seluruh isi tampil penuh tanpa terpotong.
 */
public class LoginFrame extends JFrame {

    private static final String USERNAME = "admin";
    private static final String PASSWORD = "admin";

    private final Ui.RoundedTextField txtUsername = new Ui.RoundedTextField(USERNAME);
    private final Ui.RoundedPasswordField txtPassword = new Ui.RoundedPasswordField(PASSWORD);
    private final JButton btnShow = Ui.ghostButton("Lihat");
    private final Ui.ModernButton btnLogin = Ui.accentButton("Masuk");
    private final JLabel lblError = new JLabel(" ");

    public LoginFrame() {
        setTitle("Login - Sistem Pakar Diagnosa Kerusakan Motor Matik");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Ui.PAGE_BG);
        buildUI();
        pack();
        setLocationRelativeTo(null);

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowOpened(java.awt.event.WindowEvent e) {
                txtUsername.requestFocusInWindow();
                txtUsername.selectAll();
            }
        });
    }

    private void buildUI() {
        add(buildBrandPanel(), BorderLayout.WEST);
        add(buildFormPanel(), BorderLayout.CENTER);
    }

    // ================= Papan kiri: branding =================
    private JPanel buildBrandPanel() {
        Ui.GradientPanel brand = new Ui.GradientPanel(Ui.NAVY, Ui.NAVY_MID, true, 5);
        brand.setPreferredSize(new Dimension(400, 600));
        brand.setLayout(new BoxLayout(brand, BoxLayout.Y_AXIS));

        JLabel lblApp = new JLabel("SISTEM PAKAR");
        lblApp.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblApp.setForeground(new Color(255, 255, 255, 200));
        lblApp.setAlignmentX(JLabel.LEFT_ALIGNMENT);
        brand.add(lblApp);
        brand.add(Box.createVerticalStrut(18));

        JLabel lblTitle = new JLabel("<html><b>Sistem Pakar Diagnosa</b><br>Kerusakan Motor Matik</html>");
        lblTitle.setFont(Ui.FONT_TITLE);
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setAlignmentX(JLabel.LEFT_ALIGNMENT);
        brand.add(lblTitle);
        brand.add(Box.createVerticalStrut(16));

        JLabel lblDesc = new JLabel("<html><div style='width:340px;'>"
                + "Bantu identifikasi kerusakan sepeda motor matik Anda "
                + "berdasarkan gejala yang dialami, menggunakan metode "
                + "<b>Forward Chaining</b>.</div></html>");
        lblDesc.setFont(Ui.FONT_SUB);
        lblDesc.setForeground(new Color(214, 222, 244));
        lblDesc.setAlignmentX(JLabel.LEFT_ALIGNMENT);
        brand.add(lblDesc);
        brand.add(Box.createVerticalGlue());

        String[] poin = {
            "10 gejala umum kerusakan motor matik",
            "5 jenis kerusakan beserta solusinya",
            "Hasil langsung, mudah dipahami"
        };
        for (String poinText : poin) {
            JPanel row = new JPanel(new BorderLayout());
            row.setOpaque(false);
            JLabel check = new JLabel("\u2713");
            check.setFont(new Font("Segoe UI", Font.BOLD, 16));
            check.setForeground(Ui.ACCENT);
            check.setPreferredSize(new Dimension(28, 24));
            row.add(check, BorderLayout.WEST);
            JLabel text = new JLabel(poinText);
            text.setFont(Ui.FONT_BODY);
            text.setForeground(new Color(232, 236, 249));
            row.add(text, BorderLayout.CENTER);
            row.setMaximumSize(new Dimension(360, 26));
            brand.add(row);
            brand.add(Box.createVerticalStrut(12));
        }
        brand.add(Box.createVerticalGlue());

        JLabel lblFooter = new JLabel("v1.0 - Forward Chaining");
        lblFooter.setFont(Ui.FONT_SMALL);
        lblFooter.setForeground(new Color(255, 255, 255, 160));
        lblFooter.setAlignmentX(JLabel.LEFT_ALIGNMENT);
        brand.add(lblFooter);
        brand.add(Box.createVerticalStrut(8));
        return brand;
    }

    // ================= Papan kanan: form login =================
    private JPanel buildFormPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Ui.CARD_BG);
        panel.setPreferredSize(new Dimension(480, 600));

        JPanel inner = new JPanel();
        inner.setLayout(new BoxLayout(inner, BoxLayout.Y_AXIS));
        inner.setBackground(Ui.CARD_BG);
        inner.setBorder(BorderFactory.createEmptyBorder(52, 52, 40, 52));

        JLabel lblJudul = new JLabel("Selamat Datang");
        lblJudul.setFont(Ui.FONT_HEADER);
        lblJudul.setForeground(Ui.TEXT_MAIN);
        lblJudul.setAlignmentX(JLabel.LEFT_ALIGNMENT);
        inner.add(lblJudul);
        inner.add(Box.createVerticalStrut(6));

        JLabel lblSub = new JLabel("Silakan masuk untuk memulai sesi diagnosa.");
        lblSub.setFont(Ui.FONT_SUB);
        lblSub.setForeground(Ui.TEXT_MUTED);
        lblSub.setAlignmentX(JLabel.LEFT_ALIGNMENT);
        inner.add(lblSub);
        inner.add(Box.createVerticalStrut(30));

        inner.add(labelField("Username"));
        inner.add(Box.createVerticalStrut(8));
        inner.add(fieldWrapper(txtUsername));
        inner.add(Box.createVerticalStrut(18));

        inner.add(labelField("Password"));
        inner.add(Box.createVerticalStrut(8));
        inner.add(fieldWrapper(txtPassword));
        inner.add(Box.createVerticalStrut(10));

        lblError.setFont(Ui.FONT_SMALL);
        lblError.setForeground(Ui.ERROR);
        lblError.setAlignmentX(JLabel.LEFT_ALIGNMENT);
        lblError.setPreferredSize(new Dimension(320, 18));
        lblError.setMaximumSize(new Dimension(320, 18));
        inner.add(lblError);
        inner.add(Box.createVerticalStrut(16));

        btnLogin.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        btnLogin.setMaximumSize(new Dimension(376, 46));
        btnLogin.setPreferredSize(new Dimension(376, 46));
        inner.add(btnLogin);
        inner.add(Box.createVerticalStrut(16));

        JLabel lblHint = new JLabel("Kredensial bawaan: admin / admin");
        lblHint.setFont(Ui.FONT_SMALL);
        lblHint.setForeground(Ui.TEXT_MUTED);
        lblHint.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        inner.add(lblHint);

        panel.add(inner, BorderLayout.CENTER);

        txtPassword.putClientProperty("JTextField.showPassword", false);
        txtPassword.setEchoChar('\u2022');

        btnShow.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (txtPassword.getEchoChar() == (char) 0) {
                    txtPassword.setEchoChar('\u2022');
                    btnShow.setText("Lihat");
                } else {
                    txtPassword.setEchoChar((char) 0);
                    btnShow.setText("Sembunyikan");
                }
            }
        });

        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                doLogin();
            }
        });

        getRootPane().setDefaultButton(btnLogin);
        return panel;
    }

    private JLabel labelField(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(Ui.FONT_BOLD);
        lbl.setForeground(Ui.TEXT_MAIN);
        lbl.setAlignmentX(JLabel.LEFT_ALIGNMENT);
        return lbl;
    }

    /** Bungkus field dengan tombol Lihat untuk password. */
    private JPanel fieldWrapper(JTextField field) {
        JPanel wrap = new JPanel(new BorderLayout(8, 0));
        wrap.setOpaque(false);
        int fieldW = (field instanceof JPasswordField) ? 272 : 376;
        field.setPreferredSize(new Dimension(fieldW, 44));
        field.setMaximumSize(new Dimension(fieldW, 44));
        wrap.add(field, BorderLayout.CENTER);
        if (field instanceof JPasswordField) {
            btnShow.setPreferredSize(new Dimension(96, 44));
            btnShow.setMaximumSize(new Dimension(96, 44));
            wrap.add(btnShow, BorderLayout.EAST);
        }
        wrap.setMaximumSize(new Dimension(376, 44));
        wrap.setAlignmentX(JLabel.LEFT_ALIGNMENT);
        return wrap;
    }

    private void doLogin() {
        String user = txtUsername.getText().trim();
        String pass = new String(txtPassword.getPassword());

        if (USERNAME.equals(user) && PASSWORD.equals(pass)) {
            lblError.setText(" ");
            JOptionPane.showMessageDialog(this,
                    "Berhasil Login.\nSelamat datang, " + user + "!",
                    "Login Berhasil",
                    JOptionPane.INFORMATION_MESSAGE);
            dispose();
            new MainFrame().setVisible(true);
        } else {
            lblError.setText("Username atau password salah.");
            txtPassword.selectAll();
            txtPassword.requestFocusInWindow();
        }
    }
}