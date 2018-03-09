package is.stma.judgebean.beanpoll.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class HashUtility {

    private static final String ENCODING_ALGORITHM = "SHA-256";

    public static byte[] getHash(String string) {
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance(ENCODING_ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
        return digest.digest(string.getBytes(StandardCharsets.UTF_8));
    }

    public static boolean checkHash(String string, byte[] hash) {
        return Arrays.equals(getHash(string), hash);
    }
}
