package com.example.circusbot.constants;

public class BotPhrases {
    public static final String HELP_TEXT = """
                Use this commands to interact with bot:
                /start - This command allows you to start the bot work and have greeting
                /help - This command shows you all commands that you need to interact with bot
                /register - This command allows you to register in system
                /reserve - This command allows you to reserve a ticket
                     """;

    public static final String HELLO_TEXT = "\"Welcome! This is the Circus Ticket Reservation Bot." +
            " \uD83C\uDFAA You're in the right place to reserve tickets for our spectacular circus shows!" +
            " \uD83C\uDF89 Feel free to navigate through the available commands. Type /help command to investigate available commands. ";

    public static final String REGISTERED ="You are already registered. If you want to order ticket, type \"/reserve\" command";

    public static final String NUMBER_NULL = "Provided number cannot be empty.";

    public static final String NUMBER_ERROR ="The number should match the pattern '+380' followed by nine digits (for example, +380XXXXXXXXX).";
    public static final String SUCCESS_REGISTER="Thank you for the registration! Now you can reserve the ticket. To reserve ticket type \"/reserve\" command.";

    public static final String DATE_ERROR ="The date should match the pattern DD/MM/YYYY HH:mm";
    public static final String ASK_TICKET_DATE = "Please type in the next message desire ticket date in format with command \"/reserve DD/MM/YYYY HH:mm\"";
    public static final String NOT_REGISTERED ="You are not registered. If you want to order ticket, you need to registered first by typing \"/register\" command";
}
