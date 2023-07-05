package interview_tasks_paysafe.object_oriented.softuni.java_oop.task5_interface_abstraction.task5_2;

public class Bulgarian implements Person{
    private final String name;

    public Bulgarian(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String sayHello() {
        return "pederas";
    }
}
