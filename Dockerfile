# ----------------------------
# Etapa 1: Build da aplicação Java e cache das dependências
# ----------------------------
FROM eclipse-temurin:21-jdk AS builder

# Define o diretório de trabalho dentro da imagem de build
WORKDIR /app-food-manager

# Copia apenas os arquivos necessários para baixar dependências primeiro
COPY pom.xml .
COPY mvnw .
COPY .mvn .mvn

# Baixa todas as dependências necessárias e plugins Maven, sem compilar
RUN ./mvnw dependency:go-offline

# Agora copia o restante do código-fonte
COPY . .

# Compila e empacota o projeto em um .jar
RUN ./mvnw clean package -DskipTests

# ----------------------------
# Etapa 2: Imagem de execução
# ----------------------------
FROM eclipse-temurin:21-jre

# Define o diretório de trabalho da aplicação
WORKDIR /app-food-manager

# Copia apenas o .jar gerado da etapa anterior
COPY --from=builder /app-food-manager/target/*.jar app.jar

# Expõe a porta 8080 (a mesma usada pela aplicação Spring Boot)
EXPOSE 8080

# Comando de inicialização do container
ENTRYPOINT ["java", "-jar", "app.jar"]
