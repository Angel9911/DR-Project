package interview_tasks_paysafe.object_oriented.softuni.java_oop.reflection_annotation.task1_reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.util.Arrays;


public class ReflectTask1 {
    public static void main(String[] args) {

        Class<Reflection> getClass = Reflection.class;

        System.out.println("the class type is: "+getClass.getSimpleName());
        System.out.println("the super class type is: "+getClass.getSuperclass().getSimpleName());

        try {

            Class<?>[] getInterfaces = getClass.getInterfaces();
            Arrays.stream(getInterfaces)
                    .map(Class::getSimpleName)
                    .forEach(System.out::println);

            //Constructor<?>[] declaredConstructors = getClass.getDeclaredConstructors(); all constructors independently of their access.
            //Constructor<?>[] publicConstructors = getClass.getConstructors(); only public constructor will be shown

            Constructor<Reflection> constructor = getClass.getDeclaredConstructor();
            Constructor<Reflection> constructorParameters = getClass.getDeclaredConstructor(String.class, String.class, String.class, int.class);

            Parameter[] defParams = constructor.getParameters();
            Parameter[] paramsCon = constructorParameters.getParameters();

            for (Parameter parameter:defParams){
                System.out.println("get default constructor parameters: "+parameter.getType().getSimpleName());
            }

            for (Parameter parameter:paramsCon){
                System.out.println("get constructor with parameters: "+parameter.getType().getSimpleName());
            }

            Reflection reflection = constructorParameters.newInstance("Acho","AchogotinCom","achoemail",23);

            System.out.println("get fields");

            Field getFieldName = getClass.getDeclaredField("name");
            getFieldName.setAccessible(true);// if the method has private access, we must use this method to set/get value from this method.It's a same solution
            //if the constructor has private access

            String getNameValue = (String)getFieldName.get(reflection);

            System.out.println("filed name: "+getFieldName.getName() + " with type: "+getFieldName.getType()+" with value: "+getNameValue);
            System.out.println("---------/---------");

            getFieldName.set(reflection,"Pesho");
            System.out.println("after changed value "+getFieldName.get(reflection));

            System.out.println("---------/---------");
            Field[] fields = getClass.getDeclaredFields();
            Field getStaticNickname = fields[0];
            getStaticNickname.setAccessible(true);// because its private
            Object getNicknameVal= getStaticNickname.get(null);
            System.out.println("get static nickname val: "+getNicknameVal);

            System.out.println(reflection);

        }catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchFieldException exception) {
            exception.printStackTrace();
        }
    }
}
