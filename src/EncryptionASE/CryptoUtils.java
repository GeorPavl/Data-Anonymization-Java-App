package EncryptionASE;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.ArrayList;
import java.util.List;

public class CryptoUtils{

    // Initial Value, it is random bytes, typically 12 bytes or 16 bytes.
    // In Java we can use SecureRandom to generate the random IV
    public static byte[] getRandomBytes(int numberOfBytes){
        byte[] randomBytes = new byte[numberOfBytes];
        new SecureRandom().nextBytes(randomBytes);
        return randomBytes;
    }

    // The AES secret key, either AES-128 or AES-256.
    // In Java, we can use KeyGenerator to generate the AES secret key.
    public static SecretKey getAESKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(256, SecureRandom.getInstanceStrong());
        return keyGenerator.generateKey();
    }

    // Overloaded method getAESKey.
    // Excepts one extra parameter keySize (128 or 256)
    public static SecretKey getAESKey(int keySize) throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(keySize, SecureRandom.getInstanceStrong());
        return keyGenerator.generateKey();
    }

    // The AES secret key that derived from a given password.
    // In Java, we can use the SecretKeyFactory and PBKDF2WithHmacSHA256 to generate an AES key from a given password.
    // Password derived AES 256 bits secret key
    public static SecretKey getAESKeyFromPassword(char[] password, byte[] salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        KeySpec spec = new PBEKeySpec(password, salt, 65536,256);
        SecretKey secretKey = new SecretKeySpec(factory.generateSecret(spec).getEncoded(),"AES");
        return secretKey;
    }

    // hex representation
    public static String hex(byte[] bytes){
        StringBuilder result = new StringBuilder();
        for (byte b : bytes){
            result.append(String.format("%02x", b));
        }
        return result.toString();
    }

    // print hex with block size split
    public static String hexWithBlockSize(byte[] bytes, int blockSize){
        String hex = hex(bytes);
        // one hex = 2 chars;
        blockSize = blockSize * 2;

        List<String> result = new ArrayList<>();
        int index = 0;
        while (index < hex.length()){
            result.add(hex.substring(index, Math.min(index + blockSize, hex.length())));
        index += blockSize;
        }
        return result.toString();
    }
}
