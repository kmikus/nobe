/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mikusnobe;
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
    
    //takes any binary string and converts to int
    //TODO is this necessary?
    public static long binToLong(String bigBinString) {
        char[] binAsChar = bigBinString.toCharArray();
        long sum = 0;
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
    
	//creates a big integer from an input string
	//TODO bigint operations
    public static BigInteger strToBigInt(String bigString) {
        BigInteger bigInt = new BigInteger(bigString);
        return bigInt;
    }
    
	//TODO use max size to get close to bigint, then subtract and iterate through for loop
	//possibly use array and index to imitate subtraction, seperate method?
	//outer outer while loop - set threshold to arbitrary value (1 byte? = 255 int) if less, byte[] outfile
	//outer for loop - iterate through bases, start with getMax(currentVal), reset counter to 10^i
	//inner loop - try each base to get as close as possible
    public static ArrayList nobe(BigInteger bigInt) {
        //initalizations
        BigInteger temp = bigInt; //value to be subtracted from until very small
        int expCount = getBigIntLen(bigInt)+1; //start counter at 1+len(bigIntString)
        int baseCount = 10; //start at base 10 and go down
        BigInteger getClose = bigIntPow(baseCount, expCount); //initial try value
        //once getClose is smaller than temp, subtract and repeat
        ArrayList<Integer> hash = new ArrayList();
        while (temp.intValue()>256) {
            while(expCount>0) {
                //exponent loop
                //bigint compare returns 1,0,-1
                if (getClose.compareTo(temp) < 0) {
                    System.out.println("i got in");
                    hash.add(baseCount);
                    hash.add(expCount);
                    temp = temp.subtract(getClose);
                    expCount = getBigIntLen(temp)+1;
                    baseCount = 10;
                    getClose = bigIntPow(baseCount, expCount);
                    break;
                }
                baseCount--;
                while(baseCount>0) {
                    if (getClose.compareTo(temp) < 0) {
                        System.out.println("i got in");
                        hash.add(baseCount);
                        hash.add(expCount);
                        temp = temp.subtract(getClose);
                        expCount = getBigIntLen(temp)+1;
                        baseCount = 10;
                        getClose = bigIntPow(baseCount, expCount);
                        break;
                    }
                    baseCount--;
                    getClose = bigIntPow(baseCount, expCount);
                }
                expCount--;
                getClose = bigIntPow(baseCount, expCount);
                //TODO add base loop
                //add final val to array
            }
            if (temp.intValue()<256)
                break;
        }
        return hash;
    }
    
//    public static BigInteger bruteSubtract(BigInteger bigInt, int length) {
//        BigInteger bigMax = new BigInteger(getMax(length));
//        return bigMax.subtract(bigInt);
//    }
    
	//converts bin string to as close as possible (pad to 19 digits long) and concatenate to shorten overall chars used
	//might take too long
    public static String binToFakeLong(String bigBinString) {
        char[] binAsChar = bigBinString.toCharArray();
        long sum = 0;
        long temp = 0;
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
//    public static String fakeLongtoBin(String fakeLong) {
//        String binString = "";
//        for (int i=0; i<fakeLong.length()/19; i++) {
//            binString = binString + Integer.parseInt(fakeLong.substring(i*19, i*19+19);
//        }
//    }
    
	//max possible int value if all bytes are 1
	//TODO accept bigint as param
    public static long getMax(int numOfBytes) {
        String ones = "";
        for(int i=0; i<numOfBytes*8; i++) {
            ones += "1";
        }
        return binToLong(ones);
    }
    
    public static BigInteger bigIntPow(int base, int exp) {
        BigInteger bigInt = new BigInteger(Integer.toString(base));
        System.out.println(base + " " + exp + " " + (bigInt.pow(exp)).toString());
        return bigInt.pow(exp);
    }
    
    public static int getBigIntLen(BigInteger bigInt) {
        return bigInt.toString().length();
    }
}
