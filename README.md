# 🍽️ FoodManager
*Sistema de Gestão Compartilhada para Restaurantes*

## 📋 Sobre o Projeto

O **FoodManager** é um sistema revolucionário desenvolvido para atender um consórcio de restaurantes que buscavam uma solução robusta e econômica para gerenciar suas operações. 

### 🎯 O Problema
Na nossa região, um grupo de restaurantes decidiu contratar estudantes para construir um sistema de gestão para seus estabelecimentos. Essa decisão foi motivada pelo alto custo de sistemas individuais, o que levou os restaurantes a se unirem para desenvolver um sistema único e compartilhado. Esse sistema permitirá que os clientes escolham restaurantes com base na comida oferecida, em vez de se basearem na qualidade do sistema de gestão.

### 🚀 Objetivo
Desenvolver um backend completo e robusto utilizando o framework Spring Boot, com foco no gerenciamento de usuários, incluindo operações de criação, atualização, exclusão e validação de login. O projeto será configurado para rodar em um ambiente Docker, utilizando Docker Compose, garantindo que a aplicação seja facilmente replicável e escalável.

## 🏗️ Arquitetura

- **Backend:** Spring Boot 3.5.3
- **Linguagem:** Java 21
- **Banco de Dados:** MySQL 8.0
- **Containerização:** Docker & Docker Compose
- **Migração:** Flyway
- **ORM:** Spring Data JPA
- **Validação:** Spring Boot Validation

## 🛠️ Tecnologias Utilizadas

- ☕ **Java 21** - Linguagem principal
- 🌱 **Spring Boot 3.5.3** - Framework principal
- 🗄️ **MySQL 8.0** - Banco de dados relacional
- 🐳 **Docker** - Containerização
- 🔄 **Flyway** - Migração de banco de dados
- 📊 **Spring Data JPA** - Persistência de dados
- ✅ **Spring Boot Validation** - Validação de dados
- 🎯 **Lombok** - Redução de código boilerplate

## 🚀 Como Rodar o Projeto

### 📋 Pré-requisitos
- Docker e Docker Compose instalados
- Java 21 (para desenvolvimento)
- Maven 3.6+ (para desenvolvimento)

### 🔧 Primeira Execução

1. **Clone o repositório:**
```bash
git clone <url-do-repositório>
cd FoodManager
```

2. **Inicie o banco de dados:**
```bash
docker-compose up -d
```
*Este comando irá:*
- Criar e iniciar um container MySQL 8.0
- Configurar o banco de dados `foodmanager`
- Criar o usuário `foodmanager` com senha `foodmanager`
- Expor a porta 3306 para conexão local

3. **Compile e execute a aplicação:**
```bash
./mvnw spring-boot:run
```

4. **Verifique se está funcionando:**
- A aplicação estará disponível em: `http://localhost:8080`
- O banco de dados estará acessível em: `localhost:3306`

### 🗄️ Configuração do Banco de Dados

O projeto utiliza **Flyway** para migração automática do banco de dados. As migrações estão localizadas em `src/main/resources/db/migration/`.

**Credenciais do Banco:**
- **Host:** localhost:3306
- **Database:** foodmanager
- **Usuário:** foodmanager
- **Senha:** foodmanager

### 🔄 Comandos Úteis

```bash
# Parar todos os containers
docker-compose down

# Remover volumes (limpar dados do banco)
docker-compose down -v

# Ver logs do banco de dados
docker-compose logs mysql

# Acessar o MySQL via linha de comando
docker exec -it foodmanager-mysql mysql -u foodmanager -p foodmanager
```

## 📝 Desenvolvimento

### 🏃‍♂️ Rodando em Modo de Desenvolvimento

```bash
# Compilar o projeto
./mvnw clean compile

# Executar testes
./mvnw test

# Executar a aplicação
./mvnw spring-boot:run
```

### 🗂️ Estrutura do Projeto

```
FoodManager/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── br/com/foodmanager/
│   │   └── resources/
│   │       ├── application.properties
│   │       └── db/migration/
│   └── test/
├── docker-compose.yml
├── pom.xml
└── README.md
```

## 🌟 Funcionalidades Planejadas

### Fase 1 (Atual)
- ✅ Configuração do ambiente Docker
- ✅ Configuração do banco de dados MySQL
- ✅ Estrutura base do Spring Boot
- 🔄 Gerenciamento de usuários (CRUD)
- 🔄 Sistema de autenticação e autorização

### Fases Futuras
- 🔜 Gestão de restaurantes
- 🔜 Sistema de pedidos
- 🔜 Avaliações e comentários
- 🔜 API para clientes
- 🔜 Dashboard administrativo

## 🤝 Contribuindo

1. Faça um fork do projeto
2. Crie uma branch para sua feature (`git checkout -b feature/AmazingFeature`)
3. Commit suas mudanças (`git commit -m 'Add some AmazingFeature'`)
4. Push para a branch (`git push origin feature/AmazingFeature`)
5. Abra um Pull Request

## 📄 Licença

Este projeto está sob a licença MIT. Veja o arquivo `LICENSE` para mais detalhes.
