# Usa una imagen oficial de Java + Maven
FROM maven:3.9.6-eclipse-temurin-17

# Establece el directorio de trabajo
WORKDIR /app

# Copia los archivos del proyecto
COPY . .

# Compila y empaqueta la app
RUN mvn clean install -DskipTests

# Expone el puerto de tu app (ajústalo si usas otro en Spring Boot)
EXPOSE 8080

# Comando para ejecutar la app
CMD ["java", "-jar", "target/tu-app.jar"]

