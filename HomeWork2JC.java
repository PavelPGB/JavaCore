/**
 * Java Core. HomeWork-2
 *
 * @author Pavel Pulyk
 * @version 0.1 21.12.2021
 */

class HomeWork2JC {
    public static void main(String[] args) {
        String[][] correctArray = { {"1", "1", "1", "1"}, {"2", "2", "2", "2"}, {"3", "3", "3", "3"}, {"4", "4", "4", "4"}
        };
        String[][] wrongSizeArray = { {"1", "1", "1", "1"}, {"2", "2", "2", "2"}, {"3", "3", "3", "3"}, {"4", "4", "4"}
        };
        String[][] wrongDataArray = { {"1", "1", "a", "1"}, {"2", "2", "2", "2"}, {"3", "3", "3", "3"}, {"4", "4", "4", "4"}
        };

        try {
            System.out.println("Array sum:" + Transform.arrTransform(correctArray));
        } catch (MyArraySizeException | MyArrayDataException e) {
            e.getMessage();
        }

        try {
            System.out.println("Array sum:" + Transform.arrTransform(wrongSizeArray));
        } catch (MyArraySizeException | MyArrayDataException e) {
            System.out.println(e.getMessage());
        }

        try {
            System.out.println("Array sum:" + Transform.arrTransform(wrongDataArray));
        } catch (MyArraySizeException | MyArrayDataException e) {
            System.out.println(e.getMessage());
        }
    }
}

class Transform {
    static int arrTransform(String[][] stringArray) throws MyArraySizeException, MyArrayDataException {

        int sum = 0;

        if (4 != stringArray.length) throw new MyArraySizeException();
        for (int i = 0; i < stringArray.length; i++) {
            if (4 != stringArray[i].length) throw new MyArraySizeException();
            for (int j = 0; j < stringArray[i].length; j++) {
                try {
                    sum += Integer.parseInt(stringArray[i][j]);
                } catch (NumberFormatException e) {
                    throw new MyArrayDataException(i, j);
                }
            }
        }
        return sum;
    }
}

class MyArrayDataException extends Exception {
    MyArrayDataException(int line, int column) {
        super("Invalid data in line " + line + " column " + column);
    }
}

class MyArraySizeException extends Exception {
    MyArraySizeException() {
        super("Wrong array size!");
    }
}