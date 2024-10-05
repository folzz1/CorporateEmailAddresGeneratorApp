package org.example;

import java.security.SecureRandom;

public class EmailGenerator {

    private String firstName;
    private String lastName;
    private String password;
    private String departament;
    private String email;

    public EmailGenerator(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        generateRandomPassword(12);
        generateEmail();
    }

    public void generateRandomPassword(int length) {
        SecureRandom secureRandom = new SecureRandom();
        char[] digits = {
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'
        };
        char[] letters = {
                'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'
        };
        char[] specialChars = {
                '!', '@', '#', '$', '%', '^', '&', '*', '(', ')', '-', '_', '=', '+', '[', ']', '{', '}', '|', '\\', ':', ';', '<', '>', ',', '.', '?', '/'
        };

        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int choice = secureRandom.nextInt(100);
            if (choice < 33) {
                stringBuilder.append(digits[secureRandom.nextInt(digits.length)]);
            } else if (choice < 66) {
                stringBuilder.append(letters[secureRandom.nextInt(letters.length)]);
            } else {
                stringBuilder.append(specialChars[secureRandom.nextInt(specialChars.length)]);
            }
        }

        password = stringBuilder.toString();
    }

    public void generateEmail() {
        email = firstName + lastName + "@gmail.com";
    }
}
