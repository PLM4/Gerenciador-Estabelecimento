package com.gereciador.estabelecimento.services;

import com.gereciador.estabelecimento.controllers.dto.request.CategoriaRequestDTO;
import com.gereciador.estabelecimento.controllers.dto.response.CategoriaResponseDTO;
import com.gereciador.estabelecimento.entities.Categoria;
import com.gereciador.estabelecimento.exceptions.CategoriaNotFoundException;
import com.gereciador.estabelecimento.mapper.CategoriaMapper;
import com.gereciador.estabelecimento.repositories.CategoriaRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService implements BaseService<CategoriaResponseDTO, CategoriaRequestDTO, Long> {

    private final CategoriaRepository repository;
    private final CategoriaMapper mapper;

    public CategoriaService(CategoriaRepository repository, CategoriaMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public CategoriaResponseDTO save(CategoriaRequestDTO obj) {
        Categoria categoria = this.repository.save(this.mapper.toEntity(obj));
        return this.mapper.toDTO(categoria);
    }

    @Override
    public CategoriaResponseDTO update(Long primaryKey, CategoriaRequestDTO obj) {
        Categoria categoriaUpdated = this.repository.findById(primaryKey)
            .orElseThrow(CategoriaNotFoundException::new);
        if (obj.nome() != null) categoriaUpdated.setNome(obj.nome());
        Categoria categoria = this.repository.save(categoriaUpdated);
        return this.mapper.toDTO(categoria);
    }

    @Override
    @Transactional
    public void delete(Long primaryKey) {
        this.repository.deleteById(primaryKey);
    }

    @Override
    public CategoriaResponseDTO getById(Long primaryKey) {
        Categoria categoria = this.repository.findById(primaryKey).orElseThrow(CategoriaNotFoundException::new);
        return this.mapper.toDTO(categoria);
    }

    @Override
    public List<CategoriaResponseDTO> getAll() {
        List<Categoria> categorias = this.repository.findAll();
        return categorias.stream()
                .map(this.mapper::toDTO)
                .toList();
    }

    public CategoriaResponseDTO findCategoriaByNome(String nome) {
        Categoria categoria = this.repository.findCategoriaByNome(nome).orElseThrow(CategoriaNotFoundException::new);
        return this.mapper.toDTO(categoria);
    }
}
