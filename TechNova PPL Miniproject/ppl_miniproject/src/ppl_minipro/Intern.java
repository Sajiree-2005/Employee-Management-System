package ppl_minipro;

public class Intern extends Employee {
    private int duration;

    public Intern(String name, int id, double salary, String domain, int duration) {
        super(name, id, salary, domain);
        this.duration = duration;
    }

    public int getDuration() { return duration; }

    @Override
    public void displayDetails() {
        super.displayDetails();
        System.out.println("Duration  : " + duration + " months");
    }
}
