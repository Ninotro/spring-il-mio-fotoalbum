package com.experis.course.spring.controller;

import com.experis.course.spring.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@RequestMapping ("/messages")

public class MessageController {

    @Autowired
    MessageService messageService;

    @GetMapping
    String listIndex( Model model) {
        model.addAttribute("listMessage", messageService.getMessage());
        return "admin/messages";
    }

}
