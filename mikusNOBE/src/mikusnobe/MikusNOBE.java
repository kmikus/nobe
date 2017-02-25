/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mikusnobe;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.math.*;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.util.*;

/**
 *
 * @author Kevin
 */
public class MikusNOBE {

    public static void main(String[] args) throws IOException {
        //TODO: GUI and customizable file input
        String path = "C:\\Users\\kpvid\\Desktop\\png_sample.png";
        byte[] fileBytes = algo.getBytes(path);
        int byteStreamSize = 10000;
        
        String BinString = algo.bytesToBigBinString(fileBytes, byteStreamSize);
//        System.out.println(BinString);
        String fake = algo.binToFakeLong(BinString);
        BigInteger bigInt = new BigInteger(fake);
        
        //TODO: file decompression and output
//        File fout = new File("test.png");
//        FileOutputStream fos = new FileOutputStream(fout);
//        fos.write(fileBytesOut);
//        fos.close();

//        
//        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
//        bw.write(fake);
//        bw.close();
    }
    
}
