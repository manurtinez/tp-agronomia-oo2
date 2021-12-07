package com.oo2.agronomia.unit.controllers;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oo2.agronomia.controllers.UserController;
import com.oo2.agronomia.models.User;
import com.oo2.agronomia.services.UserService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @Test
    void getAllUsers() throws Exception {
        // Crear usuarios de ejemplo
        List<User> users = new ArrayList<User>();
        users.add(new User("manu", "manu@manu.com", "1234"));
        users.add(new User("manu2", "manu2@manu.com", "1234"));

        // Mockear la accion del service, ya que solo interesa que ande el controller
        // Devuelve la lista de usuarios
        when(userService.findAll()).thenReturn(users);

        // Realizar request a /user/todos y chequear que se hayan devuelto 2 usuarios
        // como era esperado
        mockMvc.perform(get("/user/todos"))
                // .andDo(print())
                .andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("manu")));
    }

    @Test
    void addNewUserTest() throws Exception {
        User newUser = new User("manu", "manu@manu.com", "1234");

        // Mockear la accion del service, ya que solo interesa que ande el controller
        when(userService.addUser(newUser.getName(), newUser.getEmail(), newUser.getPassword())).thenReturn(newUser);

        // Generar un request para un usuario nuevo, y obtener el response
        MockHttpServletResponse response = mockMvc
                .perform(post("/user/nuevo").contentType("application/json").param("name", newUser.getName())
                        .param("email", newUser.getEmail()).param("password", newUser.getPassword())
                        .content(objectMapper.writeValueAsString(newUser)))
                .andReturn().getResponse();

        // Chequear que el response contiene el usuario recien creado, con los mismos
        // datos
        assertEquals(201, response.getStatus());
        User assertUser = objectMapper.readValue(response.getContentAsString(), User.class);
        assertEquals(newUser.getName(), assertUser.getName());
        assertEquals(newUser.getEmail(), assertUser.getEmail());
    }
}
