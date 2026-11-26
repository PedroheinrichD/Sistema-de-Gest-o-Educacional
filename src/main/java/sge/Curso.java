package sge;

/**
 * Representa um curso oferecido pelo sistema.
 */
public class Curso {
    private final String nome;
    private final String codigo;
    private final int cargaHoraria;

    /**
     * Cria um curso com seus dados básicos.
     *
     * @param nome nome do curso, não nulo nem vazio
     * @param codigo código do curso, não nulo nem vazio
     * @param cargaHoraria carga horária em horas, maior que zero
     * @throws IllegalArgumentException se um texto obrigatório for nulo ou vazio
     *                                  ou a carga horária não for positiva
     */
    public Curso(String nome, String codigo, int cargaHoraria) {
        this.nome = validarTexto(nome, "nome");
        this.codigo = validarTexto(codigo, "código");
        if (cargaHoraria <= 0) {
            throw new IllegalArgumentException("A carga horária deve ser maior que zero.");
        }
        this.cargaHoraria = cargaHoraria;
    }

    /**
     * Retorna o nome do curso.
     *
     * @return nome do curso
     */
    public String getNome() {
        return nome;
    }

    /**
     * Retorna o código do curso.
     *
     * @return código do curso
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * Retorna a carga horária do curso.
     *
     * @return carga horária em horas
     */
    public int getCargaHoraria() {
        return cargaHoraria;
    }

    /**
     * Retorna os dados básicos do curso.
     *
     * @return detalhes do curso
     */
    public String detalharCurso() {
        return "Curso: " + nome + " (" + codigo + "), carga horária: "
                + cargaHoraria + " horas";
    }

    /**
     * Valida e retorna um texto obrigatório.
     *
     * @param valor texto a validar
     * @param atributo nome do atributo usado na mensagem de erro
     * @return texto validado
     * @throws IllegalArgumentException se o texto for nulo ou vazio
     */
    protected static String validarTexto(String valor, String atributo) {
        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalArgumentException("O atributo " + atributo + " é obrigatório.");
        }
        return valor;
    }
}
