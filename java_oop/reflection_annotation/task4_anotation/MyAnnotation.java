package interview_tasks_paysafe.object_oriented.softuni.java_oop.reflection_annotation.task4_anotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.FIELD})
public @interface MyAnnotation {
    String value() default "def val";
}
