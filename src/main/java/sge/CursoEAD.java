package sge;

/**
 * Representa um curso oferecido na modalidade de educação a distância.
 */
public class CursoEAD extends Curso {
    private final String plataformaVirtual;

    /**
     * Cria um curso EAD com sua plataforma virtual.
     *
     * @param nome nome do curso, não nulo nem vazio
     * @param codigo código do curso, não nulo nem vazio
     * @param cargaHoraria carga horária em horas, maior que zero
     * @param plataformaVirtual plataforma virtual, não nula nem vazia
     * @throws IllegalArgumentException se algum dado textual for nulo ou vazio
     *                                  ou a carga horária não for positiva
     */
    public CursoEAD(String nome, String codigo, int cargaHoraria, String plataformaVirtual) {
        super(nome, codigo, cargaHoraria);
        this.plataformaVirtual = validarTexto(plataformaVirtual, "plataforma virtual");
    }

    /**
     * Retorna a plataforma virtual do curso.
     *
     * @return plataforma virtual
     */
    public String getPlataformaVirtual() {
        return plataformaVirtual;
    }

    /**
     * Retorna os dados básicos do curso e sua plataforma virtual.
     *
     * @return detalhes do curso EAD
     */
    @Override
    public String detalharCurso() {
        return super.detalharCurso() + ", modalidade: EAD, plataforma: " + plataformaVirtual;
    }
}
