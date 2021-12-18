/**
 * Java Core. HomeWork-1
 * Class Water
 *
 * @author Pavel Pulyk
 * @version 0.1 18.12.2021
 */

public class Pool implements Doable {
    private int length;

    public Pool(int length) {
        this.length = length;
    }

    public boolean doIt(Animal animal) {
        if(animal instanceof Swimable)
            return ((Swimable) animal).swim(length);
        else
            return false;
    }

    @Override
    public String toString() {
        return this.getClass().getName() + " " + length + "m";
    }
}