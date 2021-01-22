package br.com.rafaelfaustini.minecraftrpg.interfaces;

import java.util.List;

public interface IDao<I, T> { // <Type of id, entity>

    public void createTable() throws Exception;

    public T get(I id) throws Exception;

    public List<T> getAll() throws Exception;

    public void insert(T t) throws Exception;

    public void update(T t) throws Exception;

    public void delete(I id) throws Exception;
}