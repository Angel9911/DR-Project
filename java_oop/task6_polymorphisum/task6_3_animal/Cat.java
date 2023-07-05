package interview_tasks_paysafe.object_oriented.softuni.java_oop.task6_polymorphisum.task6_3_animal;

public class Cat extends Animal{
    public Cat(String name, String favouriteFood) {
        super(name, favouriteFood);
    }

    @Override
    public String explainSelf() {
        return super.explainSelf() + System.lineSeparator() + "meow" + System.lineSeparator() + "from cat";
    }
}
