package CapaNegocio;

import CapaConexion.Conexion;
import Model.Informante;
import Model.Participacion;
import Model.Principal;
import Model.Empresa;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NegocioInsert {

    private Conexion cnn;

    /**
     * Metodo para configurar la Conexion
     */
    public void configurarConexionAccess() {
        cnn = new Conexion();
        this.cnn.setDriver("sun.jdbc.odbc.JdbcOdbcDriver");
        this.cnn.setNombreBaseDatos("jdbc:odbc:aries");
    }
    SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat fusa = new SimpleDateFormat("MM/dd/yyyy");
//
//    public void ModificarPrivilegios(Usuarios user) {
//        this.configurarConexionAccess();
//        this.cnn.setNombreTabla("Usuarios");
//        this.cnn.setSentenciaSQL("UPDATE " + this.cnn.getNombreTabla()
//                + " SET "
//                + " pass='" + user.getPass() + "',"
//                + " cambioSucursal=" + user.isCambioSucursal() + ","
//                //               + " bonosViaje=" + user.isBonosViaje() + ","
//                + " admin=" + user.isAdmin() + ","
//                + "ver=" + user.isVer() + ","
//                + " configuracion=" + user.isConfiguracion() + ","
//                + " mantusu=" + user.isMantusu() + ","
//                + " apePat='" + user.getApePat() + "',"
//                + " apeMat='" + user.getApeMat() + "',"
//                + " Nombre='" + user.getNombre() + "',"
//                + " cargo='" + user.getCargo() + "'"
//                + " where "
//                + " usuario='" + user.getUsuario() + "'");
//        this.cnn.setEsSelect(false);
//        this.cnn.conectar();
//        this.cnn.cerrarConexion();
//    }

    public void ActualizarPago(String informante, String Sucursal, String fecha) {
        this.configurarConexionAccess();
        this.cnn.setNombreTabla("principal");
        this.cnn.setSentenciaSQL("UPDATE " + this.cnn.getNombreTabla()
                + " SET "
                + " pagado=true,"
                + " fechaPagado='" + formato.format(new Date()) + "'"
                + " where medico_informante='" + informante + "'"
                + " AND Sucursal='" + Sucursal + "'"
                + " AND fecha_informe BETWEEN  #01" + fecha.substring(2, 10) + "# AND #" + fecha + "#");

        this.cnn.setEsSelect(false);
        this.cnn.conectar();
        this.cnn.cerrarConexion();
    }

    public void AgregarMes(Principal pri) {
        this.configurarConexionAccess();
        this.cnn.setNombreTabla("principal");
        this.cnn.setSentenciaSQL("Insert into " + this.cnn.getNombreTabla()
                + " (id_atencion,"
                + "fecha_atencion,"
                + "ap_paterno,"
                + "ap_materno,"
                + "nombre,"
                + "codigo_prestacion,"
                + "nombre_prestacion,"
                + "cantidad,"
                + "total_prestacion,"
                + "fecha_informe,"
                + "cod_medico,"
                + "medico_informante,"
                + "sucursal,"
                + "FechaDeCarga,"
                + "pagado) "
                + " Values("
                + pri.getId_atencion() + ",'"
                + formato.format(pri.getFecha_atencion()) + "','"
                + pri.getAp_paterno() + "','"
                + pri.getAp_materno() + "','"
                + pri.getNombre() + "','"
                + pri.getCodigo_prestacion() + "','"
                + pri.getNombre_prestacion() + "',"
                + pri.getCantidad() + ",'"
                + pri.getTotal_prestacion() + "','"
                + formato.format(pri.getFecha_informe()) + "',"
                + pri.getCod_medico() + ",'"
                + pri.getMedico_informante() + "','"
                + pri.getSucursal() + "','"
                + formato.format(pri.getFechaDeCarga()) + "',"
                + "false);");

        this.cnn.setEsSelect(false);
        this.cnn.conectar();
        this.cnn.cerrarConexion();
    }

    public void AgregarEmpresaCod(Empresa emp) {
        this.configurarConexionAccess();
        this.cnn.setNombreTabla("empresa");
        this.cnn.setSentenciaSQL("Insert into " + this.cnn.getNombreTabla()
                + " (cod_empresa,"
                + " nombre_empresa) "
                + " Values('"
                + emp.getCod_empresa() + "','"
                + emp.getNombre_empresa() + "');");

        this.cnn.setEsSelect(false);
        this.cnn.conectar();
        this.cnn.cerrarConexion();
    }

    public void AgregarInformante(Informante inf) {
        this.configurarConexionAccess();
        this.cnn.setNombreTabla("Informante");
        this.cnn.setSentenciaSQL("Insert into " + this.cnn.getNombreTabla()
                + " (cod_medico,"
                + "medico_rut,"
                + "medico_retencion,"
                + "medico_mail) "
                + " Values("
                + inf.getCod_medico() + ",'"
                + inf.getMedico_rut() + "',"
                + inf.getMedico_retencion() + ",'"
                + inf.getMedico_mail() + "');");

        this.cnn.setEsSelect(false);
        this.cnn.conectar();
        this.cnn.cerrarConexion();
    }

    public void AgregarParticipacion(Participacion par) {
        this.configurarConexionAccess();
        this.cnn.setNombreTabla("Participacion");
        this.cnn.setSentenciaSQL("Insert into " + this.cnn.getNombreTabla()
                + " (codigo_prestacion,nombre_prestacion,participacion,grupo,esRetencion) "
                + " Values('" + par.getCodigo_prestacion() + "','"
                + par.getNombre_prestacion() + "',"
                + par.getParticipacion() + ",'"
                + par.getGrupo() + "',"
                + par.isEsretencion() + ");");

        this.cnn.setEsSelect(false);
        this.cnn.conectar();
        this.cnn.cerrarConexion();
    }

    public void eliminarParticipacion(String Cod_prestacion) {
        this.configurarConexionAccess();

        this.cnn.setSentenciaSQL("DELETE FROM participacion WHERE "
                + " codigo_prestacion='" + Cod_prestacion + "'");
        //pagoTotal....
//        System.out.println(cnn.getSentenciaSQL());
        this.cnn.setEsSelect(false);
        this.cnn.conectar();
        this.cnn.cerrarConexion();
//
    }

    public void eliminarPrincipal(String sucursal, Date fecha) {
        this.configurarConexionAccess();
        //MM/DD/YYYY
        this.cnn.setSentenciaSQL("DELETE FROM principal "
                + " WHERE "
                + " pagado=" + false
                + " AND "
                + " Sucursal='" + sucursal + "'"
                + " AND "
                + " FechaDeCarga=#" + fusa.format(fecha) + "#");
        //pagoTotal....
//        System.out.println(cnn.getSentenciaSQL());
        this.cnn.setEsSelect(false);
        this.cnn.conectar();
        this.cnn.cerrarConexion();
    }

    public void ActualizarInformante(Informante infor) {
        this.configurarConexionAccess();
        this.cnn.setNombreTabla("informante");
        this.cnn.setSentenciaSQL("UPDATE " + this.cnn.getNombreTabla()
                + " SET "
                + " medico_rut='" + infor.getMedico_rut() + "',"
                + " medico_retencion=" + infor.getMedico_retencion() + ","
                + " medico_mail='" + infor.getMedico_mail() + "'"
                + " where cod_medico=" + infor.getCod_medico());
        this.cnn.setEsSelect(false);
        this.cnn.conectar();
        this.cnn.cerrarConexion();
    }

    public void eliminarEmpresoCod(String cod_empresa) {
        this.configurarConexionAccess();
        //MM/DD/YYYY
        this.cnn.setSentenciaSQL("DELETE FROM empresa "
                + " WHERE "
                + " cod_empresa='" + cod_empresa + "'");
        //pagoTotal....
//        System.out.println(cnn.getSentenciaSQL());
        this.cnn.setEsSelect(false);
        this.cnn.conectar();
        this.cnn.cerrarConexion();
    }
}
