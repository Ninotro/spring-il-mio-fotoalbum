package com.experis.course.spring.service;

import com.experis.course.spring.exception.PhotoNotFoundException;
import com.experis.course.spring.model.Photo;
import com.experis.course.spring.model.User;
import com.experis.course.spring.repository.PhotoRepository;
import com.experis.course.spring.repository.UserRepository;
import com.experis.course.spring.security.DatabaseUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PhotoService {

    @Autowired
    private PhotoRepository photoRepository;

    @Autowired
    UserRepository userRepository;


    public List<Photo> GetPhoto(Optional<String> search, Integer userId, boolean isSuperAdmin) {
        if (isSuperAdmin) {
            // Se è un superadmin, restituisci tutte le foto
            if (search.isPresent()) {
                return photoRepository.findByTitleContainingIgnoreCase(search.get());
            } else {
                return photoRepository.findAll();
            }
        } else {
            // Altrimenti, restituisci solo le foto dell'utente specificato
            if (search.isPresent()) {
                return photoRepository.findByTitleContainingIgnoreCaseAndUserId(search.get(), userId);
            } else {
                return photoRepository.findAllByUserId(userId);
            }
        }
    }


    public List<Photo> getPhotoForApi(Optional<String> search) {
        if (search.isPresent()) {
            return photoRepository.findByTitleContainingIgnoreCaseAndVisibleTrue(search.get());
        } else
            return photoRepository.findAllByVisibleTrue();
    }


    public Photo getPhotoById(Integer id) throws PhotoNotFoundException {
        Optional<Photo> result = photoRepository.findById(id);

        if (result.isPresent()) {

            return result.get();
        } else {

            throw new PhotoNotFoundException("Photo with ID " + id + ": Not Found");
        }
    }

    public Photo createPhoto(Photo photo) throws RuntimeException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        DatabaseUserDetails userDetails = (DatabaseUserDetails) authentication.getPrincipal();
        User user = userRepository.getById(userDetails.getId());

        photo.setUser(user);
        return photoRepository.save(photo);
    }

    public Photo editPhoto(Integer id, Photo updatedPhoto) throws PhotoNotFoundException {
        Photo photoToEdit = getPhotoById(id);


        DatabaseUserDetails userDetails = getCurrentUserDetails();
        if (userDetails.isSuperAdmin()) {

            photoToEdit.setVisible(updatedPhoto.isVisible());
        } else {

            photoToEdit.setTitle(updatedPhoto.getTitle());
            photoToEdit.setDescription(updatedPhoto.getDescription());
            photoToEdit.setUrl(updatedPhoto.getUrl());
            photoToEdit.setVisible(updatedPhoto.isVisible());
            photoToEdit.setCategories(updatedPhoto.getCategories());
        }

        return photoRepository.save(photoToEdit);
    }


    private DatabaseUserDetails getCurrentUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (DatabaseUserDetails) authentication.getPrincipal();
    }

    public void deletePhoto(Integer id) {
        photoRepository.deleteById(id);
    }

    public List<Photo> getAllPhotoList(Optional<String> search) {
        if (search.isPresent()) {
            return photoRepository.findByTitleContainingIgnoreCase(search.get());
        } else {
            return photoRepository.findAll();
        }


    }

}




