package org.example.assg1;


import java.util.List;
import java.util.Scanner;

public class VigenereCipher {

    private String KEY;
    private int KEYSIZE;
    private final List<String> ALPHABET = List.of(
            "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M",
            "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"
    );
    public int ALPHABETSIZE = ALPHABET.size();

    public VigenereCipher(String KEY) {
        this.KEY = KEY;
        this.KEYSIZE = KEY.length();
    }

    private String prepareKey(String plainText) {
        int idx = 0;
        int itr = KEYSIZE;
        int n = plainText.length();
        String localKey = KEY;

        while (itr < n) {
            localKey = localKey + KEY.charAt(idx);
            idx = (idx + 1) % KEYSIZE;
            ++itr;
        }
        return localKey;
    }

    public String encrypt(String plainText) {

        String localKey = prepareKey(plainText);
        String cipherText = "";
        int ASCII;

        for (int i = 0; i < plainText.length(); ++i) {
            ASCII = (ALPHABET.indexOf(String.valueOf(plainText.charAt(i)).toUpperCase()) +
                    ALPHABET.indexOf(String.valueOf(localKey.charAt(i)).toUpperCase())) % ALPHABETSIZE;
            cipherText = cipherText + ALPHABET.get(ASCII);
        }

        return cipherText;
    }

    public String decrypt(String cipherText) {

        String localKey = prepareKey(cipherText);
        String plainText = "";
        int ASCII;

        for (int i = 0; i < cipherText.length(); ++i) {
            int cipherCharIndex = ALPHABET.indexOf(String.valueOf(cipherText.charAt(i)).toUpperCase());
            int keyCharIndex = ALPHABET.indexOf(String.valueOf(localKey.charAt(i)).toUpperCase());
            ASCII = (cipherCharIndex - keyCharIndex + ALPHABETSIZE) % ALPHABETSIZE; // Ensure non-negative index
            plainText = plainText + (ALPHABET.get(ASCII));
        }

        return plainText;
    }

    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);
        System.out.println("Enter the text you want to encrypt:");
        String input = scn.next();
        scn.close();

        // you can change the key here
        VigenereCipher Vg = new VigenereCipher("eng");
        String cipherText = Vg.encrypt(input);
        System.out.println("The encrypted text is: " + cipherText);
        String plainText = Vg.decrypt(cipherText);
        System.out.println("The decrypted text is: " + plainText);
    }
}
