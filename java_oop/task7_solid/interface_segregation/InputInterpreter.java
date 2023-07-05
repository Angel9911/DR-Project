package interview_tasks_paysafe.object_oriented.softuni.java_oop.task7_solid.interface_segregation;

import interview_tasks_paysafe.object_oriented.softuni.java_oop.task7_solid.MemoryRecall;

import java.util.Stack;

public class InputInterpreter {
    private CalculatorEngine calculatorEngine;
    private Stack<Integer> memory;

    public InputInterpreter(CalculatorEngine calculatorEngine, Stack<Integer> stack) {
        this.calculatorEngine = calculatorEngine;
        this.memory = stack;
    }
    public boolean interpret(String input){
        try {
            this.calculatorEngine.pushNumber(Integer.parseInt(input));
        } catch (Exception e){
            this.calculatorEngine.pushOperation(this.getOperation(input));
        }
        return true;
    }
    public Operation getOperation(String operation){
        if(operation.equals("*")){
            return new MultiplicationOperation();
        } else if(operation.equals("/")){
            return new DividedOperation();
        } else if(operation.equals("ms")){
            return new MemorySave(this.memory);
        } else if(operation.equals("mr")){
            return new MemoryRecall(this.memory);
        }
        return null;
    }
}
