/**
 * Java Core. HomeWork-4
 * Task-1
 *
 * @author Pavel Pulyk
 * @version 0.1 02.01.2022
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

class HomeWork4JCTask1 {
    public static void main(String[] args) {
        ArrayList<String> set = new ArrayList<>();
        set.add("create");
        set.add("array");
        set.add("with");
        set.add("set");
        set.add("words");
        set.add("find");
        set.add("and");
        set.add("display");
        set.add("list");
        set.add("unique");
        set.add("words");
        set.add("from");
        set.add("which");
        set.add("consists");
        set.add("array");
        set.add("count");
        set.add("how many");
        set.add("times");
        set.add("occur");
        set.add("each");
        set.add("word");
        System.out.printf("There are only %d words in the original list:\n", set.size());
        System.out.println(set);
        Map<String, Integer> map = countUniqueWords(set);
        System.out.printf("There are %d unique words in the original list:\n", map.size());
        for (String s : map.keySet()) {
            System.out.println(s + " " + map.get(s));
        }
    }

    private static Map<String, Integer> countUniqueWords(ArrayList<String> sets) {
        Map<String, Integer> map = new HashMap<>();

        for (String s : sets) {
            map.put(s, map.getOrDefault(s, 0) + 1);
        }
        return map;
    }
}


