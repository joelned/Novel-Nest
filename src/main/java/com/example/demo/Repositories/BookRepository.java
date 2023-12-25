package com.example.demo.Repositories;


import com.example.demo.Models.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Integer> {
    Book findBookByBookId(Integer bookId);
    Book findBookByTitle(String title);

}
