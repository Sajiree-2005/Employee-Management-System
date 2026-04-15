package ppl_minipro;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

/**
 * Add Employee page.
 * Handles all three types (Manager / Engineer / Intern) with
 * conditional fields and the same validation rules as the console version.
 */
public class AddEmployeePage extends JPanel {

    private final MainFrame frame;

    // Form fields
    private JComboBox<String> typeCombo;
    private JTextField nameField, idField, salaryField, domainField;
    private JTextField teamSizeField, durationField;
    private JPanel    teamSizeRow, durationRow;

    public AddEmployeePage(MainFrame frame) {
        this.frame = frame;
        setBackground(UITheme.BG_DARK);
        setLayout(new BorderLayout());
        build();
    }

    private void build() {
        add(UITheme.topBar("Add New Employee", () -> frame.go("home")), BorderLayout.NORTH);
        add(buildForm(), BorderLayout.CENTER);
    }

    // ─── Form ─────────────────────────────────────────────────────────────────
    private JPanel buildForm() {
        // Outer wrapper — centres the form card
        JPanel outer = new JPanel(new GridBagLayout());
        outer.setBackground(UITheme.BG_DARK);

        JPanel card = UITheme.card(30, 40);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setPreferredSize(new Dimension(540, 580));

        // Title
        JLabel title = UITheme.title("Register New Employee");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setBorder(BorderFactory.createEmptyBorder(0, 0, 24, 0));
        card.add(title);

        // Employee type selector
        card.add(row("Employee Type", typeCombo = UITheme.combo(
            "Manager", "Engineer", "Intern")));

        // Core fields
        card.add(Box.createVerticalStrut(4));
        card.add(row("Full Name",    nameField   = UITheme.textField()));
        card.add(Box.createVerticalStrut(4));
        card.add(row("Employee ID",  idField     = UITheme.textField()));
        card.add(Box.createVerticalStrut(4));
        card.add(row("Salary (\u20b9)",  salaryField = UITheme.textField()));
        card.add(Box.createVerticalStrut(4));
        card.add(row("Domain",       domainField = UITheme.textField()));

        // Conditional: Team Size (Manager)
        teamSizeField = UITheme.textField();
        teamSizeRow = row("Team Size", teamSizeField);
        card.add(Box.createVerticalStrut(4));
        card.add(teamSizeRow);

        // Conditional: Duration (Intern)
        durationField = UITheme.textField();
        durationRow = row("Duration (months)", durationField);
        card.add(Box.createVerticalStrut(4));
        card.add(durationRow);

        // Show/hide conditional rows on type change
        updateConditionalFields();
        typeCombo.addActionListener(e -> updateConditionalFields());

        // Buttons
        card.add(Box.createVerticalStrut(22));
        JPanel btns = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 0));
        btns.setBackground(UITheme.BG_CARD);
        btns.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton submit = UITheme.successBtn("Submit Employee");
        JButton reset  = UITheme.secondaryBtn("Reset");
        submit.addActionListener(e -> submit());
        reset .addActionListener(e -> clearForm());
        btns.add(submit);
        btns.add(reset);
        card.add(btns);

        outer.add(card);
        return outer;
    }

    // ─── Conditional field visibility ────────────────────────────────────────
    private void updateConditionalFields() {
        int sel = typeCombo.getSelectedIndex(); // 0=Mgr, 1=Eng, 2=Intern
        teamSizeRow.setVisible(sel == 0);
        durationRow .setVisible(sel == 2);
        revalidate();
        repaint();
    }

    // ─── Row builder ─────────────────────────────────────────────────────────
    private JPanel row(String labelText, JComponent field) {
        JPanel p = new JPanel(new BorderLayout(12, 0));
        p.setBackground(UITheme.BG_CARD);
        p.setMaximumSize(new Dimension(Integer.MAX_VALUE, 46));
        p.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lbl = UITheme.label(labelText + ":");
        lbl.setPreferredSize(new Dimension(155, 30));
        p.add(lbl,   BorderLayout.WEST);
        p.add(field, BorderLayout.CENTER);
        return p;
    }

    // ─── Submit logic (mirrors original validation) ───────────────────────────
    private void submit() {
        // Name validation
        String name = nameField.getText().trim();
        if (name.isEmpty() || !name.matches("[a-zA-Z ]+")) {
            UITheme.err(this, "Invalid name! Only alphabets and spaces allowed.");
            return;
        }

        // ID validation
        int id;
        try {
            id = Integer.parseInt(idField.getText().trim());
        } catch (NumberFormatException ex) {
            UITheme.err(this, "Invalid ID! Please enter an integer.");
            return;
        }
        if (EmployeeStore.idExists(id)) {
            UITheme.err(this, "Employee ID " + id + " already exists! Use a unique ID.");
            return;
        }

        // Salary validation
        double salary;
        try {
            salary = Double.parseDouble(salaryField.getText().trim());
        } catch (NumberFormatException ex) {
            UITheme.err(this, "Invalid salary! Please enter a numeric value.");
            return;
        }
        if (salary <= 0) {
            UITheme.err(this, "Salary must be a positive value.");
            return;
        }

        // Domain validation
        String domain = domainField.getText().trim();
        if (domain.isEmpty() || !domain.matches("[a-zA-Z /]+")) {
            UITheme.err(this, "Invalid domain! Only alphabets and spaces allowed.");
            return;
        }

        int type = typeCombo.getSelectedIndex(); // 0=Mgr, 1=Eng, 2=Intern

        if (type == 0) {
            // Manager: team size required
            int teamSize;
            try {
                teamSize = Integer.parseInt(teamSizeField.getText().trim());
            } catch (NumberFormatException ex) {
                UITheme.err(this, "Invalid team size! Please enter an integer.");
                return;
            }
            if (teamSize <= 0) {
                UITheme.err(this, "Team size must be positive.");
                return;
            }
            EmployeeStore.list.add(new Manager(name, id, salary, domain, teamSize));

        } else if (type == 1) {
            EmployeeStore.list.add(new Engineer(name, id, salary, domain));

        } else {
            // Intern: duration required
            int duration;
            try {
                duration = Integer.parseInt(durationField.getText().trim());
            } catch (NumberFormatException ex) {
                UITheme.err(this, "Invalid duration! Please enter months as an integer.");
                return;
            }
            if (duration <= 0) {
                UITheme.err(this, "Duration must be positive.");
                return;
            }
            EmployeeStore.list.add(new Intern(name, id, salary, domain, duration));
        }

        UITheme.ok(this, "Employee \"" + name + "\" added successfully!");
        clearForm();
    }

    // ─── Reset ────────────────────────────────────────────────────────────────
    private void clearForm() {
        nameField    .setText("");
        idField      .setText("");
        salaryField  .setText("");
        domainField  .setText("");
        teamSizeField.setText("");
        durationField.setText("");
        typeCombo.setSelectedIndex(0);
        updateConditionalFields();
    }
}
