package interview_tasks_paysafe.object_oriented.softuni.java_oop.task5_interface_abstraction.task5_2_abstraction;

public class BulgarianAbstract extends BaseCar{

    public BulgarianAbstract(String name){
        super(name);
    }

    @Override
    public String sayHello() {
        return "Hello bulgragian";
    }
}
