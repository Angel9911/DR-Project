package interview_tasks_paysafe.object_oriented.softuni.java_oop.reflection_annotation.task3_check;

import interview_tasks_paysafe.object_oriented.softuni.java_oop.reflection_annotation.task1_reflect.Reflection;
import interview_tasks_paysafe.object_oriented.softuni.java_oop.reflection_annotation.task4_anotation.AuthorAnnotation;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Task3Main {
    public static void main(String[] args) {
        Class<Reflection> getReflection = Reflection.class;

        Arrays.stream(getReflection.getDeclaredFields())
                .filter(field -> !Modifier.isPrivate(field.getModifiers()))
                .sorted(Comparator.comparing(Field::getName))
                .forEach(f-> System.out.println(String.format("%s must be private",f.getName())));

        Method[] declaredMethods = getReflection.getDeclaredMethods();

        List<Method> getters = Arrays.stream(declaredMethods)
                .filter(method -> method.getReturnType()!=void.class)
                .filter(method -> Modifier.isPublic(method.getModifiers()))
                .sorted(Comparator.comparing(Method::getName))
                .collect(Collectors.toList());

        getters.forEach(method -> System.out.println(method.getName()));

        List<Method> setters = Arrays.stream(declaredMethods)
                .filter(method -> method.getReturnType()==void.class)
                .filter(method -> Modifier.isPublic(method.getModifiers()))
                .sorted(Comparator.comparing(Method::getName))
                .collect(Collectors.toList());
        setters.forEach(method -> System.out.println(method.getName()));

        //task 4 with annotations
        List<AuthorAnnotation> getAnnotationValues = Arrays.stream(getReflection.getDeclaredMethods())
                .filter(method -> method.getAnnotation(AuthorAnnotation.class) != null)
                .map(method -> method.getAnnotation(AuthorAnnotation.class))
                .collect(Collectors.toList());

        getAnnotationValues.forEach(authAnnotation -> System.out.println("annotation's name: "+authAnnotation.name()));
    }

}
