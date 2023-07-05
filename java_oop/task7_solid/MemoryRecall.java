package interview_tasks_paysafe.object_oriented.softuni.java_oop.task7_solid;

import interview_tasks_paysafe.object_oriented.softuni.java_oop.task7_solid.interface_segregation.Operation;

import java.util.Stack;

public class MemoryRecall implements Operation {
    private Stack<Integer> memoryRecall;

    public MemoryRecall(Stack<Integer> memoryRecall) {
        this.memoryRecall = memoryRecall;
    }

    @Override
    public void addOperand(int operand) {

    }

    @Override
    public int getResult() {
        return this.memoryRecall.pop();
    }

    @Override
    public boolean isCompleted() {
        return !this.memoryRecall.empty();
    }
}
