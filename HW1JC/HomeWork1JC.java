/**
 * Java Core. HomeWork-1
 * Class HomeWork1JC
 *
 * @author Pavel Pulyk
 * @version 0.1 18.12.2021
 */

class HomeWork1JC {

    public static void main(String[] args) {
        Team team = new Team("Dream Team", new Animal[] {new Cat("Timon"), new Dog("Arti"), new Wolf("Grey"), new Turtle("Tartila")});
        Course cource = new Course(new Doable[] {new Treadmill(50), new Pool(20), new Wall(2)});

        System.out.println(team);
        cource.doIt(team);
        team.showResults();
    }
}
