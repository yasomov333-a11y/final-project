package university;

import university.entities.Course;
import university.entities.Enrollment;
import university.entities.Student;
import university.entities.Teacher;
import university.enums.Grade;
import university.enums.StudentStatus;
import university.enums.TeacherPosition;
import university.services.CourseService;
import university.services.EnrollmentService;
import university.services.StudentService;
import university.services.TeacherService;
import university.util.GPAUtils;

import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final StudentService studentService = new StudentService();
    private static final TeacherService teacherService = new TeacherService();
    private static final CourseService courseService = new CourseService();
    private static final EnrollmentService enrollmentService = new EnrollmentService();

    public static void main(String[] args) {
        seedDemoData();
        boolean running = true;
        while (running) {
            printMainMenu();
            int choice = readInt("Ваш вибір: ");
            try {
                switch (choice) {
                    case 1 -> studentsMenu();
                    case 2 -> teachersMenu();
                    case 3 -> coursesMenu();
                    case 4 -> enrollmentsMenu();
                    case 5 -> reportsMenu();
                    case 0 -> running = false;
                    default -> System.out.println("Невірний пункт меню.");
                }
            } catch (IllegalArgumentException ex) {
                System.out.println("Помилка: " + ex.getMessage());
            } catch (Exception ex) {
                System.out.println("Неочікувана помилка: " + ex.getMessage());
            }
        }
        System.out.println("До побачення!");
    }

    // ---------- ГОЛОВНЕ МЕНЮ ----------

    private static void printMainMenu() {
        System.out.println("\n===== ГОЛОВНЕ МЕНЮ =====");
        System.out.println("1. Студенти");
        System.out.println("2. Викладачі");
        System.out.println("3. Курси");
        System.out.println("4. Зарахування");
        System.out.println("5. Звіти / Пошук");
        System.out.println("0. Вихід");
    }

    // ---------- СТУДЕНТИ ----------

    private static void studentsMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- Студенти ---");
            System.out.println("1. Додати студента");
            System.out.println("2. Показати всіх студентів");
            System.out.println("3. Оновити студента");
            System.out.println("4. Видалити студента");
            System.out.println("5. Змінити статус студента");
            System.out.println("6. Фільтр за статусом");
            System.out.println("7. Фільтр за роком навчання");
            System.out.println("8. Сортувати за ПІБ");
            System.out.println("0. Назад");
            int choice = readInt("Ваш вибір: ");
            switch (choice) {
                case 1 -> addStudent();
                case 2 -> printStudents(studentService.getAllStudents());
                case 3 -> updateStudent();
                case 4 -> deleteStudent();
                case 5 -> changeStudentStatus();
                case 6 -> filterStudentsByStatus();
                case 7 -> filterStudentsByYear();
                case 8 -> printStudents(studentService.sortByName());
                case 0 -> back = true;
                default -> System.out.println("Невірний пункт меню.");
            }
        }
    }

    private static void addStudent() {
        String name = readString("Ім'я: ");
        String email = readString("Email: ");
        int year = readInt("Рік навчання: ");
        Student s = studentService.addStudent(name, email, year);
        System.out.println("Додано: " + s);
    }

    private static void updateStudent() {
        int id = readInt("ID студента: ");
        Student existing = studentService.findById(id);
        if (existing == null) {
            System.out.println("Студента не знайдено.");
            return;
        }
        System.out.println("Поточні дані: " + existing);
        String name = readOptionalString("Нове ім'я (Enter - без змін): ");
        String email = readOptionalString("Новий email (Enter - без змін): ");
        String yearStr = readOptionalString("Новий рік навчання (Enter - без змін): ");
        Integer year = yearStr.isEmpty() ? null : Integer.parseInt(yearStr);
        boolean ok = studentService.updateStudent(id,
                name.isEmpty() ? null : name,
                email.isEmpty() ? null : email,
                year);
        System.out.println(ok ? "Оновлено." : "Студента не знайдено.");
    }

    private static void deleteStudent() {
        int id = readInt("ID студента: ");
        boolean ok = studentService.deleteStudent(id);
        System.out.println(ok ? "Видалено." : "Студента не знайдено.");
    }

    private static void changeStudentStatus() {
        int id = readInt("ID студента: ");
        System.out.println("Статуси: ACTIVE, ON_LEAVE, EXPELLED, GRADUATED");
        StudentStatus status = StudentStatus.valueOf(readString("Новий статус: ").toUpperCase());
        boolean ok = studentService.changeStatus(id, status);
        System.out.println(ok ? "Статус оновлено." : "Студента не знайдено.");
    }

    private static void filterStudentsByStatus() {
        System.out.println("Статуси: ACTIVE, ON_LEAVE, EXPELLED, GRADUATED");
        StudentStatus status = StudentStatus.valueOf(readString("Статус: ").toUpperCase());
        printStudents(studentService.filterByStatus(status));
    }

    private static void filterStudentsByYear() {
        int year = readInt("Рік навчання: ");
        printStudents(studentService.filterByYear(year));
    }

    private static void printStudents(Student[] students) {
        if (students.length == 0) {
            System.out.println("Немає записів.");
            return;
        }
        for (Student s : students) {
            System.out.println(s);
        }
    }

    // ---------- ВИКЛАДАЧІ ----------

    private static void teachersMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- Викладачі ---");
            System.out.println("1. Додати викладача");
            System.out.println("2. Показати всіх викладачів");
            System.out.println("3. Оновити викладача");
            System.out.println("4. Видалити викладача");
            System.out.println("0. Назад");
            int choice = readInt("Ваш вибір: ");
            switch (choice) {
                case 1 -> addTeacher();
                case 2 -> printTeachers(teacherService.getAllTeachers());
                case 3 -> updateTeacher();
                case 4 -> deleteTeacher();
                case 0 -> back = true;
                default -> System.out.println("Невірний пункт меню.");
            }
        }
    }

    private static void addTeacher() {
        String name = readString("Ім'я: ");
        String email = readString("Email: ");
        System.out.println("Посади: ASSISTANT, LECTURER, PROFESSOR");
        TeacherPosition position = TeacherPosition.valueOf(readString("Посада: ").toUpperCase());
        Teacher t = teacherService.addTeacher(name, email, position);
        System.out.println("Додано: " + t);
    }

    private static void updateTeacher() {
        int id = readInt("ID викладача: ");
        Teacher existing = teacherService.findById(id);
        if (existing == null) {
            System.out.println("Викладача не знайдено.");
            return;
        }
        System.out.println("Поточні дані: " + existing);
        String name = readOptionalString("Нове ім'я (Enter - без змін): ");
        String email = readOptionalString("Новий email (Enter - без змін): ");
        String posStr = readOptionalString("Нова посада ASSISTANT/LECTURER/PROFESSOR (Enter - без змін): ");
        TeacherPosition position = posStr.isEmpty() ? null : TeacherPosition.valueOf(posStr.toUpperCase());
        boolean ok = teacherService.updateTeacher(id,
                name.isEmpty() ? null : name,
                email.isEmpty() ? null : email,
                position);
        System.out.println(ok ? "Оновлено." : "Викладача не знайдено.");
    }

    private static void deleteTeacher() {
        int id = readInt("ID викладача: ");
        boolean ok = teacherService.deleteTeacher(id);
        System.out.println(ok ? "Видалено." : "Викладача не знайдено.");
    }

    private static void printTeachers(Teacher[] teachers) {
        if (teachers.length == 0) {
            System.out.println("Немає записів.");
            return;
        }
        for (Teacher t : teachers) {
            System.out.println(t);
        }
    }

    // ---------- КУРСИ ----------

    private static void coursesMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- Курси ---");
            System.out.println("1. Додати курс");
            System.out.println("2. Показати всі курси");
            System.out.println("3. Оновити курс");
            System.out.println("4. Видалити курс");
            System.out.println("5. Фільтр за викладачем");
            System.out.println("6. Фільтр за кредитами");
            System.out.println("0. Назад");
            int choice = readInt("Ваш вибір: ");
            switch (choice) {
                case 1 -> addCourse();
                case 2 -> printCourses(courseService.getAllCourses());
                case 3 -> updateCourse();
                case 4 -> deleteCourse();
                case 5 -> filterCoursesByTeacher();
                case 6 -> filterCoursesByCredits();
                case 0 -> back = true;
                default -> System.out.println("Невірний пункт меню.");
            }
        }
    }

    private static void addCourse() {
        String title = readString("Назва курсу: ");
        int credits = readInt("Кредити: ");
        Teacher teacher = null;
        String teacherIdStr = readOptionalString("ID викладача (Enter - без викладача): ");
        if (!teacherIdStr.isEmpty()) {
            teacher = teacherService.findById(Integer.parseInt(teacherIdStr));
            if (teacher == null) {
                System.out.println("Викладача не знайдено, курс буде без викладача.");
            }
        }
        Course c = courseService.addCourse(title, credits, teacher);
        System.out.println("Додано: " + c);
    }

    private static void updateCourse() {
        int id = readInt("ID курсу: ");
        Course existing = courseService.findById(id);
        if (existing == null) {
            System.out.println("Курс не знайдено.");
            return;
        }
        System.out.println("Поточні дані: " + existing);
        String title = readOptionalString("Нова назва (Enter - без змін): ");
        String creditsStr = readOptionalString("Нові кредити (Enter - без змін): ");
        Integer credits = creditsStr.isEmpty() ? null : Integer.parseInt(creditsStr);
        String teacherIdStr = readOptionalString("Новий ID викладача (Enter - без змін): ");
        Teacher teacher = teacherIdStr.isEmpty() ? null : teacherService.findById(Integer.parseInt(teacherIdStr));
        boolean ok = courseService.updateCourse(id, title.isEmpty() ? null : title, credits, teacher);
        System.out.println(ok ? "Оновлено." : "Курс не знайдено.");
    }

    private static void deleteCourse() {
        int id = readInt("ID курсу: ");
        boolean ok = courseService.deleteCourse(id);
        System.out.println(ok ? "Видалено." : "Курс не знайдено.");
    }

    private static void filterCoursesByTeacher() {
        int teacherId = readInt("ID викладача: ");
        printCourses(courseService.filterByTeacher(teacherId));
    }

    private static void filterCoursesByCredits() {
        int credits = readInt("Кредити: ");
        printCourses(courseService.filterByCredits(credits));
    }

    private static void printCourses(Course[] courses) {
        if (courses.length == 0) {
            System.out.println("Немає записів.");
            return;
        }
        for (Course c : courses) {
            System.out.println(c);
        }
    }

    // ---------- ЗАРАХУВАННЯ ----------

    private static void enrollmentsMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- Зарахування ---");
            System.out.println("1. Створити зарахування");
            System.out.println("2. Поставити оцінку");
            System.out.println("3. Позначити оплату");
            System.out.println("4. Зарахування студента (з GPA)");
            System.out.println("5. Транскрипт студента");
            System.out.println("0. Назад");
            int choice = readInt("Ваш вибір: ");
            switch (choice) {
                case 1 -> createEnrollment();
                case 2 -> setGrade();
                case 3 -> markPaid();
                case 4 -> studentEnrollmentsWithGPA();
                case 5 -> printTranscript();
                case 0 -> back = true;
                default -> System.out.println("Невірний пункт меню.");
            }
        }
    }

    private static void createEnrollment() {
        int studentId = readInt("ID студента: ");
        Student student = studentService.findById(studentId);
        if (student == null) {
            System.out.println("Студента не знайдено.");
            return;
        }
        int courseId = readInt("ID курсу: ");
        Course course = courseService.findById(courseId);
        if (course == null) {
            System.out.println("Курс не знайдено.");
            return;
        }
        String semester = readString("Семестр (наприклад, 2026-1): ");
        Enrollment e = enrollmentService.addEnrollment(student, course, semester);
        System.out.println("Створено: " + e);
    }

    private static void setGrade() {
        int id = readInt("ID зарахування: ");
        System.out.println("Оцінки: A, B, C, D, F, NA");
        Grade grade = Grade.valueOf(readString("Оцінка: ").toUpperCase());
        boolean ok = enrollmentService.setGrade(id, grade);
        System.out.println(ok ? "Оцінку виставлено." : "Зарахування не знайдено.");
    }

    private static void markPaid() {
        int id = readInt("ID зарахування: ");
        boolean ok = enrollmentService.markPaid(id);
        System.out.println(ok ? "Оплату позначено." : "Зарахування не знайдено.");
    }

    private static void studentEnrollmentsWithGPA() {
        int studentId = readInt("ID студента: ");
        Enrollment[] enrollments = enrollmentService.findByStudent(studentId);
        if (enrollments.length == 0) {
            System.out.println("Немає записів.");
            return;
        }
        for (Enrollment e : enrollments) {
            System.out.println(e);
        }
        System.out.printf("GPA: %.2f%n", GPAUtils.calculateGPA(enrollments));
    }

    private static void printTranscript() {
        int studentId = readInt("ID студента: ");
        Student student = studentService.findById(studentId);
        if (student == null) {
            System.out.println("Студента не знайдено.");
            return;
        }
        Enrollment[] enrollments = enrollmentService.findByStudent(studentId);
        System.out.println("===== ТРАНСКРИПТ =====");
        System.out.println(student);
        if (enrollments.length == 0) {
            System.out.println("Немає зарахувань.");
            return;
        }
        for (Enrollment e : enrollments) {
            System.out.printf("  %s | %s | Оцінка: %s | Оплачено: %s%n",
                    e.getCourse().getTitle(), e.getSemester(), e.getGrade(), e.isPaid() ? "Так" : "Ні");
        }
        System.out.printf("GPA: %.2f%n", GPAUtils.calculateGPA(enrollments));
    }

    // ---------- ЗВІТИ / ПОШУК ----------

    private static void reportsMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- Звіти / Пошук ---");
            System.out.println("1. Пошук студента (ПІБ/email)");
            System.out.println("2. Студенти з неоплаченими курсами");
            System.out.println("3. Середній GPA по курсу/семестру");
            System.out.println("4. Топ-N студентів за GPA");
            System.out.println("0. Назад");
            int choice = readInt("Ваш вибір: ");
            switch (choice) {
                case 1 -> searchStudents();
                case 2 -> unpaidStudents();
                case 3 -> averageGpaForCourse();
                case 4 -> topNStudents();
                case 0 -> back = true;
                default -> System.out.println("Невірний пункт меню.");
            }
        }
    }

    private static void searchStudents() {
        String query = readString("Введіть частину ПІБ або email: ");
        printStudents(studentService.searchByNameOrEmail(query));
    }

    private static void unpaidStudents() {
        Enrollment[] unpaid = enrollmentService.unpaidEnrollments();
        if (unpaid.length == 0) {
            System.out.println("Немає неоплачених курсів.");
            return;
        }
        for (Enrollment e : unpaid) {
            System.out.printf("%s - %s (%s)%n", e.getStudent().getName(), e.getCourse().getTitle(), e.getSemester());
        }
    }

    private static void averageGpaForCourse() {
        int courseId = readInt("ID курсу: ");
        String semester = readOptionalString("Семестр (Enter - усі семестри): ");
        double avg = GPAUtils.calculateAverageGPAForCourse(
                enrollmentService.getAllEnrollments(), courseId, semester.isEmpty() ? null : semester);
        System.out.printf("Середній GPA: %.2f%n", avg);
    }

    private static void topNStudents() {
        int n = readInt("N: ");
        Student[] students = studentService.getAllStudents();
        double[] gpas = new double[students.length];
        Enrollment[] allEnrollments = enrollmentService.getAllEnrollments();
        for (int i = 0; i < students.length; i++) {
            gpas[i] = GPAUtils.calculateGPAForStudent(allEnrollments, students[i].getId());
        }
        GPAUtils.sortStudentsByGPADesc(students, gpas);
        int limit = Math.min(n, students.length);
        for (int i = 0; i < limit; i++) {
            System.out.printf("%d. %s - GPA: %.2f%n", i + 1, students[i].getName(), gpas[i]);
        }
    }

    // ---------- ДОПОМІЖНІ МЕТОДИ ВВОДУ ----------

    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine().trim();
            try {
                return Integer.parseInt(line);
            } catch (NumberFormatException ex) {
                System.out.println("Помилка: введіть, будь ласка, ціле число.");
            }
        }
    }

    private static String readString(String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine().trim();
            if (!line.isEmpty()) {
                return line;
            }
            System.out.println("Помилка: поле не може бути порожнім.");
        }
    }

    private static String readOptionalString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    // ---------- ТЕСТОВІ ДАНІ ----------

    private static void seedDemoData() {
        Student s1 = studentService.addStudent("Іван Петренко", "ivan.petrenko@example.com", 1);
        Student s2 = studentService.addStudent("Олена Коваль", "olena.koval@example.com", 2);
        studentService.addStudent("Andriy Boyko", "andriy.boyko@example.com", 3);

        Teacher t1 = teacherService.addTeacher("Марія Іванова", "maria.ivanova@example.com", TeacherPosition.PROFESSOR);
        teacherService.addTeacher("Петро Сидоренко", "petro.sydorenko@example.com", TeacherPosition.LECTURER);

        Course c1 = courseService.addCourse("Основи програмування", 5, t1);
        Course c2 = courseService.addCourse("Бази даних", 4, t1);

        Enrollment e1 = enrollmentService.addEnrollment(s1, c1, "2026-1");
        enrollmentService.setGrade(e1.getId(), Grade.A);
        enrollmentService.markPaid(e1.getId());

        enrollmentService.addEnrollment(s1, c2, "2026-1");

        Enrollment e3 = enrollmentService.addEnrollment(s2, c1, "2026-1");
        enrollmentService.setGrade(e3.getId(), Grade.B);
        enrollmentService.markPaid(e3.getId());
    }
}
