package ppl_minipro;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Centralized UI theme for the TechNova EMS.
 * Provides a consistent dark color palette, fonts, and factory helpers.
 */
public class UITheme {

    // ─── Color Palette (Dark Navy + Purple accent) ────────────────────────────
    public static final Color BG_DARK      = new Color(10, 14, 26);
    public static final Color BG_CARD      = new Color(18, 24, 40);
    public static final Color BG_INPUT     = new Color(28, 36, 56);
    public static final Color BG_ROW_ALT   = new Color(22, 30, 48);
    public static final Color ACCENT       = new Color(124, 58, 237);   // purple
    public static final Color ACCENT_LIGHT = new Color(167, 139, 250);
    public static final Color SUCCESS      = new Color(16, 185, 129);   // green
    public static final Color DANGER       = new Color(239, 68, 68);    // red
    public static final Color WARNING      = new Color(245, 158, 11);   // amber
    public static final Color INFO         = new Color(59, 130, 246);   // blue
    public static final Color TEXT         = new Color(241, 245, 249);  // near-white
    public static final Color TEXT_MUTED   = new Color(148, 163, 184); // slate
    public static final Color BORDER       = new Color(45, 55, 82);
    public static final Color HEADER_FROM  = new Color(10, 14, 26);
    public static final Color HEADER_TO    = new Color(88, 28, 135);

    // ─── Typography ──────────────────────────────────────────────────────────
    public static final Font F_TITLE    = new Font("Segoe UI", Font.BOLD,  26);
    public static final Font F_HEADING  = new Font("Segoe UI", Font.BOLD,  18);
    public static final Font F_SUB      = new Font("Segoe UI", Font.BOLD,  14);
    public static final Font F_BODY     = new Font("Segoe UI", Font.PLAIN, 13);
    public static final Font F_SMALL    = new Font("Segoe UI", Font.PLAIN, 11);
    public static final Font F_BTN      = new Font("Segoe UI", Font.BOLD,  13);
    public static final Font F_MONO     = new Font("Consolas",  Font.PLAIN, 13);
    public static final Font F_TABLE    = new Font("Segoe UI", Font.PLAIN, 12);
    public static final Font F_TH       = new Font("Segoe UI", Font.BOLD,  12);

    // ─── Button factory ──────────────────────────────────────────────────────
    public static JButton btn(String text, Color bg) {
        JButton b = new JButton(text);
        b.setFont(F_BTN);
        b.setBackground(bg);
        b.setForeground(Color.WHITE);
        b.setBorderPainted(false);
        b.setFocusPainted(false);
        b.setOpaque(true);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.setBorder(BorderFactory.createEmptyBorder(10, 22, 10, 22));
        hover(b, bg);
        return b;
    }

    /** Add a subtle brightness shift on hover. */
    private static void hover(JButton b, Color base) {
        Color bright = base.brighter();
        b.addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) { b.setBackground(bright); }
            @Override public void mouseExited(MouseEvent e)  { b.setBackground(base);   }
        });
    }

    public static JButton primaryBtn(String t)   { return btn(t, ACCENT);   }
    public static JButton successBtn(String t)   { return btn(t, SUCCESS);  }
    public static JButton dangerBtn(String t)    { return btn(t, DANGER);   }
    public static JButton infoBtn(String t)      { return btn(t, INFO);     }
    public static JButton warningBtn(String t)   { return btn(t, WARNING);  }
    public static JButton secondaryBtn(String t) { return btn(t, BG_INPUT); }

    // ─── Text field factory ───────────────────────────────────────────────────
    public static JTextField textField() {
        JTextField tf = new JTextField();
        tf.setFont(F_BODY);
        tf.setBackground(BG_INPUT);
        tf.setForeground(TEXT);
        tf.setCaretColor(ACCENT_LIGHT);
        tf.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER, 1, true),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        return tf;
    }

    // ─── Label factories ──────────────────────────────────────────────────────
    public static JLabel label(String t) {
        JLabel l = new JLabel(t);
        l.setFont(F_BODY);
        l.setForeground(TEXT_MUTED);
        return l;
    }

    public static JLabel heading(String t) {
        JLabel l = new JLabel(t);
        l.setFont(F_HEADING);
        l.setForeground(TEXT);
        return l;
    }

    public static JLabel title(String t) {
        JLabel l = new JLabel(t, SwingConstants.CENTER);
        l.setFont(F_TITLE);
        l.setForeground(TEXT);
        return l;
    }

    // ─── ComboBox factory ─────────────────────────────────────────────────────
    public static JComboBox<String> combo(String... items) {
        JComboBox<String> cb = new JComboBox<>(items);
        cb.setFont(F_BODY);
        cb.setBackground(BG_INPUT);
        cb.setForeground(TEXT);
        cb.setBorder(BorderFactory.createLineBorder(BORDER, 1, true));
        cb.setFocusable(false);
        cb.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value,
                    int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                setBackground(isSelected ? ACCENT : BG_INPUT);
                setForeground(TEXT);
                setBorder(BorderFactory.createEmptyBorder(6, 12, 6, 12));
                return this;
            }
        });
        return cb;
    }

    // ─── Text area factory ────────────────────────────────────────────────────
    public static JTextArea textArea() {
        JTextArea ta = new JTextArea();
        ta.setFont(F_MONO);
        ta.setBackground(BG_INPUT);
        ta.setForeground(TEXT);
        ta.setCaretColor(ACCENT_LIGHT);
        ta.setLineWrap(true);
        ta.setWrapStyleWord(true);
        ta.setBorder(BorderFactory.createEmptyBorder(10, 12, 10, 12));
        ta.setEditable(false);
        return ta;
    }

    // ─── Scroll pane factory ─────────────────────────────────────────────────
    public static JScrollPane scroll(Component c) {
        JScrollPane sp = new JScrollPane(c);
        sp.setBackground(BG_DARK);
        sp.setBorder(BorderFactory.createLineBorder(BORDER));
        sp.getViewport().setBackground(BG_INPUT);
        sp.getVerticalScrollBar().setBackground(BG_CARD);
        sp.getHorizontalScrollBar().setBackground(BG_CARD);
        return sp;
    }

    // ─── Card panel factory ───────────────────────────────────────────────────
    public static JPanel card(int padV, int padH) {
        JPanel p = new JPanel();
        p.setBackground(BG_CARD);
        p.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER, 1, true),
            BorderFactory.createEmptyBorder(padV, padH, padV, padH)
        ));
        return p;
    }

    // ─── Titled card factory ──────────────────────────────────────────────────
    public static JPanel titledCard(String title) {
        JPanel p = new JPanel();
        p.setBackground(BG_CARD);
        TitledBorder tb = BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(BORDER, 1, true), "  " + title + "  ",
            TitledBorder.LEFT, TitledBorder.TOP, F_SUB, ACCENT_LIGHT
        );
        p.setBorder(BorderFactory.createCompoundBorder(
            tb, BorderFactory.createEmptyBorder(10, 14, 14, 14)
        ));
        return p;
    }

    // ─── JTable styler ───────────────────────────────────────────────────────
    public static void styleTable(JTable table) {
        table.setFont(F_TABLE);
        table.setBackground(BG_INPUT);
        table.setForeground(TEXT);
        table.setGridColor(BORDER);
        table.setRowHeight(32);
        table.setShowHorizontalLines(true);
        table.setShowVerticalLines(false);
        table.setIntercellSpacing(new Dimension(0, 1));
        table.setSelectionBackground(ACCENT);
        table.setSelectionForeground(Color.WHITE);
        table.setFillsViewportHeight(true);

        JTableHeader header = table.getTableHeader();
        header.setFont(F_TH);
        header.setBackground(new Color(30, 20, 60));
        header.setForeground(ACCENT_LIGHT);
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, ACCENT));
        header.setReorderingAllowed(false);

        // Alternating row colors
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable tbl, Object val,
                    boolean sel, boolean focus, int row, int col) {
                super.getTableCellRendererComponent(tbl, val, sel, focus, row, col);
                setHorizontalAlignment(col == 0 || col == 3 ? CENTER : LEFT);
                setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
                if (sel) {
                    setBackground(ACCENT);
                    setForeground(Color.WHITE);
                } else {
                    setBackground(row % 2 == 0 ? BG_INPUT : BG_ROW_ALT);
                    setForeground(TEXT);
                    // color-code role column
                    if (col == 1) {
                        String v = val == null ? "" : val.toString();
                        setForeground(v.equals("Manager") ? WARNING
                            : v.equals("Engineer") ? INFO : SUCCESS);
                    }
                }
                return this;
            }
        });
    }

    // ─── Gradient panel ──────────────────────────────────────────────────────
    public static JPanel gradientPanel(Color from, Color to) {
        return new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_RENDERING,
                                    RenderingHints.VALUE_RENDER_QUALITY);
                g2.setPaint(new GradientPaint(0, 0, from, getWidth(), getHeight(), to));
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
    }

    // ─── Page skeleton ────────────────────────────────────────────────────────
    /** Creates a full-page dark JPanel with BorderLayout. */
    public static JPanel page() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(BG_DARK);
        return p;
    }

    /** Creates a horizontal top bar with a Back button and page title. */
    public static JPanel topBar(String pageTitle, Runnable onBack) {
        JPanel bar = new JPanel(new BorderLayout());
        bar.setBackground(BG_CARD);
        bar.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 1, 0, BORDER),
            BorderFactory.createEmptyBorder(10, 16, 10, 16)
        ));
        JButton back = secondaryBtn("\u2190  Back");
        back.addActionListener(e -> onBack.run());
        JLabel title = new JLabel(pageTitle);
        title.setFont(F_SUB);
        title.setForeground(TEXT_MUTED);
        bar.add(back, BorderLayout.WEST);
        bar.add(title, BorderLayout.CENTER);
        return bar;
    }

    // ─── JOptionPane styled dialogs ───────────────────────────────────────────
    public static void ok(Component parent, String msg) {
        JOptionPane.showMessageDialog(parent, msg, "Success",
            JOptionPane.INFORMATION_MESSAGE);
    }
    public static void err(Component parent, String msg) {
        JOptionPane.showMessageDialog(parent, msg, "Error",
            JOptionPane.ERROR_MESSAGE);
    }
    public static String ask(Component parent, String prompt) {
        return JOptionPane.showInputDialog(parent, prompt);
    }
}
