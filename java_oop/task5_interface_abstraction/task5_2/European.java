package interview_tasks_paysafe.object_oriented.softuni.java_oop.task5_interface_abstraction.task5_2;

public class European implements Person{
    private final String name;

    public European(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String sayHello() {
        return "Say hello";
    }
}
