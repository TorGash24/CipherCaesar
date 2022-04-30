import ceasarCipherException.CharValidException;

import java.util.List;

public class Cipher1 {
    private static final double[] ENGLISH_RUSSIAN_PROBABILITIES = {0.073, 0.009, 0.030, 0.044, 0.130, 0.028, 0.016, 0.035, 0.074,
            0.002, 0.003, 0.035, 0.025, 0.078, 0.074, 0.027, 0.003,
            0.077, 0.063, 0.093, 0.027, 0.013, 0.016, 0.005, 0.019, 0.001};

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
        List<Character> symbols = List.of('.', ',', '!', '?', '"', ':', '-', ' ');
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
            word = symbols.get((symbols.indexOf(word) + key) % symbols.size());
        }
        return word;
    }

    public static boolean isCheckValidChar(char[] array) {
        for (int i = 0; i < array.length; i++) {
            char symbol = array[i];
            if (!(Character.isLetter(symbol)||symbol == '.' || symbol == ',' || symbol == '!' || symbol == '?' || symbol == '"' || symbol == ':' || symbol == '-' || symbol == ' ')) {
                throw new CharValidException("������������ ������ ��� ��������� " + "\"" + symbol + "\"," + " ������� �������: " + i);
            }
        }
        return true;
    }

    public static double[] expectedLettersFrequencies(String text ) {
        double[] expectedLetters = new double[ENGLISH_RUSSIAN_PROBABILITIES.length];
        for (int i = 0; i < ENGLISH_RUSSIAN_PROBABILITIES.length; i++) {
            expectedLetters[i] = ENGLISH_RUSSIAN_PROBABILITIES[i] * text.length();
        }
        return expectedLetters;
    }

    public static void main(String[] args) {
        String example1 = "�, ����������, ��� ����� \"����������\" � \"��������\" ����? ��, � ������� ������ �� ��������, ��� ���: �� ����� ���������� ���� � ���� �����, ��� ��������� � ������� ���. ��� ����� �� ������� ����� \"����������� �����\", ������� ����� ���������� ��� �������� ����. �� ��� ��� ������ �� � ���������, � � \"���������������\" ������?";
        String example2 = "�!.����������!.���.�����.:����������:.�.:��������:.����\".��!.�.�������.������.��.��������!.���.���-.��.�����.����������.����.�.����.�����!.���.���������.�.�������.���,.���.�����.��.�������.�����.:�����������.�����:!.�������.�����.����������.���.��������.����,.��.���.���.������.��.�.���������!.�.�.:���������������:.������\"";
        System.out.println(codingText(example1, 1));
        System.out.println(unCodingText(example2, 1));

    }


}
