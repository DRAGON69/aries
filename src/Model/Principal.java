/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.Date;

/**
 *
 * @author jvaldivia
 */
public class Principal {

    private int id_atencion;
    private Date fecha_atencion;
    private String ap_paterno;
    private String ap_materno;
    private String nombre;
    private String codigo_prestacion;
    private String nombre_prestacion;
    private String cantidad;
    private int total_prestacion;
    private Date fecha_informe;
    private int cod_medico;
    private String medico_informante;
    private String Sucursal;
    private int participacion;
    private Date FechaDeCarga;
    private boolean pagado;
    private Date FechaPagado;
    private int retencion;
    private String rut;

    public int getRetencion() {
        return retencion;
    }

    public void setRetencion(int retencion) {
        this.retencion = retencion;
    }

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }


    public Date getFechaDeCarga() {
        return FechaDeCarga;
    }

    public void setFechaDeCarga(Date FechaDeCarga) {
        this.FechaDeCarga = FechaDeCarga;
    }

    public Date getFechaPagado() {
        return FechaPagado;
    }

    public void setFechaPagado(Date FechaPagado) {
        this.FechaPagado = FechaPagado;
    }

    public boolean isPagado() {
        return pagado;
    }

    public void setPagado(boolean pagado) {
        this.pagado = pagado;
    }

    public int getParticipacion() {
        return participacion;
    }

    public void setParticipacion(int participacion) {
        this.participacion = participacion;
    }

    public Principal() {
    }

    public String getSucursal() {
        return Sucursal;
    }

    public void setSucursal(String Sucursal) {
        this.Sucursal = Sucursal;
    }

    public String getAp_materno() {
        return ap_materno;
    }

    public void setAp_materno(String ap_materno) {
        this.ap_materno = ap_materno;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public int getCod_medico() {
        return cod_medico;
    }

    public void setCod_medico(int cod_medico) {
        this.cod_medico = cod_medico;
    }

    public String getCodigo_prestacion() {
        return codigo_prestacion;
    }

    public void setCodigo_prestacion(String codigo_prestacion) {
        this.codigo_prestacion = codigo_prestacion;
    }

    public Date getFecha_atencion() {
        return fecha_atencion;
    }

    public void setFecha_atencion(Date fecha_atencion) {
        this.fecha_atencion = fecha_atencion;
    }

    public Date getFecha_informe() {
        return fecha_informe;
    }

    public void setFecha_informe(Date fecha_informe) {
        this.fecha_informe = fecha_informe;
    }

    public int getId_atencion() {
        return id_atencion;
    }

    public void setId_atencion(int id_atencion) {
        this.id_atencion = id_atencion;
    }

    public String getMedico_informante() {
        return medico_informante;
    }

    public void setMedico_informante(String medico_informante) {
        this.medico_informante = medico_informante;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre_prestacion() {
        return nombre_prestacion;
    }

    public void setNombre_prestacion(String nombre_prestacion) {
        this.nombre_prestacion = nombre_prestacion;
    }

    public String getAp_paterno() {
        return ap_paterno;
    }

    public void setAp_paterno(String pa_paterno) {
        this.ap_paterno = pa_paterno;
    }

    public int getTotal_prestacion() {
        return total_prestacion;
    }

    public void setTotal_prestacion(int total_prestacion) {
        this.total_prestacion = total_prestacion;
    }
}
