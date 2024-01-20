package com.example.circusbot.handlers;

import com.example.circusbot.UserInput;
import com.example.circusbot.models.User;
import com.example.circusbot.services.UserService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.circusbot.constants.BotPhrases.REGISTERED;

@Component
public class RegisterCommandHandle implements Handler {

    private final UserService userService;

    public RegisterCommandHandle(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean support(String command) {
        // Checks if the command received is "/register"
        return "/register".equals(command);
    }

    @Override
    public BotApiMethod handle(UserInput userInput) {
        // Checks if the user is already registered
        Optional<User> userOptional = userService.findById(userInput.getChatId());
        if (userOptional.isPresent()) {
            // Sends a message indicating the user is already registered
            return new SendMessage(String.valueOf(userInput.getChatId()), REGISTERED);
        }
        // If not registered, sends a message asking for a contact button
        return createContactButtonMessage(userInput.getChatId());
    }

    private SendMessage createContactButtonMessage(Long chatId) {
        // Creates a "Send contact" button
        KeyboardButton contactButton = new KeyboardButton();
        contactButton.setText("Send contact");
        contactButton.setRequestContact(true);

        // Creates a keyboard markup with the contact button
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        KeyboardRow keyboardRow = new KeyboardRow();
        keyboardRow.add(contactButton);

        List<KeyboardRow> keyboard = new ArrayList<>();
        keyboard.add(keyboardRow);

        keyboardMarkup.setKeyboard(keyboard);
        keyboardMarkup.setResizeKeyboard(true);

        // Creates a message asking the user to click the "Send contact" button for registration
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText("Click the \"Send contact\" button to register!");
        sendMessage.setReplyMarkup(keyboardMarkup);

        return sendMessage;
    }
}
