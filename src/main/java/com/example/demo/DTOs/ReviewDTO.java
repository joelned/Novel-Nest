package com.example.demo.DTOs;

import com.example.demo.Models.Book;
import com.example.demo.Models.UserEntity;
import lombok.Data;
import org.springframework.security.core.userdetails.User;

@Data
public class ReviewDTO {
    private String comment;
    private Integer rating;
}
