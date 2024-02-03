/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cursojava.dao;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import com.cursojava.models.Usuario;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository //para acceder al repositorio de la BD. <Dependency>spring-boot-starter-data-jpa
@Transactional //cómo se va a armar (fragmentos de transacción) y ejecutar las consultas SQL a la BD. <Dependency>spring-tx
/**
 * Esta clase va a implementar la interfaz UsuarioDao, y su método abstracto getUsuarios()
 * 
 * @author Alejandro
 */
public class UsuarioDaoImp implements UsuarioDao{
    
    /*ATENCIÓN: los datos de la conexión con la BD y su  tabla, están en el
    archivo src/main/resources/application.properties*/

    @PersistenceContext
    private EntityManager entityManager; //entityManager hará la conexión con la BD
    
    @Override
    @Transactional
    public List<Usuario> getUsuarios() {
        String query= "FROM Usuario"; //no es una consulta a mySQL, sino a Hibernante. No se consulta la tabla usuarios de la BD, sino al objeto Usuario.java. * FROM Usario.java, Queremos todo Usuario.java, por eso NO empleamos WHERE...
        List<Usuario> resultado=entityManager.createQuery(query).getResultList(); //entityManager ejecutará la query
        return resultado;
    }

    @Override
    public void eliminar(Long id) {
        Usuario usuario=entityManager.find(Usuario.class, id); //buscar en la BD el objeto Usuario con el id señalado, y guardarlo en un objeto Usuario
        entityManager.remove(usuario); //borrar en la BD dicho objeto Usuario 
    }

    @Override
    public void registrar(Usuario usuario) {
        entityManager.merge(usuario); //.merge crea un nuevo registro o actualiza, no autocompleta el id de Ususarios.java
    }
    
    @Override    
    public Usuario verificarCredenciales(Usuario usuario) { //este usuario contiene ÚNICAMENTE el txtMail y txtPassword con el que el usuario se logueó. Siendo usuario el siguiente objeto: {null, null, null, null, txtMail, txtPassword}
        //String query= "FROM Usuario WHERE mail = :mail AND password = :password "; //no es una consulta a mySQL, sino a Hibernante. No se consulta la tabla usuarios de la BD, sino al objeto Usuario.java. :mail evita la inyección SQL, que haría posible que un hacker iniciar sesión sin usuario ni contraseña        
        String query= "FROM Usuario WHERE mail = :mail";
        List<Usuario> lista=entityManager.createQuery(query)    //entityManager ejecutará la query. Busca en la BD al usuario que contiene el txtMail con el que el cliente se logueó. (***)Lista contiene al Usuario deseado con TODOS SUS ATRIBUTOS NO NULL, ya que ha sido obtenido de la BD. Mientras que el usuario de la linea 51 solo contiene los atributos metidos en el formulario: mail y password
            .setParameter("mail", usuario.getMail())
            //.setParameter("password", usuario.getPassword())
            .getResultList();

            if(lista.isEmpty()){ //if, si la lista está vacía...salgo de este bloque
                return null;
            }            
            String passwordHashed=lista.get(0).getPassword(); //de la LISTA obtenida de la BD, tomar su primer elemento (el realidad solo habrá un Usuario), y de éste, obtiene su contraseña ya encriptada

            Argon2 argon2=Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id); //creo el objeto argon2
            if(argon2.verify(passwordHashed, usuario.getPassword())){ //método .verify() verifica si la contraseña encriptada de la LISTA tomada de la BD es la misma que la del usuario.getPassword() ENCRIPTADA EN ESTA LINEA.              
                return lista.get(0);   //retorna al objeto Usuario coincidente con todos sus atributos NOT NULL.
            }   
         
            return null;    //ELSE si las credendiales no son correctas
    }
    
    
      
}
