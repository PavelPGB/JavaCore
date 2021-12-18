/**
 * Java Core. HomeWork-1
 * Class Track
 *
 * @author Pavel Pulyk
 * @version 0.1 18.12.2021
 */

public class Treadmill implements Doable {
    private int length;

    public Treadmill(int length) {
        this.length = length;
    }

    public boolean doIt(Animal animal) {
        return animal.run(length);
    }

    @Override
    public String toString() {

        return this.getClass().getName() + " " + length + "m";
    }
}