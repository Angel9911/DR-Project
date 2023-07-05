package interview_tasks_paysafe.object_oriented.softuni.java_oop.task7_solid;

import java.util.Scanner;
// its a example of low cohesion therefore there is less coupling(when there are no some independence between modules/classes)
public class AddClass {
    private Scanner scanner;
    private int anInt;
    private int bInt;
    private int result;

    public AddClass(Scanner scanner) {
        this.scanner = new Scanner(System.in);
    }
    public int addNumbers(int a, int b){
        this.result = a + b;
        return this.result;
    }
}
