# bci-test
Examen postulación BCI

API mantenedora de usuarios, la cual es capaz de registrar nuevos usuarios junto con sus teléfonos. Debe existir un usuario administrador logueado para poder registrar usuarios

Los diagramas están en la carpeta "diagrams"

Endpoints:

POST /login - Se encarga de verificar el correo y contraseña existentes en la bd, y provee un token para utilizar la API. 
         
         El usuario y password por defecto es:
         
         User: ferna650@hotmail.com
         Password: Bciasdf12
         
         Ejemplo:
         
         {
             "email" : "ferna650@hotmail.com",
             "password" : "Bciasdf12"
         }

POST /user  - Registra un usuario en la bd, siempre y cuando se respeten los formatos de los valores, que no haya sido ingresado anteriormente, y se haya ingresado con el token correspondiente

         El token se agrega como header, con la llave "token"
         
         Ejemplo:
         
         {
             "name": "daniela",
             "email": "dani_1988@hotmail.com",
             "password": "Asdfhj64",
             "phones": [
                           {
                               "number": "95682701",
                               "cityCode": "SCL",
                               "countryCode": "CL"
                           },
                           {
                               "number": "100436726",
                               "cityCode": "MIA",
                               "countryCode": "USA"
                           }
                  ]
         }
         
POST /getAllUsers - Retorna todos los usuarios registrados. Requiere de un token.

         El token se agrega como header, con la llave "token". No requiere de un body
         

Para correr la API en la máquina virtual de JAVA, solo se necesita clonar el repositorio y ejecutar este comando en la raiz del proyecto:

         gradle bootrun
