package sistempakar;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 * Dialog Hasil Diagnosa: banner verdict, daftar gejala terpilih (checklist),
 * kesimpulan kerusakan, dan solusi penanganan.
 */
public class ResultDialog extends javax.swing.JDialog {

    private final Ui.ModernButton btnSelesai = Ui.accentButton("Selesai");

    public ResultDialog(javax.swing.JFrame owner, KnowledgeBase.Hasil hasil, boolean[] jawaban) {
        super(owner, "Hasil Diagnosa", true);
        setResizable(false);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Ui.PAGE_BG);
        buildUI(hasil, jawaban);
        pack();
        setLocationRelativeTo(owner);
    }

    private void buildUI(KnowledgeBase.Hasil hasil, boolean[] jawaban) {
        add(buildVerdict(hasil), BorderLayout.NORTH);
        add(buildBody(hasil, jawaban), BorderLayout.CENTER);

        JPanel foot = new JPanel(new GridBagLayout());
        foot.setBackground(Ui.PAGE_BG);
        foot.setBorder(BorderFactory.createEmptyBorder(4, 0, 20, 0));
        foot.setPreferredSize(new Dimension(700, 70));
        btnSelesai.setPreferredSize(new Dimension(190, 46));
        btnSelesai.setMaximumSize(new Dimension(190, 46));
        foot.add(btnSelesai);
        add(foot, BorderLayout.SOUTH);

        btnSelesai.addActionListener(e -> dispose());
    }

    // ================= Banner verdict =================
    private JPanel buildVerdict(KnowledgeBase.Hasil hasil) {
        Color c1 = hasil.terdeteksi ? Ui.SUCCESS : Ui.WARN_AMBER;
        Color c2 = hasil.terdeteksi ? Ui.SUCCESS_DARK : Ui.WARN_DARK;
        Ui.GradientPanel banner = new Ui.GradientPanel(c1, c2, false, 0);
        banner.setBorder(BorderFactory.createEmptyBorder(20, 34, 18, 34));
        banner.setLayout(new BoxLayout(banner, BoxLayout.Y_AXIS));
        banner.setPreferredSize(new Dimension(700, 96));

        JLabel lblIcon = new JLabel(hasil.terdeteksi ? "\u2713" : "\u21CA");
        lblIcon.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 46));
        lblIcon.setForeground(Color.WHITE);
        lblIcon.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        banner.add(lblIcon);
        banner.add(Box.createVerticalStrut(6));

        JLabel lblStatus = new JLabel(hasil.terdeteksi
                ? "Kerusakan Terdeteksi" : "Kerusakan Tidak Terdeteksi");
        lblStatus.setFont(Ui.FONT_HEADER);
        lblStatus.setForeground(Color.WHITE);
        lblStatus.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        banner.add(lblStatus);
        return banner;
    }

    // ================= Badan hasil =================
    private JPanel buildBody(KnowledgeBase.Hasil hasil, boolean[] jawaban) {
        JPanel body = new JPanel();
        body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));
        body.setBackground(Ui.PAGE_BG);

        List<String> gejalaList = KnowledgeBase.gejalaTerpilih(
                KnowledgeBase.kodeTerpilih(jawaban), true);

        // ---- Kartu gejala terpilih (dua kolom, tanpa scroll) ----
        int colCount = gejalaList.size() > 4 ? 2 : 1;
        int rows = gejalaList.isEmpty() ? 1 : (int) Math.ceil(gejalaList.size() / (double) colCount);
        int listHeight = rows * 28 + 8;
        int cardGejalaHeight = 46 + listHeight;

        Ui.CardPanel cardGejala = new Ui.CardPanel(16, Ui.BORDER);
        cardGejala.setLayout(new BorderLayout());
        cardGejala.setBorder(BorderFactory.createEmptyBorder(16, 18, 14, 18));
        cardGejala.setPreferredSize(new Dimension(620, cardGejalaHeight));
        cardGejala.setMaximumSize(new Dimension(620, cardGejalaHeight));

        JPanel gejalaInner = new JPanel();
        gejalaInner.setLayout(new BoxLayout(gejalaInner, BoxLayout.Y_AXIS));
        gejalaInner.setOpaque(false);
        gejalaInner.add(headerSection("Gejala yang Dipilih (" + gejalaList.size() + ")"));
        gejalaInner.add(Box.createVerticalStrut(8));

        if (gejalaList.isEmpty()) {
            JLabel empty = new JLabel("Tidak ada gejala yang Anda pilih (semua jawaban Tidak).");
            empty.setFont(Ui.FONT_BODY);
            empty.setForeground(Ui.TEXT_MUTED);
            gejalaInner.add(empty);
        } else {
            JPanel grid = new JPanel();
            grid.setLayout(new java.awt.GridLayout(rows, colCount, 12, 4));
            grid.setOpaque(false);
            for (String s : gejalaList) {
                JPanel item = new JPanel(new BorderLayout(8, 0));
                item.setOpaque(false);
                JLabel check = new JLabel("\u2713");
                check.setFont(Ui.FONT_BOLD);
                check.setForeground(Ui.SUCCESS);
                check.setPreferredSize(new Dimension(18, 22));
                item.add(check, BorderLayout.WEST);

                JLabel text = new JLabel("<html><div style='width:260px;'>" + s + "</div></html>");
                text.setFont(Ui.FONT_BODY);
                text.setForeground(Ui.TEXT_MAIN);
                item.add(text, BorderLayout.CENTER);
                grid.add(item);
            }
            gejalaInner.add(grid);
        }

        cardGejala.add(gejalaInner, BorderLayout.CENTER);
        body.add(cardGejala);
        body.add(Box.createVerticalStrut(14));

        // ---- Kartu kesimpulan ----
        Ui.CardPanel cardKesimpulan = new Ui.CardPanel(16, Ui.BORDER);
        cardKesimpulan.setLayout(new BorderLayout());
        cardKesimpulan.setBorder(BorderFactory.createEmptyBorder(14, 18, 14, 18));
        cardKesimpulan.setPreferredSize(new Dimension(620, 104));
        cardKesimpulan.setMaximumSize(new Dimension(620, 104));

        JPanel resultInner = new JPanel();
        resultInner.setLayout(new BoxLayout(resultInner, BoxLayout.Y_AXIS));
        resultInner.setOpaque(false);
        resultInner.add(headerSection("Kesimpulan Kerusakan"));
        resultInner.add(Box.createVerticalStrut(8));

        JPanel tagRow = new JPanel(new BorderLayout(12, 0));
        tagRow.setOpaque(false);
        if (hasil.terdeteksi) {
            Ui.Chip chipKode = new Ui.Chip(hasil.kodeKerusakan,
                    new Color(232, 238, 255), Ui.NAVY_MID, Ui.FONT_BOLD, 14);
            tagRow.add(chipKode, BorderLayout.WEST);
        }
        JLabel lblKerusakan = new JLabel("<html><div style='width:470px;font-weight:bold;color:#1F2A44;font-size:16px;'>"
                + hasil.kerusakan + "</div></html>");
        tagRow.add(lblKerusakan, BorderLayout.CENTER);
        resultInner.add(tagRow);
        cardKesimpulan.add(resultInner, BorderLayout.CENTER);
        body.add(cardKesimpulan);
        body.add(Box.createVerticalStrut(14));

        // ---- Kartu solusi ----
        Ui.CardPanel cardSolusi = new Ui.CardPanel(16, Ui.BORDER);
        cardSolusi.setLayout(new BorderLayout());
        cardSolusi.setBorder(BorderFactory.createEmptyBorder(14, 18, 14, 18));
        cardSolusi.setPreferredSize(new Dimension(620, 92));
        cardSolusi.setMaximumSize(new Dimension(620, 92));

        JPanel solusiInner = new JPanel();
        solusiInner.setLayout(new BoxLayout(solusiInner, BoxLayout.Y_AXIS));
        solusiInner.setOpaque(false);
        solusiInner.add(headerSection("Solusi Penanganan"));
        solusiInner.add(Box.createVerticalStrut(6));

        JLabel lblSolusi = new JLabel("<html><div style='width:570px;color:#3A4358;font-size:14.5px;'>"
                + hasil.solusi + "</div></html>");
        lblSolusi.setFont(Ui.FONT_BODY);
        solusiInner.add(lblSolusi);
        cardSolusi.add(solusiInner, BorderLayout.CENTER);
        body.add(cardSolusi);

        body.setBorder(BorderFactory.createEmptyBorder(22, 40, 14, 40));
        int bodyHeight = 36 + cardGejalaHeight + 14 + 104 + 14 + 92;
        body.setPreferredSize(new Dimension(700, Math.min(bodyHeight, 560)));
        body.setMaximumSize(new Dimension(700, 560));

        return body;
    }

    private JLabel headerSection(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(Ui.FONT_BOLD);
        lbl.setForeground(Ui.TEXT_MAIN);
        lbl.setAlignmentX(JLabel.LEFT_ALIGNMENT);
        return lbl;
    }
}