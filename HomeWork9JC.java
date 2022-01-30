/**
 * Java Core. HomeWork-9
 *
 * @author Pavel Pulyk
 * @version 0.1 30.01.2022
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Objects;

class HomeWork9JC {
    public static void main(String[] args) {
        List<Student> students = new ArrayList<>();
        students.add(new Student("Sergey", Arrays.asList(new Course("Еconomy"), new Course("Management"), new Course("Finance"), new Course("Investment"), new Course("Accounting"), new Course("Insurance"))));
        students.add(new Student("Irina", Arrays.asList(new Course("Finance"), new Course("Accounting"), new Course("Insurance"))));
        students.add(new Student("Viktor", Arrays.asList(new Course("Management"),new Course("Finance"), new Course("Investment"), new Course("Accounting"), new Course("Insurance"))));
        students.add(new Student("Vasiliy", Arrays.asList(new Course("Еconomy"), new Course("Management"), new Course("Accounting"), new Course("Insurance"))));
        students.add(new Student("Ivan", Arrays.asList(new Course("Finance"), new Course("Investment"), new Course("Insurance"))));

        System.out.println("List of unique courses:");
        System.out.println(students.stream()
                .map(s -> s.getCourses())
                .flatMap(f -> f.stream())
                .collect(Collectors.toSet()));

        System.out.println("\nThree most inquisitive students:");
        System.out.println(students.stream()
                .sorted((s1, s2) -> s2.getCourses().size() - s1.getCourses().size())
                .limit(3)
                .collect(Collectors.toList()));

        System.out.println("\nList of attendees of the course in finance:");
        Course course = new Course("Finance");
        System.out.println(students.stream()
                .filter(s -> s.getCourses().contains(course))
                .collect(Collectors.toList()));
    }
}

class Student {
    private String name;
    private List<Course> courses;

    Student(String name, List<Course> courses) {
        this.name = name;
        this.courses = courses;
    }

    String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }

    List<Course> getCourses() {
        return courses;
    }

    void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    @Override
    public String toString() {
        return "Student " +
                "name -'" + name + "\'\n" +
                "courses -" + courses + "\n";
    }
}

class Course {
    private String name;

    Course(String name) {
        this.name = name;
    }

    String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Course {" +
                "name - '" + name +
                "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return Objects.equals(name, course.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}

