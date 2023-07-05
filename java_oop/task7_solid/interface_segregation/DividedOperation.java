package interview_tasks_paysafe.object_oriented.softuni.java_oop.task7_solid.interface_segregation;

import java.util.ArrayList;
import java.util.List;

public class DividedOperation implements Operation{
    private final List<Integer> listOperands;
    private int result;

    public DividedOperation() {
        this.listOperands = new ArrayList<>();
    }

    @Override
    public void addOperand(int operand) {
        this.listOperands.add(operand);

        if (this.isCompleted()){
            this.result = this.listOperands.get(0) / this.listOperands.get(1);
        }
    }

    @Override
    public int getResult() {
        return this.result;
    }

    @Override
    public boolean isCompleted() {
        return this.listOperands.size() == 2;
    }
}
