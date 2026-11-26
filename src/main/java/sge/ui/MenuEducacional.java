package sge.ui;

import java.util.List;
import java.util.Scanner;
import sge.model.Aluno;
import sge.model.Curso;
import sge.model.Professor;
import sge.model.Turma;
import sge.service.GestaoEducacionalService;
import sge.service.RelatorioService;

/**
 * Apresenta no console as operações acadêmicas do EduConnect.
 */
public class MenuEducacional {
    private final GestaoEducacionalService gestaoService;
    private final RelatorioService relatorioService;
    private final Scanner scanner;

    /**
     * Cria o menu com os serviços da aplicação e a entrada do console.
     *
     * @param gestaoService serviço de gestão acadêmica
     * @param relatorioService serviço de relatórios
     * @param scanner entrada do console
     * @throws IllegalArgumentException se algum argumento for nulo
     */
    public MenuEducacional(
            GestaoEducacionalService gestaoService,
            RelatorioService relatorioService,
            Scanner scanner) {
        if (gestaoService == null || relatorioService == null || scanner == null) {
            throw new IllegalArgumentException("Os serviços e a entrada são obrigatórios.");
        }
        this.gestaoService = gestaoService;
        this.relatorioService = relatorioService;
        this.scanner = scanner;
    }

    /**
     * Executa o menu até que o usuário escolha sair ou a entrada termine.
     */
    public void executar() {
        while (true) {
            exibirOpcoes();
            try {
                int opcao = lerOpcao();
                if (opcao == 0) {
                    return;
                }
                executarOpcao(opcao);
            } catch (FimDaEntrada e) {
                return;
            } catch (IllegalArgumentException e) {
                System.out.println("Falha: " + e.getMessage());
            }
        }
    }

    private int lerOpcao() {
        String valor = lerObrigatorio("Escolha uma opção: ");
        try {
            return Integer.parseInt(valor);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Opção inválida. Informe um número inteiro.");
        }
    }

    private void exibirOpcoes() {
        System.out.println("\n=== EduConnect ===");
        System.out.println("1 - Cadastrar aluno");
        System.out.println("2 - Cadastrar professor");
        System.out.println("3 - Cadastrar curso");
        System.out.println("4 - Criar turma");
        System.out.println("5 - Associar aluno a uma turma");
        System.out.println("6 - Registrar avaliação");
        System.out.println("7 - Gerar relatório geral");
        System.out.println("8 - Listar cadastros");
        System.out.println("0 - Sair");
    }

    private void executarOpcao(int opcao) {
        switch (opcao) {
            case 1:
                cadastrarAluno();
                break;
            case 2:
                cadastrarProfessor();
                break;
            case 3:
                cadastrarCurso();
                break;
            case 4:
                criarTurma();
                break;
            case 5:
                associarAluno();
                break;
            case 6:
                registrarAvaliacao();
                break;
            case 7:
                System.out.println(relatorioService.gerarRelatorioGeral());
                break;
            case 8:
                listarCadastros();
                break;
            default:
                System.out.println("Opção inválida. Selecione uma opção do menu.");
        }
    }

    private void cadastrarAluno() {
        listarCursos();
        String nome = lerObrigatorio("Nome: ");
        String matricula = lerObrigatorio("Matrícula: ");
        String codigoCurso = lerObrigatorio("Código do curso: ");
        String login = lerObrigatorio("Login: ");
        String senha = lerObrigatorio("Senha: ");
        Aluno aluno = gestaoService.cadastrarAluno(nome, matricula, codigoCurso, login, senha);
        System.out.println("Aluno cadastrado: " + aluno.getNome() + ".");
    }

    private void cadastrarProfessor() {
        String nome = lerObrigatorio("Nome: ");
        String especialidade = lerObrigatorio("Especialidade: ");
        String registro = lerObrigatorio("Registro: ");
        String login = lerObrigatorio("Login: ");
        String senha = lerObrigatorio("Senha: ");
        Professor professor = gestaoService.cadastrarProfessor(
                nome, especialidade, registro, login, senha);
        System.out.println("Professor cadastrado: " + professor.getNome() + ".");
    }

    private void cadastrarCurso() {
        String nome = lerObrigatorio("Nome do curso: ");
        String codigo = lerObrigatorio("Código do curso: ");
        int cargaHoraria = lerInteiro("Carga horária (horas): ");
        System.out.println("1 - Presencial");
        System.out.println("2 - EAD");
        int modalidade = lerInteiro("Modalidade: ");
        if (modalidade == 1) {
            String sala = lerObrigatorio("Sala de aula: ");
            gestaoService.cadastrarCursoPresencial(nome, codigo, cargaHoraria, sala);
        } else if (modalidade == 2) {
            String plataforma = lerObrigatorio("Plataforma virtual: ");
            gestaoService.cadastrarCursoEad(nome, codigo, cargaHoraria, plataforma);
        } else {
            throw new IllegalArgumentException("Modalidade inválida.");
        }
        System.out.println("Curso cadastrado: " + nome + ".");
    }

    private void criarTurma() {
        listarProfessores();
        listarCursos();
        String codigo = lerObrigatorio("Código da turma: ");
        String registroProfessor = lerObrigatorio("Registro do professor: ");
        String codigoCurso = lerObrigatorio("Código do curso: ");
        Turma turma = gestaoService.criarTurma(codigo, registroProfessor, codigoCurso);
        System.out.println("Turma criada: " + turma.getCodigo() + ".");
    }

    private void associarAluno() {
        listarTurmas();
        listarAlunos();
        String codigoTurma = lerObrigatorio("Código da turma: ");
        String matricula = lerObrigatorio("Matrícula do aluno: ");
        gestaoService.associarAluno(codigoTurma, matricula);
        System.out.println("Aluno associado à turma.");
    }

    private void registrarAvaliacao() {
        listarTurmas();
        String codigoTurma = lerObrigatorio("Código da turma: ");
        List<Aluno> alunos = gestaoService.listarAlunosDaTurma(codigoTurma);
        exibirAlunos(alunos);
        String matricula = lerObrigatorio("Matrícula do aluno: ");
        String descricao = lerObrigatorio("Descrição da avaliação: ");
        double nota = lerDouble("Nota (0 a 10): ");
        gestaoService.registrarAvaliacao(codigoTurma, matricula, descricao, nota);
        System.out.println("Avaliação registrada.");
    }

    private void listarCadastros() {
        listarCursos();
        listarAlunos();
        listarProfessores();
        listarTurmas();
    }

    private void listarCursos() {
        List<Curso> cursos = gestaoService.listarCursos();
        System.out.println("\nCursos:");
        if (cursos.isEmpty()) {
            System.out.println("  Nenhum curso cadastrado.");
            return;
        }
        for (Curso curso : cursos) {
            System.out.println("  " + curso.detalharCurso());
        }
    }

    private void listarAlunos() {
        System.out.println("\nAlunos:");
        exibirAlunos(gestaoService.listarAlunos());
    }

    private void exibirAlunos(List<Aluno> alunos) {
        if (alunos.isEmpty()) {
            System.out.println("  Nenhum aluno cadastrado.");
            return;
        }
        for (Aluno aluno : alunos) {
            System.out.println("  " + aluno.getMatricula() + " - " + aluno.getNome()
                    + " (" + aluno.getCurso().getNome() + ")");
        }
    }

    private void listarProfessores() {
        List<Professor> professores = gestaoService.listarProfessores();
        System.out.println("\nProfessores:");
        if (professores.isEmpty()) {
            System.out.println("  Nenhum professor cadastrado.");
            return;
        }
        for (Professor professor : professores) {
            System.out.println("  " + professor.getRegistro() + " - " + professor.getNome()
                    + " (" + professor.getEspecialidade() + ")");
        }
    }

    private void listarTurmas() {
        List<Turma> turmas = gestaoService.listarTurmas();
        System.out.println("\nTurmas:");
        if (turmas.isEmpty()) {
            System.out.println("  Nenhuma turma cadastrada.");
            return;
        }
        for (Turma turma : turmas) {
            System.out.println("  " + turma.gerarResumo());
        }
    }

    private String lerObrigatorio(String mensagem) {
        String valor = lerLinha(mensagem).trim();
        if (valor.isEmpty()) {
            throw new IllegalArgumentException("O campo informado é obrigatório.");
        }
        return valor;
    }

    private int lerInteiro(String mensagem) {
        String valor = lerObrigatorio(mensagem);
        try {
            return Integer.parseInt(valor);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Informe um número inteiro válido.");
        }
    }

    private double lerDouble(String mensagem) {
        String valor = lerObrigatorio(mensagem);
        try {
            return Double.parseDouble(valor);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Informe uma nota numérica válida.");
        }
    }

    private String lerLinha(String mensagem) {
        System.out.print(mensagem);
        if (!scanner.hasNextLine()) {
            throw new FimDaEntrada();
        }
        return scanner.nextLine();
    }

    private static final class FimDaEntrada extends RuntimeException {
        private static final long serialVersionUID = 1L;
    }
}
