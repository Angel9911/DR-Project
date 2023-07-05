package interview_tasks_paysafe.object_oriented.softuni.java_oop.task7_solid.interface_segregation;

import java.util.Stack;

public class Extensions {
    public static InputInterpreter buildInterpreter(CalculatorEngine calculatorEngine, Stack<Integer> memory){
        return new InputInterpreter(calculatorEngine,memory);
    }
}
