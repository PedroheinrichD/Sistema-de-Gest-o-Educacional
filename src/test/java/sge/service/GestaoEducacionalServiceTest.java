package sge.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import sge.model.Aluno;
import sge.model.Curso;
import sge.model.Professor;
import sge.model.Turma;
import sge.model.Usuario;
import sge.repository.Repositorio;
import sge.repository.RepositorioEmMemoria;
import sge.ui.MenuEducacional;

/**
 * Executa verificações de integração sem dependências externas de teste.
 */
public final class GestaoEducacionalServiceTest {
    private GestaoEducacionalServiceTest() {
    }

    /**
     * Executa os cenários de sucesso, falha e menu interativo.
     *
     * @param args argumentos não utilizados
     */
    public static void main(String[] args) {
        testarOperacoesDeSucessoEFalha();
        testarFluxoInterativo();
        System.out.println("Todos os testes de gestão educacional passaram.");
    }

    private static void testarOperacoesDeSucessoEFalha() {
        Fixture fixture = new Fixture();
        Curso curso = fixture.gestao.cadastrarCursoPresencial(
                "Engenharia", "ENG", 120, "Sala 1");
        fixture.gestao.cadastrarCursoEad("Administração", "ADM", 90, "Moodle");
        Aluno aluno = fixture.gestao.cadastrarAluno(
                "Ana", "A001", "ENG", "ana", "senha-aluna");
        fixture.gestao.cadastrarProfessor(
                "Bruno", "Programação", "P001", "bruno", "senha-professor");
        Turma turma = fixture.gestao.criarTurma("T001", "P001", "ENG");
        fixture.gestao.associarAluno("T001", "A001");
        fixture.gestao.registrarAvaliacao("T001", "A001", "Prova 1", 8.5);

        assertEquals(2, fixture.gestao.listarCursos().size(), "cursos cadastrados");
        assertEquals(1, fixture.gestao.listarAlunos().size(), "alunos cadastrados");
        assertEquals(1, fixture.gestao.listarProfessores().size(), "professores cadastrados");
        assertEquals(1, turma.getListaAlunos().size(), "alunos associados à turma");
        assertEquals(8.5, turma.getAvaliacoes(aluno).get(0).getNota(), "nota registrada");

        assertThrows(IllegalArgumentException.class,
                () -> fixture.gestao.cadastrarCursoPresencial("Outro", "ENG", 60, "Sala 2"),
                "código de curso duplicado");
        assertThrows(IllegalArgumentException.class,
                () -> fixture.gestao.cadastrarAluno(
                        "Outra", "A001", "ENG", "outra", "senha"),
                "matrícula duplicada");
        assertThrows(IllegalArgumentException.class,
                () -> fixture.gestao.cadastrarProfessor(
                        "Outro", "Matemática", "P002", "ana", "senha"),
                "login duplicado entre perfis");
        assertThrows(IllegalArgumentException.class,
                () -> fixture.gestao.cadastrarAluno(
                        "Sem curso", "A002", "NAO-EXISTE", "sem-curso", "senha"),
                "curso inexistente");
        assertThrows(IllegalArgumentException.class,
                () -> fixture.gestao.criarTurma("T002", "P999", "ENG"),
                "professor inexistente");
        assertThrows(IllegalArgumentException.class,
                () -> fixture.gestao.associarAluno("T001", "A001"),
                "matrícula duplicada na turma");
        assertThrows(IllegalArgumentException.class,
                () -> fixture.gestao.registrarAvaliacao("T001", "A001", "Inválida", 11),
                "nota fora do intervalo");

        fixture.gestao.cadastrarCursoPresencial("Direito", "DIR", 80, "Sala 3");
        fixture.gestao.cadastrarAluno("Carla", "A003", "DIR", "carla", "senha-carla");
        assertThrows(IllegalArgumentException.class,
                () -> fixture.gestao.associarAluno("T001", "A003"),
                "aluno matriculado em curso diferente");
        assertThrows(IllegalArgumentException.class,
                () -> fixture.gestao.registrarAvaliacao("T001", "A003", "Prova", 7),
                "avaliação para aluno não matriculado");

        RelatorioService relatorios = fixture.criarRelatorioService();
        String relatorioGeral = relatorios.gerarRelatorioGeral();
        assertContains(relatorioGeral, "Alunos: 2", "contagem de alunos no relatório");
        assertContains(relatorioGeral, "Turmas: 1", "contagem de turmas no relatório");
        assertContains(relatorioGeral, "Prova 1 - nota 8.5", "avaliação no relatório");
        assertContains(curso.gerarRelatorio(), "Engenharia", "relatório de curso");
    }

    private static void testarFluxoInterativo() {
        Fixture fixture = new Fixture();
        String entrada = String.join("\n",
                "inválido",
                "3", "Engenharia", "ENG", "120", "1", "Sala 1",
                "2", "Bruno", "Programação", "P001", "bruno", "senha-professor",
                "1", "Ana", "A001", "ENG", "ana", "senha-aluna",
                "4", "T001", "P001", "ENG",
                "5", "T001", "A001",
                "6", "T001", "A001", "Prova 1", "9.5",
                "7",
                "3", "Duplicado", "ENG", "60", "1", "Sala 2",
                "8",
                "0",
                "");
        ByteArrayInputStream input = new ByteArrayInputStream(
                entrada.getBytes(StandardCharsets.UTF_8));
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        java.io.InputStream originalIn = System.in;
        try {
            System.setIn(input);
            System.setOut(new PrintStream(output, true, "UTF-8"));
            Scanner scanner = new Scanner(System.in);
            new MenuEducacional(
                    fixture.gestao, fixture.criarRelatorioService(), scanner).executar();
            scanner.close();
        } catch (java.io.UnsupportedEncodingException e) {
            throw new AssertionError("UTF-8 deve estar disponível.", e);
        } finally {
            System.setIn(originalIn);
            System.setOut(originalOut);
        }

        String exibicao = new String(output.toByteArray(), StandardCharsets.UTF_8);
        assertContains(exibicao, "Curso cadastrado: Engenharia", "cadastro via menu");
        assertContains(exibicao, "Professor cadastrado: Bruno", "cadastro de professor via menu");
        assertContains(exibicao, "Aluno cadastrado: Ana", "cadastro de aluno via menu");
        assertContains(exibicao, "Turma criada: T001", "criação de turma via menu");
        assertContains(exibicao, "Avaliação registrada.", "avaliação via menu");
        assertContains(exibicao, "Prova 1 - nota 9.5", "relatório via menu");
        assertContains(exibicao, "Opção inválida", "entrada de menu inválida");
        assertContains(exibicao, "Já existe um curso com esse código", "falha de cadastro via menu");
        assertEquals(1, fixture.gestao.listarCursos().size(), "cadastro duplicado não persistido");
    }

    private static void assertThrows(
            Class<? extends RuntimeException> tipo, Runnable acao, String cenario) {
        try {
            acao.run();
        } catch (RuntimeException e) {
            if (tipo.isInstance(e)) {
                return;
            }
            throw new AssertionError("Exceção incorreta no cenário: " + cenario, e);
        }
        throw new AssertionError("Era esperada uma falha no cenário: " + cenario);
    }

    private static void assertEquals(Object esperado, Object atual, String cenario) {
        if (esperado == null ? atual != null : !esperado.equals(atual)) {
            throw new AssertionError("Valor inesperado em " + cenario
                    + ": esperado " + esperado + ", recebido " + atual);
        }
    }

    private static void assertContains(String texto, String esperado, String cenario) {
        if (!texto.contains(esperado)) {
            throw new AssertionError("Texto ausente em " + cenario + ": " + esperado);
        }
    }

    private static final class Fixture {
        private final Repositorio<Aluno> alunos = new RepositorioEmMemoria<>();
        private final Repositorio<Professor> professores = new RepositorioEmMemoria<>();
        private final Repositorio<Curso> cursos = new RepositorioEmMemoria<>();
        private final Repositorio<Turma> turmas = new RepositorioEmMemoria<>();
        private final Repositorio<Usuario> usuarios = new RepositorioEmMemoria<>();
        private final GestaoEducacionalService gestao = new GestaoEducacionalService(
                alunos, professores, cursos, turmas, usuarios);

        private RelatorioService criarRelatorioService() {
            return new RelatorioService(alunos, professores, cursos, turmas);
        }
    }
}
