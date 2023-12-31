package com.example.demo.Controllers;
import com.example.demo.DTOs.BookDTO;
import com.example.demo.Models.Book;
import com.example.demo.Repositories.BookRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@RestController
@RequestMapping("/api/books")
public class BookController {
    private final BookRepository bookRepository;
    private static final Logger logger = LoggerFactory.getLogger(BookController.class);
    @Autowired
    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }
    @GetMapping
    public ResponseEntity<Object>getAllBooks(){
        try{
            List<Book> listOfBooks = bookRepository.findAll();
            logger.info("All books in database successfully fetched");
            return new ResponseEntity<>(listOfBooks, HttpStatus.OK);
        }
        catch(Exception e){
            logger.error("Error performing task: " + e);
            return new ResponseEntity<>("Error retrieving books.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/{bookId}")
    public ResponseEntity<Book>findBook(@PathVariable Integer bookId){
        try{
            Book book = bookRepository.findBookByBookId(bookId);
            logger.info(book + "fetched successfully.");
            return new ResponseEntity<>(book, HttpStatus.OK);
        }
        catch (Exception e){
            logger.error("Error retrieving book: " + e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }
    @PutMapping("/update/{bookId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String>updateBook(@PathVariable Integer bookId
    ,@RequestParam(required = false)String title, @RequestParam(required = false) String category,
     @RequestParam(required = false)String author
    ) {
        try {
            Optional<Book> existingBook = bookRepository.findById(bookId);
            if (existingBook.isEmpty()) {
                return new ResponseEntity<>("Book was not found.", HttpStatus.NOT_FOUND);
            } else {
                Book book = existingBook.get();
                if (title != null) {
                    book.setTitle(title);
                }
                if (category != null) {
                    book.setCategory(category);
                }
                if (author != null) {
                    book.setAuthor(author);
                }
                bookRepository.save(book);
                logger.info(book +" Book Successfully Updated.");
                return new ResponseEntity<>("Book updated.", HttpStatus.OK);
            }
        } catch (Exception e) {
            logger.error("Error updating book: " + e);
            return new ResponseEntity<>("Error updating book.", HttpStatus.NOT_FOUND);
        }
    }
    @Transactional
    @PostMapping("/add-book")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String>addNewBook(@Valid @RequestBody BookDTO bookDTO){
        try{
            Book book = new Book();
            book.setTitle(bookDTO.getTitle());
            book.setCategory(bookDTO.getCategory());
            book.setAuthor(bookDTO.getAuthor());
            bookRepository.save(book);
            logger.info("Book added successfully");
            return new ResponseEntity<>(book+" Book added to database successfully.", HttpStatus.CREATED);
        }
        catch(Exception e ){
            logger.error("Error adding book: "+ e);
            return new ResponseEntity<>("Error adding book to database.", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @DeleteMapping("/remove-book/{bookId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String>removeBook(@PathVariable Integer bookId){
        try{
            Optional<Book> book = bookRepository.findById(bookId);
            if(book.isEmpty()){
                return new ResponseEntity<>("Book cannot be found.", HttpStatus.NOT_FOUND);
            }
            bookRepository.deleteById(bookId);
            return new ResponseEntity<>("Book removed successfully.", HttpStatus.OK);
        }
       catch(Exception e){
            logger.error("Book could not be deleted.");
            return new ResponseEntity<>("Book could not be deleted from database.", HttpStatus.INTERNAL_SERVER_ERROR);
       }
    }

    @GetMapping("/search/author/{author}")
    public ResponseEntity<List<Book>> searchByAuthor(@PathVariable String author) {
        try {
            List<Book> allBooks = bookRepository.findAll();
            List<Book> matchingBooks = new ArrayList<>();

            for (Book book : allBooks) {
                // Check if the author's name contains the specified substring (case-insensitive)
                if (book.getAuthor().toLowerCase().contains(author.toLowerCase())) {
                    matchingBooks.add(book);
                }
            }

            if (matchingBooks.isEmpty()) {
                logger.error("No books found for the author containing: " + author);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            logger.info("Books with authors containing " + author + " successfully fetched.");
            return new ResponseEntity<>(matchingBooks, HttpStatus.OK);
        }
        catch(Exception e){
            logger.error("Error: " + e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/search/category/{category}")
    public ResponseEntity<List<Book>>getCategory(@PathVariable String category) {
        try {
            List<Book> books = bookRepository.findAll();
            List<Book> matchingBooks = new ArrayList<>();

            for (Book book : books) {
                if (book.getAuthor().toLowerCase().contains(category.toLowerCase())) {
                    matchingBooks.add(book);
                }
            }
            if (matchingBooks.isEmpty()) {
                logger.info("No category matching search " + category);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            logger.info("Category search " + category + " successful");
            return new ResponseEntity<>(matchingBooks, HttpStatus.OK);
        }
        catch(Exception e){
            logger.error("Error "+ e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
