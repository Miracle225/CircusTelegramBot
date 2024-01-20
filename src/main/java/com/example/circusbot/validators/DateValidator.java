package com.example.circusbot.validators;

import org.springframework.stereotype.Component;

import java.util.regex.Pattern;
@Component
public class DateValidator {
    // Regular expression pattern to validate a date format in the format: dd/MM/yyyy HH:mm
    private static final Pattern DATE = Pattern.compile("^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[012])/(19|20)\\d\\d\\s([01][0-9]|2[0-3]):([0-5][0-9])$");

    /**
     * Checks if the provided string matches the date pattern.
     *
     * @param date The date string to be checked for the valid format.
     * @return True if the provided string matches the date pattern, false otherwise.
     */
    public boolean validDate(String date) {
        return DATE.matcher(date).matches();
    }
}
