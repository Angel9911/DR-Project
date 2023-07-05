package interview_tasks_paysafe.object_oriented.softuni.java_oop.task6_polymorphisum;

public class AlbinoJaguar implements Jaguar{
    public String sleep(){
        return "i don't want to eat because i sleep";
    }

    @Override
    public String runFast() {
        return null;
    }
}
