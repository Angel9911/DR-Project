package interview_tasks_paysafe.object_oriented.softuni.java_oop.task4_inheritance;

public class Student extends Person{
    private String school;
    public Student(String fname, String lname, int age, String school) {
        super(fname, lname, age);
        this.school = school;
    }

    @Override
    public String toString() {
        return String.format("Student with data: %s and studies in %s",super.getPersonInfo(),this.school);
    }
}
