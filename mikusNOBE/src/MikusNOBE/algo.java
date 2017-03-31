/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MikusNOBE;
import com.sun.org.apache.xpath.internal.operations.Bool;

import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.io.IOException;
import java.math.BigInteger;


public class algo {
    
    //retrieves byte array from input file
    public static byte[] getBytes(String path) throws IOException {
        Path filePath = Paths.get(path);
        byte[] fileBytes = Files.readAllBytes(filePath);
        return fileBytes;
    }
    
    //pad string to 8 chars with 0s on left
    public static String padZeros(String inBinString) {
        while(inBinString.length()<8) {
            inBinString = "0"+inBinString;
        }
        return inBinString;
    }
   
    //reverses a string
    public static String revString(String inString) {
        StringBuilder outString = new StringBuilder(inString);
        return outString.reverse().toString();
    }
    
    //creates large binary string from byte array, param to specify number of bytes
    //TODO overload method to take entire file to convert to one string
    //TODO refactor method to give String[] of binary based on num of bytes
    public static String bytesToBigBinString(byte[] inBytes, int numOfBytes) {
        String bigBinString = "";
        for (int i=0; i<numOfBytes; i++) {
            int byteAsInt = inBytes[i]&0xff;
            String binString = padZeros(Integer.toBinaryString(byteAsInt));
            bigBinString= bigBinString + binString;
        }
        return bigBinString;
    }
	
    //reverse of previous
    //TODO overload to accept input of String[]
    public static byte[] bigBinStringToBytes(String bigBinString) {
        String tempStr = "";
        byte[] outputBytes = new byte[bigBinString.length()/8];
        for (int i=0; i<bigBinString.length()/8; i++) {
            outputBytes[i] = (byte)Integer.parseInt(bigBinString.substring(i*8, i*8+8),2);
        }
        return outputBytes;
    }
    
    //takes any binary string and converts to int, reads from left to right
    public static int binToInt(String bigBinString) {
        char[] binAsChar = bigBinString.toCharArray();
        int sum = 0;
        for (int i=0; i<binAsChar.length; i++) {
            if(binAsChar[i]=='1') {
                sum+=Math.pow(2, i);
            }
        }
        return sum;
    }
    
    //overloaded method to indicate direction, use 'r' to start from right
    public static long binToLong(String bigBinString, char direction) {
        if (direction == 'r') {
                bigBinString = revString(bigBinString);
        }
        char[] binAsChar = bigBinString.toCharArray();
        long sum = 0;
        for (int i=0; i<binAsChar.length; i++) {
            if(binAsChar[i]=='1') {
                sum+=Math.pow(2, i);
            }
        }
        return sum;
    }

    //converts bin string to as close as possible (pad to 19 digits long) and concatenate to shorten overall chars used
    //might take too long
    public static String binToFakeLong(String bigBinString) {
        char[] binAsChar = bigBinString.toCharArray();
        long sum = 0;
        long temp;
        String fakeLong = "";
        long maxLong = Long.MAX_VALUE;
        
        for (int i=0, j=0; i<binAsChar.length; i++, j++) {
            temp = sum;
            if(binAsChar[i]=='1') {
                sum+=Math.pow(2, j);
            }
            if (sum == maxLong) {
                String longAsString = Long.toString(temp);
                while (longAsString.length()<19) {
                    longAsString = "0"+longAsString;
                }
                fakeLong += longAsString;
                sum = 0;
                j=-1;
            }
        }
        return fakeLong;
    }
    
	//TODO method to convert from fake long bake to binary string

    
    //max possible int value if all bytes are 1
    //TODO accept bigint as param
    public static long getMax(int numOfBytes) {
        String ones = "";
        for(int i=0; i<numOfBytes*8; i++) {
            ones += "1";
        }
        return binToInt(ones);
    }
    
    public static BigInteger bigIntPow(int base, int exp) {
        BigInteger bigInt = new BigInteger(Integer.toString(base));
        return bigInt.pow(exp);
    }
    
    public static int getBigIntLen(BigInteger bigInt) {
        return bigInt.toString().length();
    }

    //NOW DEFUNCT DO NOT USE
    public static OtherNobe otherNobe(BigInteger bigInt) {
        //initializations
        ArrayList<Integer> hashOut = new ArrayList<>();
        String i = "1"; //concatenate to form expansion 2^0b00000001 + 2^0b00000011 + 2^0bN
        int count=0;
        BigInteger temp = new BigInteger("0"); //used to save after accumulator goes over
        BigInteger accumulator = new BigInteger("1"); //remember when reversing
        while(accumulator.compareTo(bigInt) < 0) { //compare to big int, exit when accumulator goes over
            temp = accumulator;
            accumulator = accumulator.add(bigIntPow(2,binToInt(i)));
            hashOut.add(binToInt(i));
            i+="1";
            count++;
        }
        BigInteger remainder = bigInt.subtract(temp);
        /*
        Significant digits are the leftmost values of big integer.
        These expansion either vastly overshoots them or severely goes under them.
        These get cut off into a string for storage to be concatenated later.
        */
        String sigDigits = getSigDigits(bigInt, remainder);
        int lenSigDigits = sigDigits.length();
        remainder = new BigInteger(remainder.toString().substring(lenSigDigits));
        OtherNobe output = new OtherNobe(hashOut, remainder, sigDigits, count);
        
        return output;
    }
    
    /*
    Retrieves the digits which the power series cannot approach
    They will be stored in a separate hash as strings to reconcatenate later
    */
    public static String getSigDigits(BigInteger bigInt1, BigInteger bigInt2) {
        //init output string
        String outputString = "";
        //convert the two bigints to compare to an array of characters to compare individually
        char[] bigIntAsCharArray1 = bigInt1.toString().toCharArray();
        char[] bigIntAsCharArray2 = bigInt2.toString().toCharArray();
        
        //iterate through each list, look for first difference and then break
        for(int i=0; i<bigIntAsCharArray1.length-1; i++) {
            if (bigIntAsCharArray1[i] != bigIntAsCharArray2[i]) {
                break;
            }
            outputString += bigIntAsCharArray1[i];
        }
        //keep the last value before breaking to ensure a positive value
        outputString = outputString.substring(0, outputString.length());
        return outputString;
    }
    
    public static ArrayList otherNobeRecur(BigInteger bigInt) {
        BigInteger temp = bigInt;
        BigInteger one = new BigInteger("1"); //literally the number 1 as an integer
        ArrayList<Integer> hashOut = new ArrayList<>();
        for (int i=0; i<100; i++) {
            OtherNobe data = otherNobe(temp);
            temp = data.getRemainder();
            if (data.getRemainder().equals(one)) {
                break;
            }
            hashOut.addAll(data.getHash());
            System.out.println("Pass number: " + i);
        }
        return hashOut;
    }
    
    public static ArrayList downNobe(BigInteger bigInt) {
        OtherNobe initData = otherNobe(bigInt); //use otherNobe or upNobe to get approximation for starting point
        bigInt = initData.getRemainder();
        BigInteger threshold = new BigInteger("256"); //value to stop subtracting at
        ArrayList<Integer> hashOut = new ArrayList<>();
        System.out.println(bigInt.toString());
        System.out.println(bigInt.toString().length());
        
        //number of passes is the highest exponent reached
        String binPowerString = "111";
        for (int i=0; i<initData.getNumOfPasses(); i++) {
            binPowerString += "1";
        }
//        while(bigInt.compareTo(threshold) > 0) {
        for (int j=0; j<25; j++)
        {
            for (int i = 0; i<initData.getNumOfPasses(); i++) {
                int power = binToInt(binPowerString.substring(i)); //exponent gets a 1 removed from binary string each pass
                for (int base = 9; base>1; base--) {
                    BigInteger tryValue = bigIntPow(base, power);
                    if (tryValue.compareTo(bigInt) < 0) { //see if try value is less than big int
                        //add values to hash, subtract from bigint and reset base
                        hashOut.add(base);
                        hashOut.add(power);
                        bigInt = bigInt.subtract(tryValue);
                    }
                }
 //           } 
            }
        }
        System.out.println(bigInt.toString());
        System.out.println(bigInt.toString().length());
        return hashOut;
    }

    public static Encoder encode(BigInteger bigInt) {
        System.out.println(bigInt.toString().length());
        BigInteger beforeSigDigitsCut = bigInt;
        String exponent;
        BigInteger holdValue = BigInteger.ZERO;
        BigInteger Threshold = new BigInteger("1024");
        ArrayList sigDigits = new ArrayList<>();
        ArrayList bases = new ArrayList<>();
        ArrayList exponents = new ArrayList<>();

        //outer loop
        //subtract from big int input to get below threshold
        while(bigInt.compareTo(Threshold) > 0) {
            //iterate through 2-255 as bases
            for (int base=2; base < 128; base++) {
                //break from for loop after meeting threshold
                if (bigInt.compareTo(Threshold) < 0) {
                    break;
                }
                //reset exp before entering while loop
                exponent = "1";
                //loop subtracts base to power until becoming negative and goes back to the previous non-negative value
                while (true) {
                    holdValue = bigInt; //temp value if subtraction turns big int negative
                    bigInt = bigInt.subtract(bigIntPow(base, binToInt(exponent)));
                    //TODO: don't add these when bigInt is going to be reset
                    bases.add(base);
                    exponents.add(exponent);
                    if (bigInt.compareTo(BigInteger.ZERO) < 0) {
                        bigInt = holdValue;
                        break;
                    }
                    exponent += "1";
                }
                //comment this section for only each 2-256 pass
//                String sigDigitsString = getSigDigits(bigInt, beforeSigDigitsCut);
//                sigDigits.add(sigDigitsString);
//                int sigDigitsLen = getSigDigits(bigInt, beforeSigDigitsCut).length();
//
//                System.out.println("sig digits: " + sigDigitsLen);
//
//                bigInt = new BigInteger(bigInt.toString().substring(sigDigitsLen));
//                beforeSigDigitsCut = bigInt;
//
//                System.out.println("length: " + bigInt.toString().length());
//                System.out.println("big int: " + bigInt);
            }
            //comment this for pass in each base
            String sigDigitsString = getSigDigits(bigInt, beforeSigDigitsCut);
            sigDigits.add(sigDigitsString);
            int sigDigitsLen = getSigDigits(bigInt, beforeSigDigitsCut).length();

            System.out.println("sig digits: " + sigDigitsLen);

            bigInt = new BigInteger(bigInt.toString().substring(sigDigitsLen));
            beforeSigDigitsCut = bigInt;

            System.out.println("length: " + bigInt.toString().length());
            System.out.println("big int: " + bigInt);
        }
        Encoder enc = new Encoder(sigDigits, bases, exponents);
        return enc;
    }

    public static int[] prepareEncoderForFileOutput(Encoder enc) {
        int count=0;
        ArrayList<Integer> bases = enc.getBases();
        int startingBase = (bases.size()!=0) ? bases.get(0) : 2;
        int finalBase = bases.get(bases.size()-1);
        int[] output = new int[finalBase-1];

        for (int i=startingBase; i<finalBase+1; i++) {
            for (int j=0; j<bases.size(); j++) {
                if(i==bases.get(j)) {count++;}
            }
            output[i-2]=count;
            count=0;
        }

        return output;
    }

    public static void writeToFile(Encoder enc, int[] formattedNobeValues) {
        try {
            PrintWriter pw = new PrintWriter("outputHash.txt", "UTF-8");
            String outputString = "";

            for (int eachBaseCount : formattedNobeValues) {
                outputString += eachBaseCount+",";
            }
            outputString = outputString.substring(0, outputString.length()-1);

            outputString += ":";

            for (int i = 0; i < enc.getSignificantDigits().size(); i++) {
                outputString += enc.getSignificantDigits().get(i)+",";
            }
            outputString = outputString.substring(0, outputString.length()-1);

            pw.write(outputString);
            pw.close();

        } catch (IOException e) {
            System.out.println("Error writing to file");
        }
    }

    public static void fileWrite(String s, String name) {
        try {
            PrintWriter pw = new PrintWriter(name, "ASCII");
            pw.write(s);
            pw.close();
        } catch (IOException e) {
            System.out.println("Error writing to file");
        }
    }

    public static void divisorEncode(BigInteger bigInt) {
        int count = 0;
        BigInteger two = new BigInteger("2");

        while (true) {
            if (isBigIntEven(bigInt)) {
                bigInt = bigInt.divide(two);
                System.out.println("Length of big int: " + bigInt.toString().length());
                count++;
                continue;
            }

            if (isLastDivisorItself(bigInt)) {
                break;
            }

            bigInt = bigInt.divide(findNextDivisor(bigInt));
            count++;
            System.out.println("Length of big int: " + bigInt.toString().length());

            if (count > 100000) {
                break;
            }
        }

        System.out.println(count);
    }

    public static BigInteger findNextDivisor(BigInteger bigInt) {
        BigInteger testDivisor = new BigInteger("3");
        while (true) {
            if (isDivisor(bigInt, testDivisor)) {
                return testDivisor;
            }

            addOneToBigInt(bigInt);
        }
    }

    public static boolean isBigIntEven(BigInteger bigInt) {
        BigInteger two = new BigInteger("2");
        if (isDivisor(bigInt, two)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isDivisor(BigInteger bigInt, BigInteger divisor) {
        if (bigInt.remainder(divisor).compareTo(BigInteger.ZERO) == 0) {
            return true;
        } else {
            return false;
        }
    }

    public static BigInteger addOneToBigInt(BigInteger bigInt) {
        bigInt = bigInt.add(BigInteger.ONE);
        return bigInt;
    }

    public static boolean isLastDivisorItself(BigInteger bigInt) {
        if (findNextDivisor(bigInt).compareTo(bigInt) == 0) {
            return true;
        } else {
            return false;
        }
    }

}
