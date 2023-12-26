package com.example.demo.DTOs;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
@Data
public class BookDTO {
    @NotBlank(message = "Title should not be blank")
    private String title;
    @NotBlank(message= "Author should not be blank")
    private String author;
    @NotBlank(message = "Category should not be blank")
    private String category;
}
