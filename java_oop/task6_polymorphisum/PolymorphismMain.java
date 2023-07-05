package interview_tasks_paysafe.object_oriented.softuni.java_oop.task6_polymorphisum;

import interview_tasks_paysafe.object_oriented.softuni.java_oop.task6_polymorphisum.task6_1_mathOperation.MathOperation;
import interview_tasks_paysafe.object_oriented.softuni.java_oop.task6_polymorphisum.task6_2_shapes.Circle;
import interview_tasks_paysafe.object_oriented.softuni.java_oop.task6_polymorphisum.task6_2_shapes.Rectangle;
import interview_tasks_paysafe.object_oriented.softuni.java_oop.task6_polymorphisum.task6_2_shapes.Shape;
import interview_tasks_paysafe.object_oriented.softuni.java_oop.task6_polymorphisum.task6_3_animal.Animal;
import interview_tasks_paysafe.object_oriented.softuni.java_oop.task6_polymorphisum.task6_3_animal.Cat;
import interview_tasks_paysafe.object_oriented.softuni.java_oop.task6_polymorphisum.task6_3_animal.Dog;

import java.util.Collection;
import java.util.Collections;

public class PolymorphismMain {
    public static void main(String []args){

        // runtime polymorhisum will be achieved using abstraction(method overriding)

        BlackJaguar black = new BlackJaguar();
        YellowJaguar yellow = new YellowJaguar();
        AlbinoJaguar albinoJaguar = new AlbinoJaguar();

        //System.out.println(yellow.eatPrey());
        //System.out.println(black.hideInTheDark());

        displayJaguarRunning(black);
        displayJaguarRunning(yellow);
        displayJaguarRunning(albinoJaguar);

        // compile time polymorphism will be achieved when java loads the method's signature into memory. Two
        // methods with the same names but different parameters - its the same with method overloading.
        System.out.println(MathOperation.add(1,2));
        System.out.println(MathOperation.add(1,2,3));
         System.out.println(MathOperation.add(1,2,3,4));
        System.out.println("work with abstraction below - TASK SHAPES :");
        System.out.println("--------//-------");
        Shape circle = new Circle(4);
        Shape rectangle = new Rectangle(2,4);
        printShape(circle);
        printShape(rectangle);

        System.out.println("work with abstraction below - TASK ANIMALS :");
        System.out.println("--------//-------");
        Animal cat = new Cat("catty","koteshka hrana");
        Animal dog = new Dog("roni","kabela za interner");
        explainSelf(cat);
        explainSelf(dog);
        System.out.println();
    }
    public static void explainSelf(Animal animal){
        System.out.println(animal.explainSelf());
    }
    public static void printShape(Shape shape){
        System.out.println(shape.Perimeter());
        System.out.println(shape.Area());
    }

    public static <T extends Comparable<T>> T getMin(Collection<T> collection){
        return Collections.min(collection);
    }

    public static void displayJaguarRunning(Jaguar jaguar){

        Class<? extends Jaguar> jaguarClass = jaguar.getClass();

        System.out.println(jaguar.runFast());
        if(jaguar instanceof BlackJaguar){

            System.out.println(((BlackJaguar) jaguar).hideInTheDark());
        } else if(jaguar instanceof YellowJaguar){

            System.out.println(((YellowJaguar)jaguar).eatPrey());
        } else if (jaguarClass != AlbinoJaguar.class){
            throw new IllegalStateException("unknown type" + jaguar.getClass());
        }
    }
}
