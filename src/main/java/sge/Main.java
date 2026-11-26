package sge;

import java.util.Scanner;
import sge.model.Aluno;
import sge.model.Curso;
import sge.model.CursoEAD;
import sge.model.CursoPresencial;
import sge.model.Professor;
import sge.model.Turma;
import sge.model.Usuario;
import sge.repository.Repositorio;
import sge.repository.RepositorioEmMemoria;
import sge.service.GestaoEducacionalService;
import sge.service.RelatorioService;
import sge.ui.MenuEducacional;

/**
 * Inicializa e conecta as camadas do sistema.
 */
public class Main {
    private Main() {
    }

    /**
     * Inicializa as camadas em memória e inicia o menu interativo.
     *
     * @param args argumentos da linha de comando (não utilizados)
     */
    public static void main(String[] args) {
        Repositorio<Aluno> repositorioAlunos = new RepositorioEmMemoria<>();
        Repositorio<Professor> repositorioProfessores = new RepositorioEmMemoria<>();
        Repositorio<Curso> repositorioCursos = new RepositorioEmMemoria<>();
        Repositorio<Turma> repositorioTurmas = new RepositorioEmMemoria<>();
        Repositorio<Usuario> repositorioUsuarios = new RepositorioEmMemoria<>();

        GestaoEducacionalService gestaoService = new GestaoEducacionalService(
                repositorioAlunos,
                repositorioProfessores,
                repositorioCursos,
                repositorioTurmas,
                repositorioUsuarios);
        RelatorioService relatorioService = new RelatorioService(
                repositorioAlunos, repositorioProfessores, repositorioCursos, repositorioTurmas);
        try (Scanner scanner = new Scanner(System.in)) {
            new MenuEducacional(gestaoService, relatorioService, scanner).executar();
        }
    }
}
