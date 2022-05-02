import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


public class MainApp {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Укажите путь к файлу:");
        Path path = Cipher1.getPath(scanner);
        if (!Files.exists(path)) {
            System.out.println("По указанному пути файл не был найден, повторите попытку снова!");
            path = Cipher1.getPath(scanner);
        }
        String textFromFile = Cipher1.getTextFromFile(path);

        System.out.println("[A шифрование] [J расшифровка] [B расшифровка Brute force], пожалуйста, выберите один");
        String question = scanner.nextLine().toUpperCase();


        if ("A".equals(question)) {
            int keyCipher = Cipher1.getKey(scanner);
            String cipherText = Cipher1.codingText(textFromFile, keyCipher);
            System.out.println(cipherText);
        } else if ("J".equals(question)) {
            int keyCipher = Cipher1.getKey(scanner);
            String unCipherText = Cipher1.unCodingText(textFromFile, keyCipher);
            System.out.println(unCipherText);
        } else if ("B".equals(question)) {
            String textBruteForce = Cipher1.getBruteforce2(textFromFile);
            if (textBruteForce.length() == 0) {
                System.out.println("Не удолось расшифровать, измените текст!");
            } else {
                System.out.println(textBruteForce);
            }
        }
    }
}