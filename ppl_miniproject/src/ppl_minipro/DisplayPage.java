package ppl_minipro;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * Employee Directory page.
 * Displays all employees in a styled, non-editable JTable.
 * Filter combo allows viewing All / Managers / Engineers / Interns.
 */
public class DisplayPage extends JPanel {

    private final MainFrame frame;

    private JComboBox<String> filterCombo;
    private DefaultTableModel tableModel;
    private JTable table;
    private JLabel countLabel;

    private static final String[] COLUMNS =
        {"#", "Role", "Name", "Employee ID", "Salary (\u20b9)", "Domain", "Extra Info"};

    public DisplayPage(MainFrame frame) {
        this.frame = frame;
        setBackground(UITheme.BG_DARK);
        setLayout(new BorderLayout());
        build();
    }

    private void build() {
        add(UITheme.topBar("Employee Directory", () -> frame.go("home")), BorderLayout.NORTH);
        add(buildToolbar(),  BorderLayout.CENTER);
    }

    // ─── Toolbar + table ─────────────────────────────────────────────────────
    private JPanel buildToolbar() {
        JPanel outer = new JPanel(new BorderLayout());
        outer.setBackground(UITheme.BG_DARK);
        outer.setBorder(BorderFactory.createEmptyBorder(16, 18, 18, 18));

        // ── Filter row ──
        JPanel filterBar = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 0));
        filterBar.setBackground(UITheme.BG_DARK);

        filterBar.add(UITheme.label("Show:"));
        filterCombo = UITheme.combo("All Employees", "Managers", "Engineers", "Interns");
        filterCombo.setPreferredSize(new Dimension(175, 36));
        filterCombo.addActionListener(e -> refresh());
        filterBar.add(filterCombo);

        JButton refreshBtn = UITheme.infoBtn("Refresh");
        refreshBtn.addActionListener(e -> refresh());
        filterBar.add(refreshBtn);

        countLabel = UITheme.label("  Showing — employees");
        countLabel.setForeground(UITheme.TEXT_MUTED);
        filterBar.add(countLabel);

        outer.add(filterBar, BorderLayout.NORTH);

        // ── Table ──
        tableModel = new DefaultTableModel(COLUMNS, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        table = new JTable(tableModel);
        UITheme.styleTable(table);

        // Column widths
        int[] widths = {38, 82, 165, 95, 105, 120, 130};
        for (int i = 0; i < widths.length; i++)
            table.getColumnModel().getColumn(i).setPreferredWidth(widths[i]);

        JScrollPane sp = UITheme.scroll(table);
        sp.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createEmptyBorder(12, 0, 0, 0),
            BorderFactory.createLineBorder(UITheme.BORDER)
        ));
        outer.add(sp, BorderLayout.CENTER);

        return outer;
    }

    // ─── Data refresh ─────────────────────────────────────────────────────────
    public void refresh() {
        int type = filterCombo.getSelectedIndex(); // 0=all,1=mgr,2=eng,3=intern
        tableModel.setRowCount(0);

        Object[][] data = EmployeeStore.buildTableData(type);
        for (Object[] row : data) tableModel.addRow(row);

        countLabel.setText("  Showing " + data.length + " employee"
            + (data.length != 1 ? "s" : ""));
    }
}
