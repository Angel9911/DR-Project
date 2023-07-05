package interview_tasks_paysafe.object_oriented.softuni.java_oop.task7_solid.liskov_sub;

import interview_tasks_paysafe.object_oriented.softuni.java_oop.task7_solid.single_responsibility.Calculator;

public class ProgrammerCalculator extends Calculator {

    public String getAsBinaryString(){
        return Integer.toBinaryString(super.result);
    }
}
