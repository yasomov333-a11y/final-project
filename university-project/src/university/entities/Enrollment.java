package university.entities;

import university.enums.Grade;
import university.interfaces.Payable;

public class Enrollment implements Payable {
    private final int id;
    private final Student student;
    private final Course course;
    private String semester;
    private Grade grade;
    private boolean paid;

    public Enrollment(int id, Student student, Course course, String semester) {
        if (student == null) {
            throw new IllegalArgumentException("Студент обов'язковий");
        }
        if (course == null) {
            throw new IllegalArgumentException("Курс обов'язковий");
        }
        this.id = id;
        this.student = student;
        this.course = course;
        setSemester(semester);
        this.grade = Grade.NA;
        this.paid = false;
    }

    public int getId() {
        return id;
    }

    public Student getStudent() {
        return student;
    }

    public Course getCourse() {
        return course;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        if (semester == null || semester.trim().isEmpty()) {
            throw new IllegalArgumentException("Семестр не може бути порожнім");
        }
        this.semester = semester.trim();
    }

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        if (grade == null) {
            throw new IllegalArgumentException("Оцінка не може бути null");
        }
        this.grade = grade;
    }

    @Override
    public boolean isPaid() {
        return paid;
    }

    @Override
    public void markAsPaid() {
        this.paid = true;
    }

    @Override
    public String toString() {
        return String.format("Enrollment{id=%d, student=%s, course=%s, semester='%s', grade=%s, paid=%b}",
                id, student.getName(), course.getTitle(), semester, grade, paid);
    }
}
