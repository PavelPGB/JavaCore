/**
 * Java Core. HomeWork-3
 *
 * @author Pavel Pulyk
 * @version 0.1 28.12.2021
 */

class HomeWork3JC {
    public static void main(String[] args) {
        Apple app1 = new Apple();
        Orange orange1 = new Orange();
            System.out.println("Apple weight: " + app1.getWeight());
            System.out.println("Weight of an orange: " + orange1.getWeight());

        Box<Apple> appleBox = new Box<Apple>(app1, 6);
            System.out.println("Apple box weight: " + appleBox.getWeight());
        Box<Orange> orangeBox = new Box<Orange>(orange1, 4);
            System.out.println("Box weight with oranges: " + orangeBox.getWeight());

            System.out.println(orangeBox.compare(appleBox));
    }
}

abstract class Fruit {
    private float weight;

    Fruit(){
    }

    void setWeight(float weight) {
        this.weight = weight;
    }

    float getWeight() {
        return weight;
    }
}

class Apple extends Fruit {
    Apple() {
        this.setWeight(1.0f);
    }
}

class Orange extends Fruit{
    Orange() {
        this.setWeight(1.5f);
    }
}

class Box <T extends Fruit> {
    private T fruit;
    private int fruitCount;

    Box(T fruit, int fruitCount) {
        this.fruit = fruit;
        this.fruitCount = fruitCount;
    }

    T getFruit() {
        return fruit;
    }

    void setFruit(T fruit) {
        this.fruit = fruit;
    }

    void addFruit(int num) {
        fruitCount += num;
    }

    float getWeight() {
        float boxWeight = fruitCount * fruit.getWeight();
        return boxWeight;
    }

    boolean compare(Box<?> box) {
        return this.getWeight() == box.getWeight();
    }
}


