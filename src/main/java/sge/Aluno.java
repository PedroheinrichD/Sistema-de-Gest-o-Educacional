package sge;

/**
 * Representa um aluno matriculado em um curso.
 */
public class Aluno {
    private final String nome;
    private final String matricula;
    private final Curso curso;

    /**
     * Cria um aluno com seus dados básicos.
     *
     * @param nome nome do aluno, não nulo nem vazio
     * @param matricula matrícula do aluno, não nula nem vazia
     * @param curso curso associado ao aluno, não nulo
     * @throws IllegalArgumentException se um texto obrigatório for nulo ou vazio
     *                                  ou o curso for nulo
     */
    public Aluno(String nome, String matricula, Curso curso) {
        this.nome = validarTexto(nome, "nome");
        this.matricula = validarTexto(matricula, "matrícula");
        if (curso == null) {
            throw new IllegalArgumentException("O atributo curso é obrigatório.");
        }
        this.curso = curso;
    }

    /**
     * Retorna o nome do aluno.
     *
     * @return nome do aluno
     */
    public String getNome() {
        return nome;
    }

    /**
     * Retorna a matrícula do aluno.
     *
     * @return matrícula do aluno
     */
    public String getMatricula() {
        return matricula;
    }

    /**
     * Retorna o curso associado ao aluno.
     *
     * @return curso do aluno
     */
    public Curso getCurso() {
        return curso;
    }

    private static String validarTexto(String valor, String atributo) {
        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalArgumentException("O atributo " + atributo + " é obrigatório.");
        }
        return valor;
    }
}
