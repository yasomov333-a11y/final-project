package university.services;

import university.entities.Teacher;
import university.enums.TeacherPosition;

public class TeacherService extends AbstractArrayStorage<Teacher> {
    private int nextId = 1;

    public TeacherService() {
        super(10);
    }

    public Teacher addTeacher(String name, String email, TeacherPosition position) {
        Teacher teacher = new Teacher(nextId++, name, email, position);
        add(teacher);
        return teacher;
    }

    public Teacher[] getAllTeachers() {
        Teacher[] result = new Teacher[size];
        for (int i = 0; i < size; i++) {
            result[i] = get(i);
        }
        return result;
    }

    public Teacher findById(int id) {
        for (int i = 0; i < size; i++) {
            if (get(i).getId() == id) {
                return get(i);
            }
        }
        return null;
    }

    public boolean updateTeacher(int id, String name, String email, TeacherPosition position) {
        Teacher t = findById(id);
        if (t == null) {
            return false;
        }
        if (name != null) {
            t.setName(name);
        }
        if (email != null) {
            t.setEmail(email);
        }
        if (position != null) {
            t.setPosition(position);
        }
        return true;
    }

    public boolean deleteTeacher(int id) {
        for (int i = 0; i < size; i++) {
            if (get(i).getId() == id) {
                return removeAt(i);
            }
        }
        return false;
    }
}
