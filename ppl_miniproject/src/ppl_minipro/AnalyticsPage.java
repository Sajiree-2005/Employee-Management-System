package ppl_minipro;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.util.List;

// JFreeChart imports
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 * Analytics page.
 * Left panel: action buttons.
 * Right panel: scrollable text output.
 * Two chart buttons open styled JFreeChart windows.
 */
public class AnalyticsPage extends JPanel {

    private final MainFrame frame;
    private JTextArea outputArea;

    // Input fields shown in a dialog
    private JTextField nameSearchField   = UITheme.textField();
    private JTextField minSalaryField    = UITheme.textField();
    private JTextField maxSalaryField    = UITheme.textField();

    public AnalyticsPage(MainFrame frame) {
        this.frame = frame;
        setBackground(UITheme.BG_DARK);
        setLayout(new BorderLayout());
        build();
    }

    private void build() {
        add(UITheme.topBar("Analytics & Reports", () -> frame.go("home")), BorderLayout.NORTH);

        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
            buildButtonPanel(), buildOutputPanel());
        split.setDividerLocation(260);
        split.setDividerSize(4);
        split.setBackground(UITheme.BG_DARK);
        split.setBorder(BorderFactory.createEmptyBorder(14, 16, 16, 16));
        add(split, BorderLayout.CENTER);
    }

    // ─── Left button panel ────────────────────────────────────────────────────
    private JPanel buildButtonPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(UITheme.BG_DARK);
        panel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 6));

        // Charts section
        JPanel chartCard = UITheme.titledCard("Charts");
        chartCard.setLayout(new BoxLayout(chartCard, BoxLayout.Y_AXIS));
        addActionBtn(chartCard, "Employee Distribution", UITheme.ACCENT,
            this::showEmployeeDistributionChart);
        chartCard.add(Box.createVerticalStrut(8));
        addActionBtn(chartCard, "Average Salary Chart", UITheme.INFO,
            this::showAverageSalaryChart);
        panel.add(chartCard);
        panel.add(Box.createVerticalStrut(12));

        // Text analytics section
        JPanel statsCard = UITheme.titledCard("Text Analytics");
        statsCard.setLayout(new BoxLayout(statsCard, BoxLayout.Y_AXIS));

        addActionBtn(statsCard, "Sort by Salary",        UITheme.SUCCESS,  this::sortBySalary);
        statsCard.add(Box.createVerticalStrut(8));
        addActionBtn(statsCard, "Search by Name",        UITheme.INFO,     this::searchByName);
        statsCard.add(Box.createVerticalStrut(8));
        addActionBtn(statsCard, "Filter by Salary Range",UITheme.WARNING,  this::filterBySalary);
        statsCard.add(Box.createVerticalStrut(8));
        addActionBtn(statsCard, "Count Employees",       UITheme.ACCENT,   this::countEmployees);
        statsCard.add(Box.createVerticalStrut(8));
        addActionBtn(statsCard, "Average Salary",        UITheme.SUCCESS,  this::averageSalary);
        panel.add(statsCard);

        return panel;
    }

    private void addActionBtn(JPanel parent, String text, Color color, Runnable action) {
        JButton btn = UITheme.btn(text, color);
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        btn.setAlignmentX(Component.LEFT_ALIGNMENT);
        btn.addActionListener(e -> action.run());
        parent.add(btn);
    }

    // ─── Output panel ─────────────────────────────────────────────────────────
    private JPanel buildOutputPanel() {
        JPanel right = UITheme.titledCard("Output");
        right.setLayout(new BorderLayout());

        outputArea = UITheme.textArea();
        outputArea.setText("Select an action from the left panel to see results here.");
        right.add(UITheme.scroll(outputArea), BorderLayout.CENTER);

        JButton clearBtn = UITheme.secondaryBtn("Clear");
        clearBtn.addActionListener(e -> outputArea.setText(""));
        JPanel bot = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bot.setBackground(UITheme.BG_CARD);
        bot.add(clearBtn);
        right.add(bot, BorderLayout.SOUTH);

        return right;
    }

    // ─── 1. PIE CHART – Employee Distribution ────────────────────────────────
    public void showEmployeeDistributionChart() {
        DefaultPieDataset<String> dataset = new DefaultPieDataset<>();
        dataset.setValue("Managers",  EmployeeStore.count(1));
        dataset.setValue("Engineers", EmployeeStore.count(2));
        dataset.setValue("Interns",   EmployeeStore.count(3));

        JFreeChart chart = ChartFactory.createPieChart(
            "Employee Distribution", dataset, true, true, false);

        // ── Style ──
        chart.setBackgroundPaint(new Color(18, 24, 40));
        chart.getTitle().setPaint(new Color(241, 245, 249));
        chart.getTitle().setFont(new Font("Segoe UI", Font.BOLD, 16));
        chart.getLegend().setBackgroundPaint(new Color(28, 36, 56));
        chart.getLegend().setItemPaint(new Color(241, 245, 249));

        PiePlot<?> plot = (PiePlot<?>) chart.getPlot();
        plot.setBackgroundPaint(new Color(18, 24, 40));
        plot.setOutlinePaint(new Color(45, 55, 82));
        plot.setSectionPaint("Managers",  new Color(245, 158, 11));
        plot.setSectionPaint("Engineers", new Color(59, 130, 246));
        plot.setSectionPaint("Interns",   new Color(16, 185, 129));
        plot.setLabelBackgroundPaint(new Color(28, 36, 56));
        plot.setLabelPaint(new Color(241, 245, 249));
        plot.setLabelFont(new Font("Segoe UI", Font.PLAIN, 12));
        plot.setShadowPaint(null);
        plot.setSimpleLabels(false);

        openChartWindow("Employee Distribution — TechNova", chart, 600, 460);
        outputArea.setText("[ Pie Chart Opened ]\n\nEmployee Distribution:\n"
            + "  Managers  : " + EmployeeStore.count(1) + "\n"
            + "  Engineers : " + EmployeeStore.count(2) + "\n"
            + "  Interns   : " + EmployeeStore.count(3) + "\n"
            + "  Total     : " + EmployeeStore.count(0));
    }

    // ─── 2. BAR CHART – Average Salary Comparison ────────────────────────────
    public void showAverageSalaryChart() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(EmployeeStore.avgSalary(1), "Avg Salary (\u20b9)", "Managers");
        dataset.addValue(EmployeeStore.avgSalary(2), "Avg Salary (\u20b9)", "Engineers");
        dataset.addValue(EmployeeStore.avgSalary(3), "Avg Salary (\u20b9)", "Interns");

        JFreeChart chart = ChartFactory.createBarChart(
            "Average Salary by Role", "Role", "Salary (\u20b9)",
            dataset, PlotOrientation.VERTICAL, true, true, false);

        // ── Style ──
        chart.setBackgroundPaint(new Color(18, 24, 40));
        chart.getTitle().setPaint(new Color(241, 245, 249));
        chart.getTitle().setFont(new Font("Segoe UI", Font.BOLD, 16));
        chart.getLegend().setBackgroundPaint(new Color(28, 36, 56));
        chart.getLegend().setItemPaint(new Color(241, 245, 249));

        CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(new Color(18, 24, 40));
        plot.setDomainGridlinePaint(new Color(45, 55, 82));
        plot.setRangeGridlinePaint(new Color(45, 55, 82));
        plot.setOutlinePaint(null);

        CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setTickLabelPaint(new Color(241, 245, 249));
        domainAxis.setLabelPaint(new Color(148, 163, 184));
        domainAxis.setTickLabelFont(new Font("Segoe UI", Font.PLAIN, 12));

        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setTickLabelPaint(new Color(241, 245, 249));
        rangeAxis.setLabelPaint(new Color(148, 163, 184));
        rangeAxis.setTickLabelFont(new Font("Segoe UI", Font.PLAIN, 12));

        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, new Color(124, 58, 237));
        renderer.setMaximumBarWidth(0.25);
        renderer.setItemMargin(0.08);
        // Use setBarPainter if available (JFreeChart ≥ 1.0.11)
        try {
            renderer.getClass().getMethod("setBarPainter",
                Class.forName("org.jfree.chart.renderer.category.BarPainter"))
                .invoke(renderer,
                    Class.forName("org.jfree.chart.renderer.category.StandardBarPainter")
                        .getDeclaredConstructor().newInstance());
        } catch (Exception ignored) {}

        openChartWindow("Average Salary Comparison — TechNova", chart, 600, 460);
        outputArea.setText(String.format(
            "[ Bar Chart Opened ]\n\nAverage Salary by Role:\n"
          + "  Managers  : \u20b9%.0f\n"
          + "  Engineers : \u20b9%.0f\n"
          + "  Interns   : \u20b9%.0f\n",
            EmployeeStore.avgSalary(1),
            EmployeeStore.avgSalary(2),
            EmployeeStore.avgSalary(3)));
    }

    /** Opens a chart in a styled JFrame. */
    private void openChartWindow(String title, JFreeChart chart, int w, int h) {
        JFrame win = new JFrame(title);
        win.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        ChartPanel cp = new ChartPanel(chart);
        cp.setPreferredSize(new Dimension(w, h));
        cp.setBackground(new Color(18, 24, 40));

        win.getContentPane().add(cp);
        win.pack();
        win.setLocationRelativeTo(frame);
        win.setVisible(true);
    }

    // ─── 3. Sort by Salary ────────────────────────────────────────────────────
    private void sortBySalary() {
        EmployeeStore.sortBySalary();
        StringBuilder sb = new StringBuilder("── Employees Sorted by Salary (Ascending) ──\n\n");
        int i = 1;
        for (Employee e : EmployeeStore.list) {
            sb.append(i++).append(". ")
              .append(e.name).append("  |  ")
              .append(EmployeeStore.getRole(e)).append("  |  ")
              .append("\u20b9").append(String.format("%.0f", e.getSalary())).append("\n");
        }
        outputArea.setText(sb.toString());
    }

    // ─── 4. Search by Name ────────────────────────────────────────────────────
    private void searchByName() {
        String name = UITheme.ask(this, "Enter employee name to search:");
        if (name == null || name.trim().isEmpty()) return;

        List<Employee> found = EmployeeStore.searchByName(name.trim());
        if (found.isEmpty()) {
            outputArea.setText("No employee found with the name \"" + name + "\".");
            return;
        }
        StringBuilder sb = new StringBuilder("── Search Results for \"" + name + "\" ──\n\n");
        for (Employee e : found) {
            sb.append(EmployeeStore.format(e)).append("\n──────────────────────\n\n");
        }
        outputArea.setText(sb.toString());
    }

    // ─── 5. Filter by Salary Range ────────────────────────────────────────────
    private void filterBySalary() {
        // Build input dialog
        JPanel p = new JPanel(new GridLayout(4, 1, 6, 6));
        p.setBackground(UITheme.BG_CARD);
        p.add(UITheme.label("Minimum Salary (\u20b9):"));
        JTextField minF = UITheme.textField();
        p.add(minF);
        p.add(UITheme.label("Maximum Salary (\u20b9):"));
        JTextField maxF = UITheme.textField();
        p.add(maxF);

        int res = JOptionPane.showConfirmDialog(this, p, "Filter by Salary Range",
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (res != JOptionPane.OK_OPTION) return;

        double min, max;
        try {
            min = Double.parseDouble(minF.getText().trim());
            max = Double.parseDouble(maxF.getText().trim());
        } catch (NumberFormatException ex) {
            UITheme.err(this, "Invalid salary values. Please enter numbers.");
            return;
        }
        if (min >= max) {
            UITheme.err(this, "Minimum must be less than maximum salary.");
            return;
        }

        List<Employee> found = EmployeeStore.filterBySalary(min, max);
        if (found.isEmpty()) {
            outputArea.setText("No employees found in the salary range \u20b9"
                + String.format("%.0f", min) + " – \u20b9" + String.format("%.0f", max) + ".");
            return;
        }
        StringBuilder sb = new StringBuilder("── Employees in Salary Range \u20b9"
            + String.format("%.0f", min) + " – \u20b9"
            + String.format("%.0f", max) + " ──\n\n");
        for (Employee e : found) {
            sb.append(EmployeeStore.format(e)).append("\n──────────────────────\n\n");
        }
        outputArea.setText(sb.toString());
    }

    // ─── 6. Count Employees ───────────────────────────────────────────────────
    private void countEmployees() {
        outputArea.setText(
            "── Employee Count ──\n\n"
          + "  All Employees : " + EmployeeStore.count(0) + "\n"
          + "  Managers      : " + EmployeeStore.count(1) + "\n"
          + "  Engineers     : " + EmployeeStore.count(2) + "\n"
          + "  Interns       : " + EmployeeStore.count(3) + "\n");
    }

    // ─── 7. Average Salary ────────────────────────────────────────────────────
    private void averageSalary() {
        outputArea.setText(String.format(
            "── Average Salary ──\n\n"
          + "  All Employees : \u20b9%.0f\n"
          + "  Managers      : \u20b9%.0f\n"
          + "  Engineers     : \u20b9%.0f\n"
          + "  Interns       : \u20b9%.0f\n",
            EmployeeStore.avgSalary(0),
            EmployeeStore.avgSalary(1),
            EmployeeStore.avgSalary(2),
            EmployeeStore.avgSalary(3)));
    }

    public void refresh() {
        outputArea.setText("Select an action from the left panel to see results here.");
    }
}
