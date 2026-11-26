package sge.service;

import java.util.List;
import sge.model.Aluno;
import sge.model.Avaliacao;
import sge.model.Curso;
import sge.model.CursoEAD;
import sge.model.CursoPresencial;
import sge.model.Professor;
import sge.model.Turma;
import sge.model.Usuario;
import sge.repository.Repositorio;

/**
 * Coordena os cadastros e as operações acadêmicas do sistema.
 */
public class GestaoEducacionalService {
    private final Repositorio<Aluno> repositorioAlunos;
    private final Repositorio<Professor> repositorioProfessores;
    private final Repositorio<Curso> repositorioCursos;
    private final Repositorio<Turma> repositorioTurmas;
    private final Repositorio<Usuario> repositorioUsuarios;

    /**
     * Cria o serviço com os repositórios utilizados pela gestão acadêmica.
     *
     * @param repositorioAlunos repositório de alunos
     * @param repositorioProfessores repositório de professores
     * @param repositorioCursos repositório de cursos
     * @param repositorioTurmas repositório de turmas
     * @param repositorioUsuarios repositório de usuários
     * @throws IllegalArgumentException se algum repositório for nulo
     */
    public GestaoEducacionalService(
            Repositorio<Aluno> repositorioAlunos,
            Repositorio<Professor> repositorioProfessores,
            Repositorio<Curso> repositorioCursos,
            Repositorio<Turma> repositorioTurmas,
            Repositorio<Usuario> repositorioUsuarios) {
        if (repositorioAlunos == null || repositorioProfessores == null
                || repositorioCursos == null || repositorioTurmas == null
                || repositorioUsuarios == null) {
            throw new IllegalArgumentException("Todos os repositórios são obrigatórios.");
        }
        this.repositorioAlunos = repositorioAlunos;
        this.repositorioProfessores = repositorioProfessores;
        this.repositorioCursos = repositorioCursos;
        this.repositorioTurmas = repositorioTurmas;
        this.repositorioUsuarios = repositorioUsuarios;
    }

    /**
     * Cadastra um curso presencial.
     *
     * @param nome nome do curso
     * @param codigo código único do curso
     * @param cargaHoraria carga horária em horas
     * @param salaAula sala do curso
     * @return curso cadastrado
     * @throws IllegalArgumentException se os dados forem inválidos ou o código já estiver cadastrado
     */
    public CursoPresencial cadastrarCursoPresencial(
            String nome, String codigo, int cargaHoraria, String salaAula) {
        validarCodigoCursoDisponivel(codigo);
        CursoPresencial curso = new CursoPresencial(nome, codigo, cargaHoraria, salaAula);
        repositorioCursos.salvar(curso);
        return curso;
    }

    /**
     * Cadastra um curso EAD.
     *
     * @param nome nome do curso
     * @param codigo código único do curso
     * @param cargaHoraria carga horária em horas
     * @param plataformaVirtual plataforma virtual do curso
     * @return curso cadastrado
     * @throws IllegalArgumentException se os dados forem inválidos ou o código já estiver cadastrado
     */
    public CursoEAD cadastrarCursoEad(
            String nome, String codigo, int cargaHoraria, String plataformaVirtual) {
        validarCodigoCursoDisponivel(codigo);
        CursoEAD curso = new CursoEAD(nome, codigo, cargaHoraria, plataformaVirtual);
        repositorioCursos.salvar(curso);
        return curso;
    }

    /**
     * Cadastra um aluno vinculado a um curso existente.
     *
     * @param nome nome do aluno
     * @param matricula matrícula única do aluno
     * @param codigoCurso código do curso
     * @param login login único do usuário
     * @param senha senha do usuário
     * @return aluno cadastrado
     * @throws IllegalArgumentException se o curso não existir, os dados forem inválidos
     *                                  ou a matrícula ou login já estiver cadastrado
     */
    public Aluno cadastrarAluno(
            String nome, String matricula, String codigoCurso, String login, String senha) {
        Curso curso = buscarCurso(codigoCurso);
        if (buscarAlunoPorMatricula(matricula) != null) {
            throw new IllegalArgumentException("Já existe um aluno com essa matrícula.");
        }
        validarLoginDisponivel(login);
        Aluno aluno = new Aluno(nome, matricula, curso, login, senha);
        repositorioAlunos.salvar(aluno);
        repositorioUsuarios.salvar(aluno);
        return aluno;
    }

    /**
     * Cadastra um professor.
     *
     * @param nome nome do professor
     * @param especialidade especialidade do professor
     * @param registro registro único do professor
     * @param login login único do usuário
     * @param senha senha do usuário
     * @return professor cadastrado
     * @throws IllegalArgumentException se os dados forem inválidos ou o registro ou login
     *                                  já estiver cadastrado
     */
    public Professor cadastrarProfessor(
            String nome, String especialidade, String registro, String login, String senha) {
        if (buscarProfessorPorRegistro(registro) != null) {
            throw new IllegalArgumentException("Já existe um professor com esse registro.");
        }
        validarLoginDisponivel(login);
        Professor professor = new Professor(nome, especialidade, registro, login, senha);
        repositorioProfessores.salvar(professor);
        repositorioUsuarios.salvar(professor);
        return professor;
    }

    /**
     * Cria uma turma associada a um professor e a um curso existentes.
     *
     * @param codigo código único da turma
     * @param registroProfessor registro do professor responsável
     * @param codigoCurso código do curso da turma
     * @return turma criada
     * @throws IllegalArgumentException se professor ou curso não existirem ou o código
     *                                  da turma já estiver cadastrado
     */
    public Turma criarTurma(String codigo, String registroProfessor, String codigoCurso) {
        for (Turma turma : repositorioTurmas.listarTodos()) {
            if (turma.getCodigo().equals(codigo)) {
                throw new IllegalArgumentException("Já existe uma turma com esse código.");
            }
        }
        Professor professor = buscarProfessorPorRegistro(registroProfessor);
        if (professor == null) {
            throw new IllegalArgumentException("Professor não encontrado.");
        }
        Curso curso = buscarCurso(codigoCurso);
        Turma turma = new Turma(codigo, professor, curso);
        repositorioTurmas.salvar(turma);
        return turma;
    }

    /**
     * Matricula um aluno existente em uma turma.
     *
     * @param codigoTurma código da turma
     * @param matricula matrícula do aluno
     * @throws IllegalArgumentException se a turma ou o aluno não existirem ou se o aluno
     *                                  não puder ser associado à turma
     */
    public void associarAluno(String codigoTurma, String matricula) {
        Turma turma = buscarTurma(codigoTurma);
        Aluno aluno = buscarAlunoPorMatricula(matricula);
        if (aluno == null) {
            throw new IllegalArgumentException("Aluno não encontrado.");
        }
        turma.adicionarAluno(aluno);
    }

    /**
     * Registra uma avaliação com nota para um aluno matriculado na turma.
     *
     * @param codigoTurma código da turma
     * @param matricula matrícula do aluno
     * @param descricao descrição da avaliação
     * @param nota nota entre 0 e 10, inclusive
     * @return avaliação registrada
     * @throws IllegalArgumentException se a turma ou o aluno não existirem, a descrição
     *                                  ou nota forem inválidas ou o aluno não estiver matriculado
     */
    public Avaliacao registrarAvaliacao(
            String codigoTurma, String matricula, String descricao, double nota) {
        Turma turma = buscarTurma(codigoTurma);
        Aluno aluno = buscarAlunoPorMatricula(matricula);
        if (aluno == null) {
            throw new IllegalArgumentException("Aluno não encontrado.");
        }
        Avaliacao avaliacao = new Avaliacao(descricao);
        avaliacao.atribuirNota(nota);
        turma.associarAvaliacao(aluno, avaliacao);
        return avaliacao;
    }

    /**
     * Retorna os alunos cadastrados.
     *
     * @return lista não modificável de alunos
     */
    public List<Aluno> listarAlunos() {
        return repositorioAlunos.listarTodos();
    }

    /**
     * Retorna os professores cadastrados.
     *
     * @return lista não modificável de professores
     */
    public List<Professor> listarProfessores() {
        return repositorioProfessores.listarTodos();
    }

    /**
     * Retorna os cursos cadastrados.
     *
     * @return lista não modificável de cursos
     */
    public List<Curso> listarCursos() {
        return repositorioCursos.listarTodos();
    }

    /**
     * Retorna as turmas cadastradas.
     *
     * @return lista não modificável de turmas
     */
    public List<Turma> listarTurmas() {
        return repositorioTurmas.listarTodos();
    }

    /**
     * Retorna os alunos matriculados em uma turma.
     *
     * @param codigoTurma código da turma
     * @return lista não modificável de alunos matriculados
     */
    public List<Aluno> listarAlunosDaTurma(String codigoTurma) {
        return buscarTurma(codigoTurma).getListaAlunos();
    }

    private void validarCodigoCursoDisponivel(String codigo) {
        for (Curso curso : repositorioCursos.listarTodos()) {
            if (curso.getCodigo().equals(codigo)) {
                throw new IllegalArgumentException("Já existe um curso com esse código.");
            }
        }
    }

    private void validarLoginDisponivel(String login) {
        for (Usuario usuario : repositorioUsuarios.listarTodos()) {
            if (usuario.getLogin().equals(login)) {
                throw new IllegalArgumentException("Já existe um usuário com esse login.");
            }
        }
    }

    private Curso buscarCurso(String codigo) {
        for (Curso curso : repositorioCursos.listarTodos()) {
            if (curso.getCodigo().equals(codigo)) {
                return curso;
            }
        }
        throw new IllegalArgumentException("Curso não encontrado.");
    }

    private Aluno buscarAlunoPorMatricula(String matricula) {
        for (Aluno aluno : repositorioAlunos.listarTodos()) {
            if (aluno.getMatricula().equals(matricula)) {
                return aluno;
            }
        }
        return null;
    }

    private Professor buscarProfessorPorRegistro(String registro) {
        for (Professor professor : repositorioProfessores.listarTodos()) {
            if (professor.getRegistro().equals(registro)) {
                return professor;
            }
        }
        return null;
    }

    private Turma buscarTurma(String codigo) {
        for (Turma turma : repositorioTurmas.listarTodos()) {
            if (turma.getCodigo().equals(codigo)) {
                return turma;
            }
        }
        throw new IllegalArgumentException("Turma não encontrada.");
    }
}
