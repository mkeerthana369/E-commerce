package com.incture.e_commerce.repository;

import com.incture.e_commerce.entity.Cart;
import com.incture.e_commerce.entity.CartItem;
import com.incture.e_commerce.entity.Product;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findByCart(Cart cart);

	CartItem findByCartAndProduct(Cart cart, Product product);
}
