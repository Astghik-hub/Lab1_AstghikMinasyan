/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ziptestdemo;

/**
 *
 * @author Astghik Minasyan 
 * Lab_01: Review OOP 
 * 25/08/2025
 */
public class ZipCode {
    protected int Zip;

    /**
     * First constructor, encodes zip code as an integer
     *
     * @param num the number to store as the zip code
     */
    public ZipCode(int num) {
        // Pint an error message if the zip code is more than 5 digits
        if (num > 99999) {
            System.out.printf("%d zip code is more than 5 digits%n", num);
        }
        
        this.Zip = num;
    }

    /**
     * Second constructor, takes a bar code string consisting of 0’s and 1’s and
     * encodes it as an integer
     *
     * @param barCode the input bar code string
     */
    public ZipCode(String barCode) {
        boolean validCode = true;

        // Check if the bar code is more than 27 digits
        if (barCode.length() != 27) {
            System.out.println("Error: bar code must be in multiples of 5-binary digits");
            this.Zip = 0;
            return;
        }

        // Check if the bar code starts and ends in '1'
        if (barCode.charAt(0) != '1' || barCode.charAt(barCode.length() - 1) != '1') {
            System.out.println("Error: bar code missing a 1 at start or end");
            this.Zip = 0;
            return;
        }

        // Check if the bar code contains an invalid digit
        for (int i = 1; i <= 25; i++) {
            if (barCode.charAt(i) != '0' && barCode.charAt(i) != '1') {
                System.out.printf("bar code character: %c must be '0' or '1' %n", barCode.charAt(i));
                validCode = false;
            }
        }

        // Check if the bar code contains an invalide sequence
        if (validCode) {
            for (int i = 1; i <= 21; i += 5) {
                String sequence = barCode.substring(i, i + 5);
                int count1 = 0;
                int count0 = 0;

                for (int j = 0; j < 5; j++) {
                    if (sequence.charAt(j) == '1') {
                        count1++;
                    } else {
                        count0++;
                    }
                }
                if (count1 != 2 || count0 != 3) {
                    System.out.printf("%s has invalid sequence in the bar code%n", sequence);
                    validCode = false;
                }
            }
        }

        // If the bar code is valid, call ParseBarCode method
        // to convert it and store it as an integer
        if (validCode) {
            this.Zip = ParseBarCode(barCode);
        } else this.Zip = 0;
    }

    /**
     * Converts the zip code as a properly constructed string of binary digits
     *
     * @return the string of binary digits corresponding to the zip code
     */
    public String GetBarCode() {
        String zipString = String.valueOf(Zip);
        String realZip = "";
        String barCode = "1";

        // Remove the first digit if the zip code is more than 5 digits
        if (Zip > 99999) {
            realZip += zipString.substring(1);
        }

        // Add leading 0's if the zip code is less than 5 digits
        if (Zip < 10000) {
            for (int i = 1; i <= 5 - zipString.length(); i++) {
                realZip += "0";
            }
            realZip += zipString;
        } else realZip = zipString;

        // Construct the barcode string of 0's and 1's
        for (int i = 0; i < 5; i++) {
            barCode += switch (realZip.charAt(i)) {
                case '0' -> "11000";
                case '1' -> "00011";
                case '2' -> "00101";
                case '3' -> "00110";
                case '4' -> "01001";
                case '5' -> "01010";
                case '6' -> "01100";
                case '7' -> "10001";
                case '8' -> "10010";
                case '9' -> "10100";
                default -> "";
            };
        }

        return barCode += "1";
    }

    /**
     * Decodes the binary bar code string
     *
     * @param barCode the input string to convert
     * @return the bar code as an integer
     */
    private static int ParseBarCode(String barCode) {
        String value = "";

        // Convert each 5-digit sequence into numbers
        for (int i = 1; i <= 21; i += 5) {
            value += switch (barCode.substring(i, i + 5)) {
                case "11000" -> 0;
                case "00011" -> 1;
                case "00101" -> 2;
                case "00110" -> 3;
                case "01001" -> 4;
                case "01010" -> 5;
                case "01100" -> 6;
                case "10001" -> 7;
                case "10010" -> 8;
                case "10100" -> 9;
                default -> 0;
            };
        }

        return Integer.parseInt(value);
    }
}
