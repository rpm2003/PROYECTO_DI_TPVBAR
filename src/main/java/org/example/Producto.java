package org.example;

public class Producto {
    private int id;
    private String nombre;
    private double precio;
    private String url;

    public Producto(int id, String nombre, double precio, String url) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }
    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return nombre + precio + "€";
    }
}
