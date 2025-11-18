# Étape build avec Maven + JDK 17
FROM eclipse-temurin:17 AS build

WORKDIR /app

RUN apt-get update && apt-get install -y maven

COPY pom.xml .
RUN mvn dependency:go-offline

COPY src ./src
RUN mvn clean package -DskipTests

# Étape finale : image Tomcat
FROM tomcat:10.1-jre17


# Copier le WAR
COPY --from=build /app/target/online-library.war /usr/local/tomcat/webapps/online-library.war

# Changer le port de 8080 à 8082 dans server.xml
RUN sed -i 's/port="8080"/port="8082"/' /usr/local/tomcat/conf/server.xml

EXPOSE 8082

# La commande par défaut de Tomcat est déjà "catalina.sh run"