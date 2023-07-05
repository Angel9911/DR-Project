package interview_tasks_paysafe.object_oriented.softuni.java_oop.task7_solid.single_responsibility;

// this is a single responsibility class because its the only thing that it does is add of two numbers
// if we comply single responsibility it allows to achieve open/closed principle
// its a high cohesion therefore there is high/low coupling(when there are no indedependence between modules/classes )
public class CalculateAddClass {
    private int result;

     public int addNumbers(int a, int b){
       this.result = a + b;
       return this.result;
    }

}

