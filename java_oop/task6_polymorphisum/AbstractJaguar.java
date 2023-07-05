package interview_tasks_paysafe.object_oriented.softuni.java_oop.task6_polymorphisum;

public class AbstractJaguar {

    public void runFast(Jaguar jaguar){

        if(jaguar instanceof BlackJaguar){

            System.out.println(((BlackJaguar) jaguar).hideInTheDark());
        } else if(jaguar instanceof YellowJaguar){

            System.out.println(((YellowJaguar)jaguar).eatPrey());
        } else{
            throw new IllegalStateException("unknown type" + jaguar.getClass());
        }
    }
}
