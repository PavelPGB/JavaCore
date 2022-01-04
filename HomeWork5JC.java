/**
 * Java Core. HomeWork-5
 *
 * @author Pavel Pulyk
 * @version 0.1 04.01.2022
 */

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;


class HomeWork5JC {
    public static void main(String[] args) {
        try (FileWriter file = new FileWriter("HomeWork5JC1.csv")) {
            file.write("Value 1;Value 2;Value 3\n" + "100;200;123\n" + "300;400;500\n" + "111;222;333\n");
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        AppData appData = new AppData();
            appData.load("HomeWork5JC1.csv");
            appData.save("HomeWork5JC2.csv");

            System.out.println(Arrays.toString(appData.getHeader()));
            System.out.println(Arrays.deepToString(appData.getData()));
    }
}

class AppData {
    String[] header;
    Integer[][] data;

    AppData() {
    }

    String[] getHeader() {
        return header;
    }

    void setHeader(String[] header) {
        this.header = header;
    }

    Integer[][] getData() {
        return data;
    }

    void setData(Integer[][] data) {
        this.data = data;
    }

    void load(String fileName) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            header = br.readLine().split(";");

            ArrayList<Integer[]> dataList = new ArrayList<>();
            String tempString;
            while ((tempString = br.readLine()) != null) {
                dataList.add(stringToDataLine(tempString));
            }
            data = dataList.toArray(new Integer[][]{{}});
        } catch (FileNotFoundException exception) {
            exception.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    Integer[] stringToDataLine(String string) {
        String[] strings = string.split(";");

        Integer[] integers = new Integer[strings.length];
        for (int i = 0; i < strings.length; i++) {
            integers[i] = Integer.parseInt(strings[i]);
        }
        return integers;
    }

    void save(String fileName) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            bw.write(lineToString(header));
            for (Integer[] line : data) {
                bw.write(lineToString(line));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    <T> String lineToString(T[] line) {
        String result = "";
        for (int i = 0; i < line.length; i++) {
            result = result + line[i].toString();
            if (i != line.length - 1) {
                result += ";";
            }
        }
        result += "\n";
        return result;
    }
}
