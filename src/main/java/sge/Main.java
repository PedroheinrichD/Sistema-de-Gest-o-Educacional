package sge;

import java.util.Scanner;
import sge.model.Aluno;
import sge.model.Curso;
import sge.model.CursoEAD;
import sge.model.CursoPresencial;
import sge.model.Professor;
import sge.repository.Repositorio;
import sge.repository.RepositorioEmMemoria;
import sge.service.RelatorioService;
import sge.ui.MenuRelatorios;

/**
 * Inicializa e conecta as camadas do sistema.
 */
public class Main {
    private Main() {
    }

    /**
     * Inicializa os repositórios em memória e inicia o menu de relatórios.
     *
     * @param args argumentos da linha de comando (não utilizados)
     */
    public static void main(String[] args) {
        Curso curso = new CursoPresencial("Engenharia de Software", "ES001", 3200, "Sala 101");
        Curso cursoEad = new CursoEAD("Administração", "AD001", 2400, "Moodle");
        Aluno aluno = new Aluno(
                "Pedro Dias", "2026001", curso, "pedro.aluno", "senha-aluno-demo");
        Aluno segundaAluna = new Aluno(
                "Maria Costa", "2026002", curso, "maria.aluna", "senha-aluna-demo");
        Professor professor = new Professor(
                "Carlos Souza", "Programação", "P001", "carlos.professor", "senha-prof-demo");

        Repositorio<Aluno> repositorioAlunos = new RepositorioEmMemoria<>();
        repositorioAlunos.salvar(aluno);
        repositorioAlunos.salvar(segundaAluna);

        Repositorio<Professor> repositorioProfessores = new RepositorioEmMemoria<>();
        repositorioProfessores.salvar(professor);

        Repositorio<Curso> repositorioCursos = new RepositorioEmMemoria<>();
        repositorioCursos.salvar(curso);
        repositorioCursos.salvar(cursoEad);

        RelatorioService relatorioService = new RelatorioService(
                repositorioAlunos, repositorioProfessores, repositorioCursos);
        try (Scanner scanner = new Scanner(System.in)) {
            new MenuRelatorios(relatorioService, scanner).executar();
        }
    }
}
