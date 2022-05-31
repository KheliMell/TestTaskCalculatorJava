import java.util.Objects;
import java.util.Scanner;

public class Main {
    static char _typeNumber;

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        String input = scan.nextLine();
        System.out.println(calc(input));
    }

    public static String calc(String input) {

        //разделение примера на составляющие
        String[] numbersAndOperand = input.split(" ");

        //проверка правильности ввода примера и проверка операнда
        try {
            if ((inputCheckerAndFindType(numbersAndOperand))) {
                throw new Exception();
            }
        } catch (Exception e) {
            return "throws Exception";
        }

        String finalResult;
        try {
            finalResult = operationOnNumbers(numbersAndOperand[0], numbersAndOperand[2], numbersAndOperand[1].charAt(0));
        } catch (Exception e) {
            return "throws Exception";
        }
        return finalResult;
    }

    static String operationOnNumbers(String firstNumber, String secondNumber, char operand) throws Exception {
        //Процесс вычисления
        String result = null;
        switch (_typeNumber) {
            case 'a':
                int num1 = Integer.parseInt(firstNumber);
                int num2 = Integer.parseInt(secondNumber);
                result = Integer.toString(allOperations(num1, num2, operand));
                break;
            case 'r':
                result = allOperationsOnRomanNumbers(firstNumber, secondNumber, operand);
                break;
        }
        return result;
    }

    static String allOperationsOnRomanNumbers(String firstNumber, String secondNumber, char operand) throws Exception {

        int num1 = convertRomanToArabicNum(firstNumber);
        int num2 = convertRomanToArabicNum(secondNumber);
        //проверка на допустимость операции вычитания(Первое строго должно быть больше второго)
        if (num1 < num2) {
            throw new Exception();
        }
        int forConvert = allOperations(num1, num2, operand);

        return convertArabicToRoman(forConvert);
    }

    static String convertArabicToRoman(int numberForConvert) {
        //Конвертация арабский чисел в римские
        int units = numberForConvert % 10;
        int tens = numberForConvert - units;
        String[] allTens = {"X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC", "C"};
        String[] allUnits = {"I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X"};
        String result = "";
        if (tens != 0) {
            result += allTens[tens / 10 - 1];
        }
        if (units != 0) {
            result += allUnits[units - 1];
        }
        if (result == "") {
            result = "0";
        }
        return result;

    }

    static int convertRomanToArabicNum(String number) {
        //Конвертирование римских цифр в арабские
        RomeNumbers romNum = RomeNumbers.valueOf(number);
        int r = Integer.parseInt(romNum._toArabic);
        return r;
    }

    static int allOperations(int firstNumber, int secondNumber, char operand) {
        //Все доступные операции надчислами
        int result = 0;
        switch (operand) {
            case '+':
                result = firstNumber + secondNumber;
                break;
            case '-':
                result = firstNumber - secondNumber;
                break;
            case '*':
                result = firstNumber * secondNumber;
                break;
            case '/':
                result = firstNumber / secondNumber;
                break;
        }
        return result;
    }

    static boolean inputCheckerAndFindType(String[] numbersAndOperand) {
        //Проверка на количество введенной информации и првильность операнда
        if (!(checkOperand(numbersAndOperand[1])) && (numbersAndOperand.length != 3)) {
            return true;
        } else {
            return !(checkNumbersAndSetType(numbersAndOperand[0], numbersAndOperand[2]));
        }
    }

    static boolean checkNumbersAndSetType(String firstNum, String secondNum) {

        try {
            //Попытка преобразования чисел. Если успешно - арабское, нет - римское/ошибка
            int num1 = Integer.parseInt(firstNum);
            int num2 = Integer.parseInt(secondNum);

            //Проверка, чтобы арабские числа не были больше 10
            if ((num1 <= 10) && (num2 <= 10)) {
                _typeNumber = 'a';
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            if (checkRomanNumber(firstNum) && checkRomanNumber(secondNum)) {
                _typeNumber = 'r';
                return true;
            } else {
                return false;
            }
        }
    }

    static boolean checkRomanNumber(String romanNumber) {
        //Проверка правильности римского числа
        String[] correctNumbers = {"I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X"};
        for (String correctNumber : correctNumbers) {
            if (Objects.equals(romanNumber, correctNumber)) {
                return true;
            }
        }
        return false;
    }

    static boolean checkOperand(String operand) {
        return operand == "+" || operand == "-" || operand == "*" || operand == "/";
    }

    enum RomeNumbers {
        I("1"),
        II("2"),
        III("3"),
        IV("4"),
        V("5"),
        VI("6"),
        VII("7"),
        VIII("8"),
        IX("9"),
        X("10");

        String _toArabic;

        RomeNumbers(String transform) {
            this._toArabic = transform;
        }
    }
}