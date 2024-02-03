
$(document).ready(function() {    /*cuando el document esta cargado...*/
    //on ready
    
    //la funcion iniciarSesion() sera llamada dentro de login.html, al presionar el boton <a onclick="iniciarSesion()" href="#" class="btn btn-primary btn-user btn-block">Iniciar Sesion</a>

});


//funcion para que inicien sesión los usuarios ya registrados
async function iniciarSesion(){ /*3. funcion asyncrona ya que espera la respuesta del fetch*/
    
  //CARGAMOS LOS DATOS QUE SE ESCRIBIERON EN LOS TXTFIELDS  
  let datos={}; /*declaracmos el objeto datos*/
  datos.mail= document.getElementById('txtMail').value; 
  datos.password= document.getElementById('txtPassword').value; 
    
  //HACEMOS LA REQUEST (EL LLAMADO) AL SERVIDOR  
  const request = await fetch('api/login', {
    /*1. llamamos a la misma URL que con el metodo http get */
    method: 'POST', /*metodo http post*/
    headers: {
      'Accept': 'application/json',
      'Content-Type': 'application/json'
    },
    
    //ENVIAMOS LOS DATOS YA ARMADOS
    body: JSON.stringify(datos) //el cliente (browser, login.js) envía ÚNICAMENTE EL TXTMAIL Y TXTPASSWORD al servidor (AuthenticController.java). JSON.stringify: coge cualquier objeto de javaScript y lo convierte en un String de json
      
  });
  
  //const respuesta = await request.json(); /*2. transformo la respuesta a json y la guardo en un const*/
  const respuesta = await request.text();  //request recibe desde la clase AuthentiController.java un String, por eso empleo la función .text(); 
                                                                                //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< C2
  //Si la respuesta no es fail, el Cliente (browser) guarda el JWT-Token y el mail del usuario en su localStorage
  if(respuesta !== 'FAIL'){
      localStorage.token = respuesta; //el Cliente (browser) recibe el token y mail, y lo guarda en el localStorage. Verlo en F12/Application/LocalStorage/localhost8080
      localStorage.mail=datos.mail; //lo voy a emplear en usuarios.js/actulizarUsuario(). Y se reflejará en usuarios.html
        
      window.location.href= 'usuarios.html'; //me redirijo a la dirección deseada
      
  } else{ //hay un problema de autentificación ==>error 401
      alert("Las credenciales son incorectas. Verifique los campos");
  }
  
    
}









