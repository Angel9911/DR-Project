package interview_tasks_paysafe.object_oriented.softuni.java_oop.task5_interface_abstraction;

import interview_tasks_paysafe.object_oriented.softuni.java_oop.task5_interface_abstraction.task5_2.Bulgarian;
import interview_tasks_paysafe.object_oriented.softuni.java_oop.task5_interface_abstraction.task5_2.European;
import interview_tasks_paysafe.object_oriented.softuni.java_oop.task5_interface_abstraction.task5_2.Person;

public class InheritanceAbstract {
    public static void main(String []args){

        Seat seat = new Seat("seat","cheren",120,"Bulgaria", 200.20,20.00,3);
        Seat seat2 = new Seat("seat2","bql",90,"Bulgaria", 200.20,20.00,3);

        System.out.println(seat);
        System.out.println(seat2.compareTo(seat));

        Car seat3 = new Seat("seat3","rozov",290,"Bulgaria", 200.20,20.00,3);


        Mitsubishi mitsubishi = new Mitsubishi("mitsubishi","rozov",190,"Bulgaria",200.20,20.00,3);
        System.out.println(mitsubishi);
        System.out.println(seat3);

        Person bulgarian = new Bulgarian("Chocho");
        Person djendur = new European("Djendur");

        System.out.println(bulgarian.sayHello() + " " + djendur.sayHello());
    }
}
