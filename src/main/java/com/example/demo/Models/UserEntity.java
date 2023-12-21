package com.example.demo.Models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Table
@Entity
public class UserEntity {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private int userId;
    private String username;
    private String password;

    @ManyToMany(fetch = FetchType.EAGER, cascade= CascadeType.ALL)
    @JoinTable(
            joinColumns = @JoinColumn(name= "user_id", referencedColumnName = "userId"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "roleId")
    )
    private List<Role> role;
}
