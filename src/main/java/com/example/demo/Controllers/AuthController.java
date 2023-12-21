package com.example.demo.Controllers;

import com.example.demo.DTOs.RegisterDTO;
import com.example.demo.Models.Role;
import com.example.demo.Models.UserEntity;
import com.example.demo.Repositories.RoleRepository;
import com.example.demo.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthController(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterDTO registerDTO){
        UserEntity userEntity = userRepository.findByName(registerDTO.getUsername());
        if(userEntity != null){
            return new ResponseEntity<>("User already exists", HttpStatus.BAD_REQUEST);
        }
        UserEntity user = new UserEntity();
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        user.setUsername(registerDTO.getUsername());
        Role role = roleRepository.findByName("USER");
        userEntity.setRole(Collections.singletonList(role));
        userRepository.save(userEntity);
        return new ResponseEntity<>("Registration Successful", HttpStatus.OK);
    }
}
