package com.example.lesson7;

public class NumberComparator {
    public static boolean areEqual(int x, int y) { return x == y; }
    public static String compareNumbers(int x, int y) {
        if (x > y) return "Первое число больше второго";
        else if (x < y) return "Второе число больше первого";
        else return "Числа равны";
    }
}
