package com.example.demo.Controllers;

import com.example.demo.Entities.sales;
import com.example.demo.Entities.sales_details;
import com.example.demo.Services.clientsService;
import com.example.demo.Services.productsService;
import com.example.demo.Services.salesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Map;

@RestController
@RequestMapping("/sales")
public class salesController {

    @Autowired
    salesService salesService;

    @Autowired
    clientsService clientsService;

    @Autowired
    productsService productsService;

    @RequestMapping(value = "/createSale", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> createSale(@RequestBody sales sale) {
        if (clientsService.isExists(sale.getClient().getId())) {
            int ok=1;
            for (sales_details s: sale.getDetailsList())
            {
                if (s.getQuantity()==0||!productsService.isExists(s.getProduct().getId()))
                {
                    ok=0;
                    break;
                }
            }
            if (ok==1) {
                sales createdSale = salesService.createSale(sale);
                return ResponseEntity.ok(Map.ofEntries(
                        Map.entry("message", "sale created successfully"),
                        Map.entry("createdSale", createdSale)
                ));
            }
            else {
                return ResponseEntity.ok(Map.ofEntries(
                        Map.entry("message", "Error, make sure that product_id belongs to existing product and quantity value specified for all products ")
                ));
            }
        } else {
            return ResponseEntity.ok(Map.ofEntries(
                    Map.entry("message", "Error, the client id " + sale.getClient().getId() + " is not associated with any existing client")
            ));
        }
    }

    @RequestMapping(value = "/updateSale/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Map<String, Object>> updateSale(@RequestBody ArrayList<sales_details> details, @PathVariable int id) {
        if (salesService.isExists(id)) {
            int ok = 1;
            for (sales_details s : details)
            {
                if (s.getPrice() != 0 || s.getQuantity() != 0) {
                    if(!salesService.isBelong(id,s))
                    {
                        ok=-1;
                        break;
                    }
                }
                else {
                    ok=0;
                    break;
                }
            }
            if(ok==1)
            {
                sales updatedSale = salesService.updateSale(id, details);
                return ResponseEntity.ok(Map.ofEntries(
                        Map.entry("message", "sale updated successfully"),
                        Map.entry("updatedSale", updatedSale)
                ));
            }
            else if(ok==0)
            {
                return ResponseEntity.ok(Map.ofEntries(
                        Map.entry("message", "Error, you must edit the value of the price or the quantity or both of them")
                ));
            }
            else {
                return ResponseEntity.ok(Map.ofEntries(
                        Map.entry("message", "Error, one or more product_id is not existed in the sale with id "+id)
                ));
            }
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
