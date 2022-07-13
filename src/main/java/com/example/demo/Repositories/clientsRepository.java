package com.example.demo.Repositories;

import com.example.demo.Entities.clients;
import org.springframework.data.repository.CrudRepository;

public interface clientsRepository extends CrudRepository<clients, Integer> {
    clients findById(int id);
}
