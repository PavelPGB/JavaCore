/**
 * Java Core. HomeWork-1
 * Class Wall
 *
 * @author Pavel Pulyk
 * @version 0.1 18.12.2021
 */

public class Wall implements Doable {
    private float height;

    public Wall(float height) {
        this.height = height;
    }

    public boolean doIt(Animal animal) {
        if(animal instanceof Jumpable)
            return ((Jumpable) animal).jump(height);
        else
            return false;
    }

    @Override
    public String toString() {
        return this.getClass().getName() + " " + height + "m";
    }
}