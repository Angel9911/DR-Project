package interview_tasks_paysafe.object_oriented.softuni.java_oop.task5_interface_abstraction.task5_2_abstraction;

public class Robot implements Identifiable {

    private String id;

    private String model;

    public Robot(String model, String id) {
        this.model = model;
        this.id = id;
    }

    @Override
    public String getId() {
        return this.id;
    }
    public String getModel() {
        return model;
    }


}
