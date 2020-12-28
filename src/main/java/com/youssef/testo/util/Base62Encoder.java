package com.youssef.testo.util;

import org.springframework.stereotype.Service;

@Service
public class Base62Encoder {

    private static final String ALLOWED_STR = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private char[] allowedCharacters = ALLOWED_STR.toCharArray();
    private int base = allowedCharacters.length;

    public String encode(long input){
        var encodedString = new StringBuilder();

        if(input == 0) {
            return String.valueOf(allowedCharacters[0]);
        }

        while (input > 0) {
            encodedString.append(allowedCharacters[(int) (input % base)]);
            input = input / base;
        }

        return encodedString.reverse().toString();
    }

    public long decode(String input) {
        var characters = input.toCharArray();
        var length = characters.length;

        var decoded = 0;

        //counter is used to avoid reversing input string
        var counter = 1;
        for (int i = 0; i < length; i++) {
            decoded += ALLOWED_STR.indexOf(characters[i]) * Math.pow(base, (double)length - counter);
            counter++;
        }
        return decoded;
    }
}