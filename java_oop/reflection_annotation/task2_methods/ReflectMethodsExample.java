package interview_tasks_paysafe.object_oriented.softuni.java_oop.reflection_annotation.task2_methods;

import interview_tasks_paysafe.object_oriented.softuni.java_oop.reflection_annotation.task1_reflect.Reflection;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Comparator;

public class ReflectMethodsExample {
    private static class MethodComparator implements Comparator<Method>{

        @Override
        public int compare(Method o1, Method o2) {
            boolean o1Getname= o1.getName().startsWith("get");
            boolean o2Getname = o2.getName().startsWith("get");

            if(o1Getname && o2Getname){

                return o1.getName().compareTo(o2.getName());
            }


            if(o1.getName().startsWith("set") && o2.getName().startsWith("set")){

                return o1.getName().compareTo(o2.getName());
            }
            return Boolean.compare(o2Getname,o1Getname);
        }
    }
    public static void main(String[] args) {

        Class<Reflection> getReflection = Reflection.class;

        Method[] methods = getReflection.getMethods();

        Arrays.stream(methods)
                .filter(m -> !m.getName().equals("toString"))
                .sorted(new MethodComparator())
                .forEach(ReflectMethodsExample::printMethodInfo);

        System.out.println("------/------");
        Field[]fields = getReflection.getDeclaredFields();

        for (Field field:fields) {
            System.out.println(field.toString() + "=>"+field.getModifiers()+"=>"
            + Modifier.toString(field.getModifiers()));
        }
    }
    private static void printMethodInfo(Method method){
        if(method.getName().startsWith("get")){
            System.out.append(String.format("%s will return %s ",method.getName(),method.getReturnType().getSimpleName()));
        }else if(method.getName().startsWith("set")){
            System.out.append(String.format("%s will return %s ",method.getName(),method.getReturnType().getSimpleName()));
        }
    }
}
