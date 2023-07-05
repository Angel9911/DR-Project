package interview_tasks_paysafe.object_oriented.softuni.java_oop.task7_solid;

import interview_tasks_paysafe.object_oriented.softuni.java_oop.task7_solid.interface_segregation.CalculatorEngine;
import interview_tasks_paysafe.object_oriented.softuni.java_oop.task7_solid.interface_segregation.Extensions;
import interview_tasks_paysafe.object_oriented.softuni.java_oop.task7_solid.interface_segregation.InputInterpreter;
import interview_tasks_paysafe.object_oriented.softuni.java_oop.task7_solid.liskov_sub.ProgrammerCalculator;
import interview_tasks_paysafe.object_oriented.softuni.java_oop.task7_solid.single_responsibility.CalculateAddClass;
import interview_tasks_paysafe.object_oriented.softuni.java_oop.task7_solid.single_responsibility.Calculator;

import java.util.Scanner;
import java.util.Stack;

public class SolidPrinciples {
    public static void main(String []args) throws NoSuchFieldException {
        Scanner scanner = new Scanner(System.in);
        CalculateAddClass addClass = new CalculateAddClass();
        System.out.println(addClass.addNumbers(2,4));

        Calculator calculator = new Calculator();
        System.out.println(calculator.addNumbers(2,6));

        // this is example for liskov substitution principle, because we can change class Calculator with his successor ProgrammerCalculator
        // and in this way we can utilize addNumbers() again without making any changes.
        ProgrammerCalculator programmerCalculator = new ProgrammerCalculator();
        System.out.println(programmerCalculator.addNumbers(2,6));
        System.out.println(programmerCalculator.getAsBinaryString());
        // another task
        Stack<Integer> stack = new Stack<>();
        CalculatorEngine calculatorEngine = new CalculatorEngine();
        InputInterpreter inputInterpreter = Extensions.buildInterpreter(calculatorEngine,stack);
        String []tokens = scanner.nextLine().split("\\s+");
        for (String token : tokens){
            if(token.equals("end")){
                break;
            }
            inputInterpreter.interpret(token);
        }
        System.out.println(addClass.getClass().getName());
        System.out.println(addClass.getClass().getDeclaredField("result").toString());
        System.out.println(calculatorEngine.getResult());
        System.out.println();
    }
}
