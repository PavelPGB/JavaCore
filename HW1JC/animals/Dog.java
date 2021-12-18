/**
 * Java Core. HomeWork-1
 * Class Dog
 *
 * @author Pavel Pulyk
 * @version 0.1 18.12.2021
 */

public class Dog extends Animal implements Swimable, Jumpable {
    private int swim_limit;
    private float jump_limit;

    public Dog(String name) {
        this.name = name;
        this.run_limit = 500;
        swim_limit = 100;
        jump_limit = 2f;
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
