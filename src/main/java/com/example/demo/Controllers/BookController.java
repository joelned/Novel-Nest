package com.example.demo.Controllers;

import com.example.demo.Models.Book;
import com.example.demo.Repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {
    private final BookRepository bookRepository;

    @Autowired
    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping
    public ResponseEntity<Object>getAllBooks(){
       List<Book> listOfBooks = bookRepository.findAll();
       return new ResponseEntity<>("Books Successfully Retrieved", HttpStatus.OK);
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<Book>findBook(@PathVariable Integer bookId){
        Book book = bookRepository.findBookByBookId(bookId);
        return new ResponseEntity<>(book, HttpStatus.OK);
    }


}
