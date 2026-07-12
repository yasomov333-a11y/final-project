package university.util;

import university.entities.Enrollment;
import university.entities.Student;
import university.enums.Grade;

public final class GPAUtils {

    private GPAUtils() {
    }

    public static double calculateGPA(Enrollment[] enrollments) {
        double totalPoints = 0;
        int count = 0;
        for (Enrollment e : enrollments) {
            if (e.getGrade() != Grade.NA) {
                totalPoints += e.getGrade().getPoints();
                count++;
            }
        }
        return count == 0 ? 0.0 : totalPoints / count;
    }

    public static double calculateGPAForStudent(Enrollment[] allEnrollments, int studentId) {
        int count = 0;
        for (Enrollment e : allEnrollments) {
            if (e.getStudent().getId() == studentId) {
                count++;
            }
        }
        Enrollment[] studentEnrollments = new Enrollment[count];
        int idx = 0;
        for (Enrollment e : allEnrollments) {
            if (e.getStudent().getId() == studentId) {
                studentEnrollments[idx++] = e;
            }
        }
        return calculateGPA(studentEnrollments);
    }

    public static double calculateAverageGPAForCourse(Enrollment[] allEnrollments, int courseId, String semester) {
        double total = 0;
        int count = 0;
        for (Enrollment e : allEnrollments) {
            boolean semesterMatches = semester == null || e.getSemester().equalsIgnoreCase(semester);
            if (e.getCourse().getId() == courseId && semesterMatches && e.getGrade() != Grade.NA) {
                total += e.getGrade().getPoints();
                count++;
            }
        }
        return count == 0 ? 0.0 : total / count;
    }

    /** Bubble sort двох паралельних масивів (студенти + їхні GPA) за спаданням GPA. */
    public static void sortStudentsByGPADesc(Student[] students, double[] gpas) {
        for (int i = 0; i < students.length - 1; i++) {
            for (int j = 0; j < students.length - 1 - i; j++) {
                if (gpas[j] < gpas[j + 1]) {
                    double tmpGpa = gpas[j];
                    gpas[j] = gpas[j + 1];
                    gpas[j + 1] = tmpGpa;

                    Student tmpStudent = students[j];
                    students[j] = students[j + 1];
                    students[j + 1] = tmpStudent;
                }
            }
        }
    }
}
