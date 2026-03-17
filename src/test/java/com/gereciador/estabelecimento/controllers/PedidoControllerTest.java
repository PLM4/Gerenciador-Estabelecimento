package com.gereciador.estabelecimento.controllers;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gereciador.estabelecimento.annotations.WebMvcTestWithoutSecurity;
import com.gereciador.estabelecimento.controllers.dto.request.PedidoRequestDTO;
import com.gereciador.estabelecimento.controllers.dto.response.PedidoResponseDTO;
import com.gereciador.estabelecimento.enums.Status;
import com.gereciador.estabelecimento.services.PedidoService;

@WebMvcTestWithoutSecurity(controllers = PedidoController.class)
public class PedidoControllerTest {
     @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private PedidoService pedidoService;

    @Autowired
    private MockMvc mockMvc;

    private static final String URI = "/pedidos";
    private static final String URI_WITH_ID = URI + "/{id}";

    @Test
    void shouldCreatePedidoSuccessfully() throws Exception {

        PedidoRequestDTO payload = new PedidoRequestDTO(
                List.of()
        );

        PedidoResponseDTO response = new PedidoResponseDTO(
                1L,
                List.of(),                 
                LocalDate.of(2025, 1, 9),
                Status.INICIALIZADO          
        );

        when(pedidoService.save(payload)).thenReturn(response);

        mockMvc.perform(post(URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(payload)))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(response.id()))
                .andExpect(jsonPath("$.itensPedido").isArray())
                .andExpect(jsonPath("$.itensPedido").isEmpty())
                .andExpect(jsonPath("$.data").value("2025-01-09"))
                .andExpect(jsonPath("$.status").value("INICIALIZADO"));
    }

    @Test
    void shouldFindByIdPedidoSuccessfully() throws Exception{
        PedidoResponseDTO response = new PedidoResponseDTO(
            1L,
            List.of(),                 
            LocalDate.of(2025, 1, 9),
            Status.INICIALIZADO   
        );

        when(pedidoService.getById(response.id())).thenReturn(response);

        mockMvc.perform(get(URI_WITH_ID, response.id()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(response.id()))
                .andExpect(jsonPath("$.itensPedido").isArray())
                .andExpect(jsonPath("$.itensPedido").isEmpty())
                .andExpect(jsonPath("$.data").value("2025-01-09"))
                .andExpect(jsonPath("$.status").value("INICIALIZADO"));
    }

    @Test
    void shouldFindAllPedidoSuccessfully() throws Exception {
        PedidoResponseDTO dto = new PedidoResponseDTO(
                1L,
                List.of(),
                LocalDate.of(2025, 1, 9),
                Status.INICIALIZADO
        );

        PedidoResponseDTO dto2 = new PedidoResponseDTO(
                2L,
                List.of(),
                LocalDate.of(2025, 1, 27),
                Status.INICIALIZADO
        );

        List<PedidoResponseDTO> dtos = Arrays.asList(dto, dto2);

        when(pedidoService.getAll()).thenReturn(dtos);

        mockMvc.perform(get(URI))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(dtos.size()))

                .andExpect(jsonPath("$[0].id").value(dto.id()))
                .andExpect(jsonPath("$[0].itensPedido").isArray())
                .andExpect(jsonPath("$[0].itensPedido").isEmpty())
                .andExpect(jsonPath("$[0].data").value("2025-01-09"))
                .andExpect(jsonPath("$[0].status").value("INICIALIZADO"))

                .andExpect(jsonPath("$[1].id").value(dto2.id()))
                .andExpect(jsonPath("$[1].itensPedido").isArray())
                .andExpect(jsonPath("$[1].itensPedido").isEmpty())
                .andExpect(jsonPath("$[1].data").value("2025-01-27"))
                .andExpect(jsonPath("$[1].status").value("INICIALIZADO"));
    }

    @Test
    void shouldDeletePedidoSuccessfully() throws Exception{
        Long id = 1L;

        mockMvc.perform(delete(URI_WITH_ID, id))
                .andExpect(status().isNoContent());

        verify(pedidoService, times(1)).delete(id);
    }

    @Test
    void shouldUpdatePedidoSuccessfully() throws Exception {
        Long id = 1L;

        PedidoRequestDTO update = new PedidoRequestDTO(
                List.of()
        );

        PedidoResponseDTO response = new PedidoResponseDTO(
                id,
                List.of(),
                LocalDate.of(2025, 1, 9),
                Status.INICIALIZADO
        );

        when(pedidoService.update(id, update)).thenReturn(response);

        mockMvc.perform(patch(URI_WITH_ID, id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(update)))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(response.id()))
                .andExpect(jsonPath("$.itensPedido").isArray())
                .andExpect(jsonPath("$.itensPedido").isEmpty())
                .andExpect(jsonPath("$.data").value("2025-01-09"))
                .andExpect(jsonPath("$.status").value("INICIALIZADO"));
    }
}
