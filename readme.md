# Sistema de Gestão de Biblioteca Escolar (SGEL)

## 📌 Status do Projeto
🚧 Em desenvolvimento

## 💻 Tecnologias Aplicadas
Este projeto foi desenvolvido com as seguintes tecnologias ensinadas no curso:

- Java (Linguagem de Programação - Backend)
- MySQL (Banco de Dados Relacional)
- NetBeans IDE
- JDBC (Java Database Connectivity)

## 👨‍💻 Time de Desenvolvedores
- Tiago Santana Ferreira

## 🎯 Objetivo do Software
O **SGEL - Sistema de Gestão de Empréstimos de Livros da Biblioteca Escolar** tem como objetivo digitalizar e automatizar o controle de empréstimos e devoluções de livros. O sistema oferece maior organização, eficiência e acessibilidade ao acervo da biblioteca, registrando operações de retirada e devolução, e evitando atrasos e perdas.

## ✅ Funcionalidades do Sistema

### 📘 Requisitos Funcionais
- **RF001 – Cadastro de Alunos**  
  Permitir o registro de alunos com dados como Nome completo, Matrícula, Turma/Série, Contato (e-mail, telefone, etc).

- **RF002 – Gerenciamento de Livros (Acervo)**  
  Permitir cadastro, edição, inativação, consulta e controle do status dos livros (emprestado, reservado, disponível).

- **RF003 – Cadastro dos Bibliotecários**  
  Permitir cadastro e gerenciamento de bibliotecários com controle de permissões de acesso.

### 🔐 Requisitos Não Funcionais
- **RNF001 – Permissões de Usuários**  
  Controle de acesso baseado em níveis de usuário.

- **RNF002 – Segurança**  
  Autenticação de usuários e log de alterações no sistema.

- **RNF003 – Confiabilidade**  
  Garantia de integridade e consistência dos dados.

- **RNF004 – Disponibilidade**  
  Sistema disponível pelo menos 99% do tempo útil da biblioteca.

- **RNF005 – Usabilidade**  
  Interface intuitiva, com navegação simples e responsiva.

## ⚙️ Observações Técnicas
- O sistema foi construído utilizando **Java** com persistência de dados no **MySQL**.
- As conexões com o banco de dados utilizam **JDBC**.
- O sistema pode ser expandido para outras tecnologias como React ou Angular para interface gráfica.
- Há previsão de implementação de **backup diário do banco de dados** para garantir a segurança das informações.