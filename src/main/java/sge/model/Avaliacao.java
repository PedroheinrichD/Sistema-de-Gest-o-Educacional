package sge.model;

/**
 * Representa uma avaliação de um aluno.
 */
public class Avaliacao {
    private Double nota;
    private final String descricao;

    /**
     * Cria uma avaliação sem nota atribuída.
     *
     * @param descricao descrição da avaliação, não nula nem vazia
     * @throws IllegalArgumentException se a descrição for nula ou vazia
     */
    public Avaliacao(String descricao) {
        if (descricao == null || descricao.trim().isEmpty()) {
            throw new IllegalArgumentException("A descrição da avaliação é obrigatória.");
        }
        this.descricao = descricao;
    }

    /**
     * Atribui ou atualiza a nota da avaliação.
     *
     * @param valor nota entre 0 e 10, inclusive
     * @throws IllegalArgumentException se a nota estiver fora do intervalo de 0 a 10
     */
    public void atribuirNota(double valor) {
        if (Double.isNaN(valor) || Double.isInfinite(valor) || valor < 0 || valor > 10) {
            throw new IllegalArgumentException("A nota deve estar entre 0 e 10.");
        }
        this.nota = valor;
    }

    /**
     * Retorna a nota atribuída.
     *
     * @return nota, ou {@code null} se ainda não foi atribuída
     */
    public Double getNota() {
        return nota;
    }

    /**
     * Retorna a descrição da avaliação.
     *
     * @return descrição da avaliação
     */
    public String getDescricao() {
        return descricao;
    }
}
