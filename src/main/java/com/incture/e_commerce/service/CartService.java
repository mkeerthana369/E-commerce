package com.incture.e_commerce.service;

import com.incture.e_commerce.entity.Cart;
import com.incture.e_commerce.entity.CartItem;
import com.incture.e_commerce.entity.Product;
import com.incture.e_commerce.entity.User;
import com.incture.e_commerce.repository.CartItemRepository;
import com.incture.e_commerce.repository.CartRepository;
import com.incture.e_commerce.repository.ProductRepository;
import com.incture.e_commerce.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public CartService(CartRepository cartRepository,
                       CartItemRepository cartItemRepository,
                       ProductRepository productRepository,
                       UserRepository userRepository) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    
    public Cart getOrCreateCart(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));
        return cartRepository.findByUser(user)
                .orElseGet(() -> {
                    Cart c = new Cart();
                    c.setUser(user);
                    return cartRepository.save(c);
                });
    }

    
    public Cart addToCart(Long userId, Long productId, int qty) {
        if (qty <= 0) throw new IllegalArgumentException("Quantity must be >= 1");

        Cart cart = getOrCreateCart(userId);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found: " + productId));

        
        CartItem existing = cartItemRepository.findByCartAndProduct(cart, product);

        if (existing != null) {
            int newQty = existing.getQuantity() + qty;
            if (product.getStock() < newQty) {
                throw new IllegalStateException("Not enough stock to add requested quantity for product: " + product.getName());
            }
            existing.setQuantity(newQty);
            cartItemRepository.save(existing);
        } else {
            if (product.getStock() < qty) {
                throw new IllegalStateException("Not enough stock for product: " + product.getName());
            }
            CartItem newItem = new CartItem();
            newItem.setCart(cart);             
            newItem.setProduct(product);       
            newItem.setQuantity(qty);
            cartItemRepository.save(newItem);
        }

        
        return cartRepository.findById(cart.getId()).orElse(cart);
    }

    /**
     * Update existing cart item quantity. If qty == 0 remove the item.
     */
    public Cart updateCartItem(Long userId, Long productId, int qty) {
        if (qty < 0) throw new IllegalArgumentException("Quantity must be >= 0");

        Cart cart = getOrCreateCart(userId);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found: " + productId));

        CartItem item = cartItemRepository.findByCartAndProduct(cart, product);
        if (item == null) throw new IllegalStateException("Item not found in cart for product: " + productId);

        if (qty == 0) {
            cartItemRepository.delete(item);
        } else {
            if (product.getStock() < qty) {
                throw new IllegalStateException("Not enough stock for product: " + product.getName());
            }
            item.setQuantity(qty);
            cartItemRepository.save(item);
        }

        return cartRepository.findById(cart.getId()).orElse(cart);
    }

    /**
     * Remove product from user's cart (if present).
     */
    public Cart removeFromCart(Long userId, Long productId) {
        Cart cart = getOrCreateCart(userId);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found: " + productId));

        CartItem item = cartItemRepository.findByCartAndProduct(cart, product);
        if (item != null) {
            cartItemRepository.delete(item);
        }

        return cartRepository.findById(cart.getId()).orElse(cart);
    }

    /**
     * Return the user's cart (create if missing).
     */
    public Cart getCartForUser(Long userId) {
        return getOrCreateCart(userId);
    }

    /**
     * Clear all items from the provided cart. Used by OrderService after checkout.
     */
    public void clearCart(Cart cart) {
        List<CartItem> items = cartItemRepository.findByCart(cart);
        if (items != null && !items.isEmpty()) {
            cartItemRepository.deleteAll(items);
        }
    }
}
