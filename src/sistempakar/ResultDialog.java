package sistempakar;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GridBagLayout;
import java.util.List;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 * Dialog Hasil Diagnosa: banner verdict, daftar gejala terpilih (checklist),
 * kesimpulan kerusakan, dan solusi penanganan.
 * Dimensi minimum 640 x 620; isi dihitung presisi dari konten (pack) agar
 * tidak ada teks terpotong.
 */
public class ResultDialog extends javax.swing.JDialog {

    private static final int DIALOG_W = 640;
    private static final int DIALOG_H = 620;
    private static final int CARD_W = DIALOG_W - 60;

    // Palet modern
    private static final Color EMERALD = new Color(0x10B981);
    private static final Color EMERALD_DARK = new Color(0x059669);
    private static final Color TEXT_DARK = new Color(0x0F172A);
    private static final Color DESC_TEXT = new Color(0x374151);
    private static final Color INDIGO = new Color(0x3730A3);
    private static final Color INDIGO_BG = new Color(0xE0E7FF);
    private static final Color SOL_BG = new Color(0xF0FDF4);
    private static final Color SOL_BORDER = new Color(0xBBF7D0);
    private static final Color SOL_TEXT = new Color(0x166534);
    private static final Color CODE_BLUE = new Color(0x1E3A8A);

    private final Ui.ModernButton btnSelesai = Ui.accentButton("Selesai");

    public ResultDialog(javax.swing.JFrame owner, KnowledgeBase.Hasil hasil, boolean[] jawaban) {
        super(owner, "Hasil Diagnosa", true);
        setResizable(true);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Ui.PAGE_BG);
        buildUI(hasil, jawaban);
        pack();
        setSize(Math.max(getWidth(), DIALOG_W), Math.max(getHeight(), DIALOG_H));
        setLocationRelativeTo(owner);
    }

    private void buildUI(KnowledgeBase.Hasil hasil, boolean[] jawaban) {
        add(buildVerdict(hasil), BorderLayout.NORTH);
        add(buildBody(hasil, jawaban), BorderLayout.CENTER);

        JPanel foot = new JPanel(new GridBagLayout());
        foot.setBackground(Ui.PAGE_BG);
        foot.setBorder(BorderFactory.createEmptyBorder(4, 0, 20, 0));
        foot.setPreferredSize(new Dimension(DIALOG_W, 70));
        btnSelesai.setPreferredSize(new Dimension(190, 46));
        btnSelesai.setMaximumSize(new Dimension(190, 46));
        foot.add(btnSelesai);
        add(foot, BorderLayout.SOUTH);

        btnSelesai.addActionListener(e -> dispose());
    }

    // ================= Banner verdict (emerald) =================
    private JPanel buildVerdict(KnowledgeBase.Hasil hasil) {
        Color c1 = hasil.terdeteksi ? EMERALD : Ui.WARN_AMBER;
        Color c2 = hasil.terdeteksi ? EMERALD_DARK : Ui.WARN_DARK;
        Ui.GradientPanel banner = new Ui.GradientPanel(c1, c2, false, 0);
        banner.setLayout(new BoxLayout(banner, BoxLayout.Y_AXIS));
        banner.setPreferredSize(new Dimension(DIALOG_W, 85));
        banner.setMaximumSize(new Dimension(Integer.MAX_VALUE, 85));
        banner.setBorder(BorderFactory.createEmptyBorder(8, 0, 8, 0));

        JLabel lblIcon = new JLabel(hasil.terdeteksi ? "\u2714" : "!");
        lblIcon.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblIcon.setForeground(Color.WHITE);
        lblIcon.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        banner.add(lblIcon);
        banner.add(Box.createVerticalStrut(4));

        JLabel lblStatus = new JLabel(hasil.terdeteksi
                ? "Kerusakan Terdeteksi" : "Kerusakan Tidak Terdeteksi");
        lblStatus.setFont(new Font("Segoe UI", Font.BOLD, 16));
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

        // ---- Kartu gejala terpilih (satu kolom, jarak 8px antar baris) ----
        List<JPanel> rows = new ArrayList<>();
        int rowTotal = 0;
        for (String s : gejalaList) {
            JPanel row = symptomRow(s);
            rows.add(row);
            rowTotal += row.getPreferredSize().height;
        }
        int gapCount = Math.max(0, rows.size() - 1);
        int listHeight = gejalaList.isEmpty() ? 40 : rowTotal + gapCount * 8 + 6;
        int cardGejalaHeight = 44 + listHeight;

        Ui.CardPanel cardGejala = new Ui.CardPanel(16, Ui.BORDER);
        cardGejala.setLayout(new BorderLayout());
        cardGejala.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
        cardGejala.setPreferredSize(new Dimension(CARD_W, cardGejalaHeight));
        cardGejala.setMaximumSize(new Dimension(CARD_W, cardGejalaHeight));

        JPanel gejalaInner = new JPanel();
        gejalaInner.setLayout(new BoxLayout(gejalaInner, BoxLayout.Y_AXIS));
        gejalaInner.setOpaque(false);
        gejalaInner.add(headerSection("Gejala yang Dipilih (" + gejalaList.size() + ")"));
        gejalaInner.add(Box.createVerticalStrut(8));

        if (gejalaList.isEmpty()) {
            JLabel empty = new JLabel("<html><div style='width:" + (CARD_W - 90)
                    + "px;padding-top:4px;padding-bottom:6px;'>"
                    + "Tidak ada gejala yang Anda pilih (semua jawaban Tidak).</div></html>");
            empty.setFont(Ui.FONT_BODY);
            empty.setForeground(Ui.TEXT_MUTED);
            empty.setAlignmentX(JLabel.LEFT_ALIGNMENT);
            empty.setPreferredSize(new Dimension(CARD_W - 40, 38));
            empty.setMaximumSize(new Dimension(CARD_W - 40, 38));
            gejalaInner.add(empty);
        } else {
            for (int i = 0; i < rows.size(); i++) {
                if (i > 0) {
                    gejalaInner.add(Box.createVerticalStrut(8));
                }
                gejalaInner.add(rows.get(i));
            }
        }

        cardGejala.add(gejalaInner, BorderLayout.CENTER);
        body.add(cardGejala);
        body.add(Box.createVerticalStrut(12));

        // ---- Kartu kesimpulan (fokus utama) ----
        JTextArea taKerusakan = wrapArea(hasil.kerusakan,
                new Font("Segoe UI", Font.BOLD, 16), TEXT_DARK, CARD_W - 150);
        int kesimpulanH = 40 + 42 + taKerusakan.getPreferredSize().height;

        Ui.CardPanel cardKesimpulan = new Ui.CardPanel(16, Ui.BORDER);
        cardKesimpulan.setLayout(new BorderLayout());
        cardKesimpulan.setBorder(BorderFactory.createEmptyBorder(14, 18, 14, 18));
        cardKesimpulan.setPreferredSize(new Dimension(CARD_W, kesimpulanH));
        cardKesimpulan.setMaximumSize(new Dimension(CARD_W, kesimpulanH));

        JPanel resultInner = new JPanel();
        resultInner.setLayout(new BoxLayout(resultInner, BoxLayout.Y_AXIS));
        resultInner.setOpaque(false);
        resultInner.add(headerSection("Kesimpulan Kerusakan"));
        resultInner.add(Box.createVerticalStrut(10));

        JPanel tagRow = new JPanel(new BorderLayout(14, 0));
        tagRow.setOpaque(false);
        if (hasil.terdeteksi) {
            tagRow.add(badgePill(hasil.kodeKerusakan), BorderLayout.WEST);
        }
        tagRow.add(taKerusakan, BorderLayout.CENTER);
        resultInner.add(tagRow);
        cardKesimpulan.add(resultInner, BorderLayout.CENTER);
        body.add(cardKesimpulan);
        body.add(Box.createVerticalStrut(12));

        // ---- Kartu solusi (latar hijau lembut) ----
        int solusiH = 40 + 34 + 30 + wrapHeight(hasil.solusi, Ui.FONT_BODY, CARD_W - 80);

        Ui.CardPanel cardSolusi = new Ui.CardPanel(16, SOL_BORDER, SOL_BG);
        cardSolusi.setLayout(new BorderLayout());
        cardSolusi.setBorder(BorderFactory.createEmptyBorder(14, 18, 14, 18));
        cardSolusi.setPreferredSize(new Dimension(CARD_W, solusiH));
        cardSolusi.setMaximumSize(new Dimension(CARD_W, solusiH));

        JPanel solusiInner = new JPanel();
        solusiInner.setLayout(new BoxLayout(solusiInner, BoxLayout.Y_AXIS));
        solusiInner.setOpaque(false);
        solusiInner.add(headerSection("Solusi Penanganan"));
        solusiInner.add(Box.createVerticalStrut(10));

        JLabel lblTip = new JLabel("\uD83D\uDCA1  Rekomendasi Tindakan:");
        lblTip.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblTip.setForeground(SOL_TEXT);
        lblTip.setAlignmentX(JLabel.LEFT_ALIGNMENT);
        solusiInner.add(lblTip);
        solusiInner.add(Box.createVerticalStrut(6));

        JTextArea taSolusi = wrapArea(hasil.solusi,
                new Font("Segoe UI", Font.BOLD, 13), SOL_TEXT, CARD_W - 80);
        solusiInner.add(taSolusi);
        cardSolusi.add(solusiInner, BorderLayout.CENTER);
        body.add(cardSolusi);

        body.setBorder(BorderFactory.createEmptyBorder(20, 30, 14, 30));
        int bodyHeight = 36 + cardGejalaHeight + 12 + kesimpulanH + 12 + solusiH;
        int capH = DIALOG_H - 175;
        body.setPreferredSize(new Dimension(DIALOG_W, Math.min(bodyHeight, capH)));
        body.setMaximumSize(new Dimension(DIALOG_W, Math.min(bodyHeight, capH)));

        return body;
    }

    /** Badge kode kerusakan (P03) bercorak indigo. */
    private JLabel badgePill(String text) {
        JLabel pill = new JLabel(text) {
            @Override
            protected void paintComponent(java.awt.Graphics g) {
                java.awt.Graphics2D g2 = (java.awt.Graphics2D) g.create();
                g2.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING,
                        java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(INDIGO_BG);
                g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 12, 12);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        pill.setFont(new Font("Segoe UI", Font.BOLD, 14));
        pill.setForeground(INDIGO);
        pill.setBorder(BorderFactory.createEmptyBorder(6, 12, 6, 12));
        return pill;
    }

    /** Satu baris gejala: bullet "•" + kode tebal + keterangan (word-wrap). */
    private JPanel symptomRow(String s) {
        JPanel row = new JPanel(new BorderLayout(10, 0));
        row.setOpaque(false);

        JLabel check = new JLabel(Ui.BULLET);
        check.setFont(new Font("Segoe UI", Font.BOLD, 18));
        check.setForeground(Ui.ACCENT);
        check.setPreferredSize(new Dimension(20, 26));
        row.add(check, BorderLayout.WEST);

        String code = s;
        String desc = "";
        int idx = s.indexOf(" - ");
        String html;
        if (idx > 0) {
            code = s.substring(0, idx);
            desc = s.substring(idx + 3);
            html = "<html><div style='width:" + (CARD_W - 90) + "px;'>"
                    + "<b style='color:#1E3A8A;'>" + code + "</b>"
                    + " <font color='#374151'>- " + desc + "</font></div></html>";
        } else {
            html = "<html><div style='width:" + (CARD_W - 90) + "px; color:#374151;'>"
                    + s + "</div></html>";
        }

        JLabel text = new JLabel(html);
        text.setFont(Ui.FONT_BODY);
        int rowH = Math.max(30, text.getPreferredSize().height + 6);
        row.setPreferredSize(new Dimension(CARD_W - 32, rowH));
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, rowH));
        row.add(text, BorderLayout.CENTER);
        return row;
    }

    private JTextArea wrapArea(String text, Font font, Color fg, int maxWidth) {
        JTextArea ta = new JTextArea(text);
        ta.setEditable(false);
        ta.setFocusable(false);
        ta.setOpaque(false);
        ta.setLineWrap(true);
        ta.setWrapStyleWord(true);
        ta.setBorder(null);
        ta.setFont(font);
        ta.setForeground(fg);
        ta.setPreferredSize(new Dimension(maxWidth, estimateWrapHeight(text, font, maxWidth)));
        ta.setMaximumSize(new Dimension(maxWidth, estimateWrapHeight(text, font, maxWidth)));
        return ta;
    }

    private int wrapHeight(String text, Font font, int maxWidth) {
        return estimateWrapHeight(text, font, maxWidth);
    }

    private int estimateWrapHeight(String text, Font font, int maxWidth) {
        JTextArea probe = new JTextArea(text);
        probe.setFont(font);
        FontMetrics fm = probe.getFontMetrics(font);
        int lines = 0;
        for (String line : text.split("\n")) {
            if (line.isEmpty()) {
                lines++;
            } else {
                int width = 0;
                for (String word : line.split(" ")) {
                    width += fm.stringWidth(word) + fm.stringWidth(" ");
                    if (width > maxWidth) {
                        lines++;
                        width = fm.stringWidth(word) + fm.stringWidth(" ");
                    }
                }
                lines++;
            }
        }
        return Math.max(lines, 1) * fm.getHeight() + 12;
    }

    private JLabel headerSection(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(Ui.FONT_BOLD);
        lbl.setForeground(Ui.TEXT_MAIN);
        lbl.setAlignmentX(JLabel.LEFT_ALIGNMENT);
        return lbl;
    }
}