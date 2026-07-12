package university.entities;

import university.enums.StudentStatus;

public class Student extends Person {
    private final int id;
    private StudentStatus status;
    private int year;

    public Student(int id, String name, String email, int year) {
        super(name, email);
        this.id = id;
        setYear(year);
        this.status = StudentStatus.ACTIVE;
    }

    public int getId() {
        return id;
    }

    public StudentStatus getStatus() {
        return status;
    }

    public void setStatus(StudentStatus status) {
        if (status == null) {
            throw new IllegalArgumentException("Статус не може бути null");
        }
        this.status = status;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        if (year < 1) {
            throw new IllegalArgumentException("Рік навчання має бути >= 1");
        }
        this.year = year;
    }

    @Override
    public String toString() {
        return String.format("Student{id=%d, name='%s', email='%s', year=%d, status=%s}",
                id, name, email, year, status);
    }
}
