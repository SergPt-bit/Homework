public class Lesson_3 {

    // Класс "Товар"
    static class Product {
        String name;
        String manufactureDate;
        String producer;
        String originCountry;
        double price;
        boolean reserved;

        public Product(String name, String manufactureDate, String producer,
                       String originCountry, double price, boolean reserved) {
            this.name = name;
            this.manufactureDate = manufactureDate;
            this.producer = producer;
            this.originCountry = originCountry;
            this.price = price;
            this.reserved = reserved;
        }

        public void printInfo() {
            System.out.println("Название: " + name);
            System.out.println("Дата производства: " + manufactureDate);
            System.out.println("Производитель: " + producer);
            System.out.println("Страна происхождения: " + originCountry);
            System.out.println("Цена: " + price + " USD");
            System.out.println("Забронировано: " + (reserved ? "Да" : "Нет"));
            System.out.println("------------");
        }
    }

    // Класс "Парк" с внутренним классом
    static class Park {
        String name;

        public Park(String name) {
            this.name = name;
        }

        class Attraction {
            String title;
            String hours;
            double price;

            public Attraction(String title, String hours, double price) {
                this.title = title;
                this.hours = hours;
                this.price = price;
            }

            public void printAttractionInfo() {
                System.out.println("Аттракцион: " + title);
                System.out.println("Время работы: " + hours);
                System.out.println("Цена: " + price + "BYN");
                System.out.println("------------");
            }
        }
    }

    public static void main(String[] args) {
        // Массив товаров
        Product[] productsArray = new Product[5];

        productsArray[0] = new Product("Samsung galaxy", "01.02.2025",
                "Samsung Corp.", "Korea", 500, true);
        productsArray[1] = new Product("iPhone 16", "15.03.2025",
                "Apple Inc.", "USA", 2300, false);
        productsArray[2] = new Product("Xiaomi redmi 8", "22.01.2025",
                "Xiaomi", "China", 450, true);
        productsArray[3] = new Product("LG OLED TV", "01.04.2025",
                "LG Electronics", "Korea", 380, false);
        productsArray[4] = new Product("PlayStation 6", "10.03.2025",
                "Sony", "Japan", 700, true);

        for (Product p : productsArray) {
            p.printInfo();
        }

        // Пример использования внутреннего класса
        Park park = new Park("Парк Развлечений");
        Park.Attraction coaster = park.new Attraction("Американские горки", "10:00 - 20:00", 5.0);
        Park.Attraction wheel = park.new Attraction("Колесо обозрения", "12:00 - 20:00", 4.0);

        coaster.printAttractionInfo();
        wheel.printAttractionInfo();
    }
}

