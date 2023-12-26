package com.example.demo.DTOs;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
@Data
public class ReviewDTO {
    @NotBlank(message = "Comment should not be blank")
    private String comment;
    @NotBlank(message = "Rating should not be blank")
    private Integer rating;
}
