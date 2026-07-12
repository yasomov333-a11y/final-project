package university.services;

/**
 * Проста сховище-обгортка над масивом Object[] з автоматичним
 * розширенням, яка лежить в основі усіх сервісів (StudentService,
 * TeacherService, CourseService, EnrollmentService).
 */
public abstract class AbstractArrayStorage<T> {
    protected Object[] items;
    protected int size;

    protected AbstractArrayStorage(int initialCapacity) {
        this.items = new Object[Math.max(initialCapacity, 1)];
        this.size = 0;
    }

    private void ensureCapacity() {
        if (size == items.length) {
            Object[] newItems = new Object[items.length * 2];
            System.arraycopy(items, 0, newItems, 0, items.length);
            items = newItems;
        }
    }

    protected void add(T item) {
        ensureCapacity();
        items[size++] = item;
    }

    @SuppressWarnings("unchecked")
    protected T get(int index) {
        return (T) items[index];
    }

    protected boolean removeAt(int index) {
        if (index < 0 || index >= size) {
            return false;
        }
        for (int i = index; i < size - 1; i++) {
            items[i] = items[i + 1];
        }
        items[--size] = null;
        return true;
    }

    public int size() {
        return size;
    }
}
