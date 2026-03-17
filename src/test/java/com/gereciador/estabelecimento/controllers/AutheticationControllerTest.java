package com.gereciador.estabelecimento.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gereciador.estabelecimento.controllers.dto.request.LoginResquestDTO;
import com.gereciador.estabelecimento.controllers.dto.request.UserRequestDTO;
import com.gereciador.estabelecimento.controllers.dto.response.UserResponseDTO;
import com.gereciador.estabelecimento.entities.User;
import com.gereciador.estabelecimento.enums.UserRole;
import com.gereciador.estabelecimento.services.UserService;
import com.gereciador.estabelecimento.util.TokenUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AutheticationController.class)
@AutoConfigureMockMvc(addFilters = false)
class AutheticationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private AuthenticationManager authenticationManager;

    @MockitoBean
    private UserService userService;

    @MockitoBean
    private TokenUtil tokenUtil;

    private static final String URI_LOGIN = "/auth/login";
    private static final String URI_REGISTER = "/auth/register";

    @Test
    void shouldLoginSuccessfully() throws Exception {
        LoginResquestDTO payload = new LoginResquestDTO("usuario_teste", "senha123");
        String tokenGerado = "token_jwt_simulado_123";
        
        Authentication authentication = mock(Authentication.class);
        User user = new User(); 

        when(authentication.getPrincipal()).thenReturn(user);
        when(authentication.isAuthenticated()).thenReturn(true);
        
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(tokenUtil.generateToken(any(User.class)))
                .thenReturn(tokenGerado);

        mockMvc.perform(post(URI_LOGIN)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(payload)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.jwtToken").value(tokenGerado));
    }

    @Test
    void shouldFailLoginWhenAuthenticationFails() throws Exception {
        LoginResquestDTO payload = new LoginResquestDTO("usuario", "senha_errada");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new RuntimeException("Falha na autenticação"));

        mockMvc.perform(post(URI_LOGIN)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(payload)))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void shouldRegisterUserSuccessfully() throws Exception {
        UserRequestDTO payload = new UserRequestDTO("novo_user", "senha123", UserRole.ADMIN);
        
        UserResponseDTO response = new UserResponseDTO(1L, "novo_user");

        when(userService.save(any(UserRequestDTO.class))).thenReturn(response);

        mockMvc.perform(post(URI_REGISTER)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(payload)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value(response.username()))
                .andExpect(jsonPath("$.Id").value(response.Id().intValue()));
    }

    @Test
    void shouldFailRegisterUserWhenServiceThrowsException() throws Exception {
        UserRequestDTO payload = new UserRequestDTO("novo_user", "senha123", UserRole.ADMIN);

        when(userService.save(any(UserRequestDTO.class)))
                .thenThrow(new RuntimeException("Erro ao salvar usuário"));

        mockMvc.perform(post(URI_REGISTER)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(payload)))
                .andExpect(status().isInternalServerError());
    }
}