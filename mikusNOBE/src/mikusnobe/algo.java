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
    
    public static String revString(String inString) {
        StringBuilder outString = new StringBuilder(inString);
        return outString.reverse().toString();
    }
    
	//creates large binary string from byte array
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
    public static byte[] bigBinStringToBytes(String bigBinString) {
        String tempStr = "";
        byte[] outputBytes = new byte[bigBinString.length()/8];
        for (int i=0; i<bigBinString.length()/8; i++) {
            outputBytes[i] = (byte)Integer.parseInt(bigBinString.substring(i*8, i*8+8),2);
        }
        return outputBytes;
    }
    
    //takes any binary string and converts to int
    public static int binToInt(String bigBinString, char direction) {
        if (direction == 'r') {
                bigBinString = revString(bigBinString);
        }
        char[] binAsChar = bigBinString.toCharArray();
        int sum = 0;
        for (int i=0; i<binAsChar.length; i++) {
            if(binAsChar[i]=='1') {
                sum+=Math.pow(2, i);
            }
        }
        return sum;
    }
    
    public static void binToBigInt(String bigBinString) {
        
    }
    
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
    
//    public static String fakeLongtoBin(String fakeLong) {
//        String binString = "";
//        for (int i=0; i<fakeLong.length()/19; i++) {
//            binString = binString + Integer.parseInt(fakeLong.substring(i*19, i*19+19);
//        }
//    }
    
//    public static long getMax(int numOfBytes) {
//        String ones = "";
//        for(int i=0; i<numOfBytes*8; i++) {
//            ones += "1";
//        }
//        return binToLong(ones);
//    }
    
    public static byte[] nobeComp(int bigNum) {
        ArrayList<Integer> keyList = new ArrayList<Integer>();
        return null;
    }
}
