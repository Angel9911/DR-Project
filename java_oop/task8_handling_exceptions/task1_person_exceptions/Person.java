package interview_tasks_paysafe.object_oriented.softuni.java_oop.task8_handling_exceptions.task1_person_exceptions;

public class Person {
    private String fname;
    private String lname;
    private int age;

    public Person(String fname, String lname, int age) {
        this.setFname(fname);
        this.setLname(lname);
        this.setAge(age);
    }

    private void setAge(int age) {
        if(age < 0 || age >120){
            throw new IllegalArgumentException("The age between 1 and 120");
        }else{
            this.age = age;
        }
    }

    private void setLname(String lname) {
        if(StringUtills.isNullOrEmpty(lname)){
            throw new IllegalArgumentException("The last name can not be empty");
        }else{
            this.lname = lname;
        }
    }

    private void setFname(String fname) {
        if(StringUtills.isNullOrEmpty(fname)){
            throw new IllegalArgumentException("The first name can not be empty");
        } else{
            this.fname = fname;
        }
    }

    public String getFname() {
        return fname;
    }

    public String getLname() {
        return lname;
    }

    public int getAge() {
        return age;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
