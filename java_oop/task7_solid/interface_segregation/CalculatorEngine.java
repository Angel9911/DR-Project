package interview_tasks_paysafe.object_oriented.softuni.java_oop.task7_solid.interface_segregation;

public class CalculatorEngine {
    private int result;
    private Operation calculatorOperation; // its example for dependency inversion because the class doesn't depend on
    // specific implementation of interface but depends on abstraction. In this way we could implement both * and / operation
    // without create new two different interfaces and so on.

    public CalculatorEngine() {
        this.result = 0;
        this.calculatorOperation = null;
    }

    public void pushNumber(int number){
        if(this.calculatorOperation != null){
            this.calculatorOperation.addOperand(number);

            if(this.calculatorOperation.isCompleted()){
                this.result = this.calculatorOperation.getResult();
                this.calculatorOperation = null;
            }
        }else {
            this.result = number;
        }
    }
    public void pushOperation(Operation operation){
        if(operation.isCompleted()){
            this.pushNumber(operation.getResult());
        } else{
            this.calculatorOperation=operation;
            this.pushNumber(this.result);
        }
    }
    public int getResult(){
        return this.result;
    }
}
