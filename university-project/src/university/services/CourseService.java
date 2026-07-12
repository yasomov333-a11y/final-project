package university.services;

import university.entities.Course;
import university.entities.Teacher;

public class CourseService extends AbstractArrayStorage<Course> {
    private int nextId = 1;

    public CourseService() {
        super(10);
    }

    public Course addCourse(String title, int credits, Teacher teacher) {
        Course course = new Course(nextId++, title, credits, teacher);
        add(course);
        return course;
    }

    public Course[] getAllCourses() {
        Course[] result = new Course[size];
        for (int i = 0; i < size; i++) {
            result[i] = get(i);
        }
        return result;
    }

    public Course findById(int id) {
        for (int i = 0; i < size; i++) {
            if (get(i).getId() == id) {
                return get(i);
            }
        }
        return null;
    }

    public boolean updateCourse(int id, String title, Integer credits, Teacher teacher) {
        Course c = findById(id);
        if (c == null) {
            return false;
        }
        if (title != null) {
            c.setTitle(title);
        }
        if (credits != null) {
            c.setCredits(credits);
        }
        if (teacher != null) {
            c.setTeacher(teacher);
        }
        return true;
    }

    public boolean deleteCourse(int id) {
        for (int i = 0; i < size; i++) {
            if (get(i).getId() == id) {
                return removeAt(i);
            }
        }
        return false;
    }

    public Course[] filterByTeacher(int teacherId) {
        Course[] all = getAllCourses();
        int count = 0;
        for (Course c : all) {
            if (c.getTeacher() != null && c.getTeacher().getId() == teacherId) {
                count++;
            }
        }
        Course[] result = new Course[count];
        int idx = 0;
        for (Course c : all) {
            if (c.getTeacher() != null && c.getTeacher().getId() == teacherId) {
                result[idx++] = c;
            }
        }
        return result;
    }

    public Course[] filterByCredits(int credits) {
        Course[] all = getAllCourses();
        int count = 0;
        for (Course c : all) {
            if (c.getCredits() == credits) {
                count++;
            }
        }
        Course[] result = new Course[count];
        int idx = 0;
        for (Course c : all) {
            if (c.getCredits() == credits) {
                result[idx++] = c;
            }
        }
        return result;
    }
}
