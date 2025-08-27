package com.banking.user;

import com.banking.user.dto.NewUserCreated;
import com.banking.user.dto.TransactionCompletedNotification;
import com.banking.user.dto.TransactionCompletion;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@Transactional
public class UserService {

    private final UserRepo userRepo;
    private final RabbitTemplate rabbitTemplate;

    public UserService(UserRepo userRepo, RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        this.userRepo = userRepo;
    }

    public User createUser(User user) {
        User user1=userRepo.save(user);
        NewUserCreated newUserCreated = new NewUserCreated(
                user1.getName(),
                user1.getEmail()
        );
        rabbitTemplate.convertAndSend("newUserCreatedQueue", newUserCreated);
        log.info("New user created event sent to RabbitMQ for user: {}", user1.getEmail());
        return user1;
    }

    public Optional<User> getUserById(Long id) {
        return userRepo.findById(id);
    }

    public User updateUser(Long id, User updatedUser) {
        Optional<User> optionalUser = userRepo.findById(id);

        if (optionalUser.isPresent()) {
            User existingUser = optionalUser.get();
            existingUser.setName(updatedUser.getName());
            existingUser.setEmail(updatedUser.getEmail());
            existingUser.setPassword(updatedUser.getPassword());
            existingUser.setPhoneNumber(updatedUser.getPhoneNumber());

            return userRepo.save(existingUser);
        } else {
            throw new RuntimeException("User not found with id: " + id);
        }
    }

    public void deleteUser(Long id) {
        if (userRepo.existsById(id)) userRepo.deleteById(id);
    }

    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    public void sendTransactionCompletionNotification(TransactionCompletion transactionCompletion){
        Optional<User> user= userRepo.findById(transactionCompletion.userId());
        if(user.isEmpty()){
            return;
        }
        TransactionCompletedNotification transactionCompletedNotification = new TransactionCompletedNotification(
                user.get().getName(),
                user.get().getEmail(),
                transactionCompletion.transactionId(),
                transactionCompletion.type(),
                transactionCompletion.success(),
                transactionCompletion.message()
        );
        rabbitTemplate.convertAndSend("sendNotificationQueue", transactionCompletedNotification);
        log.info("Notification sent to RabbitMQ for user: {}", user.get().getEmail());
    }

}
