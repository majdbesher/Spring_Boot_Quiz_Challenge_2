package com.example.demo.Controllers;

import com.example.demo.Entities.sellers;
import com.example.demo.Services.sellersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/sellers")
public class sellersController {

    @Autowired
    sellersService sellersService;

    @RequestMapping(value = "/createSeller", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> createClient(@RequestBody sellers seller) {
        return ResponseEntity.ok(Map.ofEntries(
                Map.entry("message", "seller created successfully"),
                Map.entry("createdSeller", sellersService.createSeller(seller))
        ));
    }

    @RequestMapping(value = "/getSeller/{id}", method = RequestMethod.GET)
    public ResponseEntity<Map<String, Object>> getSeller(@PathVariable int id) {
        sellers seller = sellersService.getSeller(id);
        if (seller != null) {
            return ResponseEntity.ok(Map.ofEntries(

                    Map.entry("message", "seller found"),
                    Map.entry("seller", seller)
            ));
        } else {
            return ResponseEntity.ok(Map.ofEntries(
                    Map.entry("message", "seller " + id + " not found")
            ));
        }
    }
}
