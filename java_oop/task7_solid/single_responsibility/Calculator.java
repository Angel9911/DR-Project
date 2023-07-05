package interview_tasks_paysafe.object_oriented.softuni.java_oop.task7_solid.single_responsibility;

public class Calculator {
    protected int result;

    public Calculator() {
        this.result = 0;// every time when create instance of Calculator, initialize it with 0.
    }
    public int addNumbers(int a, int b){
        this.result = a + b;
        return this.result;
    }
    // this example proves that this class comply open/closed principle because we add this feature without making any changes in other module/class.
    public int subtract(int a, int b){
        this.result = a - b;
        return this.result;
    }

}
