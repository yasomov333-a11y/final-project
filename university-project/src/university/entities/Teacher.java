package university.entities;

import university.enums.TeacherPosition;

public class Teacher extends Person {
    private final int id;
    private TeacherPosition position;

    public Teacher(int id, String name, String email, TeacherPosition position) {
        super(name, email);
        this.id = id;
        setPosition(position);
    }

    public int getId() {
        return id;
    }

    public TeacherPosition getPosition() {
        return position;
    }

    public void setPosition(TeacherPosition position) {
        if (position == null) {
            throw new IllegalArgumentException("Посада не може бути null");
        }
        this.position = position;
    }

    @Override
    public String toString() {
        return String.format("Teacher{id=%d, name='%s', email='%s', position=%s}",
                id, name, email, position);
    }
}
