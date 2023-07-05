package interview_tasks_paysafe.object_oriented.softuni.java_oop.task4_inheritance;

public class Employee extends Person{
    private String job;
    public Employee(String fname, String lname, int age,String job) {
        super(fname, lname, age);
        this.job = job;
    }
    public Employee(String job){
        //super() -  it's called by default
        this.job = job;
    }
    public String makeEmployee(){
        return "make employee";
    }
    @Override
    public String toString() {
        return String.format("Employee with data: %s and his job is %s",super.getPersonInfo(),this.job);
    }
}
