package com.experis.course.spring.api;

import com.experis.course.spring.exception.PhotoNotFoundException;
import com.experis.course.spring.model.Message;
import com.experis.course.spring.model.Photo;
import com.experis.course.spring.service.MessageService;
import com.experis.course.spring.service.PhotoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1")
@CrossOrigin
public class PhotoRestController {

    @Autowired
    private PhotoService photoService;
    @Autowired
    private MessageService messageService;



    @GetMapping ("/photos")
    public List  <Photo> index(@RequestParam Optional <String> search) {

        return photoService.getPhotoForApi(Optional.of(search.orElse("")));

    }



    @GetMapping ("/photos/{id}")
    public Photo details (@PathVariable Integer id) {

        try {
            return photoService.getPhotoById(id);
        }
        catch (PhotoNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }

    }

    @PostMapping("/messages")
    @ResponseStatus(HttpStatus.CREATED)
    public Message createContactMessage(@RequestBody Message message) {
        // Salva il messaggio nel database
        return messageService.createMessage(message);
    }


}




