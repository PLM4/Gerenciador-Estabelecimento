package com.gereciador.estabelecimento.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.gereciador.estabelecimento.annotations.WebMvcTestWithoutSecurity;
import com.gereciador.estabelecimento.controllers.dto.request.ProdutoRequestDTO;
import com.gereciador.estabelecimento.controllers.dto.response.CategoriaResponseDTO;
import com.gereciador.estabelecimento.controllers.dto.response.FornecedorResponseDTO;
import com.gereciador.estabelecimento.controllers.dto.response.ProdutoResponseDTO;
import com.gereciador.estabelecimento.services.ProdutoService;
import org.jspecify.annotations.NonNull;
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

@WebMvcTestWithoutSecurity(controllers = { ProdutoController.class })
class ProdutoControllerTest {

  @Autowired
  private ObjectMapper objectMapper;
  @MockitoBean
  private ProdutoService produtoService;
  @Autowired
  private MockMvc mockMvc;
  private static final String URI = "/produtos";
  private static final String URI_WITH_ID = URI + "/{id}";

  @Test
  void shouldCreateProdutoSuccessfully() throws Exception {
    ProdutoRequestDTO payload = new ProdutoRequestDTO(
        "produto",
        List.of(1L),
        BigDecimal.valueOf(12),
        10,
        List.of(3L),
        LocalDate.now()
    );

    ProdutoResponseDTO response = createProdutoResponseDTO(payload);

    when(produtoService.save(payload)).thenReturn(response);

    mockMvc.perform(post(URI)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(payload)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(response.id()))
        .andExpect(jsonPath("$.nome").value(response.nome()))
        .andExpect(jsonPath("$.categorias").isArray())
        .andExpect(jsonPath("$.categorias").isNotEmpty())
        .andExpect(jsonPath("$.preco").value(response.preco()))
        .andExpect(jsonPath("$.quantidade").value(response.quantidade()))
        .andExpect(jsonPath("$.fornecedores").isArray())
        .andExpect(jsonPath("$.fornecedores").isNotEmpty())
        .andExpect(jsonPath("$.validade").value(response.validade().toString()));
  }

  @Test
  void shouldUpdateProdutoSuccessfully() throws Exception {
    Long id = 1L;
    ProdutoRequestDTO payload = new ProdutoRequestDTO(
        "produto",
        List.of(1L),
        BigDecimal.valueOf(12),
        10,
        List.of(3L),
        LocalDate.now()
    );

    ProdutoResponseDTO response = createProdutoResponseDTO(payload);

    when(produtoService.update(id, payload)).thenReturn(response);

    mockMvc.perform(patch(URI_WITH_ID, id)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(payload)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(response.id()))
        .andExpect(jsonPath("$.nome").value(response.nome()))
        .andExpect(jsonPath("$.categorias").isArray())
        .andExpect(jsonPath("$.categorias").isNotEmpty())
        .andExpect(jsonPath("$.preco").value(response.preco()))
        .andExpect(jsonPath("$.quantidade").value(response.quantidade()))
        .andExpect(jsonPath("$.fornecedores").isArray())
        .andExpect(jsonPath("$.fornecedores").isNotEmpty())
        .andExpect(jsonPath("$.validade").value(response.validade().toString()));
  }


  @Test
  void shouldFindByIdProdutoSuccessfully() throws Exception {
    Long id = 1L;
    ProdutoRequestDTO produto = new ProdutoRequestDTO(
        "produto",
        List.of(1L),
        BigDecimal.valueOf(12),
        10,
        List.of(3L),
        LocalDate.now()
    );

    ProdutoResponseDTO response = createProdutoResponseDTO(produto);

    when(produtoService.getById(id)).thenReturn(response);

    mockMvc.perform(get(URI_WITH_ID, id))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(response.id()))
        .andExpect(jsonPath("$.nome").value(response.nome()))
        .andExpect(jsonPath("$.categorias").isArray())
        .andExpect(jsonPath("$.categorias").isNotEmpty())
        .andExpect(jsonPath("$.preco").value(response.preco()))
        .andExpect(jsonPath("$.quantidade").value(response.quantidade()))
        .andExpect(jsonPath("$.fornecedores").isArray())
        .andExpect(jsonPath("$.fornecedores").isNotEmpty())
        .andExpect(jsonPath("$.validade").value(response.validade().toString()));
  }

  @Test
  void shouldFindALlProdutoSuccessfully() throws Exception {
    ProdutoRequestDTO produto1 = new ProdutoRequestDTO(
        "produto1",
        List.of(1L),
        BigDecimal.valueOf(12),
        10,
        List.of(3L),
        LocalDate.now()
    );

    ProdutoRequestDTO produto2 = new ProdutoRequestDTO(
        "produto2",
        List.of(2L),
        BigDecimal.valueOf(278),
        22,
        List.of(8L),
        LocalDate.now()
    );

    ProdutoResponseDTO response1 = createProdutoResponseDTO(produto1);
    ProdutoResponseDTO response2 = createProdutoResponseDTO(produto2);

    when(produtoService.getAll()).thenReturn(List.of(response1, response2));

    mockMvc.perform(get(URI))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[*].id").value(containsInAnyOrder(response1.id().intValue(), response2.id().intValue())))
        .andExpect(jsonPath("$[*].nome").value(containsInAnyOrder(response1.nome(), response2.nome())))
        .andExpect(jsonPath("$[*].categorias").isArray())
        .andExpect(jsonPath("$[*].categorias").isNotEmpty())
        .andExpect(jsonPath("$[*].preco").value(containsInAnyOrder(response1.preco().intValue(), response2.preco().intValue())))
        .andExpect(jsonPath("$[*].quantidade").value(containsInAnyOrder(response1.quantidade(), response2.quantidade())))
        .andExpect(jsonPath("$[*].fornecedores").isArray())
        .andExpect(jsonPath("$[*].fornecedores").isNotEmpty())
        .andExpect(jsonPath("$[*].validade").value(containsInAnyOrder(response1.validade().toString(), response2.validade().toString())));
  }

  @Test
  void shouldDeleteProdutoSuccessfully() throws Exception {
    Long id = 1L;

    mockMvc.perform(delete(URI_WITH_ID, id))
        .andExpect(status().isNoContent());

    verify(produtoService, times(1)).delete(id);
  }

  private static @NonNull ProdutoResponseDTO createProdutoResponseDTO(ProdutoRequestDTO payload) {
    CategoriaResponseDTO categoriaResponse = new CategoriaResponseDTO(payload.idsCategorias().getFirst(), "categoria");
    FornecedorResponseDTO fornecedorResponse = new FornecedorResponseDTO(payload.idsFornecedores().getFirst(), "fornecedor", "11937435463562", Collections.emptyList());

    return new ProdutoResponseDTO(
        1L,
        payload.nome(),
        List.of(categoriaResponse),
        payload.preco(),
        payload.quantidade(),
        List.of(fornecedorResponse),
        payload.validade()
    );
  }

}