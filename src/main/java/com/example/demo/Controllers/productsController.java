package com.example.demo.Controllers;

import com.example.demo.Entities.products;
import com.example.demo.Services.productsService;
import com.example.demo.Services.sellersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/products")
public class productsController {

    @Autowired
    productsService productsService;

    @Autowired
    sellersService sellersService;

    @RequestMapping(value = "/createProduct", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> createProduct(@RequestBody products product) {
        if (sellersService.isExists(product.getSeller().getId())) {
            if (product.getPrice() != 0) {
                if (product.getName() != null && product.getDescription() != null && product.getCategory() != null) {
                    try {
                        products createdProduct = productsService.createProduct(product);
                        createdProduct.setSeller(sellersService.getSeller(product.getSeller().getId()));
                        return ResponseEntity.ok(Map.ofEntries(
                                Map.entry("message", "Product created successfully"),
                                Map.entry("createdProduct", createdProduct)
                        ));
                    } catch (Exception e) {
                        return ResponseEntity.ok(Map.ofEntries(
                                Map.entry("message", "Error, there is another product with the same name sold by the same seller")
                        ));
                    }
                } else {
                    return ResponseEntity.ok(Map.ofEntries(
                            Map.entry("message", "Error, name,description,category fields are required!")
                    ));
                }
            } else {
                return ResponseEntity.ok(Map.ofEntries(
                        Map.entry("message", "Error, you should enter the price of the product")
                ));
            }
        } else {
            return ResponseEntity.ok(Map.ofEntries(
                    Map.entry("message", "Error, the seller id " + product.getSeller().getId() + " is not associated with any existing seller")
            ));
        }
    }

    @RequestMapping(value = "/getProduct/{id}", method = RequestMethod.GET)
    public ResponseEntity<Map<String, Object>> getProduct(@PathVariable int id) {
        products product = productsService.getProduct(id);
        if (product != null) {
            return ResponseEntity.ok(Map.ofEntries(

                    Map.entry("message", "product found"),
                    Map.entry("product", product)
            ));
        } else {
            return ResponseEntity.ok(Map.ofEntries(
                    Map.entry("message", "Error, product " + id + " not found")
            ));
        }
    }

    @RequestMapping(value = "/getAllProducts", method = RequestMethod.GET)
    public ResponseEntity<Map<String, Object>> getAllProducts() {
        List<products> products = productsService.getAllProducts();
        if (!products.isEmpty()) {
            return ResponseEntity.ok(Map.ofEntries(

                    Map.entry("message", "products found"),
                    Map.entry("products", products)
            ));
        } else {
            return ResponseEntity.ok(Map.ofEntries(
                    Map.entry("message", "Error, there are no products yet!")
            ));
        }
    }

    @RequestMapping(value = "/updateProduct/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Map<String, Object>> updateProduct(@RequestBody products product, @PathVariable int id) {
        if (productsService.isExists(id)) {
            try {
                products updatedProduct = productsService.updateProduct(id, product);
                return ResponseEntity.ok(Map.ofEntries(
                        Map.entry("message", "product " + id + " updated successfully"),
                        Map.entry("updatedProduct", updatedProduct)
                ));
            } catch (Exception e) {
                return ResponseEntity.ok(Map.ofEntries(
                        Map.entry("message", "Error, there is another product with the same name sold by the same seller")
                ));
            }
        } else {
            return ResponseEntity.ok(Map.ofEntries(
                    Map.entry("message", "Error, product " + id + " not found")
            ));
        }
    }
}
