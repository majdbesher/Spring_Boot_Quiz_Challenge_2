package com.example.demo.Repositories;

import com.example.demo.Entities.sellers;
import org.springframework.data.repository.CrudRepository;

public interface sellersRepository extends CrudRepository<sellers, Integer> {
    sellers findById(int id);
}
