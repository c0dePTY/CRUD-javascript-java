/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cursojava.dao;

import com.cursojava.models.Usuario;
import java.util.List;

/**
 * interfaz con al menos un método abstracto. Las clases que implementen esta interfaz, forzosamente tendrán que desarrollar el método abstracto * 
 * DAO= Data Access Objtect
 * 
 * @author Alejandro
 */
public interface UsuarioDao { 
    
    List<Usuario> getUsuarios(); //método abstracto, obligatorio que han de desarrollar las clases que implementen esta interfaz

    public void eliminar(Long id); //segundo método abstracto que se ha de implementar en UsuarioDaoImp.java

    public void registrar(Usuario usuario);
    
    public Usuario verificarCredenciales(Usuario usuario);
    
}
