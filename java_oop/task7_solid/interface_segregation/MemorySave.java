package interview_tasks_paysafe.object_oriented.softuni.java_oop.task7_solid.interface_segregation;

import java.util.Stack;

public class MemorySave implements Operation{
    private Stack<Integer> memory;

    public MemorySave(Stack<Integer> memory) {
        this.memory = memory;
    }

    @Override
    public void addOperand(int operand) {
        this.memory.push(operand);
    }

    @Override
    public int getResult() {
        return this.memory.peek();// last element in stack
    }

    @Override
    public boolean isCompleted() {
        return false;// because is set null on current operation
    }
}
