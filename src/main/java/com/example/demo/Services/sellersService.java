package com.example.demo.Services;

import com.example.demo.Entities.sellers;
import com.example.demo.Repositories.sellersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class sellersService {

    @Autowired
    sellersRepository sellersRepository;

    public sellers createSeller(sellers seller) {
        return sellersRepository.save(seller);
    }

    public sellers getSeller(int id) {
        return sellersRepository.findById(id);
    }

    public boolean isExists(int id) {
        return sellersRepository.existsById(id);
    }
}
