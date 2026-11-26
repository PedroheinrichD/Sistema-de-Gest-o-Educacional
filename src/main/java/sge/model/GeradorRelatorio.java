package sge.model;

/**
 * Define uma entidade capaz de gerar seu próprio relatório.
 */
public interface GeradorRelatorio {
    /**
     * Gera um relatório com as informações da entidade.
     *
     * @return conteúdo do relatório
     */
    String gerarRelatorio();
}
