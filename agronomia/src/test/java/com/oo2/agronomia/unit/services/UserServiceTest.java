package com.oo2.agronomia.unit.services;

import com.oo2.agronomia.models.Client;
import com.oo2.agronomia.models.Producer;
import com.oo2.agronomia.models.User;
import com.oo2.agronomia.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@Transactional
public class UserServiceTest {
    @Autowired
    private UserService userService;

    @Test
    public void createUserTest() {
//        User newClient = new Client("manu", "manu@manu.com", "1234", "address1");
        userService.addClient("manu", "manu@manu.com", "1234", "address1");
//        User newProducer = new Producer("manuprod", "manuprod@manu.com", "1234", "1122");
        userService.addProducer("manuprod", "manuprod@manu.com", "1234", "1122");

        // La lista deberia devolver 2 usuarios totales
        List<User> userList = userService.findAll();
        assertEquals(2, userList.size());

        // El primer usuario deberia ser el cliente
        Client retrieveUser = (Client) userList.get(0);
        assertEquals("manu", retrieveUser.getName());
        assertEquals("manu@manu.com", retrieveUser.getEmail());
        assertEquals("address1", retrieveUser.getAddress());

        // El segundo el productor
        Producer retrieveUser2 = (Producer) userList.get(1);
        assertEquals("manuprod", retrieveUser2.getName());
        assertEquals("manuprod@manu.com", retrieveUser2.getEmail());
        assertEquals("1122", retrieveUser2.getCuil());
    }
}
