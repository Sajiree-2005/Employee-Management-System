package ppl_minipro;

public class Manager extends Employee {
    private int teamSize;

    public Manager(String name, int id, double salary, String domain, int teamSize) {
        super(name, id, salary, domain);
        this.teamSize = teamSize;
    }

    public int getTeamSize() { return teamSize; }

    public void setTeamSize(int teamSize) { this.teamSize = teamSize; }

    @Override
    public void displayDetails() {
        super.displayDetails();
        System.out.println("Team Size : " + teamSize);
    }
}
