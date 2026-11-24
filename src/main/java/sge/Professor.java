package sge;

/**
 * Representa um professor do sistema.
 */
public class Professor {
    private final String nome;
    private final String especialidade;
    private final String registro;

    /**
     * Cria um professor com seus dados básicos.
     *
     * @param nome nome do professor, não nulo nem vazio
     * @param especialidade especialidade do professor, não nula nem vazia
     * @param registro registro do professor, não nulo nem vazio
     * @throws IllegalArgumentException se algum texto obrigatório for nulo ou vazio
     */
    public Professor(String nome, String especialidade, String registro) {
        this.nome = validarTexto(nome, "nome");
        this.especialidade = validarTexto(especialidade, "especialidade");
        this.registro = validarTexto(registro, "registro");
    }

    /**
     * Retorna o nome do professor.
     *
     * @return nome do professor
     */
    public String getNome() {
        return nome;
    }

    /**
     * Retorna a especialidade do professor.
     *
     * @return especialidade do professor
     */
    public String getEspecialidade() {
        return especialidade;
    }

    /**
     * Retorna o registro do professor.
     *
     * @return registro do professor
     */
    public String getRegistro() {
        return registro;
    }

    private static String validarTexto(String valor, String atributo) {
        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalArgumentException("O atributo " + atributo + " é obrigatório.");
        }
        return valor;
    }
}
