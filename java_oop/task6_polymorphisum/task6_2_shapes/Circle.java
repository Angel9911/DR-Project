package interview_tasks_paysafe.object_oriented.softuni.java_oop.task6_polymorphisum.task6_2_shapes;

public class Circle extends Shape{
    private final Double radius;

    public Circle(double radius) {
        this.radius = radius;
    }

    @Override
    public double Perimeter() {
        if (super.getPerimeter() == null) {

            super.setPerimeter(2 * Math.PI + this.radius);
        }
        return super.getPerimeter();
    }

    @Override
    public double Area() {
        if(super.getArea() == null) {
            super.setArea(this.radius * this.radius * Math.PI);
        }
        return super.getArea();
    }
}
