package com.gereciador.estabelecimento.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gereciador.estabelecimento.annotations.WebMvcTestWithoutSecurity;
import com.gereciador.estabelecimento.controllers.dto.request.PagamentoRequestDTO;
import com.gereciador.estabelecimento.controllers.dto.request.TipoPagamentoDTO;
import com.gereciador.estabelecimento.controllers.dto.response.ClienteResponseDTO;
import com.gereciador.estabelecimento.controllers.dto.response.PagamentoResponseDTO;
import com.gereciador.estabelecimento.enums.Status;
import com.gereciador.estabelecimento.enums.TipoPagamento;
import com.gereciador.estabelecimento.services.PagamentoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTestWithoutSecurity(controllers = { PagamentoController.class })
class PagamentoControllerTest {

  @Autowired
  private MockMvc mockMvc;
  @MockitoBean
  private PagamentoService pagamentoService;
  @Autowired
  private ObjectMapper objectMapper;
  private static final String URI = "/pagamentos";
  private static final String URI_WITH_ID = URI + "/{id}";

  @Test
  void shouldCreatePagamentoSuccessfully() throws Exception {
    PagamentoRequestDTO payload = new PagamentoRequestDTO(1L, 2L);
    ClienteResponseDTO clienteResponse = new ClienteResponseDTO(
        1L,
        "client1",
        "123-123-123-13",
        List.of("99-9999-998"),
        Collections.emptyList()
    );
    PagamentoResponseDTO response = new PagamentoResponseDTO(
        1L,
        BigDecimal.valueOf(123),
        LocalDate.now(),
        clienteResponse,
        TipoPagamento.PIX,
        Status.INICIALIZADO
    );

    when(pagamentoService.save(payload)).thenReturn(response);

    mockMvc.perform(post(URI)
          .contentType(MediaType.APPLICATION_JSON)
          .content(objectMapper.writeValueAsString(payload)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(response.id().intValue()))
        .andExpect(jsonPath("$.valor").value(response.valor().intValue()))
        .andExpect(jsonPath("$.data").value(response.data().toString()))
        .andExpect(jsonPath("$.cliente.id").value(clienteResponse.id().intValue()))
        .andExpect(jsonPath("$.cliente.nome").value(clienteResponse.nome()))
        .andExpect(jsonPath("$.cliente.contatos[0]").value(clienteResponse.contatos().getFirst()))
        .andExpect(jsonPath("$.cliente.pagamentos").isEmpty())
        .andExpect(jsonPath("$.tipoPagamento").value(response.tipoPagamento().toString()))
        .andExpect(jsonPath("$.status").value(response.status().toString()));
  }

  @Test
  void shouldUpdatePagamentoSuccessfully() throws Exception {
    Long id = 1L;
    PagamentoRequestDTO payload = new PagamentoRequestDTO(1L, 2L);
    ClienteResponseDTO clienteResponse = new ClienteResponseDTO(
        1L,
        "client1",
        "123-123-123-13",
        List.of("99-9999-998"),
        Collections.emptyList()
    );
    PagamentoResponseDTO response = new PagamentoResponseDTO(
        id,
        BigDecimal.valueOf(123),
        LocalDate.now(),
        clienteResponse,
        TipoPagamento.CARTAO,
        Status.INICIALIZADO
    );

    when(pagamentoService.update(id, payload)).thenReturn(response);

    mockMvc.perform(patch(URI_WITH_ID, id)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(payload)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(response.id().intValue()))
        .andExpect(jsonPath("$.valor").value(response.valor().intValue()))
        .andExpect(jsonPath("$.data").value(response.data().toString()))
        .andExpect(jsonPath("$.cliente.id").value(clienteResponse.id().intValue()))
        .andExpect(jsonPath("$.cliente.nome").value(clienteResponse.nome()))
        .andExpect(jsonPath("$.cliente.contatos[0]").value(clienteResponse.contatos().getFirst()))
        .andExpect(jsonPath("$.cliente.pagamentos").isEmpty())
        .andExpect(jsonPath("$.tipoPagamento").value(response.tipoPagamento().toString()))
        .andExpect(jsonPath("$.status").value(response.status().toString()));
  }

  @Test
  void shouldFindByIdPagamentoSuccessfully() throws Exception {
    Long id = 1L;
    ClienteResponseDTO clienteResponse = new ClienteResponseDTO(
        1L,
        "client1",
        "123-123-123-13",
        List.of("99-9999-998"),
        Collections.emptyList()
    );
    PagamentoResponseDTO response = new PagamentoResponseDTO(
        id,
        BigDecimal.valueOf(123),
        LocalDate.now(),
        clienteResponse,
        TipoPagamento.PIX,
        Status.INICIALIZADO
    );

    when(pagamentoService.getById(id)).thenReturn(response);

    mockMvc.perform(get(URI_WITH_ID, id))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(response.id().intValue()))
        .andExpect(jsonPath("$.valor").value(response.valor().intValue()))
        .andExpect(jsonPath("$.data").value(response.data().toString()))
        .andExpect(jsonPath("$.cliente.id").value(clienteResponse.id().intValue()))
        .andExpect(jsonPath("$.cliente.nome").value(clienteResponse.nome()))
        .andExpect(jsonPath("$.cliente.contatos[0]").value(clienteResponse.contatos().getFirst()))
        .andExpect(jsonPath("$.cliente.pagamentos").isEmpty())
        .andExpect(jsonPath("$.tipoPagamento").value(response.tipoPagamento().toString()))
        .andExpect(jsonPath("$.status").value(response.status().toString()));
  }

  @Test
  void shouldFindAllPagamentoSuccessfully() throws Exception {
    ClienteResponseDTO clienteResponse1 = new ClienteResponseDTO(
        1L,
        "client1",
        "123-123-123-13",
        List.of("99-9999-998"),
        Collections.emptyList()
    );
    PagamentoResponseDTO response1 = new PagamentoResponseDTO(
        1L,
        BigDecimal.valueOf(123),
        LocalDate.now(),
        clienteResponse1,
        TipoPagamento.PIX,
        Status.INICIALIZADO
    );

    ClienteResponseDTO clienteResponse2 = new ClienteResponseDTO(
        2L,
        "client2",
        "187-122-111-12",
        List.of("99-9967-998"),
        Collections.emptyList()
    );
    PagamentoResponseDTO response2 = new PagamentoResponseDTO(
        2L,
        BigDecimal.valueOf(234),
        LocalDate.now(),
        clienteResponse2,
        TipoPagamento.DINHEIRO,
        Status.INICIALIZADO
    );

    when(pagamentoService.getAll()).thenReturn(List.of(response1, response2));

    mockMvc.perform(get(URI))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[*].id").value(containsInAnyOrder(response1.id().intValue(), response2.id().intValue())))
        .andExpect(jsonPath("$[*].valor").value(containsInAnyOrder(response1.valor().intValue(), response2.valor().intValue())))
        .andExpect(jsonPath("$[*].data").value(containsInAnyOrder(response1.data().toString(), response2.data().toString())))
        .andExpect(jsonPath("$[*].cliente.id").value(containsInAnyOrder(clienteResponse1.id().intValue(), clienteResponse2.id().intValue())))
        .andExpect(jsonPath("$[*].cliente.nome").value(containsInAnyOrder(clienteResponse1.nome(), clienteResponse2.nome())))
        .andExpect(jsonPath("$[*].cliente.contatos").value(containsInAnyOrder(clienteResponse1.contatos(), clienteResponse2.contatos())))
        .andExpect(jsonPath("$[*].cliente.pagamentos[0]").isEmpty())
        .andExpect(jsonPath("$[*].cliente.pagamentos[1]").isEmpty())
        .andExpect(jsonPath("$[*].tipoPagamento").value(containsInAnyOrder(response1.tipoPagamento().toString(), response2.tipoPagamento().toString())))
        .andExpect(jsonPath("$[*].status").value(containsInAnyOrder(response1.status().toString(), response2.status().toString())));
  }

  @Test
  void shouldFinalizarPedidoSuccessfully() throws Exception {
    Long id = 1L;
    TipoPagamentoDTO payload = new TipoPagamentoDTO(TipoPagamento.PIX);
    ClienteResponseDTO clienteResponse = new ClienteResponseDTO(
        1L,
        "client1",
        "123-123-123-13",
        List.of("99-9999-998"),
        Collections.emptyList()
    );
    PagamentoResponseDTO response = new PagamentoResponseDTO(
        id,
        BigDecimal.valueOf(123),
        LocalDate.now(),
        clienteResponse,
        TipoPagamento.PIX,
        Status.FINALIZADO
    );

    when(pagamentoService.finalizarPedido(id, payload.tipoPagamento())).thenReturn(response);

    String uri = String.format("%s/finalizar/{id}", URI);
    mockMvc.perform(patch(uri, id)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(payload)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(response.id().intValue()))
        .andExpect(jsonPath("$.valor").value(response.valor().intValue()))
        .andExpect(jsonPath("$.data").value(response.data().toString()))
        .andExpect(jsonPath("$.cliente.id").value(clienteResponse.id().intValue()))
        .andExpect(jsonPath("$.cliente.nome").value(clienteResponse.nome()))
        .andExpect(jsonPath("$.cliente.contatos[0]").value(clienteResponse.contatos().getFirst()))
        .andExpect(jsonPath("$.cliente.pagamentos").isEmpty())
        .andExpect(jsonPath("$.tipoPagamento").value(response.tipoPagamento().toString()))
        .andExpect(jsonPath("$.status").value(response.status().toString()));
  }

  @Test
  void shouldDeletePagamentoSuccessfully() throws Exception {
    Long id = 1L;

    mockMvc.perform(delete(URI_WITH_ID, id))
        .andExpect(status().isNoContent());

    verify(pagamentoService, times(1)).delete(id);

  }
}