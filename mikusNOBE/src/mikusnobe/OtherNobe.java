/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mikusnobe;
import java.util.*;
import java.math.BigInteger;

/**
 *
 * @author Kevin
 */
public class OtherNobe {
    private ArrayList hash;
    private BigInteger remainder;
    
    public OtherNobe(ArrayList hash, BigInteger remainder) {
        this.hash = hash;
        this.remainder = remainder;
    }
    
    public ArrayList getHash() {
        return hash;
    }
    
    public BigInteger getRemainder() {
        return remainder;
    }
}
