package task1;

import java.util.*;

public class Student {
    String name;
    String group;
    int course;
    List<Integer> grades;

    public Student(String name, String group, int course, List<Integer> grades) {
        if (name == null || group == null || grades == null) {
            throw new IllegalArgumentException("Имя, группа и оценки не могут быть null");
        }
        this.name = name;
        this.group = group;
        this.course = course;
        this.grades = grades;
    }

    public double averageGrade() {
        if (grades.isEmpty()) {
            System.out.println("У студента " + name + " отсутствуют оценки");
            return 0.0;
        }
        return grades.stream().mapToInt(g -> g).average().orElse(0.0);
    }

    @Override
    public String toString() {
        return name + " (Группа " + group + ", Курс " + course + ")";
    }

    // ✅ Добавлено: equals() и hashCode() для корректной работы Set
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student)) return false;
        Student student = (Student) o;
        return Objects.equals(name, student.name) && Objects.equals(group, student.group);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, group);
    }
}
