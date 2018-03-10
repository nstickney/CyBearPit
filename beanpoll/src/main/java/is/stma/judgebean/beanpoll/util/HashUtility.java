package is.stma.judgebean.beanpoll.util;

import javax.inject.Inject;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;
import java.util.logging.Logger;

public class HashUtility {

    private static final String ENCODING_ALGORITHM = "SHA-256";
    @Inject
    Logger log;

    public static String getHash(String string) {

        // Hash the string
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance(ENCODING_ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
        byte[] hash = digest.digest(string.getBytes(StandardCharsets.UTF_8));

        // Convert the hash to hex format and return result
        StringBuilder sb = new StringBuilder();
        for (byte aHash : hash) {
            sb.append(Integer.toString((aHash & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }

    public static boolean checkHash(String string, String hash) {
        return null != string && null != hash && Objects.equals(getHash(string), hash);
    }
}
