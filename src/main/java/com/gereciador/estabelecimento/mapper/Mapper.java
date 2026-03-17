package com.gereciador.estabelecimento.mapper;

public interface Mapper<T, S, E> {
    E toEntity(S dtoRequest);
    T toDTO(E entity);
}
