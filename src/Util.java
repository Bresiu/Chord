import java.math.BigInteger;
import java.security.MessageDigest;

public class Util {
    public static final int KEY_LENGTH = 32;
    public static final String FUNCTION = "SHA1";

    public static byte[] toHash(String identifier) {
        try {
            MessageDigest md = MessageDigest.getInstance(FUNCTION);
            md.reset();
            byte[] code = md.digest(identifier.getBytes());
            byte[] value = new byte[KEY_LENGTH / 8];
            int shrink = code.length / value.length;
            int bitCount = 1;
            for (int j = 0; j < code.length * 8; j++) {
                int currBit = ((code[j / 8] & (1 << (j % 8))) >> j % 8);
                if (currBit == 1)
                    bitCount++;
                if (((j + 1) % shrink) == 0) {
                    int shrinkBit = (bitCount % 2 == 0) ? 0 : 1;
                    value[j / shrink / 8] |= (shrinkBit << ((j / shrink) % 8));
                    bitCount = 1;
                }
            }
            return value;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static BigInteger hashToNum(byte[] array) {
        return new BigInteger(array);
    }
}
