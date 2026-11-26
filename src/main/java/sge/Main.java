package sge;

/**
 * Demonstra a criação das entidades básicas do SGE.
 */
public class Main {
    private Main() {
    }

    /**
     * Cria e exibe exemplos de curso, aluno, professor e turma.
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
        Administrador administrador = new Administrador(
                "Ana Lima", "ana.admin", "senha-admin-demo");
        
        Turma turma = new Turma("T2026-01", professor, curso);
        turma.adicionarAluno(aluno);
        turma.adicionarAluno(segundaAluna);

        Avaliacao avaliacao = new Avaliacao("Prova 1");
        avaliacao.atribuirNota(9.0);
        turma.associarAvaliacao(aluno, avaliacao);

        
        System.out.println(curso.detalharCurso());
        System.out.println(cursoEad.detalharCurso());
        System.out.println("Aluno: " + aluno.getNome() + ", matrícula "
                + aluno.getMatricula() + ", curso " + aluno.getCurso().getNome());
        System.out.println("Professor: " + professor.getNome() + ", especialidade "
                + professor.getEspecialidade() + ", registro " + professor.getRegistro());
        System.out.println("Autenticação do aluno: "
                + aluno.autenticar("pedro.aluno", "senha-aluno-demo"));
        System.out.println("Autenticação do professor: "
                + professor.autenticar("carlos.professor", "senha-prof-demo"));
        System.out.println("Autenticação do administrador: "
                + administrador.autenticar("ana.admin", "senha-admin-demo"));
        System.out.println(turma.gerarResumo());
        System.out.println("Avaliação de " + aluno.getNome() + ": "
                + turma.getAvaliacoes(aluno).get(0).getDescricao() + " - nota "
                + turma.getAvaliacoes(aluno).get(0).getNota());
    }
}
