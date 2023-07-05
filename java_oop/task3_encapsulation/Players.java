package interview_tasks_paysafe.object_oriented.softuni.java_oop.task3_encapsulation;

import java.util.Collections;
import java.util.List;

public class Players {
    private String name;
    private List<Dog> dogList1;
    private List<Dog> dogList2;

    public Players(String string, List<Dog> dogList1, List<Dog> dogList2) {
        this.setname(string);
        this.setdogList1(string);
        this.setdogList2(string);
    }

    private void setdogList1(String string) {
    }

    private void setdogList2(String string) {
    }

    private void setname(String string) {
    }

    public String getName() {
        return name;
    }

    public List<Dog> getDogList1() {
        return Collections.unmodifiableList(dogList1);
    }

    public List<Dog> getDogList2() {
        return Collections.unmodifiableList(dogList2);
    }

    @Override
    public String toString() {
        return "Players{" +
                "name='" + name + '\'' +
                ", dogList1=" + dogList1 +
                ", dogList2=" + dogList2 +
                '}';
    }
}
