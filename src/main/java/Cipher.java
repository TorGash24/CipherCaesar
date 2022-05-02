import ceasarCipherException.MyFileEmpty;
import ceasarCipherException.MyFileNotFoundException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Cipher {
    public static final List<Character> ALPHABET = getAlphabet(32);



    public static String cipherString2(List<Character> alphabet, String str, int key) {
        StringBuilder result = new StringBuilder();
        int position = 0;
        int newPosition = 0;
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            position = alphabet.indexOf(str.toLowerCase().charAt(i));
            if (c == '.' || c == ',' || c == '!' || c == '?' || c == '"' || c == ':' || c == '-' || c == ' ') {
                int symbol = ((position + key + 8) % 8) + 32;
                result.append(alphabet.get(symbol).toString());
            }

            if (Character.isLetter(c)) {
                newPosition = (32 + (position + key)) % 32;
                String newC = alphabet.get(newPosition).toString();
                if (Character.isUpperCase(c)) {
                    result.append(newC.toUpperCase());
                } else {
                    result.append(newC);
                }
            }
        }
        return result.toString();
    }


    public static List<Character> getAlphabet(int countLetters) {
        List<Character> listAlphabet = new ArrayList<>();

        for (int i = 0; i < countLetters; i++) {
            listAlphabet.add((char) (1072 + i));
        }

        listAlphabet.add('.');
        listAlphabet.add(',');
        listAlphabet.add('!');
        listAlphabet.add('?');
        listAlphabet.add(' ');
        listAlphabet.add('"');
        listAlphabet.add(':');
        listAlphabet.add('-');

        return listAlphabet;
    }


    public static Map<Integer, String> allCipherWords(String str) {
        Map<Integer, String> result = new HashMap<>();

        for (int i = 0; i <= 31; i++) {
            String rot = cipherString2(ALPHABET, str, i);

            result.put(i, rot);
        }
//        for (int i = 0; i < result.size(); i++) {
//            System.out.println(result.get(i));
//        }


        return result;
    }

    public static String getBruteforce(Map<Integer, String> example) {
        Map<Integer, String> result = new HashMap<>();
        StringBuilder resultString = new StringBuilder();
        int max = 0;
        for (int i = 0; i < example.size(); i++) {
            Map<Character, Integer> map = new HashMap<>();
            for (int j = 0; j < example.get(i).length(); j++) {
                Character ch = example.get(i).charAt(j);
                Integer value = map.get(ch);
                if (value == null) {
                    map.put(ch, 1);
                } else {
                    map.put(ch, value + 1);
                }


            }
            if (map.containsKey(' ') && map.get(' ') > max) {
                max = map.get(' ');
                resultString.delete(0, resultString.length());
                resultString.append(example.get(i));
            }


        }
        System.out.println("–¿—ÿ»‘–Œ¬€¬¿≈Ã " + resultString);


        return resultString.toString();
    }

    public static String getBruteforce2(Map<Integer, String> example) {
        List<Character> vowels = List.of('‡', 'Â', 'Ë', 'Ó', 'Û', '˚', '˛', 'ˇ');
        Map<Integer, String> result = new HashMap<>();
        StringBuilder resultString = new StringBuilder();
        int count = 0;
        int max = 0;
        for (int i = 0; i < example.size(); i++) {
            String str = example.get(i);
            int space = 0, spaceAndDot = 0, commaAndSpace = 0, question = 0, endDot = 0;
            for (int j = 0; j < str.length() - 1; j++) {
                Character c = str.charAt(j);
                if (Character.isSpaceChar(c)) {
                    space++;
                }
                if (Character.isSpaceChar(c) && Character.isUpperCase(str.charAt(j + 1))) {
                    space++;
                }
                if (!(vowels.contains(c) && vowels.contains(str.charAt(j + 1)))) {
                    space++;
                }

            }
            Character first = str.charAt(0);
            if (Character.isUpperCase(first)) {
                space++;
            }
            int indexDot = str.indexOf(". ");
            while (indexDot != -1) {
                spaceAndDot++;
                indexDot = str.indexOf(". ", indexDot + 1);
            }
            int indexCom = str.indexOf(", ");
            while (indexCom != -1) {
                commaAndSpace++;
                indexCom = str.indexOf(", ", indexCom + 1);
            }

            int indexQuestion = str.indexOf("? ");
            while (indexQuestion != -1) {
                question++;
                indexQuestion = str.indexOf("? ", indexQuestion + 1);
            }

            int indexExclamatory = str.indexOf("! ");
            while (indexExclamatory != -1) {
                question++;
                indexExclamatory = str.indexOf("! ", indexExclamatory + 1);
            }

            int indexDash = str.indexOf(" - ");
            while (indexDash != -1) {
                question++;
                indexDash = str.indexOf(" - ", indexDash + 1);
            }

            if (str.endsWith("?") || str.endsWith("!")) {
                question++;
            }
            if (str.endsWith(".")) {
                endDot++;
            }
            max = space + spaceAndDot + commaAndSpace + question + endDot;
            if (max > count) {
                count = max;
                resultString.delete(0, resultString.length());
                resultString.append(example.get(i));
            }
        }

//        for (int i = 0; i < example.size(); i++) {
//            Map<Character, Integer> map = new HashMap<>();
//            for (int j = 0; j < example.get(i).length(); j++) {
//                Character ch = example.get(i).charAt(j);
//                Integer value = map.get(ch);
//                if (value == null) {
//                    map.put(ch, 1);
//                } else {
//                    map.put(ch, value + 1);
//                }
//
//
//            }
//            if (map.containsKey(' ') && map.get(' ') > max ) {
//                max = map.get(' ');
//                resultString.delete(0, resultString.length());
//                resultString.append(example.get(i));
//            }
//
//
//
//        }
        System.out.println("–¿—ÿ»‘–Œ¬€¬¿≈Ã " + resultString);


        return resultString.toString();
    }

    public static double[] analystWords(String textAnalysis) {
        Map<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < textAnalysis.length(); i++) {
            Character ch = textAnalysis.charAt(i);
            Integer value = map.get(ch);
            if (value == null) {
                map.put(ch, 1);
            } else {
                map.put(ch, value + 1);
            }
        }
        double[] result = new double[textAnalysis.length()];

        int i = 0;
        for (Integer count : map.values()) {
            double value = count * 100.0 / textAnalysis.length();
            result[i++] = value;
        }

        System.out.println(Arrays.toString(result));
        return result;
    }
}
