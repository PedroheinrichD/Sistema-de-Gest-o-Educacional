package sge.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Armazena entidades temporariamente em uma lista em memória.
 *
 * @param <T> tipo de entidade armazenada
 */
public class RepositorioEmMemoria<T> implements Repositorio<T> {
    private final List<T> entidades = new ArrayList<>();

    /**
     * Armazena uma entidade.
     *
     * @param entidade entidade a armazenar, não nula
     * @throws IllegalArgumentException se a entidade for nula
     */
    @Override
    public void salvar(T entidade) {
        if (entidade == null) {
            throw new IllegalArgumentException("A entidade é obrigatória.");
        }
        entidades.add(entidade);
    }

    /**
     * Lista todas as entidades armazenadas.
     *
     * @return cópia não modificável das entidades armazenadas
     */
    @Override
    public List<T> listarTodos() {
        return Collections.unmodifiableList(new ArrayList<>(entidades));
    }
}
