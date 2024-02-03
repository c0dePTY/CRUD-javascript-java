/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cursojava.controllers;

import com.cursojava.dao.UsuarioDao;
import com.cursojava.models.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.cursojava.utils.JWTUtil;

/**
 * ESTO ES EL CONTROLADOR PARA EL LOGIN.JS Y LOGIN.HTML
 * @author Alejandro
 */

@RestController
public class AuthentiController {
    
    @Autowired //1. anotación: automáticamente genera una instancia de UsuarioDaoImp.java y la guarda en la variable usuarioDao. Esto es una inyección de dependencias
    private UsuarioDao usuarioDao;  
                                                                                //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< B1
    @Autowired //B1. queda cargado automáticamente la clase JWTUtil dentro de el objeto jwtUtil de abajo. Para ello, antes tendré que haber puesto la anotación @Component dentro de la clase JWTUtil.java. Con esto aplico la inyección de dependencias
    private JWTUtil jwtUtil;    //creamos un objeto JWTUtil dentro de este controlador
    
    /**
     * llamada oculta-interna por fetch.Método http POST (enviamos información internamente)
        Request para hacer el inicio de sesión
     * 
     * @param usuario 
     * @return  
     */
    @RequestMapping(value="api/login", method=RequestMethod.POST)  
    public String login(@RequestBody Usuario usuario){ //@RequestBody: transforma el String de Json (txtMail y txtPassword con el que el usuario se logueó) que recibe de login.js, a un objeto Usuario de una vez, quedando este objeto Usuario como: {null, null, null, null, txtMail, txtPassword}        
        Usuario usuarioLogueado=usuarioDao.verificarCredenciales(usuario);
                                                                                //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< C1
        if(usuarioLogueado != null){ //si el usuario que quiere loguearse supera las credenciales, este Servidor (AuthentiController.java) crea la sesión y se la pasa al Cliente a través de un token o JWT.
            System.out.println(">>>hay un usuario logueado>>>");
            
            //GENERO EL JWT-TOKEN (Java Web Token)
            String tokenJWT=jwtUtil.create(String.valueOf(usuarioLogueado.getId()), usuarioLogueado.getMail()); //C1. el método .create() genera el token pidiéndome como argumento un id de usuario. El id y el mail del usuarioLogueado es la información que incluyo en el token o JWT. usuarioLogueado.getId() lo paso a String. El método .create() está definido en la clase JWTUtil.java            
            System.out.println(">>>el Servidor ha generado un JWT-token, y se lo ha enviado al Cliente (browser): \n"+tokenJWT+" >>>");
            return tokenJWT; //enviamos el token (String) con la información deseada al Cliente (login.js) para que lo guarde y lo verifique
            

        } else {
            return "FAIL";
        }
    }  
    
    
    
}
