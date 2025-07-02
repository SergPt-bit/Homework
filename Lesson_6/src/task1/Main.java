package task1;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Set<Student> students = new HashSet<>();
        students.add(new Student("Анна", "А1", 1, Arrays.asList(4, 5, 4)));
        students.add(new Student("Борис", "Б2", 1, Arrays.asList(2, 3, 2)));
        students.add(new Student("Вера", "А1", 1, Arrays.asList(3, 3, 3)));

        StudentUtils.removeLowGrades(students);
        StudentUtils.promoteStudents(students);
        StudentUtils.printStudents(students, 2); // Выведет только переведённых
    }
}
