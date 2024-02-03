brief enumeration of the main files and their directories:

\src\main\java\com\cursojava\controllers: there you can find the Controllers 
used in the backend, with all the functions related with the authentication
of the users, and others functions used in the user management page such as:
population of the table, add and delete records in the Data Base (MySQL).

\src\main\java\com\cursojava\dao: Data Access Object DAO to isolate the 
application (business layer) from the details of the underlying data storage.

\src\main\java\com\cursojava\models\Usuario.java: where the class Usuario.java is.

\src\main\java\com\cursojava\utils\JWTUtil.java: where de class JWTUtil.java is. These 
gives us all the methods used in the users authentication. 

\src\main\resources\application.properties: application.properties is the file 
where we define:
*The localhost port where the service will be deployed 
*The configuration propreties of our Database Management System (MySQL), and
 the connection of our backend with it.
*the values of some attributes used in JWTUtil.java for the users authentication.

\src\main\resources\static: In this directory we store all the html files, and
their pertinent javascript and CSS files for each web page (login.html,
 registar.html, usuarios.html)

\pom.xml: this is the .XML file where all the dependencies used in this project 
are. 