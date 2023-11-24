package com.experis.course.spring.repository;

import com.experis.course.spring.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository <Message, Integer> {
}
