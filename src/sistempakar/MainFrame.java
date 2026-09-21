package sistempakar;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Form Utama / Dashboard dengan hero, statistik, dan langkah penggunaan.
 * Ukuran window disusun presisi dari konten (pack) agar tidak ada isi terpotong.
 */
public class MainFrame extends javax.swing.JFrame {

    public MainFrame() {
        setTitle("Dashboard - Sistem Pakar Diagnosa Motor Matik");
        setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Ui.PAGE_BG);
        buildUI();
        pack();
        setLocationRelativeTo(null);
    }

    private void buildUI() {
        add(buildHero(), BorderLayout.NORTH);
        add(buildContent(), BorderLayout.CENTER);
    }

    // ================= Hero banner =================
    private JPanel buildHero() {
        Ui.GradientPanel hero = new Ui.GradientPanel(Ui.NAVY, Ui.NAVY_MID, true, 5);
        hero.setBorder(BorderFactory.createEmptyBorder(28, 44, 24, 44));
        hero.setLayout(new BoxLayout(hero, BoxLayout.Y_AXIS));

        JLabel lblKicker = new JLabel("SISTEM PAKAR - FORWARD CHAINING");
        lblKicker.setFont(Ui.FONT_SMALL);
        lblKicker.setForeground(Ui.ACCENT);
        lblKicker.setAlignmentX(JLabel.LEFT_ALIGNMENT);
        hero.add(lblKicker);
        hero.add(Box.createVerticalStrut(10));

        JLabel lblTitle = new JLabel("Sistem Pakar Diagnosa Kerusakan Motor Matik");
        lblTitle.setFont(Ui.FONT_TITLE);
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setAlignmentX(JLabel.LEFT_ALIGNMENT);
        hero.add(lblTitle);
        hero.add(Box.createVerticalStrut(8));

        JLabel lblDesc = new JLabel("Lakukan diagnosa kerusakan berdasarkan gejala yang Anda rasakan.");
        lblDesc.setFont(Ui.FONT_SUB);
        lblDesc.setForeground(new Color(214, 222, 244));
        lblDesc.setAlignmentX(JLabel.LEFT_ALIGNMENT);
        hero.add(lblDesc);
        hero.add(Box.createVerticalStrut(20));

        Ui.ModernButton btnMulai = Ui.accentButton("Mulai Diagnosa");
        btnMulai.setPreferredSize(new Dimension(210, 46));
        btnMulai.setMaximumSize(new Dimension(210, 46));
        btnMulai.setAlignmentX(JLabel.LEFT_ALIGNMENT);
        btnMulai.addActionListener(e -> {
            dispose();
            new DiagnosisFrame().setVisible(true);
        });
        hero.add(btnMulai);

        hero.setPreferredSize(new Dimension(820, 225));
        return hero;
    }

    // ================= Konten =================
    private JPanel buildContent() {
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBackground(Ui.PAGE_BG);
        content.setBorder(BorderFactory.createEmptyBorder(24, 40, 24, 40));

        JLabel lblJudul = new JLabel("Ringkasan Sistem");
        lblJudul.setFont(Ui.FONT_HEADER);
        lblJudul.setForeground(Ui.TEXT_MAIN);
        lblJudul.setAlignmentX(JLabel.LEFT_ALIGNMENT);
        content.add(lblJudul);
        content.add(Box.createVerticalStrut(14));
        content.add(buildStatsRow());
        content.add(Box.createVerticalStrut(22));

        JLabel lblCara = new JLabel("Cara Menggunakan");
        lblCara.setFont(Ui.FONT_HEADER);
        lblCara.setForeground(Ui.TEXT_MAIN);
        lblCara.setAlignmentX(JLabel.LEFT_ALIGNMENT);
        content.add(lblCara);
        content.add(Box.createVerticalStrut(14));
        content.add(buildStepsRow());

        content.setPreferredSize(new Dimension(820, 408));
        return content;
    }

    private JPanel buildStatsRow() {
        JPanel row = new JPanel();
        row.setLayout(new BoxLayout(row, BoxLayout.X_AXIS));
        row.setOpaque(false);
        row.setMaximumSize(new Dimension(740, 110));
        row.setAlignmentX(JLabel.LEFT_ALIGNMENT);

        row.add(statCard("10", "Gejala Diagnosa"));
        row.add(Box.createHorizontalStrut(18));
        row.add(statCard("5", "Jenis Kerusakan"));
        row.add(Box.createHorizontalStrut(18));
        row.add(statCard("1", "Metode Forward Chaining"));
        return row;
    }

    private JPanel statCard(String num, String caption) {
        Ui.CardPanel card = new Ui.CardPanel(16, Ui.BORDER);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setPreferredSize(new Dimension(234, 104));
        card.setMaximumSize(new Dimension(234, 104));
        card.setBorder(BorderFactory.createEmptyBorder(18, 20, 12, 20));

        JLabel lblNum = new JLabel(num);
        lblNum.setFont(Ui.FONT_NUM);
        lblNum.setForeground(Ui.NAVY_MID);
        lblNum.setAlignmentX(JLabel.LEFT_ALIGNMENT);
        card.add(lblNum);
        card.add(Box.createVerticalStrut(4));

        JLabel lblC = new JLabel(caption);
        lblC.setFont(Ui.FONT_SMALL);
        lblC.setForeground(Ui.TEXT_MUTED);
        lblC.setAlignmentX(JLabel.LEFT_ALIGNMENT);
        card.add(lblC);
        return card;
    }

    private JPanel buildStepsRow() {
        JPanel row = new JPanel();
        row.setLayout(new BoxLayout(row, BoxLayout.X_AXIS));
        row.setOpaque(false);
        row.setMaximumSize(new Dimension(740, 170));
        row.setAlignmentX(JLabel.LEFT_ALIGNMENT);

        row.add(stepCard("1", "Jawab Pertanyaan", "Pilih gejala yang sesuai dengan kondisi motor Anda, disajikan satu per satu."));
        row.add(Box.createHorizontalStrut(18));
        row.add(stepCard("2", "Analisis Otomatis", "Sistem menelusuri aturan Forward Chaining untuk menemukan kesimpulan."));
        row.add(Box.createHorizontalStrut(18));
        row.add(stepCard("3", "Lihat Solusi", "Keterangan kerusakan dan solusi penanganan untuk Anda konsultasikan."));
        return row;
    }

    private JPanel stepCard(String no, String title, String desc) {
        Ui.CardPanel card = new Ui.CardPanel(16, Ui.BORDER);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setPreferredSize(new Dimension(234, 158));
        card.setMaximumSize(new Dimension(234, 158));
        card.setBorder(BorderFactory.createEmptyBorder(16, 18, 14, 18));

        Ui.GradientPanel step = new Ui.GradientPanel(Ui.NAVY, Ui.NAVY_MID, false, 0);
        step.setPreferredSize(new Dimension(42, 42));
        step.setMaximumSize(new Dimension(42, 42));
        step.setAlignmentX(JLabel.LEFT_ALIGNMENT);
        step.setLayout(new java.awt.GridBagLayout());
        JLabel lblNo = new JLabel(no);
        lblNo.setFont(Ui.FONT_BOLD);
        lblNo.setForeground(Ui.ACCENT);
        step.add(lblNo);
        card.add(step);
        card.add(Box.createVerticalStrut(10));

        JLabel lblT = new JLabel(title);
        lblT.setFont(Ui.FONT_BOLD);
        lblT.setForeground(Ui.TEXT_MAIN);
        lblT.setAlignmentX(JLabel.LEFT_ALIGNMENT);
        card.add(lblT);
        card.add(Box.createVerticalStrut(6));

        JLabel lblD = new JLabel("<html><div style='width:198px;'>" + desc + "</div></html>");
        lblD.setFont(Ui.FONT_SMALL);
        lblD.setForeground(Ui.TEXT_MUTED);
        lblD.setAlignmentX(JLabel.LEFT_ALIGNMENT);
        card.add(lblD);
        return card;
    }
}