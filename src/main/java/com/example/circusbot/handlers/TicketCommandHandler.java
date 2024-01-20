package com.example.circusbot.handlers;

import com.example.circusbot.UserInput;
import com.example.circusbot.dto.PhoneNumber;
import com.example.circusbot.models.Ticket;
import com.example.circusbot.models.User;
import com.example.circusbot.services.TicketService;
import com.example.circusbot.services.UserService;
import com.example.circusbot.validators.DateValidator;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.time.LocalDateTime;

import static com.example.circusbot.constants.BotPhrases.*;

public class TicketCommandHandler implements Handler {
    private final TicketService ticketService;
    private final DateValidator dateValidator;
    private final UserService userService;

    public TicketCommandHandler(TicketService ticketService,
                                DateValidator dateValidator,
                                UserService userService) {
        this.ticketService = ticketService;
        this.dateValidator = dateValidator;
        this.userService = userService;
    }

    @Override
    public boolean support(String command) {
        // Checks if the command received is "/reserve"
        return command.contains("/reserve");
    }

    @Override
    public BotApiMethod handle(UserInput userInput) {
        // Logic to handle the "/reserve" command
        // Get the value from user input
        String value = String.valueOf(userInput.getCommand());
        // Get the chat ID from user input
        long chatId = userInput.getChatId();
        //Get the user from chatId for checking registration before reserving ticket
        User user = userService.findById(chatId).orElse(null);
        if(user == null)
            return new SendMessage(String.valueOf(chatId),NOT_REGISTERED);
        // Checks if the user wants to reserve a ticket
        if (value.equals("/reserve")) {
            // Sends a manual message for reserving ticket
            return new SendMessage(String.valueOf(chatId), ASK_TICKET_DATE);
        } else {
            // Set value by date and time of desired date of entertainment by split method
            value = String.valueOf(userInput.getCommand()).split(" ")[1] + " " + String.valueOf(userInput.getCommand()).split(" ")[2];
            if (!dateValidator.validDate(value)) {
                // Validates the date using DateValidator
                // Sends an error message if the date is not valid
                return new SendMessage(String.valueOf(chatId), DATE_ERROR);
            }
            // If the date is valid, invokes the TicketService to add the ticket, and generate it.
            Ticket ticket = new Ticket(LocalDateTime.now(),value);
            ticketService.addTicket(ticket,chatId);
            String successMsg = """
        Thank you! We are processed your data, and generate your ticket. Please, don't send your ticket anyone before entertainment.
        *-----------------------------------------------------------------------------------*
                          TICKET IN CIRCUS № %d
         Name: %s
         Last Name: %s
         Entertainment date and time: %s
        *-----------------------------------------------------------------------------------*
        The next messages will be arrived directly to the chat operator for further communication.
        Thank you for using our bot!
        """.formatted(ticket.getId(),user.getFirstName(),user.getLastName(),ticket.getShowDate());
            // Returns a message with generated ticket
            return new SendMessage(String.valueOf(chatId), successMsg);
        }


    }
}