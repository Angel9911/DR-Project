package interview_tasks_paysafe.object_oriented.softuni.java_oop.task3_encapsulation;

public class Animal {
    double length;
    protected double weight;
    protected double height;

    public Animal(double weight, double height) {
        this.weight = weight;
        this.height = height;
    }

    public Animal() {
    }

    public double getWeight() {
        return weight;
    }

    /*public void setWeight(double weight) {
        this.weight = weight;
    }*/

    public double getHeight() {
        return height;
    }

    /*public void setHeight(double height) {
        this.height = height;
    }*/
    public String makeSound(){
        return "make sound from animal";
    }
}
