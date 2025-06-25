// Main.java

import java.util.*;

abstract class Animal {
    protected String name;
    public abstract void run(int distance);
    public abstract void swim(int distance);
}

class Dog extends Animal {
    private static int dogCount = 0;

    public Dog(String name) {
        this.name = name;
        dogCount++;
    }

    public void run(int distance) {
        System.out.println(name + (distance <= 500 ? " пробежал " + distance + " м." : " не смог пробежать " + distance + " м."));
    }

    public void swim(int distance) {
        System.out.println(name + (distance <= 10 ? " проплыл " + distance + " м." : " не смог проплыть " + distance + " м."));
    }

    public static int getDogCount() {
        return dogCount;
    }
}

class Cat extends Animal {
    private static int catCount = 0;
    private boolean satiety = false;

    public Cat(String name) {
        this.name = name;
        catCount++;
    }

    public void run(int distance) {
        System.out.println(name + (distance <= 200 ? " пробежал " + distance + " м." : " не смог пробежать " + distance + " м."));
    }

    public void swim(int distance) {
        System.out.println(name + " не умеет плавать.");
    }

    public void eat(Bowl bowl, int portion) {
        if (bowl.getFood() >= portion) {
            bowl.decreaseFood(portion);
            satiety = true;
        }
    }

    public boolean isSatiety() {
        return satiety;
    }

    public static int getCatCount() {
        return catCount;
    }
}

class Bowl {
    private int food;

    public Bowl(int food) {
        this.food = food;
    }

    public int getFood() {
        return food;
    }

    public void decreaseFood(int amount) {
        if (amount <= food) {
            food -= amount;
        }
    }

    public void addFood(int amount) {
        if (amount > 0) {
            food += amount;
        }
    }
}

interface Shape {
    double getPerimeter();
    double getArea();

    default void printInfo() {
        System.out.println("Периметр: " + getPerimeter());
        System.out.println("Площадь: " + getArea());
    }
}

abstract class ColoredShape implements Shape {
    protected String fillColor;
    protected String borderColor;

    public ColoredShape(String fillColor, String borderColor) {
        this.fillColor = fillColor;
        this.borderColor = borderColor;
    }

    public void printColors() {
        System.out.println("Цвет заливки: " + fillColor);
        System.out.println("Цвет границы: " + borderColor);
    }
}

class Circle extends ColoredShape {
    private double radius;

    public Circle(double radius, String fill, String border) {
        super(fill, border);
        this.radius = radius;
    }

    public double getPerimeter() {
        return 2 * Math.PI * radius;
    }

    public double getArea() {
        return Math.PI * radius * radius;
    }
}

class Rectangle extends ColoredShape {
    private double width, height;

    public Rectangle(double width, double height, String fill, String border) {
        super(fill, border);
        this.width = width;
        this.height = height;
    }

    public double getPerimeter() {
        return 2 * (width + height);
    }

    public double getArea() {
        return width * height;
    }
}

class Triangle extends ColoredShape {
    private double a, b, c;

    public Triangle(double a, double b, double c, String fill, String border) {
        super(fill, border);
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public double getPerimeter() {
        return a + b + c;
    }

    public double getArea() {
        double p = getPerimeter() / 2;
        return Math.sqrt(p * (p - a) * (p - b) * (p - c));
    }
}

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Задание 1: Животные ===");

        Cat[] cats = {
                new Cat("Мурзик"),
                new Cat("Барсик"),
                new Cat("Снежок")
        };
        Dog dog = new Dog("Бобик");

        dog.run(400);
        dog.swim(8);

        cats[0].run(150);
        cats[1].run(250); // превышение
        cats[2].swim(5);

        Bowl bowl = new Bowl(20);

        for (Cat cat : cats) {
            cat.eat(bowl, 8);
            System.out.println(cat.name + (cat.isSatiety() ? " сыт." : " голоден."));
        }

        System.out.println("Создано котов: " + Cat.getCatCount());
        System.out.println("Создано собак: " + Dog.getDogCount());

        System.out.println("\n=== Задание 2: Геометрические фигуры ===");

        Shape[] shapes = {
                new Circle(5, "Красный", "Черный"),
                new Rectangle(4, 6, "Синий", "Серый"),
                new Triangle(3, 4, 5, "Зеленый", "Белый")
        };

        for (Shape shape : shapes) {
            shape.printInfo();
            ColoredShape cs = (ColoredShape) shape;
            cs.printColors();
            System.out.println("---------------");
        }
    }
}
