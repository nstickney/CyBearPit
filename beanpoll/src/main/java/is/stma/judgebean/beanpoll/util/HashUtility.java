package is.stma.judgebean.beanpoll.util;

import de.slevermann.pwhash.argon2.Argon2Strategy;

public class HashUtility {

    public static String hash(String saltedPassword) {
        return Argon2Strategy.getDefault().hash(saltedPassword);
    }

    public static boolean verify(String saltedPassword, String hash) {
        return Argon2Strategy.getDefault().verify(saltedPassword, hash);
    }

    public static boolean needsRehashArgon2(String saltedPassword, String hash) {
        return Argon2Strategy.getDefault().needsRehash(saltedPassword, hash);
    }
}
