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
        Curso curso = new Curso("Engenharia de Software", "ES001", 3200);
        Aluno aluno = new Aluno("Pedro Dias", "2026001", curso);
        Aluno segundaAluna = new Aluno("Maria Costa", "2026002", curso);
        Professor professor = new Professor("Carlos Souza", "Programação", "P001");
        Turma turma = new Turma("T2026-01", professor, curso);
        turma.adicionarAluno(aluno);
        turma.adicionarAluno(segundaAluna);

        System.out.println("Curso: " + curso.getNome() + " (" + curso.getCodigo()
                + "), " + curso.getCargaHoraria() + " horas");
        System.out.println("Aluno: " + aluno.getNome() + ", matrícula "
                + aluno.getMatricula() + ", curso " + aluno.getCurso().getNome());
        System.out.println("Professor: " + professor.getNome() + ", especialidade "
                + professor.getEspecialidade() + ", registro " + professor.getRegistro());
        System.out.println(turma.gerarResumo());
    }
}
