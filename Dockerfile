FROM tomcat:9.0-jdk11

# Eliminar las apps por defecto
RUN rm -rf /usr/local/tomcat/webapps/*

# Copiar tu WAR al contenedor como ROOT
COPY target/ConsultaAlumnos_MServ-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/ROOT.war

# Pasar las variables de entorno al contenedor
ENV MYSQL_DATABASE=railway \
    MYSQLHOST=mysql.railway.internal \
    MYSQLPORT=3306 \
    MYSQLUSER=root \
    MYSQLPASSWORD=JSqwrsvehPwsdADYTxIHpRMUixzgzPlu
