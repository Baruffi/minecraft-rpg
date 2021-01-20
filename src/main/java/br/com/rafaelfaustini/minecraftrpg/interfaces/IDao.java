package br.com.rafaelfaustini.minecraftrpg.interfaces;

import java.util.List;

public interface IDao<I,T> { //<Type of id, entity>

    T get(I id);

    List<T> getAll();

    void insert(T t);

    void update(T t);

    void delete(I id);
}