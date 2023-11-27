package com.experis.course.spring.repository;

import com.experis.course.spring.model.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PhotoRepository extends JpaRepository <Photo, Integer> {

    List<Photo> findByTitleContainingIgnoreCase(String nameKeyword);


    List<Photo> findByTitleContainingIgnoreCaseAndVisibleTrue(String nameKeyword);;

    List<Photo> findAllByVisibleTrue();

    List<Photo> findByTitleContainingIgnoreCaseAndUserId(String nameKeyword, Integer userId);

    List<Photo> findAllByUserId(Integer id);

}
