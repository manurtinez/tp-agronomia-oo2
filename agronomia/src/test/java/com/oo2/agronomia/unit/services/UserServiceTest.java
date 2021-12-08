package com.oo2.agronomia.unit.services;

import com.oo2.agronomia.models.Purchase;
import com.oo2.agronomia.models.User;
import com.oo2.agronomia.repositories.PurchaseRepository;
import com.oo2.agronomia.repositories.UserRepository;
import com.oo2.agronomia.services.PurchaseService;
import com.oo2.agronomia.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;


import java.util.List;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@Transactional
public class UserServiceTest {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Test
    public void createPurchaseTest() {
        User newUser = new User("manu", "manu@manu.com", "1234");
        userRepository.save(newUser);

        List<User> userList = userService.findAll();
        assertEquals(1, userList.size());
        User retrieveUser = userList.get(0);
        assertEquals("manu", retrieveUser.getName());
        assertEquals("manu@manu.com", retrieveUser.getEmail());
    }
}
