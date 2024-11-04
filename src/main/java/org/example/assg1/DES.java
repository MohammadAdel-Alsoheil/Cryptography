package org.example.assg1;

import java.util.*;

public class DES {
    private int KEYSIZE = 64; // in bits
    private String KEY;
    private String LEFTKEY = "";
    private String RIGHTKEY = "";
    private int SUBKEYSIZE = 28;
    private int NUMBEROFROUNDS = 16;
    private String[] SUBKEYS = new String[NUMBEROFROUNDS];
    private List<Integer> LEFTPC1 = Arrays.asList(
            57, 49, 41, 33, 25, 17, 9,
            1, 58, 50, 42, 34, 26, 18,
            10, 2, 59, 51, 43, 35, 27,
            19, 11, 3, 60, 52, 44, 36
    );
    private List<Integer> RIGHTPC1 = Arrays.asList(
            63, 55, 47, 39, 31, 23, 15,
            7, 62, 54, 46, 38, 30, 22,
            14, 6, 61, 53, 45, 37, 29,
            21, 13, 5, 28, 20, 12, 4
    );
    private List<Integer> PC2 = Arrays.asList(
            14, 17, 11, 24, 1, 5,
            3, 28, 15, 6, 21, 10,
            23, 19, 12, 4, 26, 8,
            16, 7, 27, 20, 13, 2,
            41, 52, 31, 37, 47, 55,
            30, 40, 51, 45, 33, 48,
            44, 49, 39, 56, 34, 53,
            46, 42, 50, 36, 29, 32
    );
    private List<Integer> IP = Arrays.asList(
            58, 50, 42, 34, 26, 18, 10, 2,
            60, 52, 44, 36, 28, 20, 12, 4,
            62, 54, 46, 38, 30, 22, 14, 6,
            64, 56, 48, 40, 32, 24, 16, 8,
            57, 49, 41, 33, 25, 17, 9, 1,
            59, 51, 43, 35, 27, 19, 11, 3,
            61, 53, 45, 37, 29, 21, 13, 5,
            63, 55, 47, 39, 31, 23, 15, 7
    );
    List<Integer> IPINVERSE = Arrays.asList(
            40, 8, 48, 16, 56, 24, 64, 32,
            39, 7, 47, 15, 55, 23, 63, 31,
            38, 6, 46, 14, 54, 22, 62, 30,
            37, 5, 45, 13, 53, 21, 61, 29,
            36, 4, 44, 12, 52, 20, 60, 28,
            35, 3, 43, 11, 51, 19, 59, 27,
            34, 2, 42, 10, 50, 18, 58, 26,
            33, 1, 41, 9, 49, 17, 57, 25
    );
    List<Integer> EXPANSION = Arrays.asList(
            32, 1, 2, 3, 4, 5,
            4, 5, 6, 7, 8, 9,
            8, 9, 10, 11, 12, 13,
            12, 13, 14, 15, 16, 17,
            16, 17, 18, 19, 20, 21,
            20, 21, 22, 23, 24, 25,
            24, 25, 26, 27, 28, 29,
            28, 29, 30, 31, 32, 1
    );
    List<List<List<Integer>>> sboxes = Arrays.asList(
            Arrays.asList(
                    Arrays.asList(14, 4, 13, 1, 2, 15, 11, 8, 3, 10, 6, 12, 5, 9, 0, 7),
                    Arrays.asList(0, 15, 7, 4, 14, 2, 13, 1, 10, 6, 12, 11, 9, 5, 3, 8),
                    Arrays.asList(4, 1, 14, 8, 13, 6, 2, 11, 15, 12, 9, 7, 3, 10, 5, 0),
                    Arrays.asList(15, 12, 8, 2, 4, 9, 1, 7, 5, 11, 3, 14, 10, 0, 6, 13)
            ),
            Arrays.asList(
                    Arrays.asList(15, 1, 8, 14, 6, 11, 3, 4, 9, 7, 2, 13, 12, 0, 5, 10),
                    Arrays.asList(3, 13, 4, 7, 15, 2, 8, 14, 12, 0, 1, 10, 6, 9, 11, 5),
                    Arrays.asList(0, 14, 7, 11, 10, 4, 13, 1, 5, 8, 12, 6, 9, 3, 2, 15),
                    Arrays.asList(13, 8, 10, 1, 3, 15, 4, 2, 11, 6, 7, 12, 0, 5, 14, 9)
            ),
            Arrays.asList(
                    Arrays.asList(10, 0, 9, 14, 6, 3, 15, 5, 1, 13, 12, 7, 11, 4, 2, 8),
                    Arrays.asList(13, 7, 0, 9, 3, 4, 6, 10, 2, 8, 5, 14, 12, 11, 15, 1),
                    Arrays.asList(13, 6, 4, 9, 8, 15, 3, 0, 11, 1, 2, 12, 5, 10, 14, 7),
                    Arrays.asList(1, 10, 13, 0, 6, 9, 8, 7, 4, 15, 14, 3, 11, 5, 2, 12)
            ),
            Arrays.asList(
                    Arrays.asList(7, 13, 14, 3, 0, 6, 9, 10, 1, 2, 8, 5, 11, 12, 4, 15),
                    Arrays.asList(13, 8, 11, 5, 6, 15, 0, 3, 4, 7, 2, 12, 1, 10, 14, 9),
                    Arrays.asList(10, 6, 9, 0, 12, 11, 7, 13, 15, 1, 3, 14, 5, 2, 8, 4),
                    Arrays.asList(3, 15, 0, 6, 10, 1, 13, 8, 9, 4, 5, 11, 12, 7, 2, 14)
            ),
            Arrays.asList(
                    Arrays.asList(2, 12, 4, 1, 7, 10, 11, 6, 8, 5, 3, 15, 13, 0, 14, 9),
                    Arrays.asList(14, 11, 2, 12, 4, 7, 13, 1, 5, 0, 15, 10, 3, 9, 8, 6),
                    Arrays.asList(4, 2, 1, 11, 10, 13, 7, 8, 15, 9, 12, 5, 6, 3, 0, 14),
                    Arrays.asList(11, 8, 12, 7, 1, 14, 2, 13, 6, 15, 0, 9, 10, 4, 5, 3)
            ),
            Arrays.asList(
                    Arrays.asList(12, 1, 10, 15, 9, 2, 6, 8, 0, 13, 3, 4, 14, 7, 5, 11),
                    Arrays.asList(10, 15, 4, 2, 7, 12, 9, 5, 6, 1, 13, 14, 0, 11, 3, 8),
                    Arrays.asList(9, 14, 15, 5, 2, 8, 12, 3, 7, 0, 4, 10, 1, 13, 11, 6),
                    Arrays.asList(4, 3, 2, 12, 9, 5, 15, 10, 11, 14, 1, 7, 6, 0, 8, 13)
            ),
            Arrays.asList(
                    Arrays.asList(4, 11, 2, 14, 15, 0, 8, 13, 3, 12, 9, 7, 5, 10, 6, 1),
                    Arrays.asList(13, 0, 11, 7, 4, 9, 1, 10, 14, 3, 5, 12, 2, 15, 8, 6),
                    Arrays.asList(1, 4, 11, 13, 12, 3, 7, 14, 10, 15, 6, 8, 0, 5, 9, 2),
                    Arrays.asList(6, 11, 13, 8, 1, 4, 10, 7, 9, 5, 0, 15, 14, 2, 3, 12)
            ),
            Arrays.asList(
                    Arrays.asList(13, 2, 8, 4, 6, 15, 11, 1, 10, 9, 3, 14, 5, 0, 12, 7),
                    Arrays.asList(1, 15, 13, 8, 10, 3, 7, 4, 12, 5, 6, 11, 0, 14, 9, 2),
                    Arrays.asList(7, 11, 4, 1, 9, 12, 14, 2, 0, 6, 10, 13, 15, 3, 5, 8),
                    Arrays.asList(2, 1, 14, 7, 4, 10, 8, 13, 15, 12, 9, 0, 3, 5, 6, 11)
            )
    );

    List<Integer> P = Arrays.asList(
            16, 7, 20, 21, 29, 12, 28, 17,
            1, 15, 23, 26, 5, 18, 31, 10,
            2, 8, 24, 14, 32, 27, 3, 9,
            19, 13, 30, 6, 22, 11, 4, 25
    );


    public DES() {
        this.KEY = generateKey();
        permutedChoiceOne();
        //System.out.println(KEY);

    }

    public DES(String KEY) {
        this.KEY = KEY;
        permutedChoiceOne();
    }

    private String generateKey() {
        String key = "";
        Random random = new Random();

        for (int i = 0; i < KEYSIZE; ++i) {
            key = key + (random.nextInt(2));
        }
        return key;
    }

    private String InitialPermutation(String plainText) {
        StringBuilder permutedText = new StringBuilder();
        for (int pos : IP) {
            permutedText.append(plainText.charAt(pos - 1));
        }
        return permutedText.toString();
    }

    private String InverseInitialPermutation(String plainText) {
        StringBuilder permutedText = new StringBuilder();
        for (int pos : IPINVERSE) {
            permutedText.append(plainText.charAt(pos - 1));
        }
        return permutedText.toString();
    }

    private String expand(String text) {
        StringBuilder expandedText = new StringBuilder();
        for (int pos : EXPANSION) {
            expandedText.append(text.charAt(pos - 1));
        }
        return expandedText.toString();
    }

    private String XORFunction(String A, String B, int n) {
        StringBuilder afterXOR = new StringBuilder();

        for (int i = 0; i < n; ++i) {
            int leftBit = Character.getNumericValue(A.charAt(i));
            int rightBit = Character.getNumericValue(B.charAt(i));

            int value = leftBit ^ rightBit;
            afterXOR.append((char) (value + '0'));

        }

        return afterXOR.toString();
    }

    private String roundFunction(String leftText, String rightText, String subKey, int roundNumber) {
        String newLeftText = new String(rightText);

        rightText = expand(rightText);
        String afterXOR = XORFunction(rightText, subKey, 48);

        StringBuilder sBoxOutput = new StringBuilder();
        for (int i = 0; i < 48; i += 6) {
            int row = Integer.parseInt("" + afterXOR.charAt(i) + afterXOR.charAt(i + 5), 2);
            int column = Integer.parseInt(afterXOR.substring(i + 1, i + 5), 2);

            int sBoxValue = sboxes.get(i / 6).get(row).get(column);
            String binaryOutput = String.format("%4s", Integer.toBinaryString(sBoxValue)).replace(' ', '0');
            sBoxOutput.append(binaryOutput);
        }

        //permute
        StringBuilder afterP = new StringBuilder();
        for (int pos : P) {
            afterP.append(sBoxOutput.charAt(pos - 1));
        }

        String finalOut = XORFunction(afterP.toString(), leftText, 32);
        //left == finalOut


        return newLeftText + finalOut;

    }

    public String encrypt(String plainText) {

        String binaryRep = toBinary(plainText);
        binaryRep = InitialPermutation(binaryRep);

        String subKey = getRoundKey(1);
        String cipherText = roundFunction(binaryRep.substring(0, 32), binaryRep.substring(32, 64), subKey, 1);
        for (int roundNumber = 2; roundNumber <= NUMBEROFROUNDS; roundNumber++) {
            subKey = getRoundKey(roundNumber);
            cipherText = roundFunction(cipherText.substring(0, 32), cipherText.substring(32, 64), subKey, roundNumber);
        }

        cipherText = cipherText.substring(32, 64) + cipherText.substring(0, 32);

        return binaryToHex(InverseInitialPermutation(cipherText));
    }

    public String decrypt(String cipherText) {
        String binaryRep = hexToBinary(cipherText);

        binaryRep = InitialPermutation(binaryRep);

        String subKey = SUBKEYS[15];
        String plainText = roundFunction(binaryRep.substring(0, 32), binaryRep.substring(32, 64), subKey, 16);
        for (int roundNumber = NUMBEROFROUNDS - 1; roundNumber >= 1; roundNumber--) {
            subKey = SUBKEYS[roundNumber-1];
            plainText = roundFunction(plainText.substring(0, 32), plainText.substring(32, 64), subKey, roundNumber);
        }
        plainText = plainText.substring(32, 64) + plainText.substring(0, 32);
        //System.out.println(plainText);

        return fromBinary(InverseInitialPermutation(plainText));

    }


    private static String toBinary(String text) {
        StringBuilder binary = new StringBuilder();

        for (char character : text.toCharArray()) {
            String binaryChar = String.format("%8s", Integer.toBinaryString(character)).replace(' ', '0');
            binary.append(binaryChar);
        }


        if (binary.length() > 64) {
            return binary.substring(0, 64);
        } else if (binary.length() < 64) {
            while (binary.length() < 64) {
                binary.append("0");
            }
        }

        return binary.toString();
    }

    private String fromBinary(String binary) {
        StringBuilder textBuilder = new StringBuilder();

        for (int i = 0; i < binary.length(); i += 8) {
            if (i + 8 <= binary.length()) {
                String byteSegment = binary.substring(i, i + 8);
                int charCode = Integer.parseInt(byteSegment, 2);
                if (charCode != 0) {  // Skip null characters
                    textBuilder.append((char) charCode);
                }
            }
        }

        return textBuilder.toString();
    }

    private void permutedChoiceOne() {

        for (int pos : RIGHTPC1) {
            this.RIGHTKEY = this.RIGHTKEY + KEY.charAt(pos - 1);
        }
        for (int pos : LEFTPC1) {
            this.LEFTKEY = this.LEFTKEY + KEY.charAt(pos - 1);
        }
    }

    private String binaryToHex(String binary) {
        StringBuilder hexBuilder = new StringBuilder();

        for (int i = 0; i < binary.length(); i += 4) {
            String fourBits = binary.substring(i, i + 4);
            int decimalValue = Integer.parseInt(fourBits, 2);
            hexBuilder.append(Integer.toHexString(decimalValue).toUpperCase());
        }

        return hexBuilder.toString();
    }

    private String hexToBinary(String hex) {
        StringBuilder binaryBuilder = new StringBuilder();


        for (char hexChar : hex.toCharArray()) {
            int decimalValue = Integer.parseInt(String.valueOf(hexChar), 16);
            String binarySegment = String.format("%4s", Integer.toBinaryString(decimalValue)).replace(' ', '0');
            binaryBuilder.append(binarySegment);
        }

        return binaryBuilder.toString();
    }


    private void leftCircularShift(int roundNumber) {


        if (roundNumber == 1 || roundNumber == 2 || roundNumber == 9 || roundNumber == 16) {
            LEFTKEY = LEFTKEY.substring(1) + LEFTKEY.charAt(0);
            RIGHTKEY = RIGHTKEY.substring(1) + RIGHTKEY.charAt(0);
        } else {
            LEFTKEY = LEFTKEY.substring(2) + LEFTKEY.substring(0, 2);
            RIGHTKEY = RIGHTKEY.substring(2) + RIGHTKEY.substring(0, 2);
        }
    }

    private String permutedChoiceTwo(String key, int roundNumber) {
        String subkey = "";
        for (int pos : PC2) {
            subkey = subkey + key.charAt(pos - 1);
        }
        SUBKEYS[roundNumber-1] = subkey;
        return subkey;
    }

    private String getRoundKey(int roundNumber) {
        leftCircularShift(roundNumber);
        return permutedChoiceTwo(LEFTKEY+RIGHTKEY,roundNumber);
    }


    public static void main(String[] args) {
        //0000000100100011010001010110011110001001101010111100110111101111

        // make sure your input is 64 bits or less, I will address this in the future
        DES Ds = new DES();
        String text = "hello";
        String cipher = Ds.encrypt(text);
        System.out.println("The main text is: " + text);
        System.out.println("The encrypted text is: " + cipher);
        String decipher = Ds.decrypt(cipher);
        System.out.println("The decrypted text is: " + decipher);


    }
}
