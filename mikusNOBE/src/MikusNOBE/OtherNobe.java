/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MikusNOBE;
import java.util.*;
import java.math.BigInteger;

/**
 *
 * @author Kevin
 */
public class OtherNobe {
    private ArrayList hash;
    private BigInteger remainder;
    private String sigDigits;
    private int numOfPasses;
    
    public OtherNobe(ArrayList hash, BigInteger remainder, String sigDigits, int numOfPasses) {
        this.hash = hash;
        this.remainder = remainder;
        this.sigDigits = sigDigits;
        this.numOfPasses = numOfPasses;
    }
    
    public ArrayList getHash() {
        return hash;
    }
    
    public BigInteger getRemainder() {
        return remainder;
    }
    
    public String getSigDigits() {
        return sigDigits;
    }
    
    public int getNumOfPasses() {
        return numOfPasses;
    }
}
