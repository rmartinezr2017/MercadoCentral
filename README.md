# MercadoCentral
Practica Desarrollo de Aplicaciones Distribuidas URJC

[Rodrigo Martínez Ruiz:](https://github.com/rmartinezr2017)

## Lista de contenidos.
- [Fase 1](#fase-1)
  - [Descripción](#descripción)
  - [Entidades](#entidades)
  - [Funcionalidades](#funcionalidades)
  - [Funcionalidades del servicio interno](#funcionalidades-del-servicio-interno)
- [Fase 3](#fase-3)

## Fase 1.

### Descripción. 
MercadoCentral es una apliacion que permite traer la experiencia del mercado tradicional a tu casa a través de la ultima tecnología.

### Entidades.

- Empleado: Representa a los empleados del sistema

  - Administrador: Representa al responsable del sistema, un empleado especial. Es capaz de supervisar el trabajo del resto de empleados y modificarlo

- Usuario: Representa a las personas interesadas del sistema.

- Producto: Representa las salas y puestos del sistema.

- Codigo de Barras: Representa los códigos de barras de los que dispone el sistema.

### Funcionalidades.

- Funcionalidades públicas: Un usuario sin registrar, puede acceder a la pagina y podrá ver todos los productos, sus precios y su disponibilidad junto con otra informacion util para el mismo.

- Funcionalidades privadas: El usuario ya registrado, puede comprar los productos si hay stock. Los empleados pueden asignar nuevos códigos de barras a los productos nuevos que se ofertan.

### Funcionalidades del servicio interno.

- Control de catálogo de los productos.
- Control de stock de los productos.
- Control de la asignación de los códigos de barras a los productos.

## Fase 3.

### Descripción. 
Tras habr desarrollado una aplicación completamente funcional, incorporamos las funcionalidades de seguridad por parte de Spring Srecurity que nos permite autentificar a los usuarios, establecer que páginas son privadas y cuales no y quien tiene acceso a que páginas privadas.
También se ha externalizado el servicio externo del cual hablaremos mas adelante y se ha creado una guía para poder deplegarlo en una maquina virtual.

### Navegacion.
Se han modificado los archivos html hara hacerlos modulares y evitar duplicar codigo. por ejemplo la navegacion de la app, se encuentra en un fichero concreto el cual se encuentra tanto la barra de navegacion de los clientes como la de los trabajadores.
Basicamente existen los siguientes pantallas de navegacion:
- Pública:
  - Inicial: Es la página principal a ella todos tienen acceso, y se puede loguearse, ver mis datos personales y modificarlos, ver el carrito de la compra y se muestran las categorías. Ver mis datos personales me lleva directamente a loguearme primero. Si eres un empleado, te permitirá añadir productos, si eres un administrador además te permitira gestionar los codigos de barras y añadir/eliminar empleados.
  - Login: en ella puedo introducir mi usuario o crearme uno nuevo. Si soy empleado solo un admi puede crearme un usuario nuevo.
  - Carrito de la compra: En ella puede consultar los productos que he metido en ella, su cantidad y otra informacion. Me permite pulsar sobre un producto para poder ver la información detallada, cambiar la cantidad deseada y eliminar el producto del carrito. Si pulso confirmar compra, tendré que estar logueado como cliente para poder seguir. Los empleados no tienen esta opcion
  - Productos de una categoría: Es una extension de la inicial donde ademas de ver la lista de categorías, vemos los productos que hay en esa categoria. A los trbajadores se les añade la opcion de eliminar un producto.
  - Detalles de los productos: Si pulsas sobre un producto, te llevará a está pagina donde verás los detalles de los productos y te permitirá seleccionar cuantos quieres meter al carro o si eres un empleado, podrás modificar sus datos y asignarle un codigo de barras.

- Cliente:
  -  Confirmar compra: Me lleva a la confirmacion del metodo de pago.
  -  Pago confirmado: Una ve confirmo el metodo de pago me lleva a esta pagina que me certifica que se ha realizado la compra y me permite volver a la pagina principal.

- Trabajador:
  - Añadir producto: Permite la introduccion de nuevos productos introduciendo sus datos.
  - Modificar producto: Permite modificar los datos de los productos.

- Administrador:
  - Administrar códigos de barras: Permite tanto añadir como eliminar los codigos de barras. Si pulsas sobre un codigo en concreto, si tiene asignado un producto te enseñará dos detalles del mismo.
  - Datos de empleado: si siendo administrador, estando en la pantalla principal pulsas sobre un empleado concreto, te lleva a esta pagina que te permite modificar los atributos de ese empleado.

![image](https://github.com/rmartinezr2017/MercadoCentral/assets/108556600/14cf3433-15e3-490e-bcc1-15cf72883ca9)

### UML Clases 
TODO

### Servicio interno.

[GitHub Servicio Interno](https://github.com/rmartinezr2017/Mercado-Central-Servicio-Interno.git)

El servicion interno se encarga de gestionar los productos, el inventario y los codigos de barras. Basicamente todas las funciones atribuidas a la clase ProductService.
Se ha implementado mediante una API REST. Como interfaz básicamente se ha cogido los métodos de la clase y se ha implementado un controlador rest para accion sobre los datos.
Por lo tanto cada método de ProductService tiene que modelar una peticion REST para realizar las acciones oportunas sobre los datos.

### Instrucciones despliegue en M.V.

java -jar internal_service_dad-0.0.1-SNAPSHOT.jar --server.port=8080 --spring.datasource.url=jdbc:mysql://localhost/posts --spring.datasource.username=root --spring.datasource.password=password --spring.jpa.hibernate.ddl-auto=create-drop

java -jar demo-0.0.2-SNAPSHOT.jar --server.port=8443 --spring.datasource.url=jdbc:mysql://localhost/posts --spring.datasource.username=root --spring.datasource.password=password --rest.url=http://localhost:8083


