package com.gereciador.estabelecimento.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gereciador.estabelecimento.annotations.WebMvcTestWithoutSecurity;
import com.gereciador.estabelecimento.controllers.dto.request.CategoriaRequestDTO;
import com.gereciador.estabelecimento.controllers.dto.response.CategoriaResponseDTO;
import com.gereciador.estabelecimento.services.CategoriaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTestWithoutSecurity(controllers = CategoriaController.class)
class CategoriaControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private CategoriaService categoriaService;

    @Autowired
    private MockMvc mockMvc;

    private static final String URI = "/categorias";
    private static final String URI_WITH_ID = URI + "/{id}";

    @Test
    void shouldCreateCategoriaSuccessfully() throws Exception{
        CategoriaRequestDTO payload = new CategoriaRequestDTO("categoria");
        CategoriaResponseDTO response = new CategoriaResponseDTO(1L, payload.nome());

        when(categoriaService.save(payload)).thenReturn(response);

        mockMvc.perform(post(URI)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(payload)))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(response.id()))
                .andExpect(jsonPath("$.nome").value(response.nome()));
    }
    @Test
    void shouldFindByIdCategoriaSuccessfully() throws Exception {
        CategoriaResponseDTO response = new CategoriaResponseDTO(1L,"categoria");

        when(categoriaService.getById(response.id())).thenReturn(response);

        mockMvc.perform(get(URI_WITH_ID, response.id()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(response.id()))
                .andExpect(jsonPath("$.nome").value(response.nome()));
    }

    @Test
    void shouldFindAllCategoriasSuccessfully() throws Exception {
        CategoriaResponseDTO dto = new CategoriaResponseDTO(1L, "categoria1");
        CategoriaResponseDTO dto2 = new CategoriaResponseDTO(2L, "categoria2");

        List<CategoriaResponseDTO> dtos = Arrays.asList(dto, dto2);

        when(categoriaService.getAll()).thenReturn(dtos);

        mockMvc.perform(get(URI))
                .andExpect(jsonPath("$.length()").value(dtos.size()))
                .andExpect(jsonPath("$[0].id").value(dto.id()))
                .andExpect(jsonPath("$[0].nome").value(dto.nome()))

                .andExpect(jsonPath("$[1].id").value(dto2.id()))
                .andExpect(jsonPath("$[1].nome").value(dto2.nome()));
    }

    @Test
    void shouldDeleteCategoriasSuccessfully() throws Exception {
        Long id = 1L;

        mockMvc.perform(delete(URI_WITH_ID, id))
                .andExpect(status().isNoContent());

        verify(categoriaService, times(1)).delete(id);
    }

    @Test
    void shouldUpdateCategoriaSuccessfully() throws Exception {
        Long id = 1L;
        CategoriaRequestDTO update = new CategoriaRequestDTO("categoria");
        CategoriaResponseDTO response = new CategoriaResponseDTO(id, update.nome());

        when(categoriaService.update(id, update)).thenReturn(response);

        mockMvc.perform(patch(URI_WITH_ID, id)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(update)))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(response.id()))
                .andExpect(jsonPath("$.nome").value(response.nome()));
    }

    @Test
    void shouldFindByNomeCategoriaSuccessfully() throws Exception {
        CategoriaResponseDTO response = new CategoriaResponseDTO(1L,"categoria");

        when(categoriaService.findCategoriaByNome(response.nome())).thenReturn(response);

        mockMvc.perform(get(URI + "/filter").param("nome", response.nome()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(response.id()))
                .andExpect(jsonPath("$.nome").value(response.nome()));
    }
}


