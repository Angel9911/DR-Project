package interview_tasks_paysafe.object_oriented.softuni.java_oop.task6_polymorphisum.task6_2_shapes;

public class Rectangle extends Shape{
    private final Double width;
    private final Double height;

    public Rectangle(double width, double height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public double Perimeter() {
        if(super.getPerimeter() == null) {
            super.setPerimeter(this.width* 2 + this.height * 2);
        }
        return super.getPerimeter();
    }

    @Override
    public double Area() {
        if(super.getArea() == null) {
            super.setArea(this.height * this.width);
        }
        return super.getArea();
    }
}
