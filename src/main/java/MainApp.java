import java.nio.file.Files;
import java.nio.file.Path;

import java.util.Scanner;

public class MainApp {
    //D:\\MyFile_in_key_8_cipherText.txt
    //D:\\MyFile_in_key_8_cipherText.txt
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("������� ���� � �����:");
        Path path = Cipher.getPath(scanner);
        while (!Files.exists(path)) {
            System.out.println("�� ���������� ���� ���� �� ��� ������, ��������� ������� �����!");
            path = Cipher.getPath(scanner);
        }
        String textFromFile = Cipher.getTextFromFile(path);


        System.out.println("[A ����������] [J �����������] [B ����������� Brute force], ����������, �������� ����!");
        String question = scanner.nextLine().toUpperCase();
        while (!("A".equals(question) || "J".equals(question) || "B".equals(question))) {
            System.out.println("����� �������� ���! ��������� �������!");
            question = scanner.nextLine().toUpperCase();
        }

        if ("A".equals(question)) {
            int keyCipher = Cipher.getKey(scanner);
            String cipherText = Cipher.codingText(textFromFile, keyCipher);
            Cipher.recordingInFile(path, cipherText, keyCipher);
        } else if ("J".equals(question)) {
            int keyCipher = Cipher.getKey(scanner);
            String unCipherText = Cipher.unCodingText(textFromFile, keyCipher);
            System.out.println(unCipherText);
        } else {
            String textBruteForce = Cipher.getBruteforce(textFromFile);
            if (textBruteForce.length() == 0) {
                System.out.println("�� ������� ������������, �������� �����!");
            } else {
                System.out.println(textBruteForce);
            }
        }
    }
}