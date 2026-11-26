package sge.service;

/**
 * Contém a identificação e o conteúdo de um relatório gerado.
 */
public class RelatorioGerado {
    private final String tipo;
    private final String conteudo;

    /**
     * Cria um relatório gerado.
     *
     * @param tipo tipo da entidade relatada
     * @param conteudo conteúdo do relatório
     * @throws IllegalArgumentException se algum argumento for nulo ou vazio
     */
    public RelatorioGerado(String tipo, String conteudo) {
        if (tipo == null || tipo.trim().isEmpty()
                || conteudo == null || conteudo.trim().isEmpty()) {
            throw new IllegalArgumentException("O tipo e o conteúdo do relatório são obrigatórios.");
        }
        this.tipo = tipo;
        this.conteudo = conteudo;
    }

    /**
     * Retorna o tipo da entidade relatada.
     *
     * @return tipo da entidade
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * Retorna o conteúdo do relatório.
     *
     * @return conteúdo do relatório
     */
    public String getConteudo() {
        return conteudo;
    }
}
