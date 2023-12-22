package com.example.demo.Models;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table
public class Review {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private int reviewId;
    private String comment;
    private int rating;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "book_id", referencedColumnName = "bookId")
    private Book book;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="user_id", referencedColumnName = "userId")
    private UserEntity userEntity;
}
