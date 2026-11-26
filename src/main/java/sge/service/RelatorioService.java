package sge.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import sge.model.Aluno;
import sge.model.Avaliacao;
import sge.model.Curso;
import sge.model.GeradorRelatorio;
import sge.model.Professor;
import sge.model.Turma;
import sge.repository.Repositorio;

/**
 * Reúne as entidades disponíveis para geração de relatórios.
 */
public class RelatorioService {
    private final Repositorio<Aluno> repositorioAlunos;
    private final Repositorio<Professor> repositorioProfessores;
    private final Repositorio<Curso> repositorioCursos;
    private final Repositorio<Turma> repositorioTurmas;

    /**
     * Cria o serviço com os repositórios de entidades que geram relatórios.
     *
     * @param repositorioAlunos repositório de alunos
     * @param repositorioProfessores repositório de professores
     * @param repositorioCursos repositório de cursos
     * @param repositorioTurmas repositório de turmas
     * @throws IllegalArgumentException se algum repositório for nulo
     */
    public RelatorioService(
            Repositorio<Aluno> repositorioAlunos,
            Repositorio<Professor> repositorioProfessores,
            Repositorio<Curso> repositorioCursos,
            Repositorio<Turma> repositorioTurmas) {
        if (repositorioAlunos == null || repositorioProfessores == null
                || repositorioCursos == null || repositorioTurmas == null) {
            throw new IllegalArgumentException("Todos os repositórios são obrigatórios.");
        }
        this.repositorioAlunos = repositorioAlunos;
        this.repositorioProfessores = repositorioProfessores;
        this.repositorioCursos = repositorioCursos;
        this.repositorioTurmas = repositorioTurmas;
    }

    /**
     * Gera relatórios para as entidades atualmente armazenadas.
     *
     * @return lista não modificável de relatórios gerados
     */
    public List<RelatorioGerado> listarRelatorios() {
        List<RelatorioGerado> relatorios = new ArrayList<>();
        adicionarRelatorios(relatorios, repositorioAlunos.listarTodos());
        adicionarRelatorios(relatorios, repositorioProfessores.listarTodos());
        adicionarRelatorios(relatorios, repositorioCursos.listarTodos());
        return Collections.unmodifiableList(relatorios);
    }

    /**
     * Gera um relatório consolidado de cadastros, turmas e avaliações.
     *
     * @return relatório geral do sistema
     */
    public String gerarRelatorioGeral() {
        List<Aluno> alunos = repositorioAlunos.listarTodos();
        List<Professor> professores = repositorioProfessores.listarTodos();
        List<Curso> cursos = repositorioCursos.listarTodos();
        List<Turma> turmas = repositorioTurmas.listarTodos();
        StringBuilder relatorio = new StringBuilder("=== Relatório geral EduConnect ===\n")
                .append("Alunos: ").append(alunos.size()).append('\n')
                .append("Professores: ").append(professores.size()).append('\n')
                .append("Cursos: ").append(cursos.size()).append('\n')
                .append("Turmas: ").append(turmas.size()).append('\n');

        for (RelatorioGerado item : listarRelatorios()) {
            relatorio.append("\n[").append(item.getTipo()).append("]\n")
                    .append(item.getConteudo()).append('\n');
        }
        for (Turma turma : turmas) {
            relatorio.append("\n[").append(turma.getCodigo()).append("]\n")
                    .append(turma.gerarResumo()).append('\n');
            for (Aluno aluno : turma.getListaAlunos()) {
                relatorio.append("Aluno: ").append(aluno.getNome())
                        .append(" (").append(aluno.getMatricula()).append(")\n");
                List<Avaliacao> avaliacoes = turma.getAvaliacoes(aluno);
                if (avaliacoes.isEmpty()) {
                    relatorio.append("  Sem avaliações registradas.\n");
                }
                for (Avaliacao avaliacao : avaliacoes) {
                    Double nota = avaliacao.getNota();
                    relatorio.append("  ").append(avaliacao.getDescricao())
                            .append(" - nota ")
                            .append(nota == null ? "não atribuída" : nota)
                            .append('\n');
                }
            }
        }
        if (alunos.isEmpty() && professores.isEmpty() && cursos.isEmpty() && turmas.isEmpty()) {
            relatorio.append("\nNenhum registro cadastrado.\n");
        }
        return relatorio.toString();
    }

    private static void adicionarRelatorios(
            List<RelatorioGerado> destino, List<? extends GeradorRelatorio> entidades) {
        for (GeradorRelatorio entidade : entidades) {
            destino.add(new RelatorioGerado(
                    entidade.getClass().getSimpleName(), entidade.gerarRelatorio()));
        }
    }
}
