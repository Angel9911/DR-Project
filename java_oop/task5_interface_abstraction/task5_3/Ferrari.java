package interview_tasks_paysafe.object_oriented.softuni.java_oop.task5_interface_abstraction.task5_3;

public class Ferrari implements Car{
    private final String driverName;
    private final String model;

    public Ferrari(String driverName) {
        this.driverName = driverName;
        this.model = "448-spike";
    }

    @Override
    public String breaks() {
        return "breaks";
    }

    @Override
    public String gas() {
        return "brummmm";
    }

    @Override
    public String toString() {
        return String.format("%s, %s , %s , %s",this.driverName,this.model,breaks(),gas());
    }
}
