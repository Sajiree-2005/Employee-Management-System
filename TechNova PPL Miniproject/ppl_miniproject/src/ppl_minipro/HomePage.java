package ppl_minipro;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Home / About page for TechNova EMS.
 * Shows company branding, about info, contact details, live stats,
 * and navigation cards for all four main sections.
 */
public class HomePage extends JPanel {

    private final MainFrame frame;
    private JLabel statAll, statMgr, statEng, statIntern;

    public HomePage(MainFrame frame) {
        this.frame = frame;
        setBackground(UITheme.BG_DARK);
        setLayout(new BorderLayout());
        build();
    }

    private void build() {
        add(buildHeader(),  BorderLayout.NORTH);
        add(buildCenter(), BorderLayout.CENTER);
        add(buildNav(),    BorderLayout.SOUTH);
    }

    // ─── Header (gradient banner) ─────────────────────────────────────────────
    private JPanel buildHeader() {
        JPanel banner = UITheme.gradientPanel(UITheme.HEADER_FROM, UITheme.HEADER_TO);
        banner.setPreferredSize(new Dimension(0, 155));
        banner.setLayout(new GridBagLayout());

        GridBagConstraints gc = new GridBagConstraints();
        gc.gridx = 0; gc.gridy = 0;
        gc.insets = new Insets(0, 0, 6, 0);

        JLabel logo = new JLabel("TechNova Solutions", SwingConstants.CENTER);
        logo.setFont(new Font("Segoe UI", Font.BOLD, 30));
        logo.setForeground(Color.WHITE);
        banner.add(logo, gc);

        gc.gridy = 1;
        JLabel sub = new JLabel("Employee Management System", SwingConstants.CENTER);
        sub.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        sub.setForeground(UITheme.ACCENT_LIGHT);
        banner.add(sub, gc);

        return banner;
    }

    // ─── Center section (About + Contact + Live Stats) ────────────────────────
    private JPanel buildCenter() {
        JPanel center = new JPanel(new GridLayout(1, 3, 16, 0));
        center.setBackground(UITheme.BG_DARK);
        center.setBorder(BorderFactory.createEmptyBorder(20, 24, 10, 24));

        center.add(buildAboutCard());
        center.add(buildContactCard());
        center.add(buildStatsCard());

        return center;
    }

    private JPanel buildAboutCard() {
        JPanel c = UITheme.titledCard("About TechNova");
        c.setLayout(new BoxLayout(c, BoxLayout.Y_AXIS));

        addLine(c, "TechNova Solutions is a leading technology");
        addLine(c, "company specializing in enterprise software, AI-driven");
        addLine(c, "analytics, and cloud infrastructure services.");
        c.add(Box.createVerticalStrut(12));
        addLine(c, "Founded in 2011 \u2022 Headquartered in Pune, India");
        addLine(c, "Serving 500+ enterprise clients across 28 countries.");
        c.add(Box.createVerticalStrut(12));
        addLine(c, "\u00a9 2025 TechNova Solutions. All rights reserved.");
        return c;
    }

    private JPanel buildContactCard() {
        JPanel c = UITheme.titledCard("Contact Us");
        c.setLayout(new BoxLayout(c, BoxLayout.Y_AXIS));

        addLine(c, "Email:  hr@technova.in");
        c.add(Box.createVerticalStrut(8));
        addLine(c, "Phone Number: +91-20-4010-5555");
        c.add(Box.createVerticalStrut(8));
        addLine(c, "Address  4th Floor, Tech Park, Hinjewadi Phase-2,");
        addLine(c, "         Pune, Maharashtra — 411057");
        c.add(Box.createVerticalStrut(8));
        addLine(c, "Website:  www.technova.in");
        return c;
    }

    private JPanel buildStatsCard() {
        JPanel c = UITheme.titledCard("Live Workforce Stats");
        c.setLayout(new GridLayout(4, 1, 0, 10));

        statAll    = statRow(c, "Total Employees", UITheme.TEXT);
        statMgr    = statRow(c, "Managers",        UITheme.WARNING);
        statEng    = statRow(c, "Engineers",       UITheme.INFO);
        statIntern = statRow(c, "Interns",         UITheme.SUCCESS);

        refreshStats();
        return c;
    }

    /** Adds a stat row and returns the value label so it can be refreshed. */
    private JLabel statRow(JPanel parent, String label, Color col) {
        JPanel row = new JPanel(new BorderLayout());
        row.setBackground(UITheme.BG_INPUT);
        row.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(UITheme.BORDER, 1, true),
            BorderFactory.createEmptyBorder(6, 14, 6, 14)
        ));

        JLabel lbl = UITheme.label(label);
        JLabel val = new JLabel("0", SwingConstants.RIGHT);
        val.setFont(new Font("Segoe UI", Font.BOLD, 20));
        val.setForeground(col);

        row.add(lbl, BorderLayout.WEST);
        row.add(val, BorderLayout.EAST);
        parent.add(row);
        return val;
    }

    private void refreshStats() {
        statAll   .setText(String.valueOf(EmployeeStore.count(0)));
        statMgr   .setText(String.valueOf(EmployeeStore.count(1)));
        statEng   .setText(String.valueOf(EmployeeStore.count(2)));
        statIntern.setText(String.valueOf(EmployeeStore.count(3)));
    }

    // ─── Navigation cards ────────────────────────────────────────────────────
    private JPanel buildNav() {
        JPanel outer = new JPanel(new BorderLayout());
        outer.setBackground(UITheme.BG_DARK);
        outer.setBorder(BorderFactory.createEmptyBorder(10, 24, 24, 24));

        JLabel nav = new JLabel("Navigate to:");
        nav.setFont(UITheme.F_SUB);
        nav.setForeground(UITheme.TEXT_MUTED);
        nav.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        outer.add(nav, BorderLayout.NORTH);

        JPanel grid = new JPanel(new GridLayout(1, 4, 14, 0));
        grid.setBackground(UITheme.BG_DARK);

        grid.add(navCard("\u2795", "Add Employee",
            "Register new Manager,\nEngineer or Intern", UITheme.SUCCESS, "add"));
        grid.add(navCard("\uD83D\uDCCB", "View Employees",
            "Browse & filter the\nfull employee roster", UITheme.INFO, "display"));
        grid.add(navCard("\u270F\uFE0F", "Manage / Search",
            "Search, update or\ndelete an employee", UITheme.WARNING, "manage"));
        grid.add(navCard("\uD83D\uDCCA", "Analytics",
            "Charts, stats, salary\nsummaries & more", UITheme.ACCENT, "analytics"));

        outer.add(grid, BorderLayout.CENTER);
        return outer;
    }

    private JPanel navCard(String icon, String title, String desc, Color accent, String page) {
        JPanel card = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            }
        };
        card.setBackground(UITheme.BG_CARD);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(UITheme.BORDER, 1, true),
            BorderFactory.createEmptyBorder(18, 18, 18, 18)
        ));
        card.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Top: icon + accent bar
        JPanel top = new JPanel(new BorderLayout());
        top.setBackground(UITheme.BG_CARD);

        JLabel ic = new JLabel(icon);
        ic.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 28));
        ic.setForeground(accent);

        JSeparator sep = new JSeparator(SwingConstants.HORIZONTAL);
        sep.setForeground(accent);
        sep.setBorder(BorderFactory.createEmptyBorder(8, 0, 0, 0));

        top.add(ic,  BorderLayout.NORTH);
        top.add(sep, BorderLayout.SOUTH);
        card.add(top, BorderLayout.NORTH);

        // Center: title + desc
        JPanel mid = new JPanel();
        mid.setBackground(UITheme.BG_CARD);
        mid.setLayout(new BoxLayout(mid, BoxLayout.Y_AXIS));
        mid.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        JLabel t = new JLabel(title);
        t.setFont(new Font("Segoe UI", Font.BOLD, 15));
        t.setForeground(UITheme.TEXT);
        mid.add(t);
        mid.add(Box.createVerticalStrut(6));
        for (String line : desc.split("\n")) {
            JLabel dl = new JLabel(line);
            dl.setFont(UITheme.F_SMALL);
            dl.setForeground(UITheme.TEXT_MUTED);
            mid.add(dl);
        }
        card.add(mid, BorderLayout.CENTER);

        // Bottom: button
        JButton go = UITheme.btn("Open  \u2192", accent);
        go.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.add(go, BorderLayout.SOUTH);

        // Hover highlight on whole card
        MouseAdapter hover = new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) {
                card.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(accent, 1, true),
                    BorderFactory.createEmptyBorder(18, 18, 18, 18)));
            }
            @Override public void mouseExited(MouseEvent e) {
                card.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(UITheme.BORDER, 1, true),
                    BorderFactory.createEmptyBorder(18, 18, 18, 18)));
            }
        };
        card.addMouseListener(hover);
        go.addActionListener(ev -> { refreshStats(); frame.go(page); });
        card.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) { frame.go(page); }
        });

        return card;
    }

    // ─── Utility ─────────────────────────────────────────────────────────────
    private void addLine(JPanel p, String text) {
        JLabel l = new JLabel(text);
        l.setFont(UITheme.F_BODY);
        l.setForeground(UITheme.TEXT_MUTED);
        l.setAlignmentX(Component.LEFT_ALIGNMENT);
        p.add(l);
        p.add(Box.createVerticalStrut(3));
    }
}
