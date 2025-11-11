package com.incture.e_commerce.controller;

import com.incture.e_commerce.entity.User;
import com.incture.e_commerce.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
  private final UserService userService;
  public UserController(UserService userService) { this.userService = userService; }

  @GetMapping("/me")
  public ResponseEntity<User> me(Authentication auth) {
      if (auth == null || auth.getName() == null || auth.getName().isBlank()) {
          return ResponseEntity.status(401).build();
      }
      String email = auth.getName();
      User u = userService.getByEmail(email);
      return ResponseEntity.ok(u);
  }

  @GetMapping
  public ResponseEntity<List<User>> list() { return ResponseEntity.ok(userService.listAll()); }

  @GetMapping("/{id}")
  public ResponseEntity<User> get(@PathVariable Long id) { return ResponseEntity.ok(userService.getById(id)); }

  @PutMapping("/{id}")
  public ResponseEntity<User> update(@PathVariable Long id, @RequestBody User in) { return ResponseEntity.ok(userService.update(id, in)); }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> delete(@PathVariable Long id) { userService.delete(id); return ResponseEntity.noContent().build(); }
}
