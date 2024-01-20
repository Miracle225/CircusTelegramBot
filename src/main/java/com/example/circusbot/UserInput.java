package com.example.circusbot;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;
import java.util.Optional;
@AllArgsConstructor
@Data
public class UserInput {
    private Long chatId;                // Unique identifier for the user's chat
    private String firstName;           // First name of the user
    private String lastName;            // Last name of the user
    private Timestamp registeredAt;     // Timestamp of user registration
    private String command;             // Command received from the user
    private Optional<String> value;     // Optional value associated with the command
    // private boolean isTicketGenerated;

    // Constructor for creating UserInput instances
    public UserInput(Long chatId, String firstName, String lastName, Timestamp timestamp, String text) {
    }

    /**
     * Creates a UserInput object from a received command.
     *
     * @param chatId       Unique identifier for the user's chat
     * @param firstName    First name of the user
     * @param lastName     Last name of the user
     * @param registeredAt Timestamp of user registration
     * @param command      Command received from the user
     * @return A UserInput object created from the provided command
     */
    public static UserInput createFromCommand(Long chatId,
                                              String firstName,
                                              String lastName,
                                              Timestamp registeredAt,
                                              String command) {
        final var split = command.split("-");
        if (split.length == 1) {
            return new UserInput(chatId, firstName, lastName, registeredAt, split[0], Optional.empty());
        } else {
            return new UserInput(chatId, firstName, lastName, registeredAt, split[0].trim(), Optional.of(split[1].trim()));
        }
    }
}