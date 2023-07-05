package interview_tasks_paysafe.object_oriented.softuni.java_oop.task6_polymorphisum.task6_2_shapes;

public abstract class Shape {
    private Double area;
    private Double perimeter;

    /*protected Shape(Double area, Double perimeter){
        this.area = area;
        this.perimeter = perimeter;
    }*/

    public abstract double Perimeter();

    public abstract double Area();

    protected void setArea(Double area) {
        this.area = area;
    }

    protected void setPerimeter(Double perimeter) {
        this.perimeter = perimeter;
    }


    public Double getArea() {
        return area;
    }

    public Double getPerimeter() {
        return perimeter;
    }


}
