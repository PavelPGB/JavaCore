/**
 * Java Core. HomeWork-1
 * Class Wolf
 *
 * @author Pavel Pulyk
 * @version 0.1 18.12.2021
 */

public class Wolf extends Animal implements Swimable, Jumpable {
    private int swim_limit;
    private float jump_limit;

    public Wolf(String name) {
        this.name = name;
        this.run_limit = 800;
        swim_limit = 150;
        jump_limit = 2.5f;
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