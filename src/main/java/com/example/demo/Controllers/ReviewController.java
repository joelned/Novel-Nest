package com.example.demo.Controllers;
import com.example.demo.DTOs.ReviewDTO;
import com.example.demo.Models.Book;
import com.example.demo.Models.Review;
import com.example.demo.Models.UserEntity;
import com.example.demo.Repositories.BookRepository;
import com.example.demo.Repositories.ReviewRepository;
import com.example.demo.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@RequestMapping("/api/review")
public class ReviewController {
    private final ReviewRepository reviewRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    @Autowired
    public ReviewController(ReviewRepository reviewRepository, BookRepository bookRepository, UserRepository userRepository) {
        this.reviewRepository = reviewRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }
    @PostMapping("/add-review")
    public ResponseEntity<String>addReview(@RequestBody ReviewDTO reviewDTO,
    @RequestParam Integer bookId, @RequestParam Integer userId
    ){
        Optional<Book> book = bookRepository.findById(bookId);
        Optional<UserEntity> user = userRepository.findById(userId);
        if(book.isEmpty() || user.isEmpty()){
            return new ResponseEntity<>("Book or User not found", HttpStatus.OK);
        }
        Review review = new Review();
        review.setComment(reviewDTO.getComment());
        review.setRating(reviewDTO.getRating());
        review.setBook(book.get());
        review.setUserEntity(user.get());

        reviewRepository.save(review);
        return new ResponseEntity<>("Review added successfully", HttpStatus.CREATED);
    }
}
