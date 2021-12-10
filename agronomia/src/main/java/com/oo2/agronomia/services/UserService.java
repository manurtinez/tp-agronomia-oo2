package com.oo2.agronomia.services;

import com.oo2.agronomia.models.Client;
import com.oo2.agronomia.models.Producer;
import com.oo2.agronomia.models.User;
import com.oo2.agronomia.repositories.User.BaseUserRepository;
import com.oo2.agronomia.repositories.User.ClientRepository;
import com.oo2.agronomia.repositories.User.ProducerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    ClientRepository clientRepository;

    @Autowired
    ProducerRepository producerRepository;

    @Autowired
    BaseUserRepository<User> userRepository;

    public UserService() {
    }

    public List<User> findAll() {
        return (List<User>) userRepository.findAll();
    }

    public Client addClient(String name, String email, String password, String address) {
        Client newClient = new Client(name, email, password, address);
        return clientRepository.save(newClient);
    }

    public Producer addProducer(String name, String email, String password, String cuil) {
        Producer newProd = new Producer(name, email, password, cuil);
        return producerRepository.save(newProd);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
