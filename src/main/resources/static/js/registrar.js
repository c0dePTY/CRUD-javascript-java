
$(document).ready(function() {    /*cuando el document está cargado...*/
    //on ready
    
    //la función registrarUsuario() será llamada dentro de registrar.html, al presionar el botón <a onclick="registrarUsuario()" href="#" class="btn btn-primary btn-user btn-block">Registrarse</a>
});


//funcion para registrar usuarios
async function registrarUsuario(){ /*3. funcion asyncrona ya que espera la respuesta del fetch*/
    
  //CARGAMOS LOS DATOS QUE SE ESCRIBIERON EN LOS TXTFIELDS  
  let datos={};
  datos.nombre= document.getElementById('txtNombre').value; 
  datos.apellido= document.getElementById('txtApellido').value; 
  datos.mail= document.getElementById('txtMail').value; 
  datos.password= document.getElementById('txtPassword').value; 
  
  datos.repetirPassword= document.getElementById('txtRepetirPassword').value; 
  
  //COMPROBAMOS QUE LAS CONTRASENAS CONCIDEN
  if(datos.password !== datos.repetirPassword){
      alert('�Las contraseñas no coinciden!');
      return; //si no coinciden los passwords...se corta la función y no se hace el request de abajo
  }
    
  //HACEMOS LA REQUEST AL SERVIDOR  
  const request = await fetch('api/usuarios', { /*1. llamamos a la misma URL que con el método http get */
    method: 'POST', /*metodo http post*/
    headers: {
      'Accept': 'application/json',
      'Content-Type': 'application/json'
    },
    
    //ENVIAMOS LOS DATOS YA ARMADOS
    body: JSON.stringify(datos) //JSON.stringify: coge cualquier objeto de javaScript y lo convierte en un String de json. Este String es enviado al UsuarioController.java
      
  });
  alert("La cuenta fue creada con exito"); //mensaje pop-up
  window.location.href= 'login.html';   //redirecciono la web a login.html

  const usuarios = await request.json(); /*2. transformo la respuesta a json y la guardo en un const. [!] 11/08/2023: CREO  QUE ESTA LINEA NO SE EMPLEA*/

  console.log(usuarios); /*4. imprimo en consola del Chrome el json resultante. [!] 11/08/2023: CREO  QUE ESTA LINEA NO SE EMPLEA*/

}









