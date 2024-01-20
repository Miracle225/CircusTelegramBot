package com.example.circusbot.validators;

import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class PhoneNumberValidator {
    // Regular expression pattern to validate a phone number with  format +380XXXXXXXXX
    private static final Pattern PHONE_NUMBER = Pattern.compile("^\\+?3?8?(0\\d{9})$");


    /**
     * Checks if the provided string matches the phone number pattern.
     *
     * @param phoneNumber The phone number string to be checked for the valid format.
     * @return True if the provided string matches the phone number pattern, false otherwise.
     */
    public boolean validatePhoneNumber(String phoneNumber) {
        return PHONE_NUMBER.matcher(phoneNumber).matches();
    }
}
