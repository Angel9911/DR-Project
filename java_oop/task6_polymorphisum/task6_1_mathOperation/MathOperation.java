package interview_tasks_paysafe.object_oriented.softuni.java_oop.task6_polymorphisum.task6_1_mathOperation;

public class MathOperation {
    public static int add(int a, int b){
        return a+b;
    }
    public static int add(int a, int b, int c){
        return add(add(a,b),c);
    }
    public static int add(int a, int b, int c, int f){
        return add(add(a,b,c),f);
    }
}
