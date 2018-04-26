package com.senla.autoservice.utills.hashgenerator;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class TokenGenerator {

    public static String getMD5Hash(final String str) {
        final StringBuilder hash = new StringBuilder();
        try {
            final MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            final byte[] bytes = md.digest();

            for (final byte aByte : bytes) {
                hash.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
            }
        } catch (final NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return hash.toString();
    }

}
