package com.domo.zoom.serviceya;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;

public class Prestador {
    private int id;
    private String nombre;
    private String apellido;
    private String token;
    private String telefono;
    private String celular;
    private String email;
    private String web;
    private String password;
    private String imagen;
    private String direccion;
    private int habilitado;
    private int estado;
    private String createdAt;
    private String updatedAt;

    public Prestador (){

    }

    public Prestador(int id, String nombre, String apellido, String token, String telefono, String celular, String email, String web, String password, String imagen, String direccion, int habilitado, int estado, String createdAt, String updatedAt) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.token = token;
        this.telefono = telefono;
        this.celular = celular;
        this.email = email;
        this.web = web;
        this.password = password;
        this.imagen = imagen;
        this.direccion = direccion;
        this.habilitado = habilitado;
        this.estado = estado;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "Prestador{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", token='" + token + '\'' +
                ", telefono='" + telefono + '\'' +
                ", celular='" + celular + '\'' +
                ", email='" + email + '\'' +
                ", web='" + web + '\'' +
                ", password='" + password + '\'' +
                ", imagen='" + imagen + '\'' +
                ", direccion='" + direccion + '\'' +
                ", habilitado=" + habilitado +
                ", estado=" + estado +
                ", createdAt='" + createdAt + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                '}';
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

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public int getHabilitado() {
        return habilitado;
    }

    public void setHabilitado(int habilitado) {
        this.habilitado = habilitado;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}

class ItemPrestador{
    private Bitmap imagenItem;
    private String nombreItem;
    private String calItem;
    private String comenItem;

    ItemPrestador(Bitmap imagenItem, String nombreItem, String calItem, String comenItem){
        this.imagenItem = imagenItem;
        this.nombreItem = nombreItem;
        this.calItem = calItem;
        this.comenItem = comenItem;
    }

    public Bitmap getImagenItem() {
        return imagenItem;
    }

    public void setImagenItem(Bitmap imagenItem) {
        this.imagenItem = imagenItem;
    }

    public String getNombreItem() {
        return nombreItem;
    }

    public void setNombreItem(String nombreItem) {
        this.nombreItem = nombreItem;
    }

    public String getCalItem() {
        return calItem;
    }

    public void setCalItem(String calItem) {
        this.calItem = calItem;
    }

    public String getComenItem() {
        return comenItem;
    }

    public void setComenItem(String comenItem) {
        this.comenItem = comenItem;
    }
}
