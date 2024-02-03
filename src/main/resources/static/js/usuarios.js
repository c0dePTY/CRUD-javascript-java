// Call the dataTables jQuery plugin
$(document).ready(function() {    /*cuando el document esta cargado...*/
  cargarUsuarios(); /*...lanzo la funcion cargarUsuarios(). Ver abajo...*/      
  $('#usuarios').DataTable(); /* ...y la tabla #usuarios lanza el metodo predefinido .DataTable() que da funcionalidades a la tabla (ordenar registros, paginar...)*/
  actualizarUsuario(); //función para actualizar el usuario que aparece como logueado en usuario.html, en su esquina superior derecha
  
});


/**
 * funcion para cargar usuarios en usuarios.html/tabla
 * 
 * @return {undefined}
 */
async function cargarUsuarios(){ /*3. función asyncrona ya que espera la respuesta del fetch*/
    
  const request = await fetch('api/usuarios', { /*1. HAGO LA REQUEST AL SERVIDOR: esperando la respuesta del fetch 'usuarios' */
    method: 'GET', /*metodo get, y se van a emplear json*/
    /*headers: {
      'Accept': 'application/json',
      'Content-Type': 'application/json'
    }*/                                                                         //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< D2
    headers: getHeaders()   //D2. ahora que el Cliente (browser - usuarios.js) tiene un token, cuando este Cliente haga una request al Servidor le pasará el token a través de una cabecera (header). Además, reemplazo los headers de cada petición (request) por la función getHeaders() que se implementa abajo.         
    
  });
  const usuarios = await request.json(); /*2. transformo la respuesta a json y la guardo en un const*/

  console.log(usuarios); /*4. imprimo en consola del Chrome el json resultante*/

  let listadoHTML = '';
  for(let usuario of usuarios) {   /*recorro la lista*/
      
    let botonEliminar='<a href="#" onclick="eliminarUsuario('+usuario.id+')" class="btn btn-danger btn-circle btn-sm"><i class="fas fa-trash"></i></a>';  //html tomádo de usuarios.html/components/buttons. onclick="mÃ©todo que quiero que se dispare al hacer click"
  
    let telefono = usuario.telefono == null ? '-' : usuario.telefono; /*variable telefono a imprimir segun condicional*/
    let usuarioN='<tr><td>'+usuario.id+'</td><td>'+usuario.nombre+' '+usuario.apellido+'</td><td>'+usuario.mail+'</td><td>'+telefono+'</td><td>'+botonEliminar+'</td></tr>';
  
    listadoHTML += usuarioN; //concatenacion de Strings
  }     
  
  document.querySelector('#usuarios tbody').outerHTML = listadoHTML; //imprimo en usuarios.html/#usuarios/tbody el String anterior 
  //tambien se pudo haber usado: document.getElementById('#usuarios tbody').outerHTML=listadoHTML; No usar=> document.getElementById('#usuarios tbody').value=listadoHTML;
  
}


/**
 * funcion para eliminar usuarios al presionar el boton de eliminar
 * 
 * @param {type} id
 * @return {undefined}
 */
async function eliminarUsuario(id){
    
  if(!confirm('¿Desea eliminar este usuario?')){ //si haces click en No...
    return; //...si la respuesta es no, se corta el flujo de toda la funcion eliminarUsuario()
  }

  const request = await fetch('api/usuarios/' +id, { /*1. esperando la respuesta del fetch 'usuarios' */
    method: 'DELETE', /*metodo get, y se van a emplear json*/
    /*headers: {
      'Accept': 'application/json',
      'Content-Type': 'application/json'                                        //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< D1   
      'Authorization': localStorage.token //D1. el Cliente (browser) toma la información del token que está guardada en su localStorage, y se la envía al Servidor. Reemplazo los headers de cada petición (request) por la función getHeaders() que se implementa abajo.  
    } */                                                                        //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< D2
    headers: getHeaders() //D2. ahora que el Cliente (browser - usuarios.js) tiene un token, cuando este Cliente haga una request al Servidor le pasará el token a través de una cabecera (header).       
        
  });
  
  location.reload();//actualizar la pagina  
}


/**
 * función para reemplazar los headers de cada petición fetch. Esta función devuelve un objeto {}
 * 
 * @return {getHeaders.usuariosAnonym$2}
 */
function getHeaders(){                                                          //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< D1
    return {
      'Accept': 'application/json',
      'Content-Type': 'application/json',
      'Authorization': localStorage.token //D1. el Cliente (browser) toma la información del token que está guardada en su localStorage, y se la envía al Servidor
    } ;
} 


/**
 * función para actualizar el nombre del usuario logueado en usuarios.html, en la esquina superior derecha de usuarios.html
 * 
 */   
function actualizarUsuario(){    
    document.querySelector('#usuarioLogueado').outerHTML = localStorage.mail;
    //también se puede usar => document.getElementById('usuarioLogueado').outerHTML = localStorage.mail;
    //no usar=> document.getElementById('usuarioLogueado').value=localStorage.mail;
}

    

    




