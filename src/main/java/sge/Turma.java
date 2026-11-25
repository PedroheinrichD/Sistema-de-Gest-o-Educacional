package sge;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Representa uma turma vinculada a um professor e a um curso.
 */
public class Turma {
    private final String codigo;
    private final Professor professor;
    private final Curso curso;
    private final List<Aluno> listaAlunos = new ArrayList<>();

    /**
     * Cria uma turma para um curso e um professor.
     *
     * @param codigo código da turma, não nulo nem vazio
     * @param professor professor responsável, não nulo
     * @param curso curso da turma, não nulo
     * @throws IllegalArgumentException se o código for nulo ou vazio, ou se
     *                                  professor ou curso forem nulos
     */
    public Turma(String codigo, Professor professor, Curso curso) {
        this.codigo = validarTexto(codigo, "código");
        if (professor == null) {
            throw new IllegalArgumentException("O professor da turma é obrigatório.");
        }
        if (curso == null) {
            throw new IllegalArgumentException("O curso da turma é obrigatório.");
        }
        this.professor = professor;
        this.curso = curso;
    }

    /**
     * Adiciona à turma um aluno matriculado no mesmo curso.
     *
     * @param aluno aluno a adicionar, não nulo
     * @throws IllegalArgumentException se o aluno for nulo, estiver associado
     *                                  a outro curso ou já estiver matriculado
     */
    public void adicionarAluno(Aluno aluno) {
        if (aluno == null) {
            throw new IllegalArgumentException("O aluno é obrigatório.");
        }
        if (!curso.getCodigo().equals(aluno.getCurso().getCodigo())) {
            throw new IllegalArgumentException("O aluno deve estar matriculado no curso da turma.");
        }
        for (Aluno alunoMatriculado : listaAlunos) {
            if (alunoMatriculado.getMatricula().equals(aluno.getMatricula())) {
                throw new IllegalArgumentException("Já existe um aluno com essa matrícula na turma.");
            }
        }
        listaAlunos.add(aluno);
    }

    /**
     * Remove da turma o aluno identificado pela matrícula.
     *
     * @param aluno aluno a remover, não nulo
     * @return {@code true} se o aluno foi removido; caso contrário, {@code false}
     * @throws IllegalArgumentException se o aluno for nulo
     */
    public boolean removerAluno(Aluno aluno) {
        if (aluno == null) {
            throw new IllegalArgumentException("O aluno é obrigatório.");
        }
        for (int i = 0; i < listaAlunos.size(); i++) {
            if (listaAlunos.get(i).getMatricula().equals(aluno.getMatricula())) {
                listaAlunos.remove(i);
                return true;
            }
        }
        return false;
    }

    /**
     * Retorna o código da turma.
     *
     * @return código da turma
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * Retorna o professor responsável pela turma.
     *
     * @return professor da turma
     */
    public Professor getProfessor() {
        return professor;
    }

    /**
     * Retorna o curso ao qual a turma pertence.
     *
     * @return curso da turma
     */
    public Curso getCurso() {
        return curso;
    }

    /**
     * Retorna uma visão não modificável dos alunos matriculados.
     *
     * @return lista não modificável de alunos
     */
    public List<Aluno> getListaAlunos() {
        return Collections.unmodifiableList(listaAlunos);
    }

    /**
     * Gera um resumo com o professor, o curso e o total de alunos matriculados.
     *
     * @return resumo da turma
     */
    public String gerarResumo() {
        return "Turma: " + codigo
                + ", professor: " + professor.getNome()
                + ", curso: " + curso.getNome()
                + ", alunos matriculados: " + listaAlunos.size();
    }

    private static String validarTexto(String valor, String atributo) {
        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalArgumentException("O atributo " + atributo + " é obrigatório.");
        }
        return valor;
    }
}
