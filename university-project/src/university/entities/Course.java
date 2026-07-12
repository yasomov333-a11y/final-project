package university.entities;

public class Course {
    private final int id;
    private String title;
    private int credits;
    private Teacher teacher;

    public Course(int id, String title, int credits, Teacher teacher) {
        this.id = id;
        setTitle(title);
        setCredits(credits);
        this.teacher = teacher;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Назва курсу не може бути порожньою");
        }
        this.title = title.trim();
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        if (credits <= 0) {
            throw new IllegalArgumentException("Кредити мають бути більше 0");
        }
        this.credits = credits;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    @Override
    public String toString() {
        return String.format("Course{id=%d, title='%s', credits=%d, teacher=%s}",
                id, title, credits, teacher == null ? "немає" : teacher.getName());
    }
}
