package com.example.demo.Controllers;

import com.example.demo.Entities.sales;
import com.example.demo.Entities.sales_details;
import com.example.demo.Services.clientsService;
import com.example.demo.Services.salesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/sales")
public class salesController {

    @Autowired
    salesService salesService;

    @Autowired
    clientsService clientsService;

    @RequestMapping(value = "/createSale", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> createSale(@RequestBody sales sale) {
        if (clientsService.isExists(sale.getClient().getId())) {
            sales createdSale = salesService.createSale(sale);
            return ResponseEntity.ok(Map.ofEntries(
                    Map.entry("message", "sale created successfully"),
                    Map.entry("createdSale", createdSale)
            ));
        } else {
            return ResponseEntity.ok(Map.ofEntries(
                    Map.entry("message", "the client id " + sale.getClient().getId() + " is not associated with any existing client")
            ));
        }
    }

    @RequestMapping(value = "/updateSale/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Map<String, Object>> updateSale(@RequestBody sales_details details, @PathVariable int id) {
        if (salesService.isExists(id)) {
            sales updatedSale = salesService.updateSale(id, details);
            return ResponseEntity.ok(Map.ofEntries(
                    Map.entry("message", "sale updated successfully"),
                    Map.entry("updatedSale", updatedSale)
            ));
        } else {
            return ResponseEntity.ok(Map.ofEntries(
                    Map.entry("message", "the sale id " + id + " is not associated with any existing sale")
            ));
        }
    }

    @RequestMapping(value = "/getAllSales", method = RequestMethod.GET)
    public ResponseEntity<Map<String, Object>> getAllSales() {
        if (!salesService.isEmpty()) {
            return ResponseEntity.ok(Map.ofEntries(
                    Map.entry("message", "sales found"),
                    Map.entry("Sales", salesService.getAllSales())
            ));
        } else {
            return ResponseEntity.ok(Map.ofEntries(
                    Map.entry("message", "there are no sales operations yet! ")
            ));
        }
    }
}
