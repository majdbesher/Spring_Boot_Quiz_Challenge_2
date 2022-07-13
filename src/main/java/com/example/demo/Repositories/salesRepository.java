package com.example.demo.Repositories;

import com.example.demo.Entities.sales;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface salesRepository extends CrudRepository<sales, Integer> {
    sales findById(int id);

    ArrayList<sales> findAll();
}
