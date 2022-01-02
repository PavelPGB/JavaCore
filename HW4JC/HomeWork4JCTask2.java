/**
 * Java Core. HomeWork-4
 * Task-2
 *
 * @author Pavel Pulyk
 * @version 0.1 02.01.2022
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

class HomeWork4JCTask2 {
    public static void main(String[] args) {
        Map<String, String> map = new HashMap<>();
        PhoneBook mpb = new PhoneBook(map);
        mpb.addRecord("Alexsandrov", "81111111111");
        mpb.addRecord("Matyukhina", "82222222222");
        mpb.addRecord("Zhukova", "83333333333");
        mpb.addRecord("Migunov", "84444444444");
        mpb.addRecord("Nechayev", "85555555555");
        mpb.addRecord("Nechayev", "86666666666");

        mpb.print();

        System.out.println("Search by last name Nechayev: " + mpb.getNumber("Nechayev"));
        System.out.println("Search by last name Matyukhina: " + mpb.getNumber("Matyukhina"));
        System.out.println("Search by last name Ivanov: " + mpb.getNumber("Ivanov"));
    }
}

class PhoneBook {
    private Map<String, String> phoneBook;

    PhoneBook(Map<String, String> map) {
        phoneBook = map;
    }

    void addRecord(String surname, String phone) {
        phoneBook.put(phone, surname);
    }

    void print() {
        System.out.println("Phone Book:");
        phoneBook.forEach((key, value) -> System.out.println(value + ": " + key));
    }

    ArrayList<String> getNumber(String surname) {
        ArrayList<String> result = new ArrayList<>();
        phoneBook.forEach((key, value) -> {
            if (surname.equalsIgnoreCase(value)) {
                result.add(value + ": " + key);
            }
        });
        if (result.size() == 0) {
            result.add("The subscriber with this surname was not found.");
        }
        return result;
    }
}
