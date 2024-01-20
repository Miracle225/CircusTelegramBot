package com.example.circusbot.handlers;

import com.example.circusbot.UserInput;
import com.example.circusbot.dto.PhoneNumber;
import com.example.circusbot.services.UserService;
import com.example.circusbot.validators.PhoneNumberValidator;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import static com.example.circusbot.constants.BotPhrases.NUMBER_ERROR;
import static com.example.circusbot.constants.BotPhrases.NUMBER_NULL;

public class PhoneNumberCommandHandler implements Handler {
    private final UserService userService;
    private final PhoneNumberValidator phoneNumberValidator;

    public PhoneNumberCommandHandler(UserService userService, PhoneNumberValidator phoneNumberValidator){
        this.userService = userService;
        this.phoneNumberValidator = phoneNumberValidator;
    }

    @Override
    public boolean support(String command) {
        // Checks if the command received is "/phone"
        return "/phone".equals(command);
    }

    @Override
    public BotApiMethod handle(UserInput userInput) {
        // Retrieves the value from user input
        String value = String.valueOf(userInput.getValue());
        // Retrieves the chat ID from user input
        long chatId = userInput.getChatId();

        // Checks if the value is null
        if(value == null){
            // Sends a  null message if the value is null
            return new SendMessage(String.valueOf(chatId), NUMBER_NULL);
        } else if (!phoneNumberValidator.validatePhoneNumber(value)) {
            // Validates the phone number using PhoneNumberValidator
            // Sends an error message if the phone number is not valid
            return new SendMessage(String.valueOf(chatId), NUMBER_ERROR);
        }

        // If the phone number is valid, invokes the UserService to handle the phone number
        userService.phoneNumber(new PhoneNumber(chatId,value));
        // Returns a confirmation message
        return new SendMessage(String.valueOf(chatId),"Your phone number have been registered!");
    }
}