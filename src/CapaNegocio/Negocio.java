package CapaNegocio;

import CapaConexion.Conexion;
import Model.Informante;
import Model.Participacion;
import Model.Principal;
import Model.Empresa;
import Vista.GUI;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class Negocio {

    private Conexion cnn;
    public int cantdatos = 0;

    private void configurarConexionAccess() {
        cnn = new Conexion();
        this.cnn.setDriver("sun.jdbc.odbc.JdbcOdbcDriver");
        this.cnn.setNombreBaseDatos("jdbc:odbc:aries");
    }

    public void configurarConexionOracle() {
        cnn = new Conexion();

        this.cnn.setDriver("oracle.jdbc.driver.OracleDriver");
        this.cnn.setNombreBaseDatos("jdbc:oracle:thin:@172.31.2.181:1521:MRT");
        this.cnn.setUser("MEDIREP12");
        this.cnn.setPassword("REP12");

    }

    public ArrayList CargXLS(File archivoXLS) {
        cantdatos = 0;
        ArrayList Datos = new ArrayList();
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Workbook libro = Workbook.getWorkbook(archivoXLS);
            cantdatos = libro.getSheet(0).getRows();
            // Para obtener el libro .xls
            Sheet hoja1 = libro.getSheet(0);
            //fila
//            System.out.println(hoja1.getCell(0, 1).getContents().toString());
            String sucursal = "";
            if (hoja1.getCell(0, 1).getContents().toString().equalsIgnoreCase("SUCURSAL       :BANDERA")) {
                sucursal = "Bandera";
            }
            if (hoja1.getCell(0, 1).getContents().toString().equalsIgnoreCase("SUCURSAL       :MANUEL MONTT")) {
                sucursal = "Manuel Montt";
            }
            if (hoja1.getCell(0, 1).getContents().toString().equalsIgnoreCase("SUCURSAL       :SAN ANTONIO")) {
                sucursal = "San Antonio";
            }

            for (int fila = 5; fila < cantdatos + 6; fila++) {
                Principal pri = new Principal();
                try {
                    pri.setId_atencion(Integer.parseInt(hoja1.getCell(1, fila).getContents().toString()));
                    pri.setFecha_atencion(formato.parse(hoja1.getCell(6, fila).getContents()));
                    pri.setAp_paterno(hoja1.getCell(8, fila).getContents());
                    pri.setAp_materno(hoja1.getCell(9, fila).getContents());
                    pri.setNombre(hoja1.getCell(10, fila).getContents());
                    pri.setCodigo_prestacion(hoja1.getCell(11, fila).getContents());
                    pri.setNombre_prestacion(hoja1.getCell(13, fila).getContents());
                    pri.setCantidad(hoja1.getCell(15, fila).getContents());
                    pri.setTotal_prestacion(Integer.parseInt(hoja1.getCell(16, fila).getContents().toString()));
                    pri.setFecha_informe(formato.parse(hoja1.getCell(18, fila).getContents()));
                    pri.setCod_medico(Integer.parseInt(hoja1.getCell(22, fila).getContents()));
                    pri.setMedico_informante(hoja1.getCell(23, fila).getContents());
                    pri.setFechaDeCarga(new Date());
                    pri.setSucursal(sucursal);

                    Datos.add(pri);
                } catch (NumberFormatException ex) {
                } catch (ParseException ex) {
                    Logger.getLogger(Negocio.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Negocio.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BiffException ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (java.lang.ArrayIndexOutOfBoundsException ex) {
        }
        return Datos;
    }

    public ArrayList ListarDatos(String informante, String sucursal, boolean inicio, String fecha, String empresa, String cod, String apPat, String apMat, String nombre) {
        ArrayList listaDatos = new ArrayList();
        cantdatos = 0;
        this.configurarConexionAccess();
        String SQL = " SELECT "
                + "pri.id_atencion as a,"
                + "pri.fecha_atencion as b,"
                + "pri.ap_paterno as c,"
                + "pri.ap_materno as d,"
                + "pri.nombre as e,"
                + "pri.codigo_prestacion as f,"
                + "pri.nombre_prestacion as g,"
                + "pri.cantidad as h,"
                + "pri.total_prestacion as i,"
                + "pri.fecha_informe as j,"
                + "pri.cod_medico as k,"
                + "pri.medico_informante as l,"
                + "pri.Sucursal as m,"
                + "pri.pagado as n,"
                + "(SELECT par.participacion FROM participacion par where par.codigo_prestacion=pri.codigo_prestacion)  as porcentaje,"
                + " pri.fechapagado as o"
                + ",(SELECT inf.medico_retencion FROM informante inf where inf.cod_medico=pri.cod_medico)  as retencion"
                + ",(SELECT inf2.medico_rut FROM informante inf2 where inf2.cod_medico=pri.cod_medico)  as rut  "
                + " from principal pri";


        if (!inicio) {
            SQL = SQL + " WHERE ";

            if (!informante.equalsIgnoreCase("Seleccione")) {
                SQL = SQL + " pri.medico_informante like '" + informante + "' AND ";
            }
            if (!sucursal.equalsIgnoreCase("Seleccionar")) {
                SQL = SQL + " pri.sucursal like '" + sucursal + "' AND ";
            }
            if (!cod.equalsIgnoreCase("")) {
                SQL = SQL + " pri.id_atencion=" + cod + " AND ";
            }
            if (!apPat.equalsIgnoreCase("")) {
                SQL = SQL + " ap_paterno like '" + apPat + "' AND ";
            }
            if (!apMat.equalsIgnoreCase("")) {
                SQL = SQL + " ap_materno like '" + apMat + "' AND ";
            }
            if (!nombre.equalsIgnoreCase("")) {
                SQL = SQL + " nombre like '" + nombre + "' AND ";
            }
            if (!empresa.equalsIgnoreCase("Seleccionar")) {
                String cadena = listarEmpresas(empresa);
                SQL = SQL + " mid(codigo_prestacion, 11, 12) in (" + cadena.substring(0, cadena.length() - 1) + ") AND ";
            }

            SQL = SQL + " pri.fecha_informe BETWEEN  #" + fecha.substring(0, 3) + "01" + fecha.substring(5, 10) + "# AND #" + fecha + "#"
                    + " order by pri.ap_paterno";
        }

//        System.out.println(SQL);
        this.cnn.setSentenciaSQL(SQL);
        this.cnn.setEsSelect(true);
        this.cnn.conectar();
        try {
            //Rutina para Accesar los datos de la Tabla, con el ResultSet
            while (this.cnn.getRst().next()) {
                Principal pri = new Principal();

                pri.setId_atencion(cnn.getRst().getInt("a"));
                pri.setFecha_atencion(cnn.getRst().getDate("b"));
                pri.setAp_paterno(cnn.getRst().getString("c"));
                pri.setAp_materno(cnn.getRst().getString("d"));
                pri.setNombre(cnn.getRst().getString("e"));
                pri.setCodigo_prestacion(cnn.getRst().getString("f"));
                pri.setNombre_prestacion(cnn.getRst().getString("g"));
                pri.setCantidad(cnn.getRst().getString("h"));
                pri.setTotal_prestacion(cnn.getRst().getInt("i"));
                pri.setFecha_informe(cnn.getRst().getDate("j"));
                pri.setCod_medico(cnn.getRst().getInt("k"));
                pri.setMedico_informante(cnn.getRst().getString("l"));
                pri.setSucursal(cnn.getRst().getString("m"));
                pri.setParticipacion(cnn.getRst().getInt("porcentaje"));
                pri.setPagado(cnn.getRst().getBoolean("n"));
                pri.setFechaPagado(cnn.getRst().getDate("o"));
                pri.setRetencion(cnn.getRst().getInt("retencion"));
                pri.setRut(cnn.getRst().getString("rut"));

                listaDatos.add(pri);

                cantdatos++;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Negocio.class.getName()).log(Level.SEVERE, null, ex);
        }
        //Cerrar la Conexion
        this.cnn.cerrarConexion();
        return listaDatos;
    }

    public ArrayList ListarInformates() {
        ArrayList listaDatos = new ArrayList();
        cantdatos = 0;
        this.configurarConexionAccess();
        this.cnn.setSentenciaSQL(" SELECT medico_informante from principal group by medico_informante");
        this.cnn.setEsSelect(true);
        this.cnn.conectar();
        try {
            //Rutina para Accesar los datos de la Tabla, con el ResultSet
            while (this.cnn.getRst().next()) {
                Informante inf = new Informante();
                inf.setNombre(cnn.getRst().getString("medico_informante"));
                // inf.setId(cnn.getRst().getInt("cod_medico"));

                listaDatos.add(inf);
                cantdatos++;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Negocio.class.getName()).log(Level.SEVERE, null, ex);
        }
        //Cerrar la Conexion
        this.cnn.cerrarConexion();
        return listaDatos;

    }

    public ArrayList ListarParticipacionGrupos() {
        ArrayList listaDatos = new ArrayList();
        cantdatos = 0;
        this.configurarConexionAccess();
        this.cnn.setSentenciaSQL(" SELECT grupo from participacion group by grupo");
        this.cnn.setEsSelect(true);
        this.cnn.conectar();
        try {
            //Rutina para Accesar los datos de la Tabla, con el ResultSet
            while (this.cnn.getRst().next()) {
                Informante inf = new Informante();
                inf.setNombre(cnn.getRst().getString("grupo"));
                listaDatos.add(inf);
                cantdatos++;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Negocio.class.getName()).log(Level.SEVERE, null, ex);
        }
        //Cerrar la Conexion
        this.cnn.cerrarConexion();
        return listaDatos;
    }

    public boolean validaID(File archivoXLS) {
        boolean valida = true;
        try {

            Workbook libro = Workbook.getWorkbook(archivoXLS);
            cantdatos = libro.getSheet(0).getRows();
            // Para obtener el libro .xls
            Sheet hoja1 = libro.getSheet(0);
            String sucursal = "";
            if (hoja1.getCell(0, 1).getContents().toString().equalsIgnoreCase("SUCURSAL       :BANDERA")) {
                sucursal = "Bandera";
            }

            if (hoja1.getCell(0, 1).getContents().toString().equalsIgnoreCase("SUCURSAL       :MANUEL MONTT")) {
                sucursal = "Manuel Montt";
            }

            if (hoja1.getCell(0, 1).getContents().toString().equalsIgnoreCase("SUCURSAL       :SAN ANTONIO")) {
                sucursal = "San Antonio";
            }
            Iterator itAccess = ListarDatos("Seleccione", "Seleccionar", true, "Seleccionar", "Seleccionar", "", "", "", "").iterator();
            while (itAccess.hasNext()) {
                Principal priAccess = (Principal) itAccess.next();
                Iterator itXls = CargXLS(archivoXLS).iterator();
                while (itXls.hasNext()) {
                    Principal priXls = (Principal) itXls.next();
                    if (priAccess.getId_atencion() == (priXls.getId_atencion()) && priAccess.getSucursal().equalsIgnoreCase(sucursal)) {
                        JOptionPane.showMessageDialog(null, "El XLS contiene ID ya agregadas\n" + priAccess.getId_atencion() + " = " + priXls.getId_atencion() + " ya esta en " + priAccess.getSucursal(), "Error", JOptionPane.WARNING_MESSAGE);
//                        System.out.println(priAccess.getId_atencion() + " = " + (priXls.getId_atencion()) + " AND " + priAccess.getSucursal());
                        return false;
                    }
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Negocio.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BiffException ex) {
            Logger.getLogger(Negocio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return valida;
    }

    public ArrayList ListarParticipaciones(String NombreGrupo) {
        ArrayList listaDatos = new ArrayList();
        cantdatos = 0;
        this.configurarConexionAccess();
        this.cnn.setSentenciaSQL(" SELECT * from participacion WHERE grupo='" + NombreGrupo + "'");
        this.cnn.setEsSelect(true);
        this.cnn.conectar();
        try {
            //Rutina para Accesar los datos de la Tabla, con el ResultSet
            while (this.cnn.getRst().next()) {
                Participacion par = new Participacion();
                par.setNombre_prestacion(cnn.getRst().getString("nombre_prestacion"));
                par.setCodigo_prestacion(cnn.getRst().getString("codigo_prestacion"));
                par.setParticipacion(cnn.getRst().getInt("participacion"));
                listaDatos.add(par);
                cantdatos++;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Negocio.class.getName()).log(Level.SEVERE, null, ex);
        }
        //Cerrar la Conexion
        this.cnn.cerrarConexion();
        return listaDatos;
    }

    public ArrayList ListarEmpresa(String nombre_empresa) {
        ArrayList listaDatos = new ArrayList();
        cantdatos = 0;
        this.configurarConexionAccess();
        this.cnn.setSentenciaSQL(" SELECT * from empresa WHERE nombre_empresa='" + nombre_empresa + "' order by cod_empresa");

//        System.out.println(cnn.getSentenciaSQL());
        this.cnn.setEsSelect(true);
        this.cnn.conectar();
        try {
            //Rutina para Accesar los datos de la Tabla, con el ResultSet
            while (this.cnn.getRst().next()) {
                Empresa emp = new Empresa();
                emp.setNombre_empresa(cnn.getRst().getString("nombre_empresa"));
                emp.setCod_empresa(cnn.getRst().getString("cod_empresa"));

                listaDatos.add(emp);
                cantdatos++;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Negocio.class.getName()).log(Level.SEVERE, null, ex);
        }
        //Cerrar la Conexion
        this.cnn.cerrarConexion();
        return listaDatos;
    }

    public boolean validarCodEmpresa(String codigo) {
        boolean validar = true;
        cantdatos = 0;
        this.configurarConexionAccess();
        this.cnn.setSentenciaSQL(" SELECT * from empresa WHERE cod_empresa='" + codigo + "'");
        this.cnn.setEsSelect(true);
        this.cnn.conectar();
        try {
            //Rutina para Accesar los datos de la Tabla, con el ResultSet
            while (this.cnn.getRst().next()) {
                validar = false;
                cantdatos++;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Negocio.class.getName()).log(Level.SEVERE, null, ex);
        }
        //Cerrar la Conexion
        this.cnn.cerrarConexion();
        return validar;
    }

    public boolean validarCodigo(String codigo) {
        boolean validar = true;
        cantdatos = 0;
        this.configurarConexionAccess();
        this.cnn.setSentenciaSQL(" SELECT * from participacion WHERE codigo_prestacion='" + codigo + "'");
        this.cnn.setEsSelect(true);
        this.cnn.conectar();
        try {
            //Rutina para Accesar los datos de la Tabla, con el ResultSet
            while (this.cnn.getRst().next()) {
                validar = false;
                cantdatos++;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Negocio.class.getName()).log(Level.SEVERE, null, ex);
        }
        //Cerrar la Conexion
        this.cnn.cerrarConexion();
        return validar;
    }

    public boolean validaInf(String codigo) {
        boolean validar = true;
        cantdatos = 0;
        this.configurarConexionAccess();
        this.cnn.setSentenciaSQL(" SELECT * from informante WHERE cod_medico=" + codigo + "");
        this.cnn.setEsSelect(true);
        this.cnn.conectar();
        try {
            //Rutina para Accesar los datos de la Tabla, con el ResultSet
            while (this.cnn.getRst().next()) {
                validar = false;
                cantdatos++;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Negocio.class.getName()).log(Level.SEVERE, null, ex);
        }
        //Cerrar la Conexion
        this.cnn.cerrarConexion();
        return validar;
    }

    public String listarEmpresas(String empresa) {
        String cadena = "";
        cantdatos = 0;
        this.configurarConexionAccess();
        this.cnn.setSentenciaSQL(" SELECT * from empresa WHERE nombre_empresa='" + empresa + "'");
        this.cnn.setEsSelect(true);
        this.cnn.conectar();
        try {
            //Rutina para Accesar los datos de la Tabla, con el ResultSet
            while (this.cnn.getRst().next()) {
                cadena = cadena + "'" + cnn.getRst().getString("cod_empresa") + "',";
            }
        } catch (SQLException ex) {
            Logger.getLogger(Negocio.class.getName()).log(Level.SEVERE, null, ex);
        }
        //Cerrar la Conexion
        this.cnn.cerrarConexion();
        return cadena;
    }

    public String BuscarRutInf(String cod_informante) {
        String rut = "";
        cantdatos = 0;
        this.configurarConexionOracle();
        this.cnn.setSentenciaSQL("SELECT rut_prof,dv_prof FROM GEN_PROFESIONAL WHERE cod_prof=" + cod_informante);
        this.cnn.setEsSelect(true);
        this.cnn.conectar();
        try {
            //Rutina para Accesar los datos de la Tabla, con el ResultSet
            while (this.cnn.getRst().next()) {
                rut = cnn.getRst().getString("rut_prof") + "-" + cnn.getRst().getString("dv_prof");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Negocio.class.getName()).log(Level.SEVERE, null, ex);
        }
        //Cerrar la Conexion
        this.cnn.cerrarConexion();
        return rut;
    }

    public ArrayList ListarMes(String Sucursal, String fecha, String empresa) {
        ArrayList listaDatos = new ArrayList();
        cantdatos = 0;
        this.configurarConexionAccess();
        String SQL = " SELECT "
                + " medico_informante,"
                + " Cod_medico,"
                + " sum(total_prestacion*cantidad) as suma ,"
                + "(SELECT inf.medico_retencion FROM informante inf where inf.cod_medico=pri.cod_medico) as retencion"
                + " from "
                + " principal pri  "
                + " WHERE "
                + " SUCURSAL like '" + Sucursal + "' AND ";

        if (!empresa.equalsIgnoreCase("Seleccionar")) {
            String cadena = listarEmpresas(empresa);
            SQL = SQL + " mid(codigo_prestacion, 11, 12) in (" + cadena.substring(0, cadena.length() - 1) + ") AND ";
        }

        SQL = SQL + " pri.fecha_informe BETWEEN  #" + fecha.substring(0, 3) + "01" + fecha.substring(5, 10) + "# AND #" + fecha + "#"
                + " GROUP BY medico_informante,Cod_medico"
                + " order BY medico_informante";

//        System.out.println(SQL);
        this.cnn.setSentenciaSQL(SQL);
        this.cnn.setEsSelect(true);
        this.cnn.conectar();
        try {
            //Rutina para Accesar los datos de la Tabla, con el ResultSet
            while (this.cnn.getRst().next()) {
                Principal pri = new Principal();

                pri.setCod_medico(cnn.getRst().getInt("Cod_medico"));
                pri.setMedico_informante(cnn.getRst().getString("medico_informante"));
                pri.setTotal_prestacion(cnn.getRst().getInt("suma"));
                pri.setRetencion(cnn.getRst().getInt("retencion"));

                listaDatos.add(pri);
                cantdatos++;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Negocio.class.getName()).log(Level.SEVERE, null, ex);
        }
        //Cerrar la Conexion
        this.cnn.cerrarConexion();
        return listaDatos;
    }

    public ArrayList ListarSucursales() {
        ArrayList listaDatos = new ArrayList();
        cantdatos = 0;
        this.configurarConexionAccess();
        String SQL = " SELECT sucursal "
                + " from "
                + " principal pri  "
                + " GROUP BY sucursal"
                + " ORDER BY sucursal";

//        System.out.println(SQL);
        this.cnn.setSentenciaSQL(SQL);
        this.cnn.setEsSelect(true);
        this.cnn.conectar();
        try {
            //Rutina para Accesar los datos de la Tabla, con el ResultSet
            while (this.cnn.getRst().next()) {
                Principal pri = new Principal();

                pri.setSucursal(cnn.getRst().getString("sucursal"));

                listaDatos.add(pri);
                cantdatos++;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Negocio.class.getName()).log(Level.SEVERE, null, ex);
        }
        //Cerrar la Conexion
        this.cnn.cerrarConexion();
        return listaDatos;
    }
}
