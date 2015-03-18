/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Model;

public class Empresa {
private int id;
private String cod_empresa;
private String nombre_empresa;

    public Empresa() {
    }

    public String getCod_empresa() {
        return cod_empresa;
    }

    public void setCod_empresa(String cod_empresa) {
        this.cod_empresa = cod_empresa;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre_empresa() {
        return nombre_empresa;
    }

    public void setNombre_empresa(String nombre_empresa) {
        this.nombre_empresa = nombre_empresa;
    }
}
