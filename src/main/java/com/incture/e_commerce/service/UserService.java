package com.incture.e_commerce.service;

import com.incture.e_commerce.entity.User;
import com.incture.e_commerce.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User register(User user) {
        if (user.getPassword() == null) throw new IllegalArgumentException("Password required");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (user.getRole() == null) user.setRole("CUSTOMER");
        return userRepository.save(user);
    }

    public User login(String email, String rawPassword) {
        if (email == null || rawPassword == null) {
            throw new IllegalArgumentException("Email and password required");
        }
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Invalid credentials"));
        if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
            throw new IllegalArgumentException("Invalid credentials");
        }
        return user;
    }

    public User getByEmail(String email) {
        if (email == null) return null;
        return userRepository.findByEmail(email).orElse(null);
    }

    // skeletons used by controllers - implement if required
    public List<User> listAll() { return userRepository.findAll(); }
    public User getById(Long id) { return userRepository.findById(id).orElse(null); }
    public User update(Long id, User in) {
        User u = userRepository.findById(id).orElseThrow();
        u.setName(in.getName() == null ? u.getName() : in.getName());
        u.setEmail(in.getEmail() == null ? u.getEmail() : in.getEmail());
        if (in.getPassword() != null) u.setPassword(passwordEncoder.encode(in.getPassword()));
        if (in.getRole() != null) u.setRole(in.getRole());
        return userRepository.save(u);
    }
    public void delete(Long id) { userRepository.deleteById(id); }
}
