package com.incture.e_commerce.service;
import com.incture.e_commerce.entity.Product;
import com.incture.e_commerce.exception.ResourceNotFoundException;
import com.incture.e_commerce.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
  private final ProductRepository repo;
  public ProductService(ProductRepository repo) { this.repo = repo; }

  public Product create(Product p) { return repo.save(p); }
  public Product get(Long id) { return repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found")); }
  public Product update(Long id, Product in) {
    Product p = get(id);
    if (in.getName() != null) p.setName(in.getName());
    if (in.getDescription() != null) p.setDescription(in.getDescription());
    if (in.getPrice() != null) p.setPrice(in.getPrice());
    if (in.getStock() != null) p.setStock(in.getStock());
    if (in.getCategory() != null) p.setCategory(in.getCategory());
    if (in.getImageUrl() != null) p.setImageUrl(in.getImageUrl());
    if (in.getRating() != null) p.setRating(in.getRating());
    return repo.save(p);
  }
  public void delete(Long id) { repo.delete(get(id)); }
  public Page<Product> list(String category, Double min, Double max, Pageable pageable) {
    if (min != null && max != null) return repo.findByPriceBetween(min, max, pageable);
    if (category != null && !category.isBlank()) return repo.findByCategoryContainingIgnoreCase(category, pageable);
    return repo.findAll(pageable);
  }
}
