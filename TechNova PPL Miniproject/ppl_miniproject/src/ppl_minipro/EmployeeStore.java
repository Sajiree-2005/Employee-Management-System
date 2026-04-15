package ppl_minipro;

import java.util.ArrayList;
import java.util.List;

/**
 * Shared static data store and backend logic.
 * Preserves all original logic from EmployeeManagementSystem.
 */
public class EmployeeStore {

    public static ArrayList<Employee> list = new ArrayList<>();

    // ─── Preload 12 dummy employees ──────────────────────────────────────────
    static {
        list.add(new Manager("Arjun Sharma",    101, 125000, "Engineering",   10));
        list.add(new Manager("Priya Mehta",     102, 140000, "Product",       14));
        list.add(new Manager("Vikram Singh",    103, 108000, "Operations",     7));
        list.add(new Engineer("Rohit Verma",    201,  82000, "Backend"));
        list.add(new Engineer("Sneha Patel",    202,  76000, "Frontend"));
        list.add(new Engineer("Karan Joshi",    203,  89000, "DevOps"));
        list.add(new Engineer("Anjali Gupta",   204,  84000, "Data Science"));
        list.add(new Engineer("Dev Malhotra",   205,  78000, "Mobile"));
        list.add(new Intern ("Neha Kapoor",     301,  28000, "Frontend",  6));
        list.add(new Intern ("Rahul Nair",      302,  24000, "Backend",   3));
        list.add(new Intern ("Simran Kaur",     303,  26000, "UI/UX",     6));
        list.add(new Intern ("Aditya Rao",      304,  25000, "Testing",   4));
    }

    // ─── Core search (same logic as original) ────────────────────────────────
    public static int search(int id) {
        for (int i = 0; i < list.size(); i++)
            if (list.get(i).getId() == id) return i;
        return -1;
    }

    public static boolean idExists(int id) {
        return search(id) != -1;
    }

    public static Employee getById(int id) {
        int idx = search(id);
        return idx != -1 ? list.get(idx) : null;
    }

    // ─── Delete (same logic as original) ─────────────────────────────────────
    public static boolean deleteById(int id) {
        int idx = search(id);
        if (idx != -1) { list.remove(idx); return true; }
        return false;
    }

    // ─── Sort by salary (same logic as original) ─────────────────────────────
    public static void sortBySalary() {
        list.sort((a, b) -> Double.compare(a.getSalary(), b.getSalary()));
    }

    // ─── Search by name (same logic as original) ─────────────────────────────
    public static List<Employee> searchByName(String name) {
        List<Employee> result = new ArrayList<>();
        for (Employee e : list)
            if (e.name.equalsIgnoreCase(name)) result.add(e);
        return result;
    }

    // ─── Filter by salary range (same logic as original) ─────────────────────
    public static List<Employee> filterBySalary(double min, double max) {
        List<Employee> result = new ArrayList<>();
        for (Employee e : list)
            if (e.getSalary() >= min && e.getSalary() <= max) result.add(e);
        return result;
    }

    // ─── Count (same logic as original) ──────────────────────────────────────
    // type: 0=All, 1=Manager, 2=Engineer, 3=Intern
    public static int count(int type) {
        int c = 0;
        for (Employee e : list) {
            if (type == 0)                              c++;
            else if (type == 1 && e instanceof Manager)  c++;
            else if (type == 2 && e instanceof Engineer) c++;
            else if (type == 3 && e instanceof Intern)   c++;
        }
        return c;
    }

    // ─── Average salary (same logic as original) ─────────────────────────────
    public static double avgSalary(int type) {
        double sum = 0; int c = 0;
        for (Employee e : list) {
            boolean match = type == 0
                || (type == 1 && e instanceof Manager)
                || (type == 2 && e instanceof Engineer)
                || (type == 3 && e instanceof Intern);
            if (match) { sum += e.getSalary(); c++; }
        }
        return c == 0 ? 0 : sum / c;
    }

    // ─── Helpers for GUI display ──────────────────────────────────────────────
    public static String getRole(Employee e) {
        if (e instanceof Manager)  return "Manager";
        if (e instanceof Engineer) return "Engineer";
        if (e instanceof Intern)   return "Intern";
        return "Unknown";
    }

    public static String getExtraInfo(Employee e) {
        if (e instanceof Manager)  return "Team: " + ((Manager) e).getTeamSize();
        if (e instanceof Intern)   return "Duration: " + ((Intern) e).getDuration() + " mo";
        return "—";
    }

    /** Formats employee details for the text output area. */
    public static String format(Employee e) {
        StringBuilder sb = new StringBuilder();
        sb.append("Role    : ").append(getRole(e)).append("\n");
        sb.append("Name    : ").append(e.name).append("\n");
        sb.append("ID      : ").append(e.getId()).append("\n");
        sb.append("Salary  : \u20b9").append(String.format("%.0f", e.getSalary())).append("\n");
        sb.append("Domain  : ").append(e.domain).append("\n");
        if (e instanceof Manager)
            sb.append("Team    : ").append(((Manager) e).getTeamSize()).append(" members\n");
        if (e instanceof Intern)
            sb.append("Duration: ").append(((Intern) e).getDuration()).append(" months\n");
        return sb.toString();
    }

    /** Builds the Object[][] rows for the JTable. */
    public static Object[][] buildTableData(int filterType) {
        List<Employee> filtered = new ArrayList<>();
        for (Employee e : list) {
            if (filterType == 0)                               filtered.add(e);
            else if (filterType == 1 && e instanceof Manager)  filtered.add(e);
            else if (filterType == 2 && e instanceof Engineer) filtered.add(e);
            else if (filterType == 3 && e instanceof Intern)   filtered.add(e);
        }
        Object[][] data = new Object[filtered.size()][7];
        for (int i = 0; i < filtered.size(); i++) {
            Employee e = filtered.get(i);
            data[i][0] = i + 1;
            data[i][1] = getRole(e);
            data[i][2] = e.name;
            data[i][3] = e.getId();
            data[i][4] = "\u20b9" + String.format("%.0f", e.getSalary());
            data[i][5] = e.domain;
            data[i][6] = getExtraInfo(e);
        }
        return data;
    }
}
