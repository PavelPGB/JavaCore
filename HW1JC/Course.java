/**
 * Java Core. HomeWork-1
 * Class Course
 *
 * @author Pavel Pulyk
 * @version 0.1 18.12.2021
 */

import java.util.Arrays;

public class Course {
    Doable[] obstacles;

    public Course(Doable[] obstacles) {
        this.obstacles = obstacles;
    }

    public void doIt(Team team) {
        for (Doable obstacle : obstacles)
            team.doIt(obstacle);
    }

    @Override
    public String toString() {
        return Arrays.toString(obstacles);
    }
}
