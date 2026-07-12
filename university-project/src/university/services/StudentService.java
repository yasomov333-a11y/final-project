package university.services;

import university.entities.Student;
import university.enums.StudentStatus;

public class StudentService extends AbstractArrayStorage<Student> {
    private int nextId = 1;

    public StudentService() {
        super(10);
    }

    public Student addStudent(String name, String email, int year) {
        Student student = new Student(nextId++, name, email, year);
        add(student);
        return student;
    }

    public Student[] getAllStudents() {
        Student[] result = new Student[size];
        for (int i = 0; i < size; i++) {
            result[i] = get(i);
        }
        return result;
    }

    public Student findById(int id) {
        for (int i = 0; i < size; i++) {
            if (get(i).getId() == id) {
                return get(i);
            }
        }
        return null;
    }

    public boolean updateStudent(int id, String name, String email, Integer year) {
        Student s = findById(id);
        if (s == null) {
            return false;
        }
        if (name != null) {
            s.setName(name);
        }
        if (email != null) {
            s.setEmail(email);
        }
        if (year != null) {
            s.setYear(year);
        }
        return true;
    }

    public boolean deleteStudent(int id) {
        for (int i = 0; i < size; i++) {
            if (get(i).getId() == id) {
                return removeAt(i);
            }
        }
        return false;
    }

    public boolean changeStatus(int id, StudentStatus status) {
        Student s = findById(id);
        if (s == null) {
            return false;
        }
        s.setStatus(status);
        return true;
    }

    public Student[] filterByStatus(StudentStatus status) {
        Student[] all = getAllStudents();
        int count = 0;
        for (Student s : all) {
            if (s.getStatus() == status) {
                count++;
            }
        }
        Student[] result = new Student[count];
        int idx = 0;
        for (Student s : all) {
            if (s.getStatus() == status) {
                result[idx++] = s;
            }
        }
        return result;
    }

    public Student[] filterByYear(int year) {
        Student[] all = getAllStudents();
        int count = 0;
        for (Student s : all) {
            if (s.getYear() == year) {
                count++;
            }
        }
        Student[] result = new Student[count];
        int idx = 0;
        for (Student s : all) {
            if (s.getYear() == year) {
                result[idx++] = s;
            }
        }
        return result;
    }

    public Student[] searchByNameOrEmail(String query) {
        String q = query.toLowerCase();
        Student[] all = getAllStudents();
        int count = 0;
        for (Student s : all) {
            if (s.getName().toLowerCase().contains(q) || s.getEmail().toLowerCase().contains(q)) {
                count++;
            }
        }
        Student[] result = new Student[count];
        int idx = 0;
        for (Student s : all) {
            if (s.getName().toLowerCase().contains(q) || s.getEmail().toLowerCase().contains(q)) {
                result[idx++] = s;
            }
        }
        return result;
    }

    /** Простий bubble sort за ПІБ. */
    public Student[] sortByName() {
        Student[] result = getAllStudents();
        for (int i = 0; i < result.length - 1; i++) {
            for (int j = 0; j < result.length - 1 - i; j++) {
                if (result[j].getName().compareToIgnoreCase(result[j + 1].getName()) > 0) {
                    Student tmp = result[j];
                    result[j] = result[j + 1];
                    result[j + 1] = tmp;
                }
            }
        }
        return result;
    }
}
