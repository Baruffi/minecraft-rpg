package br.com.rafaelfaustini.minecraftrpg.interfaces;

import java.util.List;

public interface IDao<I, T> { // <Type of id, entity>

    T get(I id) throws Exception;

    List<T> getAll() throws Exception;

    void insert(T t) throws Exception;

    void update(T t) throws Exception;

    void delete(I id) throws Exception;
}