package sge.repository;

import java.util.List;

/**
 * Define operações de armazenamento para entidades.
 *
 * @param <T> tipo de entidade armazenada
 */
public interface Repositorio<T> {
    /**
     * Armazena uma entidade.
     *
     * @param entidade entidade a armazenar, não nula
     * @throws IllegalArgumentException se a entidade for nula
     */
    void salvar(T entidade);

    /**
     * Lista todas as entidades armazenadas.
     *
     * @return cópia não modificável das entidades armazenadas
     */
    List<T> listarTodos();
}
