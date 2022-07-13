package com.example.demo.Repositories;

import com.example.demo.Entities.products;
import org.springframework.data.repository.CrudRepository;

public interface productsRepository extends CrudRepository<products, Integer> {

    products findById(int id);
}
