package com.example.demo.Services;
import com.example.demo.Models.Role;
import com.example.demo.Models.UserEntity;
import com.example.demo.Repositories.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public UserDetails loadUserByUsername(String username){
        UserEntity user = userRepository.findByUsername(username);
        if(user == null){
            throw new UsernameNotFoundException("Username not found");
        }
        return new User(user.getUsername(), user.getPassword(), mapRolesToAuthorities(user.getRole()));
    }
    private List<GrantedAuthority>mapRolesToAuthorities(List<Role>roles){
        return roles.stream()
                .map(role-> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }
}
