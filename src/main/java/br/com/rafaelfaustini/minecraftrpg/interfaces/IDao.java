package br.com.rafaelfaustini.minecraftrpg.interfaces;

import java.util.List;

public interface IDao<T> {

    T get(long id);

    List<T> getAll();

    void insert(T t);

    void update(long id, T obj);

    void delete(long id);
}