FROM openjdk:21-jdk-slim

WORKDIR /app

# Copiar o arquivo pom.xml e baixar as dependências
COPY pom.xml .
COPY mvnw .
COPY mvnw.cmd .
COPY .mvn .mvn

# Baixar dependências do Maven
RUN ./mvnw dependency:go-offline -B

# Copiar o código fonte
COPY src src

# Compilar e empacotar a aplicação
RUN ./mvnw clean package -DskipTests

# Expor a porta da aplicação
EXPOSE 8085

# Comando para executar a aplicação
CMD ["java", "-jar", "target/orders-0.0.1-SNAPSHOT.jar"] 