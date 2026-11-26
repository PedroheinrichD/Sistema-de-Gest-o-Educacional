package sge;

/**
 * Representa um curso realizado presencialmente.
 */
public class CursoPresencial extends Curso {
    private final String salaAula;

    /**
     * Cria um curso presencial com sua sala de aula.
     *
     * @param nome nome do curso, não nulo nem vazio
     * @param codigo código do curso, não nulo nem vazio
     * @param cargaHoraria carga horária em horas, maior que zero
     * @param salaAula sala de aula, não nula nem vazia
     * @throws IllegalArgumentException se algum dado textual for nulo ou vazio
     *                                  ou a carga horária não for positiva
     */
    public CursoPresencial(String nome, String codigo, int cargaHoraria, String salaAula) {
        super(nome, codigo, cargaHoraria);
        this.salaAula = validarTexto(salaAula, "sala de aula");
    }

    /**
     * Retorna a sala de aula do curso.
     *
     * @return sala de aula
     */
    public String getSalaAula() {
        return salaAula;
    }

    /**
     * Retorna os dados básicos do curso e sua sala de aula.
     *
     * @return detalhes do curso presencial
     */
    @Override
    public String detalharCurso() {
        return super.detalharCurso() + ", modalidade: presencial, sala: " + salaAula;
    }
}
