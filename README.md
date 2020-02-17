# bci-test
Examen postulación BCI

API mantenedora de usuarios, la cual es capaz de registrar nuevos usuarios junto con sus teléfonos. Debe existir un usuario administrador logueado para poder registrar usuarios

Endpoints:

/login - Se encarga de verificar el correo y contraseña existentes en la bd, y provee un token para utilizar la API

/user  - Registra un usuario en la bd, siempre y cuando se respeten los formatos de los valores, que no haya sido ingresado anteriormente, 
         y se haya ingresado con el token correspondiente
         
/getAllUsers - Retorna todos los usuarios registrados. Requiere de un token
