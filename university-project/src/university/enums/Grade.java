package university.enums;

public enum Grade {
    A(5.0),
    B(4.0),
    C(3.0),
    D(2.0),
    F(0.0),
    NA(-1.0);

    private final double points;

    Grade(double points) {
        this.points = points;
    }

    public double getPoints() {
        return points;
    }
}
