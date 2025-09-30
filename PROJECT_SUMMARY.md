# 📚 AluraFake – API de Gerenciamento de Cursos & Atividades  

Este projeto é um **teste técnico** que implementa uma API REST para gerenciar cursos, tarefas e opções de tarefas.  
A aplicação foi desenvolvida em **Java + Spring Boot**, com persistência em banco de dados relacional.  

---

## 🗂️ Sumário  

- [Motivação](#-motivação)  
- [Principais Funcionalidades](#-principais-funcionalidades)  
- [Stack Tecnológico](#-stack-tecnológico)  
- [Arquitetura](#-arquitetura)  
- [Como Rodar](#-como-rodar)  
- [Endpoints](#-endpoints)  
- [Testes](#-testes)  
- [Próximos Passos](#-próximos-passos)  
- [Autor](#-autor)  

---

## 🎯 Motivação  

O projeto tem como objetivo simular uma plataforma de cursos online, permitindo:  
- Criação e publicação de cursos;  
- Cadastro e ordenação de tarefas (tasks) associadas a cada curso;  
- Inclusão de opções para tarefas de múltipla escolha.  

---

## 🚀 Principais Funcionalidades  

- Criar cursos com título, descrição e status;  
- Publicar cursos;  
- Criar tarefas de diferentes tipos (texto aberto, múltipla escolha, etc.);  
- Alterar ordem das tarefas em um curso;  
- Criar opções de resposta para tarefas de múltipla escolha;  
- Buscar tarefas e cursos já cadastrados.  

---

## 🛠️ Stack Tecnológico  

- **Java 17+**  
- **Spring Boot** (Web, Data JPA)  
- **Banco de Dados**: MySQL (ou H2 para testes locais)  
- **Maven** como gerenciador de dependências  
- **JUnit 5 + Mockito** para testes automatizados  

---

## 🏗️ Arquitetura  

O projeto segue uma arquitetura em camadas:  

```
Controller → Service → Repository → Database
```

- **Controller**: expõe os endpoints REST  
- **Service**: contém a lógica de negócio  
- **Repository**: abstrai o acesso ao banco de dados  
- **Entities**: representam as tabelas do domínio (Course, Task, TaskOption)  

---

## 💻 Como Rodar  

### Pré-requisitos  
- Java 17+  
- Maven 3+  
- MySQL em execução  

### Passos  
```bash
# Clonar o repositório
git clone https://github.com/diegondev/alurafake-main.git
cd alurafake-main

# Construir o projeto
mvn clean install

# Rodar a aplicação
mvn spring-boot:run
```

A API estará disponível em:  
👉 `http://localhost:8080`  

---

## 🌐 Endpoints  

### Cursos  
- `POST /user/new` → cria um novo usuário  
- `GET /user/all` → consulta todos os usuários cadastrados  
- `POST /instructor/{id}/courses` → Gera um relatório dos cursos do instrutor  

### Cursos  
- `POST /course/new` → cria um novo curso  
- `GET /course/all` → consulta todos os cursos cadastrados  
- `POST /course/{id}/publish` → publica um curso existente  

### Tarefas  
- `POST /task/new/opentext` → cria uma nova tarefa de texto aberto  
- `POST /task/new/multiplechoice` → cria uma tarefa de múltipla escolha  
- `POST /task/new/singlechoice` → cria uma tarefa de escolha unica  

### Opções de Tarefa  
- `POST /task/{taskId}/option` → adiciona uma opção de resposta a uma tarefa  

---

## 🧪 Testes  

Para rodar os testes:  

```bash
mvn test
```

Cobertura básica inclui:  
- Testes unitários de serviços  
- Testes de repositório usando banco em memória  

---

## 📌 Próximos Passos  

- Documentação Swagger/OpenAPI  
- Autenticação de usuários (JWT)  
- Dockerfile + docker-compose para facilitar setup  
- Validações adicionais em entidades (Bean Validation)  

---

## 👤 Autor  

**Diego Nascimento**  
- GitHub: [@diegondev](https://github.com/diegondev)  
- LinkedIn: [https://www.linkedin.com/in/diego-nascimento-a51b6898](#)  
