/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CapaConexion;

//import java.io.*;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Alumno
 */
public class Conexion {
    //Atributos Clase

    private String nombreBaseDatos;
    private String driver;
    private String nombreTabla;
    private String user;
    private String password;
    private String sentenciaSQL;
    private boolean esSelect;
    private int resultadoSQL;
    //Atributos del API JDBC
    private Connection cnn;
    private Statement stm;
    private ResultSet rst;

    public Conexion(String nombreBaseDatos, String driver, String nombreTabla, String user, String password, String sentenciaSQL, boolean esSelect, int resultadoSQL) {
        this.nombreBaseDatos = nombreBaseDatos;
        this.driver = driver;
        this.nombreTabla = nombreTabla;
        this.user = user;
        this.password = password;
        this.sentenciaSQL = sentenciaSQL;
        this.esSelect = esSelect;
        this.resultadoSQL = resultadoSQL;
    }

    public Conexion() {
        this.nombreBaseDatos = "";
        this.driver = "";
        this.nombreTabla = "";
        this.user = "";
        this.password = "";
        this.sentenciaSQL = "";
        this.esSelect = false;
        this.resultadoSQL = 0;
    }

    /**
     * @return the nombreBaseDatos
     */
    public String getNombreBaseDatos() {
        return nombreBaseDatos;
    }

    /**
     * @param nombreBaseDatos the nombreBaseDatos to set
     */
    public void setNombreBaseDatos(String nombreBaseDatos) {
        this.nombreBaseDatos = nombreBaseDatos;
    }

    /**
     * @return the driver
     */
    public String getDriver() {
        return driver;
    }

    /**
     * @param driver the driver to set
     */
    public void setDriver(String driver) {
        this.driver = driver;
    }

    /**
     * @return the nombreTabla
     */
    public String getNombreTabla() {
        return nombreTabla;
    }

    /**
     * @param nombreTabla the nombreTabla to set
     */
    public void setNombreTabla(String nombreTabla) {
        this.nombreTabla = nombreTabla;
    }

    /**
     * @return the user
     */
    public String getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the sentenciaSQL
     */
    public String getSentenciaSQL() {
        return sentenciaSQL;
    }

    /**
     * @param sentenciaSQL the sentenciaSQL to set
     */
    public void setSentenciaSQL(String sentenciaSQL) {
        this.sentenciaSQL = sentenciaSQL;
    }

    /**
     * @return the esSelect
     */
    public boolean getEsSelect() {
        return esSelect;
    }

    /**
     * @param esSelect the esSelect to set
     */
    public void setEsSelect(boolean esSelect) {
        this.esSelect = esSelect;
    }

    /**
     * @return the resultadoSQL
     */
    public int getResultadoSQL() {
        return resultadoSQL;
    }

    /**
     * @param resultadoSQL the resultadoSQL to set
     */
    public void setResultadoSQL(int resultadoSQL) {
        this.resultadoSQL = resultadoSQL;
    }

    /**
     * @return the cnn
     */
    public Connection getCnn() {
        return cnn;
    }

    /**
     * @param cnn the cnn to set
     */
    public void setCnn(Connection cnn) {
        this.cnn = cnn;
    }

    /**
     * @return the stm
     */
    public Statement getStm() {
        return stm;
    }

    /**
     * @param stm the stm to set
     */
    public void setStm(Statement stm) {
        this.stm = stm;
    }

    /**
     * @return the rst
     */
    public ResultSet getRst() {
        return rst;
    }

    /**
     * @param rst the rst to set
     */
    public void setRst(ResultSet rst) {
        this.rst = rst;
    }

    /**
     * Metodo para Cerrar la Conexion
     */
    public void cerrarConexion() {
        try {
            this.getCnn().close();
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Metodo Principal de la Conexion
     */
    public void conectar() {

        try {
            //Cargar el Driver de Conexión
            Class.forName(this.getDriver());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex);
        }
        try {
            //Crear Conexión
            this.setCnn(DriverManager.getConnection(this.getNombreBaseDatos(),
                    this.getUser(), this.getPassword()));
            //Crear el Comando para SQL
            this.setStm(this.getCnn().createStatement());
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null,
                    "Error en el Origen ODBC Base de Datos\n" + ex,
                    "Error",
                    JOptionPane.WARNING_MESSAGE);
            System.exit(0);
        }

        /**
         * Verificar si es un QUERY o un UPDATE
         */
        if (this.getEsSelect() == true) {
            try {
                //Ejecutar una Consulta
                this.setRst(this.getStm().executeQuery(this.getSentenciaSQL()));
            } catch (SQLException ex) {
                Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex+"\n Tabla Abierta", "Error de Base de Datos", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            try {
                //Ejecutar una Actualización
                this.setResultadoSQL(this.getStm().executeUpdate(this.getSentenciaSQL()));
            } catch (SQLException ex) {
                Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
