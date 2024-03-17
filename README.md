# APLICACIÓN DISTRIBUIDA SEGURA EN TODOS SUS FRENTES

# TALLER DE TRABAJO INDIVIDUAL EN PATRONES ARQUITECTURALES

En este taller construimos una aplicación web segura con la arquitectura  presentada en  la siguiente imagen, que garantiza la autenticación, autorización e integridad de usuarios y servicios. 


## Requerimientos de Seguridad
- Acceso seguro desde el navegador: La aplicación web garantiza la autenticación, autorización e integridad de los usuarios que acceden a través del navegador.
- Comunicación segura entre computadoras: La comunicación entre al menos dos computadoras se realiza de manera segura, garantizando autenticación, autorización e integridad entre los servicios. Ningún servicio puede ser invocado por un cliente no autorizado.
- Escalabilidad: La arquitectura de seguridad debe ser escalable para incorporar nuevos servicios de manera eficiente y sin comprometer la seguridad existente.


## Diseño de la aplicación

La aplicación está diseñada para cumplir con los requisitos especificados en el enunciado del taller y proporcionar una experiencia de usuario fluida y satisfactoria. A continuación, se describen los principales componentes y características de la aplicación:

- La clase `LoginServer` es responsable de gestionar las solicitudes de inicio de sesión de usuarios en un sistema web, utilizando el framework Spark para configurar las rutas y la seguridad de la aplicación. En su método principal, configura las rutas para manejar solicitudes HTTP GET, estableciendo también la ubicación de los archivos estáticos en el directorio /public. Proporciona un punto de entrada para autenticar usuarios y manejar el proceso de inicio de sesión de manera segura. La clase define una ruta GET para `/loginservice`, donde intenta autenticar las credenciales proporcionadas por el usuario utilizando el servicio userService.login(). Si la autenticación es exitosa, se invoca un servicio seguro SecureURLReader.invokeService("hello"). En caso de que la autenticación falle, se devuelve un estado de error `UNAUTHORIZED_401` al cliente. El método getPort() proporciona el puerto en el que el servidor debe ejecutarse, ya sea obteniendo el valor de la variable de entorno PORT o utilizando un puerto predeterminado.
- La clase `SecureURLReader` proporciona funcionalidades para realizar solicitudes seguras a un servicio web a través de HTTPS. El método invokeService(String service) establece una conexión segura con el servicio web especificado. Primero, carga un almacén de confianza con los certificados necesarios y configura un contexto SSL. Luego, invoca readURL() para realizar la solicitud al servicio seguro y devuelve la respuesta como una cadena de texto. El método readURL(String sitetoread) realiza la solicitud HTTP GET al servicio web seguro utilizando la URL proporcionada. Después de establecer la conexión, obtiene los campos del encabezado de la respuesta y lee el contenido del mensaje del servidor, devolviéndolo como una cadena de texto.
- La clase `HelloServer` define un servidor web Java utilizando el framework Spark. Define una ruta `/hello` que maneja solicitudes GET y valida las credenciales de servicio proporcionadas, solo las solicitudes del servidor principal tendran una respuesta. Si las credenciales son correctas, genera un código secreto único y lo devuelve como respuesta; de lo contrario, responde con un mensaje de error y un código de estado HTTP 401. A través del mapa serviceUsers, la clase gestiona las cuentas de servicio y contraseñas asociadas para la autenticación. Además, el método getPort() determina el puerto de escucha del servidor, mientras que getPassword() obtiene la contraseña del servicio desde la variable de entorno SERVICE_PASSWD.

## Arquitectura


## Guía de Inicio

Las siguientes instrucciones le permitirán descargar una copia y ejecutar la aplicación en su máquina local.

### Prerrequisitos

- Java versión 8 OpenJDK
- Docker
- Maven
- Git

## Instalación 

1. Abra la aplicación de escritorio de Docker.

   ![image](https://github.com/AndresOnate/AREP_TALLER5/assets/63562181/f1051197-c00b-4603-9dfd-d427a26d0eab)

2. Ubíquese sobre el directorio donde desea realizar la descarga y ejecute el siguiente comando:

    ``` git clone https://github.com/AndresOnate/AREP-TALLER6.git ```

3. Navegue al directorio del proyecto

   ``` cd AREP-TALLER6 ```

4. Ejecute el siguiente comando para  iniciar los contenedores definidos en el archivo `docker-compose.yml`:
   
      ``` docker-compose up -d ``` 
   
   Debería ver algo así en consola:

   ![image](https://github.com/AndresOnate/AREP-TALLER6/assets/63562181/8914eb81-f40d-456b-8c8f-25d76e2512fe)

   Verifique en la aplicación de escritorio de Docker que los contenedores se esté ejecutando

   ![image](https://github.com/AndresOnate/AREP-TALLER6/assets/63562181/e784076f-d88d-4124-9536-e785c307be1b)

   
El comando `docker-compose up -d` inicia los servicios definidos en el archivo `docker-compose.yml`.
   
## Probando la Aplicación.  

Ingrese a la siguiente URL para ingresar a el cliente: `http://localhost:38000/index.html`.

![image](https://github.com/AndresOnate/AREP-TALLER6/assets/63562181/d6bb1df5-fe2d-4adb-9ea5-69260f5a676f)

Ingrese el valor del mensaje y de clic en el botón `Send`:

![image](https://github.com/AndresOnate/AREP-TALLER6/assets/63562181/ed754812-8647-4cea-81de-0a5954a7b5f5)


La aplicación mostrará las 10 ultimas cadenas almacenadas en la base de datos y la fecha en que fueron almacenadas.

![image](https://github.com/AndresOnate/AREP-TALLER6/assets/63562181/39eb7e7d-be76-4262-ba28-7ba99a79e99b)


## Generando las imágenes para el despliegue.

Para generar las imágenes de Docker se crearon los archivos `Dockerfile.app` y `Dockerfile.logservice`, ubicados en el directorio `Dockerfiles`. Cada archivo define una imagen basada en la imagen oficial de OpenJDK 17. Estos archivos configuran el directorio de trabajo, establecen variables de entorno para los puertos, copian los archivos de clases y dependencias de la aplicación al contenedor, y especifican el comando para ejecutar la aplicación Java cuando el contenedor esté en funcionamiento. `Dockerfile.app` se encarga de crear la imagen del servidor Fachada, mientras que `Dockerfile.logservice` crea la imagen que será instanciada tres veces según la arquitectura definida.

### Servidor Fachada:

Ubiquese sobre el directorio raiz del repositorio, ejecute el siguiente comando para construir la imagen:

``` docker build -f .\Dockerfiles\Dockerfile.app -t approundrobin . ```

![image](https://github.com/AndresOnate/AREP-TALLER6/assets/63562181/5cdfb3ac-80cf-43e2-ae86-766488189964)

Puede agregar una referencia a su imagen con el nombre del repositorio a donde desea subirla, por ejemplo:

``` docker tag approundrobin aonatecamilo/arep_taller6_approundrobin  ``` 

![image](https://github.com/AndresOnate/AREP-TALLER6/assets/63562181/f103e3fa-6f58-48c0-a595-4a21e780bcdf)

Empuje la imagen al repositorio en DockerHub

``` docker push aonatecamilo/arep_taller6_approundrobin ``` 

 ![image](https://github.com/AndresOnate/AREP-TALLER6/assets/63562181/3db2ea40-f0dd-4d0e-8bf2-cec10d3cfaea)

### LogService

Ubiquese sobre el directorio raiz del repositorio, ejecute el siguiente comando para construir la imagen:

``` docker build -f .\Dockerfiles\Dockerfile.logservice -t logservice . ```

![image](https://github.com/AndresOnate/AREP-TALLER6/assets/63562181/27b3ba92-4d9c-4d9f-a028-27395129fcaf)

Puede agregar una referencia a su imagen con el nombre del repositorio a donde desea subirla, por ejemplo:

``` docker tag logservice aonatecamilo/arep_taller6_logservice ``` 

![image](https://github.com/AndresOnate/AREP-TALLER6/assets/63562181/11f0ab32-9451-45e8-a12f-7bf13c15a3d8)

Empuje la imagen al repositorio en DockerHub

``` docker push aonatecamilo/arep_taller6_logservice ``` 

![image](https://github.com/AndresOnate/AREP-TALLER6/assets/63562181/cd6a2615-c262-4a06-a0d5-1248021b9a1e)


## Despliegue en AWS

Se creó una instancia de EC2 en AWS con las siguientes características.

![image](https://github.com/AndresOnate/AREP-TALLER6/assets/63562181/8edad574-fb65-4bc9-bca5-751189a6f1b2)

Se instala git con el siguiente comando: 

``` sudo yum install git ``` 

Se instala los componentes necesarios para ejecutar `docker-compose` con los siguientes comandos: 

``` sudo curl -L https://github.com/docker/compose/releases/latest/download/docker-compose-$(uname -s)-$(uname -m) -o /usr/local/bin/docker-compose ```

``` sudo chmod +x /usr/local/bin/docker-compose```

``` docker-compose version ```

![image](https://github.com/AndresOnate/AREP-TALLER6/assets/63562181/0a56a98b-fbb8-49bf-8ca3-f6d1c5d9194f)

En el siguiente video se muestran los despliegues funcionando en la máquina virtual:  https://youtu.be/l76ZwRivvy4?si=XUhw48hv1A8jfqxr

Imágenes del despliegue:

Servidor de la aplicación:

![image](https://github.com/AndresOnate/AREP-TALLER6/assets/63562181/641b2ec2-3a5d-4e8a-bd7f-6da917eb1a98)

Instancias de los contenedores:

![image](https://github.com/AndresOnate/AREP-TALLER6/assets/63562181/d6e2737f-ca9e-4524-ba0c-6c3db316e207)


## Construido Con. 

- Maven - Administrador de dependencias

## Autores 

- Andrés Camilo Oñate Quimbayo

## Versión
1.0.0
