package EncryptorAES;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public interface Encryptor {
    static byte[] encryptWithPrefixIV(byte[] pText, SecretKey secret, byte[] iv) throws Exception {
        return new byte[0];
    }
    static byte[] encrypt(byte[] pText, SecretKey secretKey, byte[] initialValue) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        return new byte[0];
    }

    static String decrypt(byte[] cText, SecretKey secret, byte[] iv) throws Exception{
        return null;
    }

    static String decryptWithPrefixIV(byte[] cText, SecretKey secret) throws Exception {
        return null;
    }
}
