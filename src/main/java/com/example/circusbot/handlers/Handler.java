package com.example.circusbot.handlers;

import com.example.circusbot.UserInput;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;


public interface Handler {
    /**
     *
     * @param command Command which user input in telegram bot
     * @return boolean value with a check for correctness of command
     */
    boolean support(String command);

    /**
     * Method handle user input and generate appropriate message
     * @param userInput Message typed by user
     * @return SendMessage variable in reply to user input
     */
    BotApiMethod handle(UserInput userInput);

}