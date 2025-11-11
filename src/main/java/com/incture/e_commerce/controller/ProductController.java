package com.incture.e_commerce.controller;
import com.incture.e_commerce.entity.Product;
import com.incture.e_commerce.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
public class ProductController {
  private final ProductService productService;
  public ProductController(ProductService productService) { this.productService = productService; }

  @PostMapping
  public ResponseEntity<Product> create(@RequestBody Product p) { return ResponseEntity.ok(productService.create(p)); }

  @GetMapping
  public ResponseEntity<Page<Product>> list(@RequestParam(required = false) String category,
                                            @RequestParam(required = false) Double min,
                                            @RequestParam(required = false) Double max,
                                            @RequestParam(defaultValue = "0") int page,
                                            @RequestParam(defaultValue = "10") int size) {
    return ResponseEntity.ok(productService.list(category, min, max, PageRequest.of(page, size)));
  }

  @GetMapping("/{id}") public ResponseEntity<Product> get(@PathVariable Long id) { return ResponseEntity.ok(productService.get(id)); }

  @PutMapping("/{id}") public ResponseEntity<Product> update(@PathVariable Long id, @RequestBody Product in) { return ResponseEntity.ok(productService.update(id, in)); }

  @DeleteMapping("/{id}") public ResponseEntity<?> delete(@PathVariable Long id) { productService.delete(id); return ResponseEntity.noContent().build(); }
}
