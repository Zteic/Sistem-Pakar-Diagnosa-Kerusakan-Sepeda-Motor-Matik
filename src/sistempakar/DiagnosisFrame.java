package sistempakar;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;

/**
 * Form Diagnosa Gejala: pertanyaan G01-G10 satu per satu dengan
 * progress bar, kartu pertanyaan, dan opsi jawaban kartu radio.
 */
public class DiagnosisFrame extends javax.swing.JFrame implements ItemListener {

    private static final int TOTAL = KnowledgeBase.KODE_GEJALA.length;

    private final boolean[] jawaban = new boolean[TOTAL];
    private final ButtonGroup btnGroup = new ButtonGroup();
    private int index = 0;
    private boolean selesaiDiproses = false;

    private final JLabel lblProgress = new JLabel();
    private final Ui.Chip chipKode = new Ui.Chip("G01", new Color(232, 238, 255),
            Ui.NAVY_MID, Ui.FONT_BOLD, 14);
    private final JTextArea txtGejala = new JTextArea();
    private final Ui.ModernButton btnSelanjutnya = Ui.primaryButton("Selanjutnya");
    private final Ui.ModernButton btnKembali = Ui.ghostButton("Kembali");
    private final Ui.ProgressTrack progressTrack = new Ui.ProgressTrack(8);

    private final JRadioButton rbYa = buildRadio("Ya");
    private final JRadioButton rbTidak = buildRadio("Tidak");
    private final Ui.CardPanel cardYa;
    private final Ui.CardPanel cardTidak;

    public DiagnosisFrame() {
        cardYa = optionCard(rbYa);
        cardTidak = optionCard(rbTidak);

        setTitle("Diagnosa Gejala - Sistem Pakar Motor Matik");
        setDefaultCloseOperation(javax.swing.JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Ui.PAGE_BG);
        buildUI();

        btnSelanjutnya.addActionListener(e -> onSelanjutnya());
        btnKembali.addActionListener(e -> onKembali());
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                if (!selesaiDiproses) {
                    new MainFrame().setVisible(true);
                }
            }
        });

        pack();
        setLocationRelativeTo(null);
        tampilkanGejala(index);
    }

    // ================= Konstruksi UI =================
    private void buildUI() {
        add(buildHeader(), BorderLayout.NORTH);
        add(buildQuestion(), BorderLayout.CENTER);
    }

    private JPanel buildHeader() {
        Ui.GradientPanel header = new Ui.GradientPanel(Ui.NAVY, Ui.NAVY_MID, false, 0);
        header.setBorder(BorderFactory.createEmptyBorder(24, 34, 18, 34));
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.setPreferredSize(new Dimension(660, 118));

        JLabel lblTitle = new JLabel("Form Diagnosa Gejala");
        lblTitle.setFont(Ui.FONT_HEADER);
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setAlignmentX(JLabel.LEFT_ALIGNMENT);
        header.add(lblTitle);
        header.add(Box.createVerticalStrut(4));

        lblProgress.setFont(Ui.FONT_BODY);
        lblProgress.setForeground(new Color(214, 222, 244));
        lblProgress.setAlignmentX(JLabel.LEFT_ALIGNMENT);
        header.add(lblProgress);
        header.add(Box.createVerticalStrut(14));

        progressTrack.setAlignmentX(JLabel.LEFT_ALIGNMENT);
        header.add(progressTrack);
        return header;
    }

    private JPanel buildQuestion() {
        JPanel wrap = new JPanel();
        wrap.setLayout(new BoxLayout(wrap, BoxLayout.Y_AXIS));
        wrap.setBackground(Ui.PAGE_BG);
        wrap.setBorder(BorderFactory.createEmptyBorder(28, 40, 30, 40));

        Ui.CardPanel card = new Ui.CardPanel(18, Ui.BORDER);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setPreferredSize(new Dimension(580, 300));
        card.setMaximumSize(new Dimension(580, 300));
        card.setBorder(BorderFactory.createEmptyBorder(26, 30, 22, 30));

        chipKode.setAlignmentX(JLabel.LEFT_ALIGNMENT);
        card.add(chipKode);
        card.add(Box.createVerticalStrut(16));

        txtGejala.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 17));
        txtGejala.setForeground(Ui.TEXT_MAIN);
        txtGejala.setEditable(false);
        txtGejala.setFocusable(false);
        txtGejala.setOpaque(false);
        txtGejala.setLineWrap(true);
        txtGejala.setWrapStyleWord(true);
        txtGejala.setAlignmentX(JTextArea.LEFT_ALIGNMENT);
        card.add(txtGejala);
        card.add(Box.createVerticalStrut(20));

        JPanel options = new JPanel();
        options.setLayout(new BoxLayout(options, BoxLayout.X_AXIS));
        options.setOpaque(false);
        options.setMaximumSize(new Dimension(520, 72));
        options.setAlignmentX(JLabel.LEFT_ALIGNMENT);
        options.add(cardYa);
        options.add(Box.createHorizontalStrut(16));
        options.add(cardTidak);
        card.add(options);
        card.add(Box.createVerticalGlue());

        JPanel btnRow = new JPanel(new BorderLayout());
        btnRow.setOpaque(false);
        btnRow.setMaximumSize(new Dimension(520, 46));
        btnRow.setAlignmentX(JLabel.LEFT_ALIGNMENT);
        btnKembali.setPreferredSize(new Dimension(120, 44));
        btnKembali.setMaximumSize(new Dimension(120, 44));
        btnRow.add(btnKembali, BorderLayout.WEST);
        btnSelanjutnya.setPreferredSize(new Dimension(190, 44));
        btnSelanjutnya.setMaximumSize(new Dimension(190, 44));
        btnRow.add(btnSelanjutnya, BorderLayout.EAST);
        card.add(btnRow);

        wrap.add(card);
        wrap.setPreferredSize(new Dimension(660, 360));
        return wrap;
    }

    private JRadioButton buildRadio(String text) {
        JRadioButton rb = new JRadioButton(text);
        rb.setFont(Ui.FONT_BODY);
        rb.setForeground(Ui.TEXT_MAIN);
        rb.setOpaque(false);
        rb.setFocusPainted(false);
        rb.setIcon(new Ui.RadioIcon(false));
        rb.setSelectedIcon(new Ui.RadioIcon(true));
        btnGroup.add(rb);
        rb.addItemListener(this);
        return rb;
    }

    private Ui.CardPanel optionCard(JRadioButton rb) {
        Ui.CardPanel card = new Ui.CardPanel(14, Ui.BORDER);
        card.setLayout(new GridBagLayout());
        card.setPreferredSize(new Dimension(252, 68));
        card.setMaximumSize(new Dimension(252, 68));
        card.add(rb);
        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                rb.doClick();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                card.setCardHover(true);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                card.setCardHover(false);
            }
        });
        return card;
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        updateOptionBorders();
    }

    private void updateOptionBorders() {
        cardYa.setCardBorder(rbYa.isSelected() ? Ui.SUCCESS : Ui.BORDER);
        cardTidak.setCardBorder(rbTidak.isSelected() ? Ui.SUCCESS : Ui.BORDER);
        cardYa.setCardHover(rbYa.isSelected());
        cardTidak.setCardHover(rbTidak.isSelected());
    }

    // ================= Logika =================
    private void tampilkanGejala(int i) {
        lblProgress.setText("Pertanyaan " + (i + 1) + " dari " + TOTAL);
        chipKode.setText(KnowledgeBase.KODE_GEJALA[i]);
        txtGejala.setText(KnowledgeBase.DESKRIPSI_GEJALA[i]);
        txtGejala.setCaretPosition(0);
        progressTrack.setFraction((double) (i + 1) / TOTAL);

        btnGroup.clearSelection();
        if (jawaban[i]) {
            rbYa.setSelected(true);
        } else if (i > 0) {
            rbTidak.setSelected(true);
        }
        updateOptionBorders();

        btnKembali.setEnabled(i > 0);
        boolean terakhir = (i == TOTAL - 1);
        btnSelanjutnya.setText(terakhir ? "Lihat Hasil" : "Selanjutnya");
    }

    private void onKembali() {
        if (index == 0) {
            return;
        }
        simpanJawaban(index);
        index--;
        tampilkanGejala(index);
    }

    private void onSelanjutnya() {
        if (!rbYa.isSelected() && !rbTidak.isSelected()) {
            JOptionPane.showMessageDialog(this,
                    "Silakan pilih jawaban terlebih dahulu.",
                    "Perhatian",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        simpanJawaban(index);

        if (index < TOTAL - 1) {
            index++;
            tampilkanGejala(index);
        } else {
            tampilkanHasil();
        }
    }

    private void simpanJawaban(int i) {
        if (i >= 0 && i < TOTAL) {
            jawaban[i] = rbYa.isSelected();
        }
    }

    private void tampilkanHasil() {
        KnowledgeBase.Hasil hasil = KnowledgeBase.forwardChaining(jawaban);
        ResultDialog dialog = new ResultDialog(this, hasil, jawaban);
        dialog.setVisible(true);

        selesaiDiproses = true;
        dispose();
        new MainFrame().setVisible(true);
    }
}