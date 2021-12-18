/**
 * Java Core. HomeWork-1
 * Class Animal
 *
 * @author Pavel Pulyk
 * @version 0.1 18.12.2021
 */

public abstract class Animal implements Runable {
    protected String name;
    protected int run_limit;

    public boolean run(int length) {
        return run_limit > length;
    }

    @Override
    public String toString() {
        return this.getClass().getName() + " " + name;
    }
}