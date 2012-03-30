/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Auth;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 *
 * @author Stan
 */
public class Cipher {
    public static String generateSalt() {
        SecureRandom random = new SecureRandom();
        // Generate 20 char salt (100 bits / 5 <-- 2^5 = 32
        return new BigInteger(100, random).toString(32);
    }

    // This belongs somewhere else
    public static String generateSHA(String text)
            throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md = MessageDigest.getInstance("SHA1");
        byte[] digest = new byte[64];
        md.update(text.getBytes("utf-16le"), 0, text.length());
        digest = md.digest();

        BigInteger bi = new BigInteger(1, digest);
        return String.format("%0" + (digest.length << 1) + "X", bi);
    }
}
