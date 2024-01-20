package com.example.circusbot.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "users")
@Getter
public class User {
    @Id
    private Long chatId;

    private String firstName;

    private String lastName;

    private Timestamp registerAt;

    private String phoneNumber;

    @OneToMany(mappedBy = "user")
    private List<Ticket> tickets = new ArrayList<>();


    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }


    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    public void setRegisterAt(Timestamp registerAt) {

        this.registerAt = registerAt;
    }


    public void setTickets(Ticket ticket){
        if(ticket != null){
            tickets.add(ticket);
        }
    }


    public void setPhoneNumber(String phoneNumber) {
        if(phoneNumber != null){
            this.phoneNumber = phoneNumber;
        }
    }
    @Override
    public String toString() {
        return "User{" +
                "chatId=" + chatId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", registerAt=" + registerAt +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }

}