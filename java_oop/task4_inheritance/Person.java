package interview_tasks_paysafe.object_oriented.softuni.java_oop.task4_inheritance;

public class Person {
    private String fname;
    private String lname;
    private int age;


    public Person(String fname, String lname, int age) {
        this.fname = fname;
        this.lname = lname;
        this.age = age;
    }

    public Person() { // when Employee create instance with job parameter - this constructor is calling

    }

    public String getPersonInfo(){
        return String.format("The persion with first naem %s and last name %s is %d", this.fname,this.lname, this.age);
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person{" +
                "fname='" + fname + '\'' +
                ", lname='" + lname + '\'' +
                ", age=" + age +
                '}';
    }
}
