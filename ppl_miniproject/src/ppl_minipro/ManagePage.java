package ppl_minipro;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

/**
 * Manage Employees page.
 * Combines Search-by-ID, Delete, and Update in one panel.
 *
 * Workflow:
 *   1. Enter ID → Search   → output area shows employee details
 *   2. Enter ID → Delete   → removes employee after confirmation
 *   3. Enter ID → then choose field & enter new value → Update
 */
public class ManagePage extends JPanel {

    private final MainFrame frame;

    private JTextField searchIdField;
    private JTextArea  outputArea;

    // Update section widgets
    private JTextField   updateIdField;
    private JComboBox<String> fieldCombo;
    private JTextField   newValueField;
    private JLabel       teamSizeNote;

    public ManagePage(MainFrame frame) {
        this.frame = frame;
        setBackground(UITheme.BG_DARK);
        setLayout(new BorderLayout());
        build();
    }

    private void build() {
        add(UITheme.topBar("Manage Employees", () -> frame.go("home")), BorderLayout.NORTH);

        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
            buildLeftPanel(), buildOutputPanel());
        split.setDividerLocation(420);
        split.setDividerSize(4);
        split.setBackground(UITheme.BG_DARK);
        split.setBorder(BorderFactory.createEmptyBorder(14, 16, 16, 16));
        add(split, BorderLayout.CENTER);
    }

    // ─── Left panel (search / delete / update forms) ─────────────────────────
    private JPanel buildLeftPanel() {
        JPanel left = new JPanel();
        left.setBackground(UITheme.BG_DARK);
        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
        left.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 6));

        left.add(buildSearchDeleteCard());
        left.add(Box.createVerticalStrut(14));
        left.add(buildUpdateCard());

        return left;
    }

    // ─── Search & Delete card ─────────────────────────────────────────────────
    private JPanel buildSearchDeleteCard() {
        JPanel card = UITheme.titledCard("Search / Delete Employee");
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));

        card.add(UITheme.label("Enter Employee ID:"));
        card.add(Box.createVerticalStrut(6));
        searchIdField = UITheme.textField();
        searchIdField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        searchIdField.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.add(searchIdField);

        card.add(Box.createVerticalStrut(14));

        JPanel btns = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        btns.setBackground(UITheme.BG_CARD);
        btns.setAlignmentX(Component.LEFT_ALIGNMENT);

        JButton searchBtn = UITheme.infoBtn("Search");
        JButton deleteBtn = UITheme.dangerBtn("Delete");
        searchBtn.addActionListener(e -> doSearch());
        deleteBtn.addActionListener(e -> doDelete());
        btns.add(searchBtn);
        btns.add(deleteBtn);
        card.add(btns);

        return card;
    }

    // ─── Update card ─────────────────────────────────────────────────────────
    private JPanel buildUpdateCard() {
        JPanel card = UITheme.titledCard("Update Employee");
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));

        card.add(UITheme.label("Employee ID to update:"));
        card.add(Box.createVerticalStrut(6));
        updateIdField = UITheme.textField();
        updateIdField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        updateIdField.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.add(updateIdField);
        card.add(Box.createVerticalStrut(12));

        card.add(UITheme.label("Field to update:"));
        card.add(Box.createVerticalStrut(6));
        fieldCombo = UITheme.combo("Name", "Salary", "Domain", "Team Size (Manager only)");
        fieldCombo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        fieldCombo.setAlignmentX(Component.LEFT_ALIGNMENT);
        fieldCombo.addActionListener(e -> {
            boolean tsSelected = fieldCombo.getSelectedIndex() == 3;
            teamSizeNote.setVisible(tsSelected);
        });
        card.add(fieldCombo);
        card.add(Box.createVerticalStrut(12));

        teamSizeNote = UITheme.label("  \u26a0 Only applies to Manager employees");
        teamSizeNote.setForeground(UITheme.WARNING);
        teamSizeNote.setVisible(false);
        teamSizeNote.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.add(teamSizeNote);
        card.add(Box.createVerticalStrut(4));

        card.add(UITheme.label("New value:"));
        card.add(Box.createVerticalStrut(6));
        newValueField = UITheme.textField();
        newValueField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        newValueField.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.add(newValueField);
        card.add(Box.createVerticalStrut(14));

        JButton updateBtn = UITheme.warningBtn("Update Employee");
        updateBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
        updateBtn.addActionListener(e -> doUpdate());
        card.add(updateBtn);

        return card;
    }

    // ─── Output panel ─────────────────────────────────────────────────────────
    private JPanel buildOutputPanel() {
        JPanel right = UITheme.titledCard("Result");
        right.setLayout(new BorderLayout());

        outputArea = UITheme.textArea();
        outputArea.setText("Enter an Employee ID above and click Search, Delete, or Update.");
        JScrollPane sp = UITheme.scroll(outputArea);
        right.add(sp, BorderLayout.CENTER);

        JButton clearBtn = UITheme.secondaryBtn("Clear");
        clearBtn.addActionListener(e -> outputArea.setText(""));
        JPanel bot = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bot.setBackground(UITheme.BG_CARD);
        bot.add(clearBtn);
        right.add(bot, BorderLayout.SOUTH);

        return right;
    }

    // ─── Actions ──────────────────────────────────────────────────────────────
    private void doSearch() {
        int id = parseId(searchIdField);
        if (id == -1) return;

        Employee e = EmployeeStore.getById(id);
        if (e == null) {
            outputArea.setText("Employee with ID " + id + " not found.");
        } else {
            outputArea.setText("── Search Result ──\n\n" + EmployeeStore.format(e));
        }
    }

    private void doDelete() {
        int id = parseId(searchIdField);
        if (id == -1) return;

        Employee e = EmployeeStore.getById(id);
        if (e == null) {
            outputArea.setText("Employee with ID " + id + " not found.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
            "Delete employee \"" + e.name + "\" (ID " + id + ")?\nThis cannot be undone.",
            "Confirm Deletion", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            EmployeeStore.deleteById(id);
            outputArea.setText("Employee \"" + e.name + "\" (ID " + id + ") deleted successfully.");
        }
    }

    private void doUpdate() {
        int id = parseId(updateIdField);
        if (id == -1) return;

        Employee e = EmployeeStore.getById(id);
        if (e == null) {
            outputArea.setText("Employee with ID " + id + " not found.");
            return;
        }

        int choice = fieldCombo.getSelectedIndex(); // 0=Name,1=Salary,2=Domain,3=TeamSize
        String val = newValueField.getText().trim();

        if (val.isEmpty()) {
            UITheme.err(this, "New value cannot be empty.");
            return;
        }

        switch (choice) {
            case 0: // Name
                if (!val.matches("[a-zA-Z ]+")) {
                    UITheme.err(this, "Invalid name! Only alphabets and spaces allowed.");
                    return;
                }
                e.setName(val);
                break;

            case 1: // Salary
                double salary;
                try { salary = Double.parseDouble(val); }
                catch (NumberFormatException ex) {
                    UITheme.err(this, "Invalid salary! Enter a numeric value.");
                    return;
                }
                if (salary <= 0) {
                    UITheme.err(this, "Salary must be positive.");
                    return;
                }
                e.setSalary(salary);
                break;

            case 2: // Domain
                if (!val.matches("[a-zA-Z /]+")) {
                    UITheme.err(this, "Invalid domain! Only alphabets allowed.");
                    return;
                }
                e.setDomain(val);
                break;

            case 3: // Team Size (Manager only)
                if (!(e instanceof Manager)) {
                    UITheme.err(this, "Team Size can only be updated for Manager employees.");
                    return;
                }
                int ts;
                try { ts = Integer.parseInt(val); }
                catch (NumberFormatException ex) {
                    UITheme.err(this, "Invalid team size! Enter an integer.");
                    return;
                }
                if (ts <= 0) {
                    UITheme.err(this, "Team size must be positive.");
                    return;
                }
                ((Manager) e).setTeamSize(ts);
                break;
        }

        newValueField.setText("");
        outputArea.setText("── Updated Successfully ──\n\n" + EmployeeStore.format(e));
    }

    // ─── Helpers ─────────────────────────────────────────────────────────────
    private int parseId(JTextField field) {
        try {
            return Integer.parseInt(field.getText().trim());
        } catch (NumberFormatException ex) {
            UITheme.err(this, "Invalid ID! Please enter an integer.");
            return -1;
        }
    }

    public void refresh() {
        outputArea.setText("Enter an Employee ID above and click Search, Delete, or Update.");
        searchIdField.setText("");
        updateIdField.setText("");
        newValueField.setText("");
    }
}
