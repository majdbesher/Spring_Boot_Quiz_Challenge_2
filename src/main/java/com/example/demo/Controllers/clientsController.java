package com.example.demo.Controllers;

import com.example.demo.Entities.clients;
import com.example.demo.Services.clientsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/clients")
public class clientsController {

    @Autowired
    clientsService clientsService;

    @RequestMapping(value = "/createClient", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> createClient(@RequestBody clients client) {
        try {
            if (client.getName() != null && client.getLast_name() != null && client.getMobile() != null) {
                return ResponseEntity.ok(Map.ofEntries(
                        Map.entry("message", "client created successfully"),
                        Map.entry("createdClient", clientsService.createClient(client))
                ));
            }
            else {
                return ResponseEntity.ok(Map.ofEntries(
                        Map.entry("message", "Error,name,last_name,mobile fields are required!")
                ));
            }
        }
        catch (Exception e)
        {
            if (e.getMessage().contains("could not execute statement")) {
                return ResponseEntity.ok(Map.ofEntries(
                        Map.entry("message", "Error,there is another client with the same name,last_name,mobile!")
                ));
            } else {
                return ResponseEntity.ok(Map.ofEntries(
                        Map.entry("message", "Error,check the entered data then try again")
                ));
            }
        }
    }

    @RequestMapping(value = "/getClient/{id}", method = RequestMethod.GET)
    public ResponseEntity<Map<String, Object>> getClient(@PathVariable int id) {
        clients client = clientsService.getClient(id);
        if (client != null) {
            return ResponseEntity.ok(Map.ofEntries(

                    Map.entry("message", "client found"),
                    Map.entry("client", client)
            ));
        } else {
            return ResponseEntity.ok(Map.ofEntries(
                    Map.entry("message", "client " + id + " not found")
            ));
        }
    }

    @RequestMapping(value = "/getAllClients", method = RequestMethod.GET)
    public ResponseEntity<Map<String, Object>> getAllClients() {
        List<clients> clients = clientsService.getAllClients();
        if (!clients.isEmpty()) {
            return ResponseEntity.ok(Map.ofEntries(

                    Map.entry("message", "clients found"),
                    Map.entry("clients", clients)
            ));
        } else {
            return ResponseEntity.ok(Map.ofEntries(
                    Map.entry("message", "there are no clients yet!")
            ));
        }
    }

    @RequestMapping(value = "/updateClient/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Map<String, Object>> updateClient(@RequestBody clients client, @PathVariable int id) {
        clients updatedClient = clientsService.updateClient(id, client);
        if (updatedClient.getId() != -1) {
            return ResponseEntity.ok(Map.ofEntries(
                    Map.entry("message", "client " + id + " updated successfully"),
                    Map.entry("updatedClient", updatedClient)
            ));
        } else {
            return ResponseEntity.ok(Map.ofEntries(
                    Map.entry("message", "client " + id + " not found")
            ));
        }
    }
}
