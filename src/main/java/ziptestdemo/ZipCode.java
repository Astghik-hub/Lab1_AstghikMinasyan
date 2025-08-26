/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ziptestdemo;

/**
 *
 * @author 2466920
 */
public class ZipCode {
    protected int Zip;

    public ZipCode(int num) {
       if (num > 99999) {
           System.out.printf("%d zip code is more than 5 digits %n", num);
           this.Zip = Integer.parseInt(String.valueOf(num).substring(1));
       } else this.Zip = num;
    }

    public ZipCode(String zipCode) {
        boolean validCode = true;
        if (zipCode.length() != 27) {
            System.out.println("Error: bar code must be in multiples of 5-binary digits");
            validCode = false;
        } 
        
        if (zipCode.charAt(0) != '1' || zipCode.charAt(zipCode.length() - 1) != '1') {
            System.out.println("Error: bar code missing a 1 at start or end");
            validCode = false;
        }
        
        for (int i = 1; i < 24; i++) {
            if (zipCode.charAt(i) != '0' || zipCode.charAt(i) != '1') {
                System.out.printf("bar code character: %c must be '0' or '1' %n", zipCode.charAt(i));
                validCode = false;
            }
        }
        
        for (int i = 1; i < 24; i += 5) {
            String sequence = zipCode.substring(i, i + 5);
            int count1 = 0;
            int count0 = 0;
            
            for (int j = 0; j < 5; j++) {
                if (sequence.charAt(i) == '1') {
                    count1++;
                } else {
                    count0++;
                }
            }
            
            if (count1 != 2 || count0 != 3) {
                System.out.printf("%s has invlalid sequence in the bar code %n", sequence);
                validCode = false;
            }
        }
        
        if (validCode) {
            this.Zip = ParseBarCode(zipCode);
        } else this.Zip = 0;
    }
    
    /**
     * converts the ZIP code as a properly constructed string of binary digits
     * @return 
     */
    public String GetBarCode() {
        String zip = "1";
        
        for(int i = 0; i < 5; i++) {
            switch(String.valueOf(Zip).charAt(i)) {
                case '0' -> zip += "11000";
                case '1' -> zip += "00011";
                case '2' -> zip += "00101";
                case '3' -> zip += "00110";
                case '4' -> zip += "01001";
                case '5' -> zip += "01010";
                case '6' -> zip += "01100";
                case '7' -> zip += "10001";
                case '8' -> zip += "10010";
                case '9' -> zip += "10100";
            }
        }
        
        return zip += "1";
    }
    
    /**
     * converts the corresponding Zip code as an integer
     * @param barCode
     * @return Zip code as an integer
     */
    private static int ParseBarCode(String barCode) {
        int num = 0;
        String reduced = barCode.substring(1, barCode.length());
        for(int i = 4; i >= 0; i--) {
            num = switch(reduced) {
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
        
        return num;
    }  
}
