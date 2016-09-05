package net.juanfrancisco.blog.pedidos.Models;

import com.orm.SugarRecord;

/**
 * Created by franc on 29/08/2016.
 */



public class Producto extends SugarRecord {

    String nombre;

    public Producto(String nombre, String creado, String modificado, String activo, String descripcion, String imagen, String valor, String iva) {
        this.nombre = nombre;
        this.creado = creado;
        this.modificado = modificado;
        this.activo = activo;
        this.descripcion = descripcion;
        this.imagen = imagen;
        this.valor = valor;
        this.iva = iva;
    }

    String creado;
    String modificado;
    String activo;
    String descripcion;
    String imagen;
    String valor;
    String iva;

    public Producto()
    {
    }


    @Override
    public String toString() {
        return "Producto{" +
                "nombre='" + nombre + '\'' +
                ", creado='" + creado + '\'' +
                ", modificado='" + modificado + '\'' +
                ", activo='" + activo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", imagen='" + imagen + '\'' +
                ", valor='" + valor + '\'' +
                ", iva='" + iva + '\'' +
                '}';
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCreado() {
        return creado;
    }

    public void setCreado(String creado) {
        this.creado = creado;
    }

    public String getModificado() {
        return modificado;
    }

    public void setModificado(String modificado) {
        this.modificado = modificado;
    }

    public String getActivo() {
        return activo;
    }

    public void setActivo(String activo) {
        this.activo = activo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getIva() {
        return iva;
    }

    public void setIva(String iva) {
        this.iva = iva;
    }
}
