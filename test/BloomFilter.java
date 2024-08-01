package test;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.BitSet;
import java.util.ArrayList;

public class BloomFilter {
    private final BitSet bitSet;
    private final ArrayList<MessageDigest> hashFunctions;
    public BloomFilter(int size, String... algorithms) {
        this.bitSet = new BitSet(size);
        this.hashFunctions = new ArrayList<>();

        for (String algorithm : algorithms) {
            MessageDigest md = null;
            try {
                md = MessageDigest.getInstance(algorithm);
            }
            catch (NoSuchAlgorithmException ignored) {
                //throw new RuntimeException(e);
            }
            hashFunctions.add(md);
        }
    }

    public void add(String word) {
        for (MessageDigest hashFunction : hashFunctions) {
            byte[] hash = hashFunction.digest(word.getBytes()); // return an array of bytes
            int index = Math.abs(new BigInteger(1, hash).intValue()) % bitSet.size(); // get the modulo of the index i need to active
            bitSet.set(index, true);
        }
    }

    public boolean contains(String word) {
        for (MessageDigest hashFunction : hashFunctions) {
            byte[] hash = hashFunction.digest(word.getBytes());
            int index = Math.abs(new BigInteger(1, hash).intValue()) % bitSet.size(); // use abs to avoid negative values
            if (!bitSet.get(index)) { // if one bit is of so the word is certainly not in the dictionary
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(); // using StringBuilder because bitSet is getting increased dynamically
        for (int i = 0; i < bitSet.length(); i++) {
            if(bitSet.get(i)) {
                sb.append("1");
            }
            else {
                sb.append("0");
            }
        }
        return sb.toString();
    }
}
