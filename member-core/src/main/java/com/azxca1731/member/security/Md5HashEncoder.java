package com.azxca1731.member.security;

import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Component
public class Md5HashEncoder {
    public String encode(CharSequence input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(input.toString().getBytes());
            byte[] digest = md.digest();
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b & 0xff));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("MD5 algorithm not found");
        }
    }

    public boolean matches(CharSequence input, String encodedInput) {
        return encode(input).equals(encodedInput);
    }

}