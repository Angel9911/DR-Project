package interview_tasks_paysafe.object_oriented.softuni.java_oop.task5_interface_abstraction.task5_2_abstraction;

import interview_tasks_paysafe.object_oriented.softuni.java_oop.task5_interface_abstraction.task5_2.Person;

public abstract class BaseCar implements Person {
    private String name;

    protected BaseCar(String name){
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }
}
