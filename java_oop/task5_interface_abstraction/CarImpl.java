package interview_tasks_paysafe.object_oriented.softuni.java_oop.task5_interface_abstraction;

public class CarImpl implements Car{
    private final String model;
    private final String color;
    private final int horsePower;
    private final String countryProduce;

    public CarImpl(String model, String color, int horsePower, String countryProduce) {
        this.model = model;
        this.color = color;
        this.horsePower = horsePower;
        this.countryProduce = countryProduce;
    }

    @Override
    public String getModel() {
        return this.model;
    }

    @Override
    public String getColor() {
        return this.color;
    }

    @Override
    public Integer getHorsePower() {
        return this.horsePower;
    }

    @Override
    public String countryProduced() {
        return this.countryProduce;
    }
    // its example for polymorphism
    @Override
    public String toString() {
        return "CarImpl{" +
                "model='" + model + '\'' +
                ", color='" + color + '\'' +
                ", horsePower=" + horsePower +
                ", countryProduce='" + countryProduce + '\'' +
                '}';
    }
}
