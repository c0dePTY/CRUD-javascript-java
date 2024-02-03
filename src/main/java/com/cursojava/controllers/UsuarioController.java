/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cursojava.controllers;

import com.cursojava.dao.UsuarioDao;
import com.cursojava.models.Usuario;
import com.cursojava.utils.JWTUtil;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**cada vez que el Cliente (browser) lance una request al Server (Controlador), el Cliente le enviará un token con cierta información, que será verificada por el Controlador. Es decir, el Controlador verificará de que el Cliente ha iniciado sesión correctamente.
 *
 * @author Alejandro
 */

@RestController
public class UsuarioController {
    
    @Autowired //1. anotación: automáticamente genera una instancia de UsuarioDaoImp.java y la guarda en la variable usuarioDao de abajo. Se emplea la inetrfaz y no la clase implementada. Esto es una inyección de dependencias
    private UsuarioDao usuarioDao;   
                                                                                
                                                                                //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< B1
    @Autowired //B1. queda cargado automáticamente la clase JWTUtil dentro de el objeto jwtUtil de abajo. Para ello, antes tendré que haber puesto la anotación @Component dentro de la clase JWTUtil.java. Con esto aplico la inyección de dependencias
    private JWTUtil jwtUtil;  //creamos un objeto JWTUtil dentro de este controlador                                                  
    
    
    
    /**
     * >>llamada oculta por fetch. Método http GET (obtiene ususarios de la BD para populate la tabla del html)
     * 
     * @return 
     */                                                                         //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< D3 hecho por lucas. Yo he de repetirlo con: eliminar
    @RequestMapping(value="api/usuarios", method=RequestMethod.GET)   //capta la petición oculta hecha por el fetch 'usuarios' en usuarios.js
    public List<Usuario> getUsuarios(@RequestHeader (value="Authorization")String token){ //D3. @RequestHeader: anotación para que el Servidor reciba el token enviado por usuarios.js a través de su header. El token queda guardado dentro del String token de esta linea.
                                                                                            //el Servidor verifica que el token enviado por el Cliente es correcto
                                                                                            //si Authorization viene vacio, sin ningún token desde usuarios.js, veremos en el chorme/network - name: usuarios fetch /headers/authorization/status code: 400 error==> significa que al parámetro Authorization, en el UsuarioController, le falta información ==>min [4:10:00, 4:16:20]        
        String usuarioId=jwtUtil.getKey(token); //extraemos del token el id del usuario
        if (usuarioId ==null){  //Esta linea es una comprobación POBRE. Mejor haber comprobado si el usuarioId corresponde a algún registro de la BD.
            return new ArrayList<>(); //si el id extraido del token es null, se retorna un ArrayList<> vacio para populate la tabla de .../usuarios.html.
        }        
        
        return usuarioDao.getUsuarios(); //2. devuelve una lista llena con los resultados de la query hecha a la BD en UsuarioDaoImp.java 
    }    

    
    
    /**
     * >>llamada oculta por fetch. Método http DELETE
     * 
     * @param id 
     */
    @RequestMapping(value="api/usuarios/{id}", method = RequestMethod.DELETE) //capta la petición oculta DELETE hecha por la URL
    public void eliminar(@PathVariable Long id, @RequestHeader (value="Authorization")String token){ //captamos la id (del usuario) en la URL, y la usamos como argumento.
        
        //el Servidor verifica que el token enviado por el Cliente es correcto
        String usuarioId=jwtUtil.getKey(token); //extraemos del token el id de usuario
        if(usuarioId ==null){ //Esta linea es una comprobación POBRE. Mejor haber comprobado si el usuarioId corresponde a algún registro de la BD.
            return; //si el id extraido del token es null, finaliza este bloque de codigo
        }

        usuarioDao.eliminar(id); //eliminar es un método abstracto desarrolado en usuarioDaoImp.java     
    }      
    


    /**
     * >>llamada oculta por fetch. Método http POST (registrar un nuevo usuarios en la BD)
     * 
     * @param usuario 
     */
    @RequestMapping(value="api/usuarios", method=RequestMethod.POST)  
    public void registrarUsuario(@RequestBody Usuario usuario){ //@RequestBody: transforma el json que reciba a un objeto Usuario de una vez
        
        /*encriptar contraseña antes de registrar un nuevo usuario en la BD*/
        Argon2 argon2=Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id); //creamos objeto argon2 ==> libreria (dependencia) insertada en pom.xml:  <groupId>de.mkammerer</groupId> <artifactId>argon2-jvm</artifactId>                   
        String hash=argon2.hash(1, 1024, 1, usuario.getPassword()); //PARÁMETROS: ITERATIONS=se hace la encriptación ireversible (hasheo) una sola vez, MEMORY, PARALLELISM= numéro de hilos para hacer varios procesos al mismo tiempo que se encripta, CONTRASEÑA A ENCRIPTAR.
        usuario.setPassword(hash);  //al usuario le establecemos la contraseña ya encriptada
        
        
        usuarioDao.registrar(usuario); 
    }    
 


    
    /*_______________OTROS REQUESTMAPPING:_________________________________*/


    /**
     * llamada explicita por URL indicando id de usuario. Metodo http GET
     * 
     * @param id
     * @return 
     */
    @RequestMapping(value="api/usuario/{id}", method = RequestMethod.GET) //capta la petición NO oculta hecha por la URL, que incluye el id del usuario. api/ para separar el frontend del backend
    public Usuario getUsuarios(@PathVariable Long id){ //captamos la id (del usuario) en la URL, y la usamos como argumento.
        Usuario usuario= new Usuario();
        usuario.setId(id);
        usuario.setNombre("Fran");
        usuario.setApellido("García");
        usuario.setMail("frangarcia@hotmail.com");
        usuario.setTelefono("pfffff");
        return usuario;        
    }
    
    
    @RequestMapping(value="usuario54")
    public Usuario editar(){
        Usuario usuario= new Usuario();
        usuario.setNombre("Lucas");
        usuario.setApellido("Moy");
        usuario.setMail("lucasmoy@hotmail.com");
        usuario.setTelefono("123456");
        return usuario;        
    }     
    

    @RequestMapping(value="usuario321")
    public Usuario buscar(){
        Usuario usuario= new Usuario();
        usuario.setNombre("Lucas");
        usuario.setApellido("Moy");
        usuario.setMail("lucasmoy@hotmail.com");
        usuario.setTelefono("123456");
        return usuario;        
    }
    
}
