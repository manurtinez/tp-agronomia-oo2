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
        // Creo un usuario de cada tipo
        Client cli = userService.addClient("manu", "manu@manu.com", "1234", "address1");
        Producer producer = userService.addProducer("manuprod", "manuprod@manu.com", "1234", "1122");

        // El primer usuario deberia ser el cliente
        assertEquals("manu", cli.getName());
        assertEquals("manu@manu.com", cli.getEmail());
        assertEquals("address1", cli.getAddress());

        // El segundo el productor
        assertEquals("manuprod", producer.getName());
        assertEquals("manuprod@manu.com", producer.getEmail());
        assertEquals("1122", producer.getCuil());

    }

    @Test
    public void getAllUsersTest() {
        // Para fines de este test, creo 4 clientes
        for (int i = 0; i < 4; i++) {
            userService.addClient("name" + i, "email" + i, "1234", "address" + i);
        }

        // La lista deberia devolver 4 usuarios totales
        List<User> userList = userService.findAll();
        assertEquals(4, userList.size());
    }
}
