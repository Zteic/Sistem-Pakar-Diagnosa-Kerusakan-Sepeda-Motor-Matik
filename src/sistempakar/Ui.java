package sistempakar;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.Icon;

/**
 * Sistem desain tampilan: tema Biru Navy + aksen Amber.
 * Semua komponen murni Swing (Java 8), tanpa library eksternal atau file gambar.
 */
public final class Ui {

    // ---- Palet warna (tema navy elegan) ----
    public static final Color NAVY = new Color(16, 28, 78);
    public static final Color NAVY_MID = new Color(39, 52, 139);
    public static final Color NAVY_LIGHT = new Color(88, 116, 224);
    public static final Color ACCENT = new Color(245, 166, 35);
    public static final Color ACCENT_HOVER = new Color(255, 182, 61);
    public static final Color ACCENT_DARK = new Color(216, 137, 12);
    public static final Color PAGE_BG = new Color(237, 240, 247);
    public static final Color CARD_BG = Color.WHITE;
    public static final Color TEXT_MAIN = new Color(31, 42, 68);
    public static final Color TEXT_MUTED = new Color(110, 120, 142);
    public static final Color SUCCESS = new Color(34, 164, 93);
    public static final Color SUCCESS_DARK = new Color(21, 110, 64);
    public static final Color WARN_AMBER = new Color(214, 150, 34);
    public static final Color WARN_DARK = new Color(160, 112, 20);
    public static final Color ERROR = new Color(206, 70, 70);
    public static final Color BORDER = new Color(219, 226, 240);
    public static final Color FIELD_BG = new Color(246, 248, 252);

    // ---- Simbol aman (render di semua sistem) ----
    public static final String BULLET = "\u2022";
    public static final String CHECK = "\u2713";

    // ---- Tipografi ----
    public static final Font FONT_APP = new Font("Segoe UI", Font.PLAIN, 14);
    public static final Font FONT_TITLE = new Font("Segoe UI", Font.BOLD, 26);
    public static final Font FONT_HEADER = new Font("Segoe UI", Font.BOLD, 19);
    public static final Font FONT_SUB = new Font("Segoe UI", Font.PLAIN, 15);
    public static final Font FONT_BODY = new Font("Segoe UI", Font.PLAIN, 14);
    public static final Font FONT_SMALL = new Font("Segoe UI", Font.PLAIN, 12);
    public static final Font FONT_BOLD = new Font("Segoe UI", Font.BOLD, 14);
    public static final Font FONT_NUM = new Font("Segoe UI", Font.BOLD, 32);

    private Ui() {
    }

    /** Panel dengan latar gradasi navy + garis aksen amber di bawah. */
    public static class GradientPanel extends JPanel {
        private final Color c1;
        private final Color c2;
        private final boolean accentLine;
        private final int accentHeight;

        public GradientPanel(Color c1, Color c2, boolean accentLine, int accentHeight) {
            this.c1 = c1;
            this.c2 = c2;
            this.accentLine = accentLine;
            this.accentHeight = accentHeight;
            setOpaque(true);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setPaint(new GradientPaint(0, 0, c1, getWidth(), getHeight(), c2));
            g2.fillRect(0, 0, getWidth(), getHeight());
            if (accentLine) {
                g2.setColor(ACCENT);
                g2.fillRect(0, getHeight() - accentHeight, getWidth(), accentHeight);
            }
            g2.dispose();
        }
    }

    /** Kartu putih membulat dengan garis tepi opsional. */
    public static class CardPanel extends JPanel {
        private int radius;
        private Color borderColor;
        private Color fill;
        private boolean hover;

        public CardPanel(int radius, Color borderColor) {
            this(radius, borderColor, CARD_BG);
        }

        public CardPanel(int radius, Color borderColor, Color fill) {
            this.radius = radius;
            this.borderColor = borderColor;
            this.fill = fill;
            setOpaque(false);
            if (borderColor != null) {
                setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }
        }

        public void setCardBorder(Color c) {
            this.borderColor = c;
            repaint();
        }

        public void setCardHover(boolean h) {
            this.hover = h;
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            int w = getWidth();
            int h = getHeight();

            for (int i = 4; i >= 1; i--) {
                g2.setColor(new Color(16, 28, 78, 8 * i));
                g2.fillRoundRect(i, i + 3, w - i * 2 - 1, h - i * 2 - 4, radius, radius);
            }

            g2.setColor(fill);
            g2.fillRoundRect(1, 1, w - 2, h - 2, radius, radius);

            Color bc = this.borderColor;
            if (bc != null) {
                g2.setStroke(new BasicStroke(hover ? 2.0f : 1.0f));
                g2.setColor(hover ? NAVY_LIGHT : bc);
                g2.drawRoundRect(2, 2, w - 5, h - 5, radius, radius);
            }
            g2.dispose();
            super.paintComponent(g);
        }
    }

    /** Tombol membulat dengan efek hover / tekan. */
    public static class ModernButton extends JButton {
        private final Color fill;
        private final Color hoverFill;
        private final Color pressFill;
        private final Color textColor;
        private final boolean outline;
        private int radius;
        private boolean isHover;
        private boolean isPress;

        public ModernButton(String text, Color fill, Color hoverFill, Color pressFill,
                            Color textColor, boolean outline, int radius) {
            super(text);
            this.fill = fill;
            this.hoverFill = hoverFill;
            this.pressFill = pressFill;
            this.textColor = textColor;
            this.outline = outline;
            this.radius = radius;
            setFont(FONT_BODY);
            setForeground(textColor);
            setFocusPainted(false);
            setBorderPainted(false);
            setContentAreaFilled(false);
            setOpaque(false);
            setUI(new javax.swing.plaf.basic.BasicButtonUI());
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    isHover = true;
                    repaint();
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    isHover = false;
                    isPress = false;
                    repaint();
                }

                @Override
                public void mousePressed(MouseEvent e) {
                    isPress = true;
                    repaint();
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    isPress = false;
                    repaint();
                }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            int w = getWidth();
            int h = getHeight();
            Color c = isPress ? pressFill : (isHover ? hoverFill : fill);

            g2.setComposite(AlphaComposite.SrcOver);
            g2.setColor(c);
            g2.fillRoundRect(0, 0, w - 1, h - 1, radius, radius);
            if (outline) {
                g2.setColor(new Color(255, 255, 255, isHover ? 220 : 255));
                g2.setStroke(new BasicStroke(2f));
                g2.drawRoundRect(1, 1, w - 4, h - 4, radius - 2, radius - 2);
            }
            g2.dispose();
            super.paintComponent(g);
        }
    }

    // ---- Factory tombol ----
    public static ModernButton accentButton(String text) {
        return new ModernButton(text, ACCENT, ACCENT_HOVER, ACCENT_DARK, Color.WHITE, false, 24);
    }

    public static ModernButton primaryButton(String text) {
        return new ModernButton(text, NAVY_MID, NAVY_LIGHT, NAVY, Color.WHITE, false, 24);
    }

    public static ModernButton ghostButton(String text) {
        return new ModernButton(text, new Color(242, 244, 249), new Color(226, 231, 245),
                new Color(210, 218, 238), TEXT_MAIN, false, 24);
    }

    /** Lencana kode gejala (misal G01) berbentuk pil. */
    public static class Chip extends JLabel {
        private final Color bg;
        private final int radius;

        public Chip(String text, Color bg, Color fg, Font font, int radius) {
            super(text);
            this.bg = bg;
            this.radius = radius;
            setFont(font);
            setForeground(fg);
            setOpaque(false);
            setHorizontalAlignment(JLabel.CENTER);
            setBorder(BorderFactory.createEmptyBorder(5, 16, 5, 16));
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(bg);
            g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);
            g2.dispose();
            super.paintComponent(g);
        }

        @Override
        public Dimension getPreferredSize() {
            Dimension d = super.getPreferredSize();
            return new Dimension(d.width, Math.max(d.height, 32));
        }
    }

    /**
     * Border garis tepi untuk bidang input.
     * Hanya menggambar stroke (teks & kursor tidak tertutup oleh fill).
     */
    private static final javax.swing.border.AbstractBorder FIELD_BORDER = new javax.swing.border.AbstractBorder() {
        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            RoundRectangle2D r = new RoundRectangle2D.Float(x, y, w - 1, h - 1, 14, 14);
            g2.setStroke(new BasicStroke(c.hasFocus() ? 2f : 1.2f));
            g2.setColor(c.hasFocus() ? NAVY_LIGHT : BORDER);
            g2.draw(r);
            g2.dispose();
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(12, 14, 12, 14);
        }
    };

    /** Bidang teks membulat: latar digambar di paintComponent agar tidak menutupi teks/kursor. */
    public static class RoundedTextField extends JTextField {
        public RoundedTextField(String text) {
            super(text);
            styleField(this);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(FIELD_BG);
            g2.fillRoundRect(1, 1, getWidth() - 2, getHeight() - 2, 14, 14);
            g2.dispose();
            super.paintComponent(g);
        }
    }

    /** Bidang password membulat (sama dengan RoundedTextField). */
    public static class RoundedPasswordField extends JPasswordField {
        public RoundedPasswordField(String text) {
            super(text);
            styleField(this);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(FIELD_BG);
            g2.fillRoundRect(1, 1, getWidth() - 2, getHeight() - 2, 14, 14);
            g2.dispose();
            super.paintComponent(g);
        }
    }

    /** Label HTML dengan lebar terkunci (mencegah bocor/render overflow). */
    public static JLabel htmlLabel(String html, Font font, Color fg, int width, int alignment) {
        JLabel lbl = new JLabel(html);
        lbl.setUI(new javax.swing.plaf.basic.BasicLabelUI());
        lbl.setFont(font);
        lbl.setForeground(fg);
        lbl.setOpaque(false);
        lbl.setHorizontalAlignment(alignment);
        Dimension d = lbl.getPreferredSize();
        lbl.setPreferredSize(new Dimension(width, Math.max(d.height, 22)));
        lbl.setMaximumSize(new Dimension(width, Math.max(d.height, 22)));
        return lbl;
    }

    public static void styleField(final JTextField f) {
        f.setFont(FONT_BODY);
        f.setForeground(TEXT_MAIN);
        f.setCaretColor(NAVY);
        f.setOpaque(false);
        f.setSelectedTextColor(Color.WHITE);
        f.setSelectionColor(NAVY_LIGHT);
        f.setBorder(FIELD_BORDER);
        f.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                f.repaint();
            }

            @Override
            public void focusLost(FocusEvent e) {
                f.repaint();
            }
        });
    }

    /** Batang progres tipis dengan pengisian warna aksen. */
    public static class ProgressTrack extends JPanel {
        private double fraction;

        public ProgressTrack(int height) {
            setPreferredSize(new Dimension(0, height));
            setMaximumSize(new Dimension(Integer.MAX_VALUE, height));
            setOpaque(false);
        }

        public void setFraction(double f) {
            this.fraction = Math.max(0, Math.min(1, f));
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            int w = getWidth();
            int h = getHeight() - 1;
            g2.setColor(new Color(226, 231, 244));
            g2.fillRoundRect(0, 0, w, h, h, h);
            if (fraction > 0) {
                g2.setColor(ACCENT);
                int fill = (int) Math.round(w * fraction);
                g2.fillRoundRect(0, 0, fill, h, h, h);
            }
            g2.dispose();
        }
    }

    public static class RadioIcon implements Icon {
        private final boolean selected;

        public RadioIcon(boolean selected) {
            this.selected = selected;
        }

        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            int s = 20;
            if (selected) {
                g2.setColor(NAVY_LIGHT);
                g2.fillOval(x, y, s, s);
                g2.setColor(Color.WHITE);
                g2.fillOval(x + 6, y + 6, s - 12, s - 12);
            } else {
                g2.setColor(BORDER);
                g2.fillOval(x, y, s, s);
                g2.setColor(FIELD_BG);
                g2.fillOval(x + 2, y + 2, s - 4, s - 4);
            }
            g2.dispose();
        }

        @Override
        public int getIconWidth() {
            return 20;
        }

        @Override
        public int getIconHeight() {
            return 20;
        }
    }
}