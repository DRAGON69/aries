/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author jvaldivia
 */
public class Participacion {

    private int id;
    private String codigo_prestacion;
    private String nombre_prestacion;
    private int participacion;
    private String grupo;
    private boolean esretencion;

    public Participacion() {
    }

    public boolean isEsretencion() {
        return esretencion;
    }

    public void setEsretencion(boolean esretencion) {
        this.esretencion = esretencion;
    }

    public String getCodigo_prestacion() {
        return codigo_prestacion;
    }

    public void setCodigo_prestacion(String codigo_prestacion) {
        this.codigo_prestacion = codigo_prestacion;
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre_prestacion() {
        return nombre_prestacion;
    }

    public void setNombre_prestacion(String nombre_prestacion) {
        this.nombre_prestacion = nombre_prestacion;
    }

    public int getParticipacion() {
        return participacion;
    }

    public void setParticipacion(int participacion) {
        this.participacion = participacion;
    }
}
