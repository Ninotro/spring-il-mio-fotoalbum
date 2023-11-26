package com.experis.course.spring.service;

import com.experis.course.spring.model.Message;
import com.experis.course.spring.model.Photo;
import com.experis.course.spring.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MessageService {
    // Istanzio automaticamente categoryRepository tramite Autowired
    @Autowired
    private MessageRepository messageRepository;

    public Message createMessage(Message message) throws RuntimeException {
        return messageRepository.save(message);
    }

    public List<Message> getMessage () {

            return messageRepository.findAll();
    }

}