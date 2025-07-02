package task2;

import java.util.*;

public class PhoneBook {
    private final Map<String, List<String>> phoneMap = new HashMap<>();

    public void add(String surname, String phone) {
        if (surname == null || phone == null) {
            throw new IllegalArgumentException("Фамилия и номер телефона не могут быть null");
        }
        phoneMap.computeIfAbsent(surname, k -> new ArrayList<>()).add(phone);
    }

    public List<String> get(String surname) {
        List<String> phones = phoneMap.get(surname);
        if (phones == null) {
            System.out.println("Фамилия '" + surname + "' не найдена в справочнике.");
            return Collections.emptyList();
        }
        return phones;
    }

    // ✅ Добавлен метод для печати всех записей
    public void printAll() {
        System.out.println("Список всех записей в справочнике:");
        phoneMap.forEach((surname, phones) -> {
            System.out.println(surname + ": " + phones);
        });
    }
}
