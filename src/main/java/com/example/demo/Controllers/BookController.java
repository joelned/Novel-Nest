package com.example.demo.Controllers;

import com.example.demo.DTOs.BookDTO;
import com.example.demo.Models.Book;
import com.example.demo.Repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
    @PutMapping("/update/{bookId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String>updateBook(@PathVariable Integer bookId
    ,@RequestParam(required = false)String title, @RequestParam(required = false) String category,
     @RequestParam(required = false)String author
    ) {
        Optional<Book>existingBook = bookRepository.findById(bookId);
        if(existingBook.isEmpty()){
            return new ResponseEntity<>("Book was not found", HttpStatus.NOT_FOUND);
        }
        else{
            Book book = existingBook.get();
            if(title != null){
                book.setTitle(title);
            }
            if(category != null){
                book.setCategory(category);
            }
        if(author != null){
            book.setAuthor(author);
        }
            bookRepository.save(book);
        return new ResponseEntity<>("Book updated", HttpStatus.OK);
        }
    }
    @PostMapping("/add-book")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String>addNewBook(@RequestBody BookDTO bookDTO){
        Book book = new Book();
        book.setTitle(bookDTO.getTitle());
        book.setCategory(bookDTO.getCategory());
        book.setAuthor(bookDTO.getAuthor());
        bookRepository.save(book);
        return new ResponseEntity<>("Book added successfully", HttpStatus.OK);
    }

    @DeleteMapping("/remove-book/{bookId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String>removeBook(@PathVariable Integer bookId){
        Optional<Book> book = bookRepository.findById(bookId);
        if(book.isEmpty()){
            return new ResponseEntity<>("Book cannot be found", HttpStatus.NOT_FOUND);
        }
        bookRepository.deleteById(bookId);
        return new ResponseEntity<>("Book removed successfully", HttpStatus.OK);
    }
}
