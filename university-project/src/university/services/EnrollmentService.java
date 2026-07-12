package university.services;

import university.entities.Course;
import university.entities.Enrollment;
import university.entities.Student;
import university.enums.Grade;

public class EnrollmentService extends AbstractArrayStorage<Enrollment> {
    private int nextId = 1;

    public EnrollmentService() {
        super(10);
    }

    public Enrollment addEnrollment(Student student, Course course, String semester) {
        Enrollment enrollment = new Enrollment(nextId++, student, course, semester);
        add(enrollment);
        return enrollment;
    }

    public Enrollment[] getAllEnrollments() {
        Enrollment[] result = new Enrollment[size];
        for (int i = 0; i < size; i++) {
            result[i] = get(i);
        }
        return result;
    }

    public Enrollment findById(int id) {
        for (int i = 0; i < size; i++) {
            if (get(i).getId() == id) {
                return get(i);
            }
        }
        return null;
    }

    public boolean setGrade(int id, Grade grade) {
        Enrollment e = findById(id);
        if (e == null) {
            return false;
        }
        e.setGrade(grade);
        return true;
    }

    public boolean markPaid(int id) {
        Enrollment e = findById(id);
        if (e == null) {
            return false;
        }
        e.markAsPaid();
        return true;
    }

    public Enrollment[] findByStudent(int studentId) {
        Enrollment[] all = getAllEnrollments();
        int count = 0;
        for (Enrollment e : all) {
            if (e.getStudent().getId() == studentId) {
                count++;
            }
        }
        Enrollment[] result = new Enrollment[count];
        int idx = 0;
        for (Enrollment e : all) {
            if (e.getStudent().getId() == studentId) {
                result[idx++] = e;
            }
        }
        return result;
    }

    public Enrollment[] unpaidEnrollments() {
        Enrollment[] all = getAllEnrollments();
        int count = 0;
        for (Enrollment e : all) {
            if (!e.isPaid()) {
                count++;
            }
        }
        Enrollment[] result = new Enrollment[count];
        int idx = 0;
        for (Enrollment e : all) {
            if (!e.isPaid()) {
                result[idx++] = e;
            }
        }
        return result;
    }
}
