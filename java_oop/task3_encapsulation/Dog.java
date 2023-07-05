package interview_tasks_paysafe.object_oriented.softuni.java_oop.task3_encapsulation;

public class Dog extends Animal {

    public void setHeight(double height) {
        this.height = height;
    }
    public void setWeight(double weight) {
        this.weight = weight;
    }

    @Override
    public String makeSound() {
        return super.makeSound();
    }
}
