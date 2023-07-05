package interview_tasks_paysafe.object_oriented.softuni.java_oop.task4_inheritance;

import interview_tasks_paysafe.object_oriented.softuni.java_oop.task4_inheritance.task4_2_inheritance.RandomArrayList;

import java.util.ArrayList;
import java.util.List;

public class Inheritance {
    public static void main(String []args){

    Person person  = new Person("angel","dimitrov",20);
    Employee employee = new Employee("dimitur","dimitrov",25,"zemedelec");
    Student student = new Student("student","studentov", 21,"elektro");

    employee.makeEmployee();

    printPersonInfo(person);
    printPersonInfo(employee);
    System.out.println(employee.getClass().getName());

    Person employe1 = new Employee("pesjo","dada",22,"zemedelec");
    Person student1 = new Student("student2","student2",25,"mehano");
    List<Person> personList = new ArrayList<>();
    personList.add(employe1);
    personList.add(student1);

    //task 2
    RandomArrayList<Integer> randomArrayList = new RandomArrayList<>();
    for(int i=1;i<10;i++){
            randomArrayList.add(i);
    }

    System.out.println(randomArrayList.size());

    System.out.println(randomArrayList.getRandomElement());
   // System.out.println();
   // System.out.println(person.getPersonInfo());
  //  System.out.println(employee.toString());
    }
    public static void printPersonInfo(Person person){
        System.out.println(person);
    }
    // its overload because this method has same name but with different parameter.
    public static void printPersonInfo(Employee employee){
        System.out.println(employee);
    }
}
