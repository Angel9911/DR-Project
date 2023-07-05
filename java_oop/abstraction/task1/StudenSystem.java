package interview_tasks_paysafe.object_oriented.softuni.java_oop.abstraction.task1;

import java.util.Map;

public class StudenSystem {

    private Map<String, Student> students;

    private Student getInputData(String []args){

        Student student = new Student();

        student.setName(args[1]);
        student.setAge(Integer.parseInt(args[2]));
        student.setGrade(Double.parseDouble(args[3]));

        return student;
    }
    private boolean isStudentExists(String studentName){
        return students.containsKey(studentName);
    }
    private void insertStudent(Student student){
        students.put(student.getName(),student);
    }

    private String  showStudent(String nameStudent){
        Student getStudent = new Student();
        String view = "";
        if(nameStudent == null){
            throw new IllegalArgumentException("ivalid parameter");
        }
        if(this.isStudentExists(nameStudent)){
            getStudent = students.get(nameStudent);
           return StringFormatter.formatData(getStudent);
        }else{
            throw new IllegalArgumentException("doesn't exist");
        }
    }

    public String ParseCommand (String []args){
        String getName = "";
        if(args[0].equals("Create")){
            Student getInputStudent = this.getInputData(args);

            if(!this.isStudentExists(getInputStudent.getName())){
                this.insertStudent(getInputStudent);
                return "Success created";
            }
        } else if(args[0].equals("Show")){
            getName = args[1];
        }else{
            return "The command doesn't exist";
        }
        String result = "";
        if(getName != null){
            result = this.showStudent(getName);
        }
        return result;
    }

}
