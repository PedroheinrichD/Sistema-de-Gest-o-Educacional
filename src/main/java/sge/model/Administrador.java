package sge.model;

/**
 * Representa um administrador do sistema.
 */
public class Administrador extends Usuario implements Autenticacao {
    /**
     * Cria um administrador com seus dados de acesso.
     *
     * @param nome nome do administrador, não nulo nem vazio
     * @param login login do administrador, não nulo nem vazio
     * @param senha senha do administrador, não nula nem vazia
     */
    public Administrador(String nome, String login, String senha) {
        super(nome, login, senha);
    }

    /**
     * Verifica as credenciais do administrador.
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
