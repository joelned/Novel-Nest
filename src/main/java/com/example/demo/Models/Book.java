package com.example.demo.Models;
import jakarta.persistence.*;
import lombok.Data;
@Data
@Table
@Entity
public class Book {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private int bookId;
    private String title;
    private String category;
    private String author;
}
