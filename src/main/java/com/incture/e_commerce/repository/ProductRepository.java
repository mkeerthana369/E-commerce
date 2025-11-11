package com.incture.e_commerce.repository;
import com.incture.e_commerce.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
  Page<Product> findByCategoryContainingIgnoreCase(String category, Pageable pageable);
  Page<Product> findByPriceBetween(Double min, Double max, Pageable pageable);
}
