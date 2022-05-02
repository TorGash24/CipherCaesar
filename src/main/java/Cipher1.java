import ceasarCipherException.CharValidException;
import ceasarCipherException.MyFileEmpty;
import ceasarCipherException.MyFileNotFoundException;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.*;

public class Cipher1 {
    private static final List<Character> SYMBOLS = List.of('.', ',', '!', '?', '"', ':', '-', ' ');
    private static final List<Character> VOWELS = List.of('а', 'е', 'ё', 'и', 'й', 'о', 'у', 'ы', 'ю', 'я');

    public static Path getPath(Scanner scanner) {
        return Path.of(scanner.nextLine());
    }
    public static String getTextFromFile(Path path) {
        String text;
        try (FileChannel channel = FileChannel.open(path)) {
            ByteBuffer buffer = ByteBuffer.allocate((int) channel.size());
            channel.read(buffer);
            buffer.flip();
            text = new String(buffer.array(), StandardCharsets.UTF_8);
            if (text.length() == 0) {
                throw new MyFileEmpty("Загруженный файл не имеет текста для шифрования или для расшифровки!");
            }
        } catch (IOException e) {
            throw new MyFileNotFoundException("По указанному пути " + "\"" + path.toString() + "\"" + " файл не был найден");
        }
        return text;
    }

    public static int getKey(Scanner scanner) {
        System.out.println("Укажите ключ:");
        return Integer.parseInt(scanner.nextLine());
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

    public static char replacementSymbolAndChar(char word, int key) {
        final int ALPHABET_SIZE = 32;

        if (word >= 'А' && word <= 'Я') {
            word += key % ALPHABET_SIZE;
            if (word < 'А') {
                word += ALPHABET_SIZE;
            }
            if (word > 'Я') {
                word -= ALPHABET_SIZE;
            }
        } else if (word >= 'а' && word <= 'я') {
            word += key % ALPHABET_SIZE;
            if (word < 'а') {
                word += ALPHABET_SIZE;
            }
            if (word > 'я') {
                word -= ALPHABET_SIZE;
            }
        } else {
            word = SYMBOLS.get((SYMBOLS.indexOf(word) + key) % SYMBOLS.size());
        }
        return word;
    }

    public static boolean isCheckValidChar(char[] array) {
        for (int i = 0; i < array.length; i++) {
            char symbol = array[i];
            if (!(symbol >= 1040 && symbol <= 1103 || SYMBOLS.contains(symbol))) {
                throw new CharValidException("Недопустимый символ для кодировки " + "\"" + symbol + "\"," + " позиция символа: " + i);
            }
        }

        return true;
    }

    public static Map<Integer, String> getAllRot(String text) {
        Map<Integer, String> result = new HashMap<>();

        for (int i = 0; i <= 31; i++) {
            String rot = codingText(text, i);
            result.put(i, rot);
        }

        return result;
    }

    public static String getBruteforce2(String textFromFile) {
        Map<Integer, String> allRot = Cipher1.getAllRot(textFromFile);
        StringBuilder resultString = new StringBuilder();
        int max = 0;
        for (int i = 0; i < allRot.size(); i++) {
            int count = 0;

            String str = allRot.get(i);
            for (int j = 0; j < str.length() - 1; j++) {
                Character c = str.charAt(j);
                if (Character.isSpaceChar(c)) {
                    count++;
                }
                if (Character.isSpaceChar(c) && Character.isUpperCase(str.charAt(j + 1))) {
                    count++;
                }
            }

            int indexDot = str.indexOf(". ");
            while (indexDot != -1) {
                count += 5;
                indexDot = str.indexOf(". ", indexDot + 1);
            }
            int indexCom = str.indexOf(", ");
            while (indexCom != -1) {
                count += 5;
                ;
                indexCom = str.indexOf(", ", indexCom + 1);
            }

            int indexQuestion = str.indexOf("? ");
            while (indexQuestion != -1) {
                count += 5;
                ;
                indexQuestion = str.indexOf("? ", indexQuestion + 1);
            }

            int indexExclamatory = str.indexOf("! ");
            while (indexExclamatory != -1) {
                count += 5;
                ;
                indexExclamatory = str.indexOf("! ", indexExclamatory + 1);
            }

            int indexDash = str.indexOf(" - ");
            while (indexDash != -1) {
                count++;
                indexDash = str.indexOf(" - ", indexDash + 1);
            }

            for (int j = 0; j < str.length() - 5; j++) {
                char c1 = str.charAt(j);
                char c2 = str.charAt(j + 1);
                char c3 = str.charAt(j + 2);
                char c4 = str.charAt(j + 3);
                char c5 = str.charAt(j + 4);
                if ((!VOWELS.contains(c1) && !SYMBOLS.contains(c1)) && (!VOWELS.contains(c2) && !SYMBOLS.contains(c2)) && (!VOWELS.contains(c3) && !SYMBOLS.contains(c3)) && (!VOWELS.contains(c4) && !SYMBOLS.contains(c4)) && (!VOWELS.contains(c5) && !SYMBOLS.contains(c5))) {
                    count -= 20;
                }
            }
            if (count > max) {
                max = count;
                resultString.delete(0, resultString.length());
                resultString.append(allRot.get(i));
            }
        }

        return resultString.toString();
    }


    public static void main(String[] args) {


        String example1 = "Но главное - вы решите очень много задач разной сложности. Не переживайте, если у вас нет опыта в программировании: чтобы \"догнать\" материал, у вас будут и онлайн-занятия с менторами, и факультативы. В конце модуля, после знакомства с Гит, вас ожидает итоговый проект - написание криптоанализатора.";
        String example2 = "Эгбяш!ебцб\"!вбдэбюпэж!вгбвыдаош!ы!дегбкаош!фжэхо!ыдвбюпъжседт!чют!бвгшчшюшаыт!ебцб\"!хоибчте!юы!баы!ъу!вгшчшюо\"!вгбцгуяяу!гуъчшютше!дыяхбюо!ау!вгбвыдаош!ы!дегбкаош!ы!бфгуфуеохуше!ыи!бечшюпаб?";
//        System.out.println(codingText(example1, 1));
        System.out.println("С ключем: " + unCodingText(example2, 0));
        Map<Integer, String> allRoots = getAllRot(example2);
        String result = getBruteforce2(example2);
        //System.out.println(result);


    }


}
