package ppl_minipro;

import javax.swing.*;
import java.awt.*;

/**
 * Application entry point.
 * Hosts all pages in a CardLayout so navigation is instant.
 */
public class MainFrame extends JFrame {

    private final CardLayout cardLayout = new CardLayout();
    private final JPanel     root       = new JPanel(cardLayout);

    // Keep references so we can call refresh() when switching to a page
    private final DisplayPage    displayPage;
    private final ManagePage     managePage;
    private final AnalyticsPage  analyticsPage;

    public MainFrame() {
        super("TechNova Solutions — Employee Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1180, 720);
        setMinimumSize(new Dimension(980, 620));
        setLocationRelativeTo(null);

        root.setBackground(UITheme.BG_DARK);

        // Build pages 
        displayPage   = new DisplayPage(this);
        managePage    = new ManagePage(this);
        analyticsPage = new AnalyticsPage(this);

        root.add(new HomePage(this),   "home");
        root.add(new AddEmployeePage(this), "add");
        root.add(displayPage,          "display");
        root.add(managePage,           "manage");
        root.add(analyticsPage,        "analytics");

        add(root);
        cardLayout.show(root, "home");
    }

    /** Navigate to the named page, refreshing data-driven pages. */
    public void go(String page) {
        switch (page) {
            case "display":   displayPage.refresh();   break;
            case "manage":    managePage.refresh();    break;
            case "analytics": analyticsPage.refresh(); break;
        }
        cardLayout.show(root, page);
    }

    // ─── Main ─────────────────────────────────────────────────────────────────
    public static void main(String[] args) {
        // Apply system look-and-feel as a base, then override with theme
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception ignored) {}

        // Global Swing defaults so our dark colors propagate
        UIManager.put("Panel.background",      UITheme.BG_DARK);
        UIManager.put("OptionPane.background",  UITheme.BG_CARD);
        UIManager.put("OptionPane.messageForeground", UITheme.TEXT);
        UIManager.put("Button.background",      UITheme.BG_INPUT);
        UIManager.put("Button.foreground",      UITheme.TEXT);
        UIManager.put("TextField.background",   UITheme.BG_INPUT);
        UIManager.put("TextField.foreground",   UITheme.TEXT);
        UIManager.put("TextField.caretForeground", UITheme.ACCENT_LIGHT);
        UIManager.put("ComboBox.background",    UITheme.BG_INPUT);
        UIManager.put("ComboBox.foreground",    UITheme.TEXT);
        UIManager.put("ScrollPane.background",  UITheme.BG_DARK);
        UIManager.put("Viewport.background",    UITheme.BG_INPUT);

        SwingUtilities.invokeLater(() -> new MainFrame().setVisible(true));
    }
}
