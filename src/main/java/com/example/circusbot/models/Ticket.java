package com.example.circusbot.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity(name = "tickets")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    LocalDateTime orderTime;
    String showDate;
    @ManyToOne
    @JoinColumn(name = "chat_id")
    User user;

    public Ticket() {
    }

    public Ticket(LocalDateTime orderTime, String showDate) {
        this.orderTime = orderTime;
        this.showDate = showDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(LocalDateTime orderTime) {
        this.orderTime = orderTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getShowDate() {
        return showDate;
    }

    public void setShowDate(String showDate) {
        this.showDate = showDate;
    }
}
