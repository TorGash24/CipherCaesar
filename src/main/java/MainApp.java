import java.nio.file.Files;
import java.nio.file.Path;

import java.util.Scanner;

public class MainApp {
    //D:\\MyFile_in.txt
    //D:\\MyFile_in_key_8_cipherText.txt
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("������� ���� � �����:");
        Path path = CipherUtils.getPath(scanner);
        while (!Files.exists(path)) {
            System.out.println("�� ���������� ���� ���� �� ��� ������, ��������� ������� �����!");
            path = CipherUtils.getPath(scanner);
        }
        String textFromFile = CipherUtils.getTextFromFile(path);

        System.out.println("[A ����������] [J �����������] [B ����������� Brute force], ����������, �������� ����!");
        String question = scanner.nextLine().toUpperCase();
        while (!("A".equals(question) || "J".equals(question) || "B".equals(question))) {
            System.out.println("����� �������� ���! ��������� �������!");
            question = scanner.nextLine().toUpperCase();
        }

        if ("A".equals(question)) {
            int keyCipher = CipherUtils.getKey(scanner);
            String cipheredText = CipherUtils.codingText(textFromFile, keyCipher);
            CipherUtils.recordingInFile(path, cipheredText, keyCipher);
        } else if ("J".equals(question)) {
            int keyCipher = CipherUtils.getKey(scanner);
            String unCipherText = CipherUtils.unCodingText(textFromFile, keyCipher);
            System.out.println(unCipherText);
        } else {
            String textBruteForce = CipherUtils.getBruteforce(textFromFile);
            if (textBruteForce.length() == 0) {
                System.out.println("�� ������� ������������, �������� �����!");
            } else {
                System.out.println(textBruteForce);
            }
        }
    }
}