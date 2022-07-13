package com.example.demo.Services;

import com.example.demo.Entities.clients;
import com.example.demo.Repositories.clientsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class clientsService {

    @Autowired
    clientsRepository clientsRepository;

    public clients createClient(clients client) {
        return clientsRepository.save(client);
    }

    public clients getClient(int id) {
        return clientsRepository.findById(id);
    }

    public List<clients> getAllClients() {
        return (List<clients>) clientsRepository.findAll();
    }

    public clients updateClient(int id, clients client) {
        if (clientsRepository.existsById(id)) {
            clients temp = clientsRepository.findById(id);
            if (client.getName() != null)
                temp.setName(client.getName());

            if (client.getLast_name() != null)
                temp.setLast_name(client.getLast_name());

            if (client.getMobile() != null)
                temp.setMobile(client.getMobile());

            return clientsRepository.save(temp);
        } else {
            clients c = new clients();
            c.setId(-1);
            return c;
        }
    }

    public boolean isExists(int id) {
        return clientsRepository.existsById(id);
    }
}
