package sge.model;

import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.SecureRandom;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
 * Representa os dados comuns a todos os usuários do sistema.
 */
public abstract class Usuario {
    private static final int ITERACOES_HASH = 600_000;
    private static final int TAMANHO_HASH_BITS = 256;
    private static final int TAMANHO_SALT_BYTES = 16;
    private static final SecureRandom GERADOR_ALEATORIO = new SecureRandom();

    private final String nome;
    private final String login;
    private final byte[] salt;
    private final byte[] hashSenha;

    /**
     * Cria um usuário com nome, login e senha.
     *
     * @param nome nome do usuário, não nulo nem vazio
     * @param login login do usuário, não nulo nem vazio
     * @param senha senha do usuário, não nula nem vazia
     * @throws IllegalArgumentException se algum dado obrigatório for nulo ou vazio
     * @throws IllegalStateException se o algoritmo de hash não estiver disponível
     */
    protected Usuario(String nome, String login, String senha) {
        this.nome = validarTexto(nome, "nome");
        this.login = validarTexto(login, "login");
        String senhaValidada = validarTexto(senha, "senha");
        this.salt = new byte[TAMANHO_SALT_BYTES];
        GERADOR_ALEATORIO.nextBytes(this.salt);
        this.hashSenha = gerarHash(senhaValidada, this.salt);
    }

    /**
     * Retorna o nome do usuário.
     *
     * @return nome do usuário
     */
    public String getNome() {
        return nome;
    }

    /**
     * Retorna o login do usuário.
     *
     * @return login do usuário
     */
    public String getLogin() {
        return login;
    }

    /**
     * Compara as credenciais informadas com as armazenadas para este usuário.
     *
     * @param login login informado
     * @param senha senha informada
     * @return {@code true} se as credenciais forem válidas
     */
    protected final boolean validarCredenciais(String login, String senha) {
        if (login == null || senha == null || !this.login.equals(login)) {
            return false;
        }
        return MessageDigest.isEqual(hashSenha, gerarHash(senha, salt));
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

    private static byte[] gerarHash(String senha, byte[] salt) {
        PBEKeySpec especificacao = new PBEKeySpec(
                senha.toCharArray(), salt, ITERACOES_HASH, TAMANHO_HASH_BITS);
        try {
            SecretKeyFactory fabrica = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            return fabrica.generateSecret(especificacao).getEncoded();
        } catch (GeneralSecurityException e) {
            throw new IllegalStateException("Não foi possível gerar o hash da senha.", e);
        } finally {
            especificacao.clearPassword();
        }
    }
}
