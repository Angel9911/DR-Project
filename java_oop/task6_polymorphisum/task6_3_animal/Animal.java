package interview_tasks_paysafe.object_oriented.softuni.java_oop.task6_polymorphisum.task6_3_animal;

public class Animal {
    private String name;
    private String favouriteFood;

    protected Animal(String name, String favouriteFood){
        this.name = name;
        this.favouriteFood = favouriteFood;
    }
    public String explainSelf(){
        return String.format("I'm %s, and i love to eat %s",this.name,this.favouriteFood);
    }
}
