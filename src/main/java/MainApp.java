import java.nio.file.Files;
import java.nio.file.Path;

import java.util.Scanner;

public class MainApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Укажите путь к файлу:");
        Path path = CipherUtils.getPath(scanner);
        while (!Files.exists(path)) {
            System.out.println("По указанному пути файл не был найден, повторите попытку снова!");
            path = CipherUtils.getPath(scanner);
        }
        String textFromFile = CipherUtils.getTextFromFile(path);

        System.out.println("[A шифрование] [J расшифровка] [B расшифровка Brute force], пожалуйста, выберите один!");
        String question = scanner.nextLine().toUpperCase();
        while (!("A".equals(question) || "J".equals(question) || "B".equals(question))) {
            System.out.println("Такой операции нет! Повторите попытку!");
            question = scanner.nextLine().toUpperCase();
        }

        if ("A".equals(question)) {
            int keyCipher = CipherUtils.getKey(scanner);
            String cipheredText = CipherUtils.codingText(textFromFile, keyCipher);
            CipherUtils.recordingInFile(path, cipheredText, keyCipher);
            System.out.println("Шифрование успешно завершено!");
        } else if ("J".equals(question)) {
            int keyCipher = CipherUtils.getKey(scanner);
            String unCipherText = CipherUtils.unCodingText(textFromFile, keyCipher);
            System.out.println(unCipherText);
        } else {
            String textBruteForce = CipherUtils.getBruteforce(textFromFile);
            if (textBruteForce.length() == 0) {
                System.out.println("Не удолось расшифровать, измените текст!");
            } else {
                System.out.println(textBruteForce);
            }
        }
    }
}