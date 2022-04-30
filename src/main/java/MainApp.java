import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


public class MainApp {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("������� ���� � �����:");
        Path path = Cipher.getPath(scanner);

        String textFromFile = Cipher.loadFromFile(path);
        System.out.println(textFromFile);

        System.out.println(textFromFile);
        String question = scanner.nextLine();

        System.out.println("������� ����:");
        int keyCipher = Integer.parseInt(scanner.nextLine());


        Map<Integer, String> allResult = new HashMap<>();
        if ("1".equals(question)) {
            allResult = Cipher.allCipherWords(textFromFile);
            String mapa = Cipher.getBruteforce2(allResult);
            for (Map.Entry<Integer, String> entry : allResult.entrySet()) {
                System.out.println(entry);
            }
            System.out.println("����: " + allResult.get(keyCipher));
        } else if ("-1".equals(question)) {
            allResult = Cipher.allCipherWords(textFromFile);
            String res = Cipher.cipherString2(Cipher.ALPHABET, textFromFile, 32 - (keyCipher % 32));
            String mapa = Cipher.getBruteforce2(allResult);
            System.out.println("����: " + res);
        }
        //System.out.println(Cipher.allCipherWords("����� �� �����������, ��� �������� ������������, � ��������� �������� ������ �������� ����� � �������. ����� �� ����� �������� ��� ���������� ������� �� �������� ��������:"));


        // "D:\\MyFile_in.txt"


        //System.out.println(shiftChars);

        //String anti = Cipher.shiftChars(shiftChars, -20);

        //System.out.println("AntiCipher " + anti);


    }
}