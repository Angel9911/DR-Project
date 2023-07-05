package interview_tasks_paysafe.object_oriented.softuni.java_oop.abstraction.task1;

public class StringFormatter {

    public static String formatData(Student student){
        return String.format("%s is %s years old. %s", student.getName(),student.getAge(),getGradeCommentary(student.getGrade()));

    }
    private static String getGradeCommentary(double grade){
        if(grade >= 5.00){
            return  "mn dobre";
        }else if(grade <= 4.00){
            return "ne e mn dobre";
        }else{
            return "mn zle";
        }
    }

}
