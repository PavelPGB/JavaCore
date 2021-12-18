/**
 * Java Core. HomeWork-1
 * Class Сat
 *
 * @author Pavel Pulyk
 * @version 0.1 18.12.2021
 */

public class Cat extends Animal implements Swimable, Jumpable {
    private int swim_limit;
    private float jump_limit;

    public Cat(String name) {
        this.name = name;
        this.run_limit = 80;
        swim_limit = 40;
        jump_limit = 1.5f;
    }

    @Override
    public boolean swim(int length) {
        return swim_limit > length;
    }

    @Override
    public boolean jump(float height) {
        return jump_limit > height;
    }
}