package com.example.demo.Repositories;

import com.example.demo.Entities.sales_details;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface sales_detailsRepository extends CrudRepository<sales_details, Integer> {
    sales_details findByProduct_idAndSale_id(int product_id, int sale_id);

    ArrayList<sales_details> findAllBySale_id(int sale_id);
}
