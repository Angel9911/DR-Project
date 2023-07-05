package interview_tasks_paysafe.object_oriented.softuni.java_oop.task6_polymorphisum;

public class YellowJaguar implements Jaguar{
    @Override
    public String runFast() {
        return "YellowJaguar";
    }
    public String eatPrey(){
        return "eating what i caught";
    }
}
