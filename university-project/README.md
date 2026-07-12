# University Console App

Консольна Java-програма для управління університетом: студенти, викладачі,
курси та зарахування. Дані зберігаються в пам'яті (у масивах з ручним
розширенням), взаємодія відбувається через консольне меню.

## Структура проєкту

```
src/
 └── university/
      ├── Main.java
      ├── entities/
      │    ├── Person.java          // абстрактний батьківський клас
      │    ├── Student.java
      │    ├── Teacher.java
      │    ├── Course.java
      │    └── Enrollment.java      // implements Payable
      ├── enums/
      │    ├── Grade.java
      │    ├── StudentStatus.java
      │    └── TeacherPosition.java
      ├── interfaces/
      │    └── Payable.java
      ├── services/
      │    ├── AbstractArrayStorage.java  // спільна логіка масиву, що росте
      │    ├── StudentService.java
      │    ├── TeacherService.java
      │    ├── CourseService.java
      │    └── EnrollmentService.java
      └── util/
           └── GPAUtils.java        // обчислення GPA + bubble sort
```

## Реалізовані вимоги

- ООП: успадкування (`Person` → `Student`/`Teacher`), інкапсуляція
  (валідація в сеттерах), інтерфейс `Payable`, enum-и `Grade`,
  `StudentStatus`, `TeacherPosition`.
- CRUD для студентів, викладачів, курсів; створення/оновлення зарахувань.
- Фільтри: студенти за статусом/роком, курси за викладачем/кредитами.
- Сортування bubble sort: студенти за ПІБ, топ-N студентів за GPA.
- Звіти: пошук студента за ПІБ/email, неоплачені курси, середній GPA по
  курсу/семестру, топ-N за GPA, транскрипт студента.
- Обробка помилок: некоректний ввід і бізнес-правила кидають
  `IllegalArgumentException`, який перехоплюється в `Main` і виводиться
  зрозумілим повідомленням; порожні результати повідомляються окремо.
- Дані зберігаються в масивах (`Object[]` з ручним подвоєнням розміру),
  без `ArrayList`/`List`.
- При старті програма заповнюється кількома тестовими записами
  (студенти, викладач, курси, зарахування), щоб одразу було що
  переглядати.

## Компіляція та запуск

Потрібен JDK 17+ (використовується switch-вирази `case X -> ...`).

```bash
cd university-project
javac -d out $(find src -name "*.java")
java -cp out university.Main
```

## Можливі наступні кроки

- Додати збереження даних у файл (наприклад, CSV) між запусками.
- Покрити сервіси unit-тестами (JUnit).
- Додати клас `Address` чи `PhoneNumber` для розширення `Person`.
