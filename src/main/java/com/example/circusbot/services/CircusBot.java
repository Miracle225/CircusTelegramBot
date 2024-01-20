package com.example.circusbot.services;

import com.example.circusbot.UserInput;
import com.example.circusbot.botconfig.BotConfig;
import com.example.circusbot.handlers.*;
import com.example.circusbot.handlers.HelpCommandHandler;
import com.example.circusbot.models.Ticket;
import com.example.circusbot.models.User;
import com.example.circusbot.repository.TicketRepository;
import com.example.circusbot.repository.UserRepository;
import com.example.circusbot.validators.DateValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.circusbot.constants.BotPhrases.SUCCESS_REGISTER;

@Component
@Slf4j
public class CircusBot extends TelegramLongPollingBot {

    // Dependency injection of various services and configurations
    private final UserRepository userRepository;
    private final List<Handler> handlers;
    private final TicketService ticketService;
    private final UserService userService;
    private final BotConfig botConfig;
    private final DateValidator dateValidator;
   private final TicketRepository ticketRepository;

    // Constructor with dependencies injected
    public CircusBot(BotConfig botConfig,
                     UserService userService,
                     TicketService ticketService,
                     UserRepository userRepository,
                     DateValidator dateValidator,
                     TicketRepository ticketRepository) {
        this.botConfig = botConfig;
        this.userRepository = userRepository;
        this.userService = userService;
        this.ticketService = ticketService;
        this.dateValidator = dateValidator;
        this.ticketRepository = ticketRepository;


        // Initializing handlers for different commands
        this.handlers = List.of(
                new HelpCommandHandler(),
                new StartCommandHandler(userService),
                new RegisterCommandHandle(userService),
                new TicketCommandHandler(ticketService,dateValidator,userService)
        );

        // Creating a list of bot commands
        List<BotCommand> menu = new ArrayList<>();
        menu.add(new BotCommand("/start", "welcome message"));
        menu.add(new BotCommand("/help", "information about all commands in this bot"));
        menu.add(new BotCommand("/register", "register button"));
        // menu.add(new BotCommand("/phone", "input phone button"));
        menu.add(new BotCommand("/reserve", "reserve ticket button"));

        try {
            // Setting bot commands using SetMyCommands API
            execute(new SetMyCommands(menu, new BotCommandScopeDefault(), null));
        } catch (TelegramApiException e) {
            log.error("Setting bot command list: " + e.getMessage());
        }
    }

    // Overriding method to get the bot's username
    @Override
    public String getBotUsername() {
        return botConfig.getBotName();
    }

    // Overriding method to get the bot's token
    @Override
    public String getBotToken() {
        return botConfig.getToken();
    }

    // Method to handle incoming updates
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            if (update.getMessage().hasContact()) {
                // Handling received contact information
                // Creating a new User entity and setting contact details
                User user = new User();
                long chatId = update.getMessage().getChatId();
                String firstName = update.getMessage().getContact().getFirstName();
                String lastName = update.getMessage().getContact().getLastName();
                String phoneNumber = update.getMessage().getContact().getPhoneNumber();
                user.setChatId(chatId);
                user.setFirstName(firstName);
                user.setLastName(lastName);
                user.setPhoneNumber(phoneNumber);
                user.setRegisterAt(new Timestamp(System.currentTimeMillis()));
                userRepository.save(user);
                SendMessage sendMessage = new SendMessage(Long.toString(chatId),SUCCESS_REGISTER);

                try {
                    execute(sendMessage);
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
            }
            if (update.getMessage().hasText()) {
                long chatId = update.getMessage().getChatId();
                String firstName = update.getMessage().getChat().getFirstName();
                String lastName = update.getMessage().getChat().getLastName();
                String text = update.getMessage().getText();

                // Creating UserInput object with message details
                UserInput userInput = UserInput.createFromCommand(chatId, firstName, lastName, new Timestamp((long) update.getMessage().getDate() * 1000), text);

                // Handling the received message using appropriate handler
                SendMessage message = handle(userInput);

                // Sending the response message
                sendMessage(message);
            }
        }
    }

    // Method to send a message via the Telegram API
    private void sendMessage(SendMessage message) {
        try {
            execute(message);
        } catch (TelegramApiException e) {
            // Handling exception if sending message fails
            log.error("Error sending message: " + e.getMessage());
        }
    }

    // Method to handle user input based on commands and execute appropriate handler
    private SendMessage handle(UserInput userInput) {
        Optional<Handler> handler = Optional.empty();
        for (Handler current : handlers) {
            if (current.support(userInput.getCommand())) {
                handler = Optional.of(current);
                break;
            }
        }
        return (SendMessage) handler
                .orElseThrow(() -> new IllegalArgumentException("Sorry, command was not recognized: " + userInput.getCommand()))
                .handle(userInput);
    }
}
