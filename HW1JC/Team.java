/**
 * Java Core. HomeWork-1
 * Class Team
 *
 * @author Pavel Pulyk
 * @version 0.1 18.12.2021
 */

import java.util.Arrays;

public class Team {
    private String name;
    private Animal[] animals;
    private String result;

    public Team(String name, Animal[] animals) {
        this.name = name;
        this.animals = animals;
        result = "";
    }

    public void doIt(Doable obstacle) {
        result += obstacle.toString() + "\n";
        for (Animal animal : animals)
            result += "> " + animal + " " + obstacle.doIt(animal) + "\n";
    }

    public void showResults() {
        System.out.println(result);
    }

    @Override
    public String toString() {
        return "Team: " + name + " " + Arrays.toString(animals);
    }
}
