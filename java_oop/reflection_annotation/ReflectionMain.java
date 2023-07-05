package interview_tasks_paysafe.object_oriented.softuni.java_oop.reflection_annotation;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReflectionMain {
    public static void main(String []args) throws ClassNotFoundException, NoSuchMethodException {

        Person person = new Person("da",2);

        Field[] fields = person.getClass().getDeclaredFields();

        List<String> stringList = new ArrayList<>();

        for(Field field:fields){
            stringList.add(field.getName());
        }

        stringList.forEach(field -> {System.out.println(field);});


        System.out.println("---------//--------");
        Class personclass = Class.forName("interview_tasks_paysafe.object_oriented.softuni.java_oop.reflection_annotation.Person");

        Arrays.stream(personclass.getDeclaredFields()).forEach(field -> System.out.println(field.getName()));
        System.out.println(personclass.getMethod("getName").getName());

        System.out.println("---------//--------");
        System.out.println("get class - " + personclass.getSimpleName()+" methods");

        Arrays.stream(personclass.getDeclaredMethods()).forEach(method -> {System.out.println(method.getName());});

        Class<?> getSuperClass = personclass.getSuperclass();
        Class<?>[] getInterfaces = Person.class.getSuperclass().getInterfaces();
        System.out.println();
    }
}
