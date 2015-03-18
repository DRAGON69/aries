/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author jvaldivia
 */
public class Informante {

    private int id;
    private int cod_medico;
    private String medico_rut;
    private int medico_retencion;
    private String medico_mail;
    private String nombre;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Informante() {
    }

    public int getCod_medico() {
        return cod_medico;
    }

    public void setCod_medico(int cod_medico) {
        this.cod_medico = cod_medico;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMedico_mail() {
        return medico_mail;
    }

    public void setMedico_mail(String medico_mail) {
        this.medico_mail = medico_mail;
    }

    public int getMedico_retencion() {
        return medico_retencion;
    }

    public void setMedico_retencion(int medico_retencion) {
        this.medico_retencion = medico_retencion;
    }

    public String getMedico_rut() {
        return medico_rut;
    }

    public void setMedico_rut(String medico_rut) {
        this.medico_rut = medico_rut;
    }
}
