package sge.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import sge.model.Aluno;
import sge.model.Curso;
import sge.model.GeradorRelatorio;
import sge.model.Professor;
import sge.repository.Repositorio;

/**
 * Reúne as entidades disponíveis para geração de relatórios.
 */
public class RelatorioService {
    private final Repositorio<Aluno> repositorioAlunos;
    private final Repositorio<Professor> repositorioProfessores;
    private final Repositorio<Curso> repositorioCursos;

    /**
     * Cria o serviço com os repositórios de entidades que geram relatórios.
     *
     * @param repositorioAlunos repositório de alunos
     * @param repositorioProfessores repositório de professores
     * @param repositorioCursos repositório de cursos
     * @throws IllegalArgumentException se algum repositório for nulo
     */
    public RelatorioService(
            Repositorio<Aluno> repositorioAlunos,
            Repositorio<Professor> repositorioProfessores,
            Repositorio<Curso> repositorioCursos) {
        if (repositorioAlunos == null || repositorioProfessores == null
                || repositorioCursos == null) {
            throw new IllegalArgumentException("Todos os repositórios são obrigatórios.");
        }
        this.repositorioAlunos = repositorioAlunos;
        this.repositorioProfessores = repositorioProfessores;
        this.repositorioCursos = repositorioCursos;
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

    private static void adicionarRelatorios(
            List<RelatorioGerado> destino, List<? extends GeradorRelatorio> entidades) {
        for (GeradorRelatorio entidade : entidades) {
            destino.add(new RelatorioGerado(
                    entidade.getClass().getSimpleName(), entidade.gerarRelatorio()));
        }
    }
}
