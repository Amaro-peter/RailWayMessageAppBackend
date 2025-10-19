# --- Estágio de Build ---
# Usa uma imagem oficial do Maven com o Java 21 (OpenJDK) para construir o projeto.
FROM maven:3.9-eclipse-temurin-21 AS build

# Define o diretório de trabalho dentro do container
WORKDIR /app

# Copia o pom.xml e os scripts do maven para o container
COPY ./apirestfulv1/pom.xml ./apirestfulv1/
COPY ./apirestfulv1/mvnw ./apirestfulv1/
COPY ./apirestfulv1/.mvn ./apirestfulv1/.mvn

# Baixa as dependências do projeto. Isso otimiza o cache em builds futuros.
RUN mvn -f ./apirestfulv1/pom.xml dependency:go-offline

# Copia o restante do código-fonte da aplicação
COPY ./apirestfulv1/src ./apirestfulv1/src

# Constrói a aplicação, empacotando-a em um .jar e pulando os testes
RUN mvn -f ./apirestfulv1/pom.xml clean install -DskipTests


# --- Estágio de Execução (Runtime) ---
# Usa uma imagem mais leve, apenas com o Java 21, para rodar a aplicação.
FROM eclipse-temurin:21-jre-jammy

# Define o diretório de trabalho
WORKDIR /app

# Copia o .jar construído no estágio anterior para o novo container
COPY --from=build /app/apirestfulv1/target/apirestfulv1-0.0.1-SNAPSHOT.jar ./app.jar

# Expõe a porta que a aplicação vai usar (o Railway vai mapear isso automaticamente)
EXPOSE 8080

# Comando para iniciar a aplicação quando o container for executado
CMD ["java", "-jar", "app.jar"]