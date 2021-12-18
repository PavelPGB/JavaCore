/**
 * Java Core. HomeWork-1
 * Class Turtle
 *
 * @author Pavel Pulyk
 * @version 0.1 18.12.2021
 */

public class Turtle extends Animal implements Swimable {
    private int swim_limit;
   
    public Turtle(String name) {
        this.name = name;
        this.run_limit = 50;
        swim_limit = 100;
    }

    @Override
    public boolean swim(int length) {
        return swim_limit > length;
    }
}