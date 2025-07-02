package task1;

import java.util.Set;

public class StudentUtils {

    // ✅ Добавлено логирование
    public static void removeLowGrades(Set<Student> students) {
        int before = students.size();
        students.removeIf(s -> s.averageGrade() < 3.0);
        int after = students.size();
        System.out.println("Удалено студентов: " + (before - after));
    }

    public static void promoteStudents(Set<Student> students) {
        for (Student s : students) {
            if (s.averageGrade() >= 3.0) {
                s.course++;
            }
        }
    }

    public static void printStudents(Set<Student> students, int course) {
        System.out.println("Студенты курса " + course + ":");
        students.stream()
                .filter(s -> s.course == course)
                .forEach(System.out::println); // ✅ Печатаем toString, а не только имя
    }
}
