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

    ``` git clone https://github.com/AndresOnate/AREP-TALLER7.git ```

3. Navegue al directorio del proyecto

   ``` cd AREP-TALLER7 ```

4. Ejecute el siguiente comando para  iniciar los contenedores definidos en el archivo `docker-compose.yml`:
   
      ``` docker-compose up -d ``` 
   
   Debería ver algo así en consola:

   ![image](https://github.com/AndresOnate/AREP-TALLER7/assets/63562181/2d8b9a4e-a12f-445f-8343-985995838c55)

   Verifique en la aplicación de escritorio de Docker que los contenedores se esté ejecutando

   ![image](https://github.com/AndresOnate/AREP-TALLER7/assets/63562181/ce7a3a96-f326-4943-92aa-bbe496f2f7a3)


El comando `docker-compose up -d` inicia los servicios definidos en el archivo `docker-compose.yml`.
   
## Probando la Aplicación.  

Ingrese a la siguiente URL para ingresar a el cliente: `https://localhost:46000/index.html` .

![image](https://github.com/AndresOnate/AREP-TALLER7/assets/63562181/e4fcb7c4-54b1-4935-bd36-f642a7b99960)

 Es posible que le muestre un mensaje de advertencia por el certificado que se está usando, de clic en `Avanzado` y de clic en continuar:

![image](https://github.com/AndresOnate/AREP-TALLER7/assets/63562181/1e12652d-aeb0-44d4-b1c2-dd16e0b97a6a)

Ingresamos las credenciales; si estas son correctas, nos mostrará un código secreto dinámico, de lo contrario, un mensaje de error:

![image](https://github.com/AndresOnate/AREP-TALLER7/assets/63562181/d57c860f-ccdc-40e6-80d9-e76571981ac2)

Credenciales incorrectas:

![image](https://github.com/AndresOnate/AREP-TALLER7/assets/63562181/970ca210-a238-4ae9-a65b-87d7eddaaf13)

Para asegurar el requerimiento de autorización, el servicio `/hello`, que es el encargado de retornar los códigos secretos,  solo puede ser accedido por el servidor Login mediante usuarios funcionales.

![image](https://github.com/AndresOnate/AREP-TALLER7/assets/63562181/7689495b-c2f7-4354-aa14-0c3b9cf71aca)

Cómo podemos ver, el servicio no reconoce la solicitud .

## Generando las imágenes para el despliegue.

Para generar las imágenes de Docker se crearon los archivos `Dockerfile.app` y `Dockerfile.helloservice`, ubicados en el directorio `Dockerfiles`. Cada archivo define una imagen basada en la imagen oficial de OpenJDK 17. Estos archivos configuran el directorio de trabajo, establecen variables de entorno para los puertos y las contraseñas de las llaves, copian los archivos de clases y dependencias de la aplicación al contenedor, y especifican el comando para ejecutar la aplicación Java cuando el contenedor esté en funcionamiento. 

### Servidor Login:

Ubiquese sobre el directorio raiz del repositorio, ejecute el siguiente comando para construir la imagen:

``` docker build -f .\Dockerfiles\Dockerfile.app -t applogin . ```

![image](https://github.com/AndresOnate/AREP-TALLER7/assets/63562181/ffd62610-3e94-4ec9-a5f5-f47bdf8f393f)

Puede agregar una referencia a su imagen con el nombre del repositorio a donde desea subirla, por ejemplo:

``` docker tag applogin aonatecamilo/arep_taller7_applogin ``` 

Suba la imagen al repositorio en DockerHub

``` docker push aonatecamilo/arep_taller7_applogin ``` 

### Hello Server

Ubiquese sobre el directorio raiz del repositorio, ejecute el siguiente comando para construir la imagen:

``` docker build -f .\Dockerfiles\Dockerfile.helloservice -t helloservice . ```

![image](https://github.com/AndresOnate/AREP-TALLER7/assets/63562181/db018888-8bed-4a83-9eda-8519ceff5fb0)

Puede agregar una referencia a su imagen con el nombre del repositorio a donde desea subirla, por ejemplo:

``` docker tag helloservice aonatecamilo/arep_taller7_helloservice ``` 

Empuje la imagen al repositorio en DockerHub

``` docker push aonatecamilo/arep_taller7_helloservice ``` 


## Despliegue en AWS

Se creó una instancia de EC2 en AWS con las siguientes características.

![image](https://github.com/AndresOnate/AREP-TALLER6/assets/63562181/8edad574-fb65-4bc9-bca5-751189a6f1b2)

Se ejecutan los siguientes comandos para crear imagenes de los contenedores:

```docker run -d -p 46000:46000 --name loginserver aonatecamilo/arep_taller7_apploginvm ```

``` docker run -d -p 35001:35000 --name helloserver aonatecamilo/arep_taller7_helloservicevm ```

En el siguiente video se muestran los despliegues funcionando en la máquina virtual:  https://youtu.be/HSLhpJOz6Nc

Imágenes del despliegue:

Servidor de la aplicación:

![image](https://github.com/AndresOnate/AREP-TALLER7/assets/63562181/fd8b2881-b28e-4363-9922-83af7804f5df)

Ejecución:

![image](https://github.com/AndresOnate/AREP-TALLER7/assets/63562181/f98fb772-ab83-4d6b-863a-fa0405e19ea7)

![image](https://github.com/AndresOnate/AREP-TALLER7/assets/63562181/1b5e3c23-f93a-4e22-b08f-2b203ab393c1)

Instancias de los contenedores:

![image](https://github.com/AndresOnate/AREP-TALLER7/assets/63562181/aff6d12d-625f-4a0a-b9c2-257e6318311a)


## Construido Con. 

- Maven - Administrador de dependencias

## Autores 

- Andrés Camilo Oñate Quimbayo

## Versión
1.0.0
