package sge.model;

/**
 * Representa um professor do sistema.
 */
public class Professor extends Usuario implements Autenticacao, GeradorRelatorio {
    private final String especialidade;
    private final String registro;

    /**
     * Cria um professor com seus dados básicos.
     *
     * @param nome nome do professor, não nulo nem vazio
     * @param especialidade especialidade do professor, não nula nem vazia
     * @param registro registro do professor, não nulo nem vazio
     * @param login login do professor, não nulo nem vazio
     * @param senha senha do professor, não nula nem vazia
     * @throws IllegalArgumentException se algum texto obrigatório for nulo ou vazio
     */
    public Professor(
            String nome, String especialidade, String registro, String login, String senha) {
        super(nome, login, senha);
        this.especialidade = validarTexto(especialidade, "especialidade");
        this.registro = validarTexto(registro, "registro");
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

    /**
     * Gera um relatório com os dados profissionais do professor.
     *
     * @return relatório do professor
     */
    @Override
    public String gerarRelatorio() {
        return "Relatório do professor\n"
                + "Nome: " + getNome() + "\n"
                + "Registro: " + registro + "\n"
                + "Especialidade: " + especialidade;
    }

    /**
     * Verifica as credenciais do professor.
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
