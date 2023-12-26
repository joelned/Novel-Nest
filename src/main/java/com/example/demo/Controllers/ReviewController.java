package com.example.demo.Controllers;
import com.example.demo.DTOs.ReviewDTO;
import com.example.demo.Models.Book;
import com.example.demo.Models.Review;
import com.example.demo.Models.UserEntity;
import com.example.demo.Repositories.BookRepository;
import com.example.demo.Repositories.ReviewRepository;
import com.example.demo.Repositories.UserRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@RequestMapping("/api/review")
public class ReviewController {
    private final ReviewRepository reviewRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(ReviewController.class);

    @Autowired
    public ReviewController(ReviewRepository reviewRepository, BookRepository bookRepository, UserRepository userRepository) {
        this.reviewRepository = reviewRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }
    @Transactional
    @PostMapping("/add-review")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String>addReview(@Valid @RequestBody ReviewDTO reviewDTO,
    @RequestParam Integer bookId, @RequestParam Integer userId
    ) {
            Optional<Book> book = bookRepository.findById(bookId);
            Optional<UserEntity> user = userRepository.findById(userId);
            if (book.isEmpty() || user.isEmpty()) {
                logger.error("Book or user not found.");
                return new ResponseEntity<>("Book or User not found", HttpStatus.NOT_FOUND);
            }
            Review review = new Review();
            review.setComment(reviewDTO.getComment());
            review.setRating(reviewDTO.getRating());
            review.setBook(book.get());
            review.setUserEntity(user.get());
            reviewRepository.save(review);
            logger.info("Review of book with id: "+ bookId + " successfully added.");
            return new ResponseEntity<>("Review added successfully", HttpStatus.CREATED);

    }
}
