package net.juanfrancisco.blog.pedidos.Models;

import com.orm.SugarRecord;

/**
 * Created by franc on 29/08/2016.
 */
public class Producto extends SugarRecord {
    String Nombre;
    String Descripcion;
    String Imagen;
    Float Valor;
    Float Iva;

    // Default constructor is necessary for SugarRecord
    public Producto() {

    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public String getImagen() {
        return Imagen;
    }

    public void setImagen(String imagen) {
        Imagen = imagen;
    }

    public Float getValor() {
        return Valor;
    }

    public void setValor(Float valor) {
        Valor = valor;
    }

    public Float getIva() {
        return Iva;
    }

    public void setIva(Float iva) {
        Iva = iva;
    }



}
