package com.example.demo.Models;
import jakarta.persistence.*;
import lombok.Data;
@Data
@Table
@Entity
public class Role {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private int roleId;
    private String name;
}
