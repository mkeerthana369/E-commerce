package com.incture.e_commerce.controller;

import com.incture.e_commerce.entity.Cart;
import com.incture.e_commerce.entity.User;
import com.incture.e_commerce.service.CartService;
import com.incture.e_commerce.service.UserService;
import com.incture.e_commerce.config.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;
    private final UserService userService;
    private final JwtUtil jwtUtil;

    public CartController(CartService cartService, UserService userService, JwtUtil jwtUtil) {
        this.cartService = cartService;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    private User getLoggedUser(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) {
            throw new RuntimeException("Missing token");
        }
        String token = header.substring(7);
        String email = jwtUtil.extractUsername(token);
        return userService.getByEmail(email);
    }

    @PostMapping("/add/{productId}")
    public ResponseEntity<Cart> add(@PathVariable Long productId,
                                    @RequestParam(defaultValue = "1") int qty,
                                    HttpServletRequest request) {
        User user = getLoggedUser(request);
        return ResponseEntity.ok(cartService.addToCart(user.getId(), productId, qty));
    }

    @PutMapping("/update/{productId}")
    public ResponseEntity<Cart> update(@PathVariable Long productId,
                                       @RequestParam int qty,
                                       HttpServletRequest request) {
        User user = getLoggedUser(request);
        return ResponseEntity.ok(cartService.updateCartItem(user.getId(), productId, qty));
    }

    @DeleteMapping("/remove/{productId}")
    public ResponseEntity<Cart> remove(@PathVariable Long productId, HttpServletRequest request) {
        User user = getLoggedUser(request);
        return ResponseEntity.ok(cartService.removeFromCart(user.getId(), productId));
    }

    @GetMapping
    public ResponseEntity<Cart> get(HttpServletRequest request) {
        User user = getLoggedUser(request);
        return ResponseEntity.ok(cartService.getCartForUser(user.getId()));
    }
}
