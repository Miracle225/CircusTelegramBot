package com.example.circusbot.handlers;

import com.example.circusbot.UserInput;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import static com.example.circusbot.constants.BotPhrases.HELP_TEXT;

@Component
public class HelpCommandHandler implements Handler {

    @Override
    public boolean support(String command) {
        return "/help".equals(command);
    }

    @Override
    public BotApiMethod handle(UserInput userInput) {
        long chatId = userInput.getChatId();
        String answer = HELP_TEXT;
        return new SendMessage(String.valueOf(chatId), answer);
    }

}