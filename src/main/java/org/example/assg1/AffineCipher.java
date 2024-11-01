package org.example.assg1;

// E ( x ) = ( a x + b ) mod m
// D ( x ) = a^-1 ( x - b ) mod m

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AffineCipher {
    private final List<String> ALPHABET = List.of(
            "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M",
            "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"
    );
    public int ALPHABETSIZE = ALPHABET.size();
    int a;
    int b;

    public AffineCipher() {
        this.a = generateRandomRelativelyPrime();   // a is relatively prime to m
        this.b =  (int) (Math.random() * ALPHABETSIZE);
    }

    // I could manually get them but I used this method just in case I change the alphabet size in the future
    private int generateRandomRelativelyPrime() {

        List<Integer> numbers = new ArrayList<>();

        for (int i = 0; i < ALPHABETSIZE; ++i) {
            if (GCD(i, ALPHABETSIZE) == 1) {
                numbers.add(i);
            }
        }

//        for(int i = 0;i<numbers.size();++i){
//            System.out.println(numbers.get(i));
//        }

        int randomIdx = (int) (Math.random() * numbers.size());
        return numbers.get(randomIdx);
    }

    private int GCD(int a, int b) {

        int gcd = 1;
        for (int i = 2; i < Math.min(a, b) + 1; ++i) {
            if (a % i == 0 && b % i == 0) {
                gcd = i;
            }
        }
        return gcd;
    }

    private int getModularInverse() {
        int a = this.a % ALPHABETSIZE;
        for (int x = 1; x < ALPHABETSIZE; x++) {
            if ((a * x) % ALPHABETSIZE == 1) {
                return x;
            }
        }
        return -1;
    }

    public String encrypt(String plainText) {
        int n = plainText.length();
        int charNumber;
        String cipherText = "";

        for (int i = 0; i < n; ++i) {
            charNumber = ALPHABET.indexOf(String.valueOf(plainText.charAt(i)).toUpperCase());
            cipherText = cipherText + ALPHABET.get(((charNumber * this.a + this.b) % ALPHABETSIZE));
        }

        return cipherText;
    }

    public String decrypt(String cipherText) {
        int n = cipherText.length();
        int charNumber;
        String plainText = "";

        int inverseOfa = getModularInverse();
        if (inverseOfa == -1) {
            throw new IllegalArgumentException("No modular inverse found for a");
        }


        for (int i = 0; i < n; ++i) {
            charNumber = ALPHABET.indexOf(String.valueOf(cipherText.charAt(i)).toUpperCase());
            plainText = plainText + ALPHABET.get(((inverseOfa * (charNumber - this.b)) % ALPHABETSIZE + ALPHABETSIZE) % ALPHABETSIZE);
        }

        return plainText;
    }

    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);
        System.out.println("Enter the text you want to encrypt:");
        String input = scn.next();
        scn.close();

        AffineCipher Af = new AffineCipher();
        String cipherText = Af.encrypt(input);
        System.out.println("The encrypted text is: " + cipherText);
        String plainText = Af.decrypt(cipherText);
        System.out.println("The decrypted text is: " + plainText);


    }
}
