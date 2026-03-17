package com.gereciador.estabelecimento.controllers;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gereciador.estabelecimento.annotations.WebMvcTestWithoutSecurity;
import com.gereciador.estabelecimento.controllers.dto.request.ClienteRequestDTO;
import com.gereciador.estabelecimento.controllers.dto.response.ClienteResponseDTO;
import com.gereciador.estabelecimento.services.ClienteService;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTestWithoutSecurity(controllers = ClienteController.class)
class ClienteControllerTest {
     @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ClienteService clienteService;

    @Autowired
    private MockMvc mockMvc;

    private static final String URI = "/clientes";
    private static final String URI_WITH_ID = URI + "/{id}";

    @Test
    void shouldCreateClienteSuccessfully() throws Exception{
        ClienteRequestDTO payload = new ClienteRequestDTO(
            "Cliente",
            "000.000.000-00",
            List.of());
        ClienteResponseDTO response = new ClienteResponseDTO(
            1L,
            payload.nome(),
            payload.cpf(),
            payload.contatos(), 
            List.of()
    );

    when(clienteService.save(payload)).thenReturn(response);

    mockMvc.perform(post(URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(payload)))

            .andExpect(jsonPath("$.id").value(response.id()))
            .andExpect(jsonPath("$.nome").value(response.nome()))
            .andExpect(jsonPath("$.cpf").value(response.cpf()))
            .andExpect(jsonPath("$.contatos").isEmpty())
            .andExpect(jsonPath("$.pagamentos").isEmpty());
    }

    @Test
    void shouldFindByIdClienteSuccessfully() throws Exception{
        ClienteResponseDTO response = new ClienteResponseDTO(
                1L,
                "Cliente",
                "000.000.000-00",
                List.of(),
                List.of()
        );

        when(clienteService.getById(response.id())).thenReturn(response);

        mockMvc.perform(get(URI_WITH_ID, response.id()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(response.id()))
                .andExpect(jsonPath("$.nome").value(response.nome()))
                .andExpect(jsonPath("$.cpf").value(response.cpf()))
                .andExpect(jsonPath("$.contatos").isEmpty());
    }

    @Test
    void shouldFindAllClienteSuccessfully() throws Exception {
        ClienteResponseDTO dto = new ClienteResponseDTO(
                1L,
                "test1",
                "000.000.000",
                List.of(),
                List.of()
        );
        ClienteResponseDTO dto2 = new ClienteResponseDTO(
                1L,
                "test2",
                "111.111.111",
                List.of(),
                List.of()
        );

        List<ClienteResponseDTO> dtos = Arrays.asList(dto,dto2);

        when(clienteService.getAll()).thenReturn(dtos);

        mockMvc.perform(get(URI))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(dtos.size()))
                .andExpect(jsonPath("$[0].id").value(dto.id()))
                .andExpect(jsonPath("$[0].nome").value(dto.nome()))
                .andExpect(jsonPath("$[0].cpf").value(dto.cpf()))
                .andExpect(jsonPath("$[0].contatos").isEmpty())
                .andExpect(jsonPath("$[0].pagamentos").isEmpty())

                .andExpect(jsonPath("$[1].id").value(dto2.id()))
                .andExpect(jsonPath("$[1].nome").value(dto2.nome()))
                .andExpect(jsonPath("$[1].cpf").value(dto2.cpf()))
                .andExpect(jsonPath("$[1].contatos").isEmpty())
                .andExpect(jsonPath("$[1].pagamentos").isEmpty());
    }

    @Test
    void shouldDeleteClienteSuccessfully() throws Exception{
        Long id = 1L;

        mockMvc.perform(delete(URI_WITH_ID, id))
                .andExpect(status().isNoContent());

        verify(clienteService, times(1)).delete(id);
    }

    @Test
    void shouldUpdateClienteSuccessfully() throws Exception {
        Long id = 1L;
        ClienteRequestDTO update = new ClienteRequestDTO(
                "test",
                "0000.000.000",
                List.of("900000000")
        );

        ClienteResponseDTO response = new ClienteResponseDTO(
                id,
                update.nome(),
                update.cpf(),
                update.contatos(),
                List.of()
        );

        when(clienteService.update(id,update)).thenReturn(response);

        mockMvc.perform(patch(URI_WITH_ID, response.id())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(update)))

                .andExpect(jsonPath("$.id").value(response.id()))
                .andExpect(jsonPath("$.nome").value(response.nome()))
                .andExpect(jsonPath("$.cpf").value(response.cpf()))
                .andExpect(jsonPath("$.contatos").value(response.contatos().getFirst()));
    }
}