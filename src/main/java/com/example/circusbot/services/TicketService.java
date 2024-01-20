package com.example.circusbot.services;

import com.example.circusbot.models.Ticket;
import com.example.circusbot.models.User;
import com.example.circusbot.repository.TicketRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class TicketService {
    private final UserService userService;
    private final TicketRepository ticketRepository;

    TicketService(UserService userService,
                  TicketRepository ticketRepository) {
        this.userService = userService;
        this.ticketRepository = ticketRepository;
    }

    /**
     * void method for saving ticket in database, and connect the ticket with user
     * @param ticket which need to be added
     * @param chatId which need to find a user, to which to add a ticket
     */
    @Transactional
    public void addTicket(Ticket ticket, long chatId) {
        User user = userService.findById(chatId).orElse(null);
        assert user != null;
        user.getTickets().add(ticket);
        ticket.setUser(user);
        ticketRepository.save(ticket);
    }

}