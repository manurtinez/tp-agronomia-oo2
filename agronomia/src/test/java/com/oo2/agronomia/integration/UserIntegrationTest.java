package com.oo2.agronomia.integration;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.transaction.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oo2.agronomia.models.User;
import com.oo2.agronomia.repositories.UserRepository;
import com.oo2.agronomia.services.UserService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Esta clase define el test de integracion de usuario. Usa requests y
 * repositorios reales para probar que la interaccion desde arriba hasta abajo
 * funciona correctamente
 */
@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@Transactional
public class UserIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Test
    void registrarUsuarioTest() throws Exception {
        User user = new User("manu", "manu@manu.com", "1234");

        // Hacer un request a /user/nuevo con un nuevo usuario, y esperar que la
        // respuesta sea el nuevo usuario con sus datos
        mockMvc.perform(post("/user/nuevo").contentType("application/json").param("name", user.getName())
                .param("email", user.getEmail()).param("password", user.getPassword())
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isCreated()).andExpect(jsonPath("$.name", is("manu")))
                .andExpect(jsonPath("$.id", notNullValue())).andExpect(jsonPath("$.email", is("manu@manu.com")));

        // Comprobar que efectivamente el usuario fue persistido en la DB y puede ser
        // traido con el repositorio
        User createdUser = userRepository.findByName("manu");
        assertEquals(createdUser.getEmail(), "manu@manu.com");
        assertEquals(createdUser.getName(), "manu");
        assertNotNull(createdUser.getId());

    }

    @Test
    void traerTodosUsusariosTest() throws Exception {

        // Crear 2 usuarios de prueba usando el servicio directamente
        userService.addUser("manu1", "manu1@manu.com", "1234");
        userService.addUser("manu2", "manu2@manu.com", "1234");

        // Hacer request a /user/todos y comprobar que se traen todos los usuarios
        // creados en la DB, con los datos correctos
        mockMvc.perform(get("/user/todos")).andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].email", is("manu1@manu.com"))).andExpect(jsonPath("$[1].name", is("manu2")));
    }
}
