package com.example.circusbot.handlers;

import com.example.circusbot.UserInput;
import com.example.circusbot.services.UserService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import static com.example.circusbot.constants.BotPhrases.HELLO_TEXT;

@Component
public class StartCommandHandler implements Handler {

    public final UserService userService;

    public StartCommandHandler(UserService userService){
        this.userService = userService;
    }

    @Override
    public boolean support(String command) {
        // Checks if the command received is "/start"
        return "/start".equals(command);
    }

    @Override
    public BotApiMethod handle(UserInput userInput) {
        long chatId = userInput.getChatId();
        return new SendMessage(String.valueOf(chatId), HELLO_TEXT);
    }
}