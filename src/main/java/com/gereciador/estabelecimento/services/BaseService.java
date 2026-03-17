package com.gereciador.estabelecimento.services;

import java.util.List;

public interface BaseService<S, T, P> {
    S save(T obj);
    S update(P primaryKey, T obj);
    void delete(P primaryKey);
    S getById(P primaryKey);
    List<S> getAll();
}
