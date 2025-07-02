package task2;

public class MainPhone {
    public static void main(String[] args) {
        PhoneBook phoneBook = new PhoneBook();
        phoneBook.add("Иванов", "123-45-67");
        phoneBook.add("Петров", "234-56-78");
        phoneBook.add("Иванов", "345-67-89");

        System.out.println("Телефоны Иванова: " + phoneBook.get("Иванов"));
        System.out.println("Телефоны Петрова: " + phoneBook.get("Петров"));
        System.out.println("Телефоны Сидорова: " + phoneBook.get("Сидоров"));

        phoneBook.printAll();
    }
}
