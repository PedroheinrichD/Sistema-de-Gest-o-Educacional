package sge;

/**
 * Representa um aluno matriculado em um curso.
 */
public class Aluno extends Usuario implements Autenticacao, GeradorRelatorio {
    private final String matricula;
    private final Curso curso;

    /**
     * Cria um aluno com seus dados básicos.
     *
     * @param nome nome do aluno, não nulo nem vazio
     * @param matricula matrícula do aluno, não nula nem vazia
     * @param curso curso associado ao aluno, não nulo
     * @param login login do aluno, não nulo nem vazio
     * @param senha senha do aluno, não nula nem vazia
     * @throws IllegalArgumentException se um texto obrigatório for nulo ou vazio
     *                                  ou o curso for nulo
     */
    public Aluno(String nome, String matricula, Curso curso, String login, String senha) {
        super(nome, login, senha);
        this.matricula = validarTexto(matricula, "matrícula");
        if (curso == null) {
            throw new IllegalArgumentException("O atributo curso é obrigatório.");
        }
        this.curso = curso;
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

    /**
     * Gera um relatório com os dados do aluno e do curso associado.
     *
     * @return relatório do aluno
     */
    @Override
    public String gerarRelatorio() {
        return "Relatório do aluno\n"
                + "Nome: " + getNome() + "\n"
                + "Matrícula: " + matricula + "\n"
                + "Curso: " + curso.detalharCurso();
    }

    /**
     * Verifica as credenciais do aluno.
     *
     * @param login login informado
     * @param senha senha informada
     * @return {@code true} se as credenciais forem válidas
     */
    @Override
    public boolean autenticar(String login, String senha) {
        return validarCredenciais(login, senha);
    }
}
