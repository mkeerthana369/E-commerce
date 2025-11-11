package com.incture.e_commerce.controller;
import com.incture.e_commerce.entity.Order;
import com.incture.e_commerce.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
  private final OrderService orderService;
  public OrderController(OrderService orderService) { this.orderService = orderService; }

  @PostMapping("/checkout/{userId}")
  public ResponseEntity<Order> checkout(@PathVariable Long userId) { return ResponseEntity.ok(orderService.checkout(userId)); }

  @GetMapping("/{id}") public ResponseEntity<Order> get(@PathVariable Long id) { return ResponseEntity.ok(orderService.getById(id)); }
}
