# tp-agronomia-oo2
Este repositorio es para el TP final de la materia objetos 2 de UNLP, relacionado al taller de agronomia familiar

# PASOS PARA EJECUTAR PROYECTO
1. Levantar base de datos. Esto puede hacerse localmente o con docker.
    1. Localmente: Tener mysql instalado, y ejecutando (en el puerto 3306, que es el que viene por defecto).
    2. Con docker: Tener docker instalado y corriendo, y correr el siguiente comando en una terminal `docker run --name mysql -p 3306:3306 -e MYSQL_DATABASE=agronomia -e MYSQL_USER=agronomia -e MYSQL_PASSWORD=agronomia -e MYSQL_ROOT_PASSWORD=agronomia -d mysql`

    Esto va a dejar la base de datos corriendo en localhost:3306
2. En una terminal y parado en la carpeta del proyecto (dentro de la carpeta agronomia), ejecutar este comando: PARA WINDOWS `mvn.cmd spring-boot:run` PARA LINUX `./mvnw spring-boot:run`