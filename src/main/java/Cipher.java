import ceasarCipherException.CharValidException;
import ceasarCipherException.MyFileEmpty;
import ceasarCipherException.MyFileNotFoundException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Cipher {
    private static final List<Character> SYMBOLS = List.of('.', ',', '!', '?', '"', ':', '-', ' ');
    private static final List<Character> VOWELS = List.of('�', '�', '�', '�', '�', '�', '�', '�', '�', '�');

    private static int coincidence(String text, String subString) {
        int count = 0;
        int indexDot = text.indexOf(subString);
        while (indexDot != -1) {
            count += 5;
            indexDot = text.indexOf(subString, indexDot + 1);
        }
        return count;
    }

    private static Path changePath(Path oldPath, int keyCipher) {
        int beginIndex = oldPath.toString().indexOf("\\");
        int endIndex = oldPath.toString().indexOf(".", beginIndex);
        String nameFile = oldPath.toString().substring(beginIndex, endIndex);

        return Path.of(oldPath.toString().replace(nameFile, nameFile + "_key_" + keyCipher +"_cipherText"));
    }

    private static int isNeverCoincidence(String str) {
        int count = 0;
        for (int j = 0; j < str.length() - 5; j++) {
            char c1 = str.charAt(j);
            char c2 = str.charAt(j + 1);
            char c3 = str.charAt(j + 2);
            char c4 = str.charAt(j + 3);
            char c5 = str.charAt(j + 4);
            if ((!VOWELS.contains(c1) && !SYMBOLS.contains(c1)) && (!VOWELS.contains(c2) && !SYMBOLS.contains(c2)) && (!VOWELS.contains(c3) && !SYMBOLS.contains(c3)) && (!VOWELS.contains(c4) && !SYMBOLS.contains(c4)) && (!VOWELS.contains(c5) && !SYMBOLS.contains(c5))) {
                count += 20;
            }
        }
        return count;
    }

    private static char replacementSymbolAndChar(char word, int key) {
        final int ALPHABET_SIZE = 32;

        if (word >= '�' && word <= '�') {
            word += key % ALPHABET_SIZE;
            if (word < '�') {
                word += ALPHABET_SIZE;
            }
            if (word > '�') {
                word -= ALPHABET_SIZE;
            }
        } else if (word >= '�' && word <= '�') {
            word += key % ALPHABET_SIZE;
            if (word < '�') {
                word += ALPHABET_SIZE;
            }
            if (word > '�') {
                word -= ALPHABET_SIZE;
            }
        } else {
            word = SYMBOLS.get((SYMBOLS.indexOf(word) + key) % SYMBOLS.size());
        }
        return word;
    }

    private static boolean isCheckValidChar(char[] array) {
        for (int i = 0; i < array.length; i++) {
            char symbol = array[i];
            if (!(symbol >= 1040 && symbol <= 1103 || SYMBOLS.contains(symbol))) {
                throw new CharValidException("������������ ������ ��� ��������� " + "\"" + symbol + "\"," + " ������� �������: " + i);
            }
        }

        return true;
    }

    private static Map<Integer, String> getAllRot(String text) {
        Map<Integer, String> result = new HashMap<>();

        for (int i = 0; i <= 31; i++) {
            String rot = codingText(text, i);
            result.put(i, rot);
        }

        return result;
    }

    public static Path getPath(Scanner scanner) {
        return Path.of(scanner.nextLine());
    }

    public static String getTextFromFile(Path path) {
        String text;
        try (FileChannel inChannel = FileChannel.open(path)) {
            ByteBuffer buffer = ByteBuffer.allocate((int) inChannel.size());
            inChannel.read(buffer);
            buffer.flip();
            text = new String(buffer.array(), StandardCharsets.UTF_8);
            if (text.length() == 0) {
                throw new MyFileEmpty("����������� ���� �� ����� ������ ��� ���������� ��� ��� �����������!");
            }
        } catch (IOException e) {
            throw new MyFileNotFoundException("�� ���������� ���� " + "\"" + path.toString() + "\"" + " ���� �� ��� ������");
        }
        return text;
    }

    public static int getKey(Scanner scanner) {
        System.out.println("������� ����:");
        int key = 0;
        if (scanner.hasNextInt()) {
            key = Integer.parseInt(scanner.nextLine());
            while (key < 0) {
                System.out.println("���� �� ����� ���� �������������!");
                key = Integer.parseInt(scanner.nextLine());
            }
        }

        return key;
    }

    public static String codingText(String text, int key) {
        char[] charArray = text.toCharArray();
        if (isCheckValidChar(charArray)) {
            for (int i = 0; i < charArray.length; i++) {
                char c = charArray[i];
                charArray[i] = replacementSymbolAndChar(c, key);
            }
        }
        return new String(charArray);
    }

    public static String unCodingText(String text, int key) {

        return codingText(text, 32 - (key % 26));
    }

    public static String getBruteforce(String textFromFile) {
        Map<Integer, String> allRot = Cipher.getAllRot(textFromFile);
        StringBuilder resultString = new StringBuilder();
        int max = 0;
        for (int i = 0; i < allRot.size(); i++) {
            int count = 0;

            String rot = allRot.get(i);
            for (int j = 0; j < rot.length() - 1; j++) {
                Character c = rot.charAt(j);
                if (Character.isSpaceChar(c)) {
                    count++;
                }
                if (Character.isSpaceChar(c) && Character.isUpperCase(rot.charAt(j + 1))) {
                    count++;
                }
            }

            count += coincidence(rot, ". ");
            count += coincidence(rot, ", ");
            count += coincidence(rot, "? ");
            count += coincidence(rot, "! ");
            count += coincidence(rot, " - ");

            count -= isNeverCoincidence(rot);

            if (count > max) {
                max = count;
                resultString.delete(0, resultString.length());
                resultString.append(allRot.get(i));
            }
        }

        return resultString.toString();
    }

    public static void recordingInFile(Path oldPath, String textForRecording, int keyCipher) {
        Path newPath = changePath(oldPath, keyCipher);

        try (RandomAccessFile raf = new RandomAccessFile(String.valueOf(Files.createFile(newPath)), "rw");
             FileChannel out = raf.getChannel()) {
            ByteBuffer buffer = ByteBuffer.wrap(textForRecording.getBytes(StandardCharsets.UTF_8));
            out.write(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}