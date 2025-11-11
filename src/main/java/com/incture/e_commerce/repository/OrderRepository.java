package com.incture.e_commerce.repository;

import com.incture.e_commerce.entity.Order;
import com.incture.e_commerce.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUser(User user);
}
