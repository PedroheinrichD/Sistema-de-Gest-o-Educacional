package sge.model;

/**
 * Define a autenticação de um usuário por login e senha.
 */
public interface Autenticacao {
    /**
     * Verifica se o login e a senha correspondem às credenciais do usuário.
     *
     * @param login login informado
     * @param senha senha informada
     * @return {@code true} se as credenciais forem válidas
     */
    boolean autenticar(String login, String senha);
}
