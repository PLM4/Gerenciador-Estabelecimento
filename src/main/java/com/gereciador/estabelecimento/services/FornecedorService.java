package com.gereciador.estabelecimento.services;

import com.gereciador.estabelecimento.controllers.dto.request.FornecedorRequestDTO;
import com.gereciador.estabelecimento.controllers.dto.response.FornecedorResponseDTO;
import com.gereciador.estabelecimento.entities.Fornecedor;
import com.gereciador.estabelecimento.exceptions.FornecedorNotFoundException;
import com.gereciador.estabelecimento.mapper.FornecedorMapper;
import com.gereciador.estabelecimento.repositories.FornecedorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FornecedorService implements BaseService<FornecedorResponseDTO, FornecedorRequestDTO, Long> {

    private final FornecedorRepository fornecedorRepository;
    private final FornecedorMapper mapper = new FornecedorMapper();

    public FornecedorService(FornecedorRepository fornecedorRepository) {
        this.fornecedorRepository = fornecedorRepository;
    }

    @Override
    @Transactional
    public FornecedorResponseDTO save(FornecedorRequestDTO obj){
        Fornecedor fornecedor = this.fornecedorRepository.save(this.mapper.toEntity(obj));
        return this.mapper.toDTO(fornecedor);
    }

    @Override
    public FornecedorResponseDTO update(Long primaryKey, FornecedorRequestDTO obj) {
        Fornecedor fornecedorUpdated = this.fornecedorRepository.findById(primaryKey).orElseThrow(FornecedorNotFoundException::new);
        if(obj.nome() != null) fornecedorUpdated.setNome(obj.nome());
        if (obj.cnpj() != null) fornecedorUpdated.setCnpj(obj.cnpj());
        Fornecedor fornecedor = this.fornecedorRepository.save(fornecedorUpdated);
        return this.mapper.toDTO(fornecedor);
    }

    @Override
    @Transactional
    public void delete(Long primaryKey) {
        this.fornecedorRepository.deleteById(primaryKey);
    }

    @Override
    public FornecedorResponseDTO getById(Long primaryKey) {
        Fornecedor fornecedor = this.fornecedorRepository.findById(primaryKey).orElseThrow(FornecedorNotFoundException::new);
        return this.mapper.toDTO(fornecedor);
    }

    @Override
    public List<FornecedorResponseDTO> getAll() {
        List<Fornecedor> fornecedor = this.fornecedorRepository.findAll();
        return fornecedor.stream()
                .map(this.mapper::toDTO)
                .toList();
    }
}
