package interview_tasks_paysafe.object_oriented.softuni.java_oop.task5_interface_abstraction;

public class Seat extends CarImpl implements Rentable,Sellable, Comparable<Seat>{
    private final double price;
    private final double pricePerDay;
    private final int rendDay;

    public Seat(String model, String color, int horsePower, String countryProduce, double price, double pricePerDay, int rendDay) {
        super(model, color, horsePower, countryProduce);
        this.price = price;
        this.pricePerDay = pricePerDay;
        this.rendDay = rendDay;
    }

    @Override
    public Integer getMinRentDay() {
        return null;
    }

    @Override
    public Double getPricePerDay() {
        return null;
    }

    @Override
    public Double getPrice() {
        return null;
    }

    @Override
    public int compareTo(Seat o) {
        return 0;
    }
    //its example for polymorphism
    @Override
    public String toString() {
        return "the model is :"+super.toString()+"" +
                "price=" + price +
                ", pricePerDay=" + pricePerDay +
                ", rendDay=" + rendDay +
                '}';
    }
}
