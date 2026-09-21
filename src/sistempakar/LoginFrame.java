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
import javax.swing.JTextField;

/**
 * Form Login dengan layout split: panel kiri branding, panel kanan form.
 * Panel kanan memakai BorderLayout + EmptyBorder(40) dan seluruh input
 * (Username, Password, Masuk) mengisi lebar penuh secara seragam.
 * Margin kanan sengaja 50px agar inti form tidak menempel ke tepi window.
 */
public class LoginFrame extends JFrame {

    private static final String USERNAME = "admin";
    private static final String PASSWORD = "admin";

    private static final int FORM_WIDTH = 520;

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
        brand.setLayout(new BorderLayout());

        JPanel pad = new JPanel();
        pad.setLayout(new BoxLayout(pad, BoxLayout.Y_AXIS));
        pad.setOpaque(false);
        pad.setBorder(BorderFactory.createEmptyBorder(34, 34, 26, 34));

        JLabel lblApp = new JLabel("SISTEM PAKAR");
        lblApp.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblApp.setForeground(new Color(255, 255, 255, 200));
        lblApp.setAlignmentX(JLabel.LEFT_ALIGNMENT);
        pad.add(lblApp);
        pad.add(Box.createVerticalStrut(18));

        JLabel lblTitle = new JLabel("<html><b>Sistem Pakar Diagnosa</b><br>Kerusakan Motor Matik</html>");
        lblTitle.setFont(Ui.FONT_TITLE);
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setAlignmentX(JLabel.LEFT_ALIGNMENT);
        pad.add(lblTitle);
        pad.add(Box.createVerticalStrut(16));

        JLabel lblDesc = new JLabel("<html><div style='width:320px;'>"
                + "Bantu identifikasi kerusakan sepeda motor matik Anda "
                + "berdasarkan gejala yang dialami, menggunakan metode "
                + "<b>Forward Chaining</b>.</div></html>");
        lblDesc.setFont(Ui.FONT_SUB);
        lblDesc.setForeground(new Color(214, 222, 244));
        lblDesc.setAlignmentX(JLabel.LEFT_ALIGNMENT);
        lblDesc.setPreferredSize(new Dimension(320, 62));
        lblDesc.setMaximumSize(new Dimension(320, 62));
        pad.add(lblDesc);
        pad.add(Box.createVerticalGlue());

        String[] poin = {
            " 10 gejala umum kerusakan motor matik",
            " 5 jenis kerusakan beserta solusinya",
            " Hasil langsung, mudah dipahami"
        };
        for (String poinText : poin) {
            JLabel row = new JLabel("<html><div style='width:315px;'>"
                    + "<span style='color:#F5A623;'><b><font size='4'>" + Ui.BULLET
                    + "</font></b></span>&nbsp;&nbsp;" + poinText + "</div></html>");
            row.setFont(Ui.FONT_BODY);
            row.setForeground(new Color(232, 236, 249));
            row.setAlignmentX(JLabel.LEFT_ALIGNMENT);
            row.setPreferredSize(new Dimension(320, 26));
            row.setMaximumSize(new Dimension(320, 26));
            pad.add(row);
            pad.add(Box.createVerticalStrut(12));
        }
        pad.add(Box.createVerticalGlue());

        JLabel lblFooter = new JLabel("v1.0 - Forward Chaining");
        lblFooter.setFont(Ui.FONT_SMALL);
        lblFooter.setForeground(new Color(255, 255, 255, 160));
        lblFooter.setAlignmentX(JLabel.LEFT_ALIGNMENT);
        pad.add(lblFooter);
        pad.add(Box.createVerticalStrut(8));

        brand.add(pad, BorderLayout.CENTER);
        return brand;
    }

    // ================= Papan kanan: form login =================
    private JPanel buildFormPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Ui.CARD_BG);
        panel.setPreferredSize(new Dimension(FORM_WIDTH, 640));

        JPanel inner = new JPanel();
        inner.setLayout(new BoxLayout(inner, BoxLayout.Y_AXIS));
        inner.setBackground(Ui.CARD_BG);
        inner.setBorder(BorderFactory.createEmptyBorder(40, 45, 40, 50));

        JLabel lblJudul = new JLabel("Selamat Datang");
        lblJudul.setFont(Ui.FONT_HEADER);
        lblJudul.setForeground(Ui.TEXT_MAIN);
        lblJudul.setAlignmentX(JLabel.LEFT_ALIGNMENT);
        inner.add(lblJudul);
        inner.add(Box.createVerticalStrut(6));

        JLabel lblSub = new JLabel("<html>Silakan masuk untuk memulai sesi diagnosa.</html>");
        lblSub.setFont(Ui.FONT_SUB);
        lblSub.setForeground(Ui.TEXT_MUTED);
        lblSub.setAlignmentX(JLabel.LEFT_ALIGNMENT);
        lblSub.setPreferredSize(new Dimension(FILL_WIDTH, 38));
        lblSub.setMaximumSize(new Dimension(FILL_WIDTH, 45));
        inner.add(lblSub);
        inner.add(Box.createVerticalStrut(30));

        inner.add(labelField("Username"));
        inner.add(Box.createVerticalStrut(8));
        inner.add(usernameWrapper());
        inner.add(Box.createVerticalStrut(18));

        inner.add(labelField("Password"));
        inner.add(Box.createVerticalStrut(8));
        inner.add(passwordWrapper());
        inner.add(Box.createVerticalStrut(10));

        lblError.setFont(Ui.FONT_SMALL);
        lblError.setForeground(Ui.ERROR);
        lblError.setAlignmentX(JLabel.LEFT_ALIGNMENT);
        lblError.setPreferredSize(new Dimension(FILL_WIDTH, 18));
        lblError.setMaximumSize(new Dimension(FILL_WIDTH, 18));
        inner.add(lblError);
        inner.add(Box.createVerticalStrut(18));

        btnLogin.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        btnLogin.setMaximumSize(new Dimension(FILL_WIDTH, 48));
        btnLogin.setPreferredSize(new Dimension(FILL_WIDTH, 48));
        inner.add(btnLogin);
        inner.add(Box.createVerticalStrut(20));

        JLabel lblHint = new JLabel("<html><div style='width:" + FILL_WIDTH
                + "px;text-align:center;'>"
                + "Kredensial bawaan: <b>admin / admin</b></div></html>");
        lblHint.setFont(Ui.FONT_SMALL);
        lblHint.setForeground(Ui.TEXT_MUTED);
        lblHint.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        lblHint.setPreferredSize(new Dimension(FILL_WIDTH, 25));
        lblHint.setMaximumSize(new Dimension(FILL_WIDTH, 25));
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

    /** Lebar konten isi panel kanan = lebar frame - padding kiri 45px - kanan 50px. */
    private static final int FILL_WIDTH = FORM_WIDTH - 95;

    private JLabel labelField(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(Ui.FONT_BOLD);
        lbl.setForeground(Ui.TEXT_MAIN);
        lbl.setAlignmentX(JLabel.LEFT_ALIGNMENT);
        return lbl;
    }

    /** Input Username selebar penuh konten. */
    private JPanel usernameWrapper() {
        JPanel wrap = new JPanel(new BorderLayout());
        wrap.setOpaque(false);
        wrap.setPreferredSize(new Dimension(FILL_WIDTH, 48));
        wrap.setMaximumSize(new Dimension(FILL_WIDTH, 48));
        sizeField(txtUsername, FILL_WIDTH, 48);
        wrap.add(txtUsername, BorderLayout.CENTER);
        wrap.setAlignmentX(JLabel.LEFT_ALIGNMENT);
        return wrap;
    }

    /** Row Password: JPasswordField CENTER (memanjang) + tombol "Lihat" EAST. */
    private JPanel passwordWrapper() {
        JPanel wrap = new JPanel(new BorderLayout(8, 0));
        wrap.setOpaque(false);
        wrap.setPreferredSize(new Dimension(FILL_WIDTH, 48));
        wrap.setMaximumSize(new Dimension(FILL_WIDTH, 48));

        txtPassword.setPreferredSize(new Dimension(FILL_WIDTH - 95, 48));

        wrap.add(txtPassword, BorderLayout.CENTER);

        btnShow.setPreferredSize(new Dimension(90, 48));
        btnShow.setMaximumSize(new Dimension(90, 48));
        btnShow.setMinimumSize(new Dimension(90, 48));
        wrap.add(btnShow, BorderLayout.EAST);

        wrap.setAlignmentX(JLabel.LEFT_ALIGNMENT);
        return wrap;
    }

    private void sizeField(JTextField field, int w, int h) {
        field.setPreferredSize(new Dimension(w, h));
        field.setMaximumSize(new Dimension(w, h));
        field.setMinimumSize(new Dimension(w, h));
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