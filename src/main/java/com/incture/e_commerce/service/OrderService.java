package com.incture.e_commerce.service;

import com.incture.e_commerce.entity.*;
import com.incture.e_commerce.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;
    private final CartService cartService;

    public OrderService(OrderRepository orderRepository,
                        OrderItemRepository orderItemRepository,
                        ProductRepository productRepository,
                        CartRepository cartRepository,
                        CartItemRepository cartItemRepository,
                        UserRepository userRepository,
                        CartService cartService) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.userRepository = userRepository;
        this.cartService = cartService;
    }

    
    public Order checkout(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));

        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new IllegalStateException("Cart not found for user: " + userId));

        List<CartItem> items = cartItemRepository.findByCart(cart);
        if (items == null || items.isEmpty()) {
            throw new IllegalStateException("Cart is empty");
        }

        
        float total = 0f;
        for (CartItem ci : items) {
            Product p = productRepository.findById(ci.getProduct().getId())
                    .orElseThrow(() -> new IllegalStateException("Product not found: " + ci.getProduct().getId()));
            if (p.getStock() < ci.getQuantity()) {
                throw new IllegalStateException("Insufficient stock for product: " + p.getName());
            }
            total += p.getPrice() * ci.getQuantity();
        }

       
        Order order = new Order();
        order.setUser(user);
        order.setOrderDate(LocalDateTime.now());
        order.setOrderStatus("PLACED");
        order.setPaymentStatus("PAID"); 
        order.setTotalAmount(total);
        order.setOrderItems(new ArrayList<>()); 

        Order savedOrder = orderRepository.save(order);

       
        List<OrderItem> orderItemsToSave = new ArrayList<>(items.size());
        for (CartItem ci : items) {
            Product p = productRepository.findById(ci.getProduct().getId())
                    .orElseThrow(() -> new IllegalStateException("Product not found: " + ci.getProduct().getId()));

            
            int newStock = p.getStock() - ci.getQuantity();
            if (newStock < 0) {
                throw new IllegalStateException("Insufficient stock for product during finalization: " + p.getName());
            }
            p.setStock(newStock);
            productRepository.save(p);

            OrderItem oi = new OrderItem();
            oi.setOrder(savedOrder);         
            oi.setProduct(p);
            oi.setQuantity(ci.getQuantity());
            oi.setPrice(p.getPrice());
            orderItemsToSave.add(oi);

            
            savedOrder.getOrderItems().add(oi);
        }

       
        orderItemRepository.saveAll(orderItemsToSave);

        
        cartService.clearCart(cart);

       
        return savedOrder;
    }

    public Order getById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found: " + id));
    }

    public List<Order> getOrdersByUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));
        return orderRepository.findByUser(user);
    }
}
