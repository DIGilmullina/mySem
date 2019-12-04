package ru.sirosh;

import java.security.SecureRandom;

public class TokenGenerator {
    private static TokenGenerator instance;
    private SecureRandom random;

    public static TokenGenerator getInstance() {
        if (instance == null){
            instance=new TokenGenerator();
            instance.random = new SecureRandom();
        }
        return instance;
    }

    private TokenGenerator() {
    }

    public String getToken(){                                       //making magic
        byte[] bytes = new byte[20];
        for (int i = 0; i < 20; i++) {
            int chose = random.nextInt(3);

            if (chose == 0){
                bytes[i] = (byte) random.nextInt(91-65 );
                bytes[i] += 65;
            }

            if (chose == 1){
                bytes[i] = (byte) random.nextInt(123-97 );
                bytes[i] += 97;
            }

            if (chose == 2){
                bytes[i] = (byte) random.nextInt(58-48 );
                bytes[i] += 48;
            }

        }
        return new String(bytes);
    }
}
