package com.example.circusbot.services;

import com.example.circusbot.dto.PhoneNumber;
import com.example.circusbot.models.User;
import com.example.circusbot.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Return a user by their unique chat ID.
     *
     * @param chatId The unique identifier for the user's chat.
     * @return An Optional containing the User if found, empty otherwise.
     */
    public Optional<User> findById(Long chatId) {
        return userRepository.findById(chatId);
    }

    /**
     * Saves a User entity into the UserRepository.
     *
     * @param user The User entity to be saved.
     * @return User if the user is successfully saved, null otherwise.
     */
    public User save(User user) {
        return userRepository.save(user);
    }

    /**
     * Updates the phone number of a User identified by their chat ID.
     *
     * @param phoneNumber The PhoneNumber object containing the chat ID and phone number to update.
     */
    public void phoneNumber(PhoneNumber phoneNumber) {
        Optional<User> user = findById(phoneNumber.getChatId());
        if (user.isPresent()) {
            user.get().setPhoneNumber(phoneNumber.getPhoneNumber());
        }
    }
}
