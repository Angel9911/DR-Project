package interview_tasks_paysafe.object_oriented.softuni.java_oop;

import interview_tasks_paysafe.object_oriented.softuni.java_oop.task3_encapsulation.Animal;

public class Cat extends Animal {

    public Cat() {
        this.weight = 20;
        //this.length = 20; error because the length attribute is package private, it should be protected to access out of package.
    }

    @Override
    public String makeSound() {
        return super.makeSound();
    }
}
