package MikusNOBE;

import java.awt.image.AreaAveragingScaleFilter;
import java.math.BigInteger;
import java.util.ArrayList;

/**
 * Created by Kevin on 3/14/2017.
 */
public class Encoder {
    private String filePath;
    private byte[] inputByteArray;
    private BigInteger inputFileBigInt;
    private ArrayList significantDigits;
    private ArrayList bases;
    private ArrayList exponents;

    protected Encoder(ArrayList sigDigits, ArrayList bases, ArrayList exponents) {
        this.significantDigits = sigDigits;
        this.bases = bases;
        this.exponents = exponents;
    }

    public ArrayList getSignificantDigits() {
        return significantDigits;
    }

    public ArrayList getBases() {
        return bases;
    }

    public ArrayList getExponents() {
        return exponents;
    }

}
