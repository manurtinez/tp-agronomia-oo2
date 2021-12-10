package com.oo2.agronomia.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oo2.agronomia.models.Client;
import com.oo2.agronomia.models.User;
import com.oo2.agronomia.repositories.User.BaseUserRepository;
import com.oo2.agronomia.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
    private BaseUserRepository<User> userRepository;

    @Autowired
    private UserService userService;

    @Test
    void registrarUsuarioTest() throws Exception {
        Client user = new Client("manu", "manu@manu.com", "1234", "address1");

        // Hacer un request a /user/nuevo con un nuevo usuario, y esperar que la
        // respuesta sea el nuevo usuario con sus datos
        mockMvc.perform(post("/user/nuevo-cliente").contentType("application/json").param("name", user.getName())
                        .param("email", user.getEmail()).param("password", user.getPassword()).param("address", user.getAddress())
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isCreated()).andExpect(jsonPath("$.name", is("manu")))
                .andExpect(jsonPath("$.id", notNullValue())).andExpect(jsonPath("$.email", is("manu@manu.com")));

        // Comprobar que efectivamente el usuario fue persistido en la DB y puede ser
        // traido con el repositorio
        Client createdUser = (Client) userRepository.findByName("manu");
        assertEquals(createdUser.getEmail(), "manu@manu.com");
        assertEquals(createdUser.getName(), "manu");
        assertNotNull(createdUser.getId());

    }

    @Test
    void traerTodosUsusariosTest() throws Exception {

        // Crear 2 usuarios de prueba usando el servicio directamente
        userService.addClient("manu1", "manu1@manu.com", "1234", "address1");
        userService.addProducer("manu2", "manu2@manu.com", "1234", "cuil1");

        // Hacer request a /user/todos y comprobar que se traen todos los usuarios
        // creados en la DB, con los datos correctos
        mockMvc.perform(get("/user/todos")).andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].email", is("manu1@manu.com"))).andExpect(jsonPath("$[1].name", is("manu2")));
    }
}
