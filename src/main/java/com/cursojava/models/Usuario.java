/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cursojava.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
//import lombok.Getter;
//import lombok.Setter;

/**
 *
 * @author Alejandro
 */

@Entity //anotación que indica ¿a Hibernate? que Usuario.java es una entidad que va a ser referida en la BD
@Table(name= "usuarios") //anotación que indica a Hibernate que el modelo Usuario.java corresponde con la tabla de mySQL usuarios.
public class Usuario {
    
    @Id //anotación que indica ¿a Hibernate? que el atributo id es clave primaria.
    @Column(name="id") //@Column indica a HIBERNATE el nombre de la columna en la tabla mySQL al que coresponde cada atributo de ususario.java    
    @GeneratedValue(strategy=GenerationType.IDENTITY) //¿03:00:00? ¿indica que el atributo Id se autogenera en phpMySQL?
    private Long id;
    
    @Column(name="nombre")
    private String nombre;
    
    @Column(name="apellido")
    private String apellido;
    
    @Column(name="mail")
    private String mail;
    
    @Column(name="telefono")
    private String telefono;
    
    @Column(name="password")
    private String password;

    
    //getters&setters
    /*public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    */

    

        
}
