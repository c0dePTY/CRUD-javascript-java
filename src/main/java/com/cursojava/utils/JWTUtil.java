package com.cursojava.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;

/**
AUTENTICACIÓN DE UN USUARIO AL INICIAR SESION, PASOS:
JWTUtil.java (An)=> copiamos esta clase directamente de la descripción del video. Esta clase contiene atributos y funciones que vamos a emplear dentro de otras clases (AuthentiController.java) siguiendo el paradigma de la inyección de dependencias.
            Estas funciones se emplearán para crear el token que el Servidor (AuthentiController.java) le pasará al Cliente (login.js-browser) para que éste una vez logueado quede inicializado su sesión pudiendo hacer tantas peticiones quiera sin tener que volverese a loguear en cada fetch.
            Cuando el Servidor crea el token, el Servidor le inserta un texto encriptado con cierta información del Cliente
login.js=> [!] el Cliente (browser-login.js) toma el mail y password que el usuario ha insertado en el formulario de login.html, y se lo envía al Servidor. Si el Servidor (AuthentiController.java) autentifica al usuario, creará un Token y se lo enviará al Cliente. C2=> El Cliente guardara el Token en el LocalStorage del browser para reutilizarlo cada vez que haga una nueva fetch y no haya cerrado sesión.

Bn=> referido a la inyección de dependencia de la clase JWTUtil.java dentro del Servidor AuthentiController.java y el Servidor UsuarioController.java

AuthentiController.java (C1)=> el Servidor (a través de JWYUtil.java) autentifica al usuario. Si el usuario resulta autenticado positivamente, el Servidor genera el token y se lo envía al Cliente (login.js)
login.js (C2)=> Una vez el Servidor envía el Token, login.js guardara el Token en el LocalStorage del browser para reutilizarlo cada vez que haga una nueva fetch y no haya cerrado sesión.

usuarios.js (D1, D2)=> reutilizar el Token guardado en localStorage del browser cuando lancemos el resto de fetchs de "usuarios.js" (cargarUsuarios(), eliminarUsuario(id))
                    => para ello, en los headers de los fetch cargarUsuaios() y eliminarusuario(id), "usuarios.js" envía el token guardado a su Servidor UsuarioController.java



*/


/**
 *  CLASE COPIADA DE LA DESCRIPCIÓN DEL VIDEO DE YOUTUBE: https://www.youtube.com/watch?v=7vHzVN0EiQc&t=12762s ==>
 *  ==> SE HA DE AÑADIR LA DEPENDENCIA <groupId>io.jsonwebtoken</groupId> <artifactId>jjwt</artifactId> AL POM.XML
 *
 * @author Mahesh
 */
@Component //B2. @Component=anotación que vale para poder compartir la clase JWTUtil en todos los lugares( por ejemplo dentro de AuthentiController.java, tras la anotación @Autowired). Y permite utilizar las anotacion de @Value de abajo, que a la vez vale para poder cargar información de las properties que están escritas en "src/main/resources/application.properties"
public class JWTUtil {
                                                                                 
    //atributos de la clase JWTUtil:                                            //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< A1=B3
    @Value("${security.jwt.secret}") //A1=B3. @Value=anotación que sirve para poder cargar la información de las properties. @Value implica que, al atributo String key se le va a cargar el property "${security.jwt.secret}", que yo he de definir en el archivo projects/src/main/resources/APPLICATION.PROPERTIES
    private String key;

    @Value("${security.jwt.issuer}")
    private String issuer;

    @Value("${security.jwt.ttlMillis}")
    private long ttlMillis;

    private final Logger log = LoggerFactory
            .getLogger(JWTUtil.class);
                                                                                //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< A2
    /**
     * Create a new token. A2.Método para crear el JWT Ó TEXTO DE INFORMACIÓN-TOKEN (sesión iniciada) que el servidor le pasará al cliente (browser) con cierta información (nombre de usuario, telf, mail, id, roles de usuario, permisos...la info que yo quiera incluir del cliente) 
     *
     * @param id
     * @param subject
     * @return
     */
    public String create(String id, String subject) { 

        // The JWT signature algorithm used to sign the token
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        //  sign JWT with our ApiKey secret
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(key);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        //  set the JWT Claims
        JwtBuilder builder = Jwts.builder().setId(id).setIssuedAt(now).setSubject(subject).setIssuer(issuer)
                .signWith(signatureAlgorithm, signingKey);

        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }

        // Builds the JWT and serializes it to a compact, URL-safe string
        return builder.compact();
    }
                                                                                //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< A3
    /**
     * Method to validate and read the JWT. A3.Método para obtener información (nombre de usuario, telf, mail, id, roles de usuario, permisos...la info que yo quiera incluir del cliente) que yo A TRAVÉS DEL SERVIDOR le haya agregado al JWT-TOKEN. Concretamente se trata del atributo del objeto Usuario que no es su id, en este proyecto es el usuario.mail
     *
     * @param jwt
     * @return
     */
    public String getValue(String jwt) {
        // This line will throw an exception if it is not a signed JWS (as
        // expected)
        Claims claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(key))
                .parseClaimsJws(jwt).getBody();

        return claims.getSubject();
    }
                                                                                //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< A4
    /**
     * Method to validate and read the JWT. A4.Método para obtener información que yo le haya agregado al JWT-TOKEN, concretamente el id del objeto Usuario.
     *
     * @param jwt
     * @return
     */
    public String getKey(String jwt) {
        // This line will throw an exception if it is not a signed JWS (as
        // expected)
        Claims claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(key))
                .parseClaimsJws(jwt).getBody();

        return claims.getId();
    }
}