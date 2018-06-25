package it.dan.dao;

public interface InterfaceDAO<T> {
    void save(T obj);

    void update(T obj);

    T get(Object pk);

    void delete(Object pk);
}
