package sge;

/**
 * Demonstra a criação das entidades básicas do SGE.
 */
public class Main {
    private Main() {
    }

    /**
     * Cria e exibe exemplos de curso, aluno e professor.
     *
     * @param args argumentos da linha de comando (não utilizados)
     */
    public static void main(String[] args) {
        Curso curso = new Curso("Engenharia de Software", "ES001", 3200);
        Aluno aluno = new Aluno("Ana Silva", "2026001", curso);
        Professor professor = new Professor("Carlos Souza", "Programação", "P001");

        System.out.println("Curso: " + curso.getNome() + " (" + curso.getCodigo()
                + "), " + curso.getCargaHoraria() + " horas");
        System.out.println("Aluno: " + aluno.getNome() + ", matrícula "
                + aluno.getMatricula() + ", curso " + aluno.getCurso().getNome());
        System.out.println("Professor: " + professor.getNome() + ", especialidade "
                + professor.getEspecialidade() + ", registro " + professor.getRegistro());
    }
}
