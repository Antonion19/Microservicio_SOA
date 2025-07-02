FROM tomcat:9.0-jdk11

# Elimina aplicaciones por defecto
RUN rm -rf /usr/local/tomcat/webapps/*

# Copia tu WAR con el nombre ROOT.war para que sea accesible desde /
COPY target/ConsultaAlumnos_MServ-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/ROOT.war
