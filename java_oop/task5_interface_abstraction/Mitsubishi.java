package interview_tasks_paysafe.object_oriented.softuni.java_oop.task5_interface_abstraction;

public class Mitsubishi extends CarImpl implements Rentable,Sellable {
    private final double price;
    private final double pricePerDay;
    private final int rendDay;
    public Mitsubishi(String model, String color, int horsePower, String countryProduce, double price, double pricePerDay, int rendDay) {
        super(model, color, horsePower, countryProduce);
        this.price = price;
        this.pricePerDay = pricePerDay;
        this.rendDay = rendDay;
    }

    @Override
    public Integer getMinRentDay() {
        return this.rendDay;
    }

    @Override
    public Double getPricePerDay() {
        return this.pricePerDay;
    }

    @Override
    public Double getPrice() {
        return this.price;
    }
    //its example for polymorphism
    @Override
    public String toString() {
        return String.format("The car with model %s, color %s with horsepower %d, produced in %s, price %f, pricePerDay %f, and minimum rent day %d"
                ,super.getModel(),super.getColor(),super.getHorsePower(),super.countryProduced(),price,pricePerDay,rendDay);

    }
}
