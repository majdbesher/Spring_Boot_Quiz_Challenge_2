package com.example.demo.Services;

import com.example.demo.Entities.products;
import com.example.demo.Repositories.productsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;

@Service
public class productsService {

    @Autowired
    productsRepository productsRepository;

    public products createProduct(products product) {
        product.setCreation_date(Calendar.getInstance().getTime());

        return productsRepository.save(product);
    }

    public products getProduct(int id) {
        return productsRepository.findById(id);
    }

    public List<products> getAllProducts() {
        return (List<products>) productsRepository.findAll();
    }

    public products updateProduct(int id, products product) {
        products temp = productsRepository.findById(id);

        if (product.getName() != null)
            temp.setName(product.getName());

        if (product.getDescription() != null)
            temp.setDescription(product.getDescription());

        if (product.getCategory() != null)
            temp.setCategory(product.getCategory());

        if (product.getPrice() != 0)
            temp.setPrice(product.getPrice());

        return productsRepository.save(temp);
    }

    public boolean isExists(int id) {
        return productsRepository.existsById(id);
    }
}
