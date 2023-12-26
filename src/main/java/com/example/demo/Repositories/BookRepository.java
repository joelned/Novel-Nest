package com.example.demo.Repositories;
import com.example.demo.Models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface BookRepository extends JpaRepository<Book, Integer> {
    Book findBookByBookId(Integer bookId);
    List<Book> findByCategoryIgnoreCase(String category);

    List<Book> findByAuthorIgnoreCase(String author);

}
