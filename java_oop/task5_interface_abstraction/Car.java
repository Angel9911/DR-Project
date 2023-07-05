package interview_tasks_paysafe.object_oriented.softuni.java_oop.task5_interface_abstraction;

import java.io.Serializable;

public interface Car extends Serializable {
    public static final int TIRES = 4; // by default variable in interface is abstract final. Because we couldn't change value of variable which is couldn't instantiated.
    public String getModel();
    public abstract String getColor();
    Integer getHorsePower();
    String countryProduced();
}
