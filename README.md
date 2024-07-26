# 🚀 Proyecto de Spring Boot

Este proyecto es una aplicación CRUD de Spring Boot que maneja la lógica de productos con sus atributos y se conecta a una base de datos MySQL.
## 🛠️ Prerrequisitos

Para poder levantar el proyecto, se debe utilizar:

- **Java JDK 17**
- **Spring Boot 3.3.2**
- **MySQL**
- **Postman**

## ⚙️ Instrucciones para levantar el proyecto

### 🛠️ Configuración del proyecto

1. Clona el repositorio:
    ```bash
    git clone https://github.com/mdiazpj/pj-app.git
    ```

2. Navega al directorio del proyecto:
    ```bash
    cd pj-app
    ```

3. Configura las propiedades de la base de datos en el archivo `src/main/resources/application.properties`:
    ```properties
    spring.datasource.url=jdbc:mysql://localhost:3306/{nombre_base_de_datos}
    spring.datasource.username={nombre_de_usuario}
    spring.datasource.password={contraseña_de_usuario}
    spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

    spring.jpa.hibernate.ddl-auto=update
    spring.jpa.show-sql=true
    ```

   **Nota:** Reemplaza `{nombre_base_de_datos}`, `{nombre_de_usuario}` y `{contraseña_de_usuario}` con los valores correspondientes a tu configuración de MySQL.

4. Compila y ejecuta el proyecto:
    ```bash
    ./mvnw spring-boot:run
    ```

El backend estará disponible en `http://localhost:8080`.

## 📬 Colección de Postman

Para facilitar las pruebas de los endpoints de la API, hemos creado una colección de Postman. Puedes descargarla desde el siguiente enlace:

[📥 Descargar Colección de Postman]([ruta/al/archivo/postman_collection.json](https://pjespana-my.sharepoint.com/:u:/g/personal/manolo_diaz_pjchile_com/Ed0fef-fSfZHqtJS7b8S8ekByN1n8eVR_v7CqPe4Vkheyg?e=EHI2Ee))

## 📧 Contacto

Para cualquier consulta o problema, por favor contacta a [manolo.diaz@pjchile.com](mailto:manolo.diaz@pjchile.com).
