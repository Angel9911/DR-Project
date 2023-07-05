package interview_tasks_paysafe.object_oriented.softuni.java_oop.abstraction.task2;

public enum Discount {
    VIP(0.2),
    SECOND_VISIT(0.1),
    NONE(0.0);

    private double percentageDiscount;

     Discount(double percentageDiscount){
        this.percentageDiscount = percentageDiscount;
    }

    public double getPercentageDiscount() {
        return percentageDiscount;
    }

    public static Discount convertDiscountFromString(String discount){
        Discount getDiscount = switch (discount){
            case "VIP" -> VIP;
            case "SecondVisit" -> SECOND_VISIT;
            case "NONE" -> NONE;
            default -> throw new IllegalArgumentException("unkown");
        };
        return getDiscount;
    }
}
