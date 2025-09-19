Gestor Financeiro API
Uma API RESTful robusta e segura, construída com Java e Spring Boot, desenhada para ser a espinha dorsal de uma aplicação moderna de gestão financeira pessoal e para casais.

🎯 O Problema a Ser Resolvido
No mundo agitado de hoje, a gestão financeira pode ser uma fonte significativa de stress e incerteza, especialmente para casais que tentam alinhar os seus objetivos. A falta de uma visão clara e centralizada sobre as receitas, despesas e orçamentos leva a:

Desorganização e Perda de Controlo: Dificuldade em saber para onde o dinheiro está a ir.

Stress no Relacionamento: Divergências sobre gastos e prioridades financeiras.

Dificuldade em Atingir Metas: A falta de planeamento torna desafiante poupar para objetivos importantes, como uma viagem, a compra de um imóvel ou a criação de uma reserva de emergência.

✨ A Solução: Clareza e Controlo Total
A API do Gestor Financeiro surge como a solução de back-end para uma ferramenta que devolve o poder e a tranquilidade aos seus utilizadores. Ao fornecer uma plataforma centralizada, segura e eficiente, a API permite que uma aplicação (seja ela web ou mobile) ofereça:

Visão Unificada: Todos os dados financeiros num único local, acessíveis a qualquer momento.

Tomada de Decisão Informada: Relatórios e dashboards que transformam dados brutos em insights valiosos.

Colaboração Financeira: (Futuramente) Um espaço partilhado para casais gerirem as suas finanças em conjunto, promovendo transparência e trabalho em equipa.

Planeamento e Conquista: Ferramentas para definir orçamentos e acompanhar o progresso em direção a metas financeiras.

🚀 Funcionalidades Principais
🔐 Autenticação e Segurança: Sistema completo de registo e login com autenticação via JWT (JSON Web Tokens), garantindo que os dados de cada utilizador são privados e seguros.

🗂️ Gestão de Categorias: CRUD completo para que os utilizadores possam personalizar as suas categorias de receitas e despesas.

🏦 Gestão de Contas: Permite o registo e controlo de múltiplas contas (conta-corrente, carteira, poupança).

💸 Lançamento de Transações: Registo detalhado de todas as receitas e despesas, associando-as a categorias e contas.

📊 Definição de Orçamentos: Permite definir limites de gastos mensais por categoria para um controlo proativo das finanças.

📈 Dashboard Consolidado: Um único endpoint que agrega e calcula métricas chave (saldo total, receitas/despesas do mês, resumo dos orçamentos) para uma visão geral instantânea.

🛠️ Stack Tecnológico e Arquitetura
Este projeto foi construído com tecnologias modernas e boas práticas de desenvolvimento, focando em escalabilidade, manutenibilidade e segurança.

Arquitetura:
A aplicação segue uma Arquitetura em Camadas bem definida, promovendo uma clara separação de responsabilidades:
Cliente ↔ Controller (API) ↔ Service (Lógica de Negócio) ↔ Repository (Acesso a Dados) ↔ Base de Dados

Tecnologias Utilizadas:

Backend: Java 21, Spring Boot 3.5.6, Spring Web

Persistência de Dados: Spring Data JPA, Hibernate

Base de Dados: PostgreSQL

Segurança: Spring Security, JWT

Utilitários: Lombok, Jakarta Bean Validation

Build Tool: Maven

🏁 Como Começar (Ambiente Local)
Siga os passos abaixo para executar o projeto na sua máquina local.

Pré-requisitos
JDK 21 ou superior

Maven 3.6+

PostgreSQL a correr localmente ou num container Docker

Passos
Clone o repositório:

git clone [https://github.com/welingtonargolo/gestorFinanceiro.git](https://github.com/welingtonargolo/gestorFinanceiro.git)
cd gestorFinanceiro

Configure a Base de Dados:

Crie uma base de dados no PostgreSQL chamada gestor_financeiro.

Abra o ficheiro src/main/resources/application.properties.

Altere as seguintes propriedades com as suas credenciais:

spring.datasource.url=jdbc:postgresql://localhost:5432/gestor_financeiro
spring.datasource.username=seu_usuario_postgres
spring.datasource.password=sua_senha_postgres

Configure a Chave Secreta do JWT:

No mesmo ficheiro application.properties, altere a chave secreta:

jwt.secret=esta-e-uma-chave-secreta-de-exemplo-altere-em-producao

Execute a Aplicação:

mvn spring-boot:run

A API estará pronta a ser utilizada em http://localhost:8080.

🗺️ Visão Geral dos Endpoints da API
Autenticação:

POST /api/users/register - Regista um novo utilizador.

POST /api/auth/login - Autentica e retorna um token JWT.

Recursos Protegidos (Requerem 'Authorization: Bearer <token>'):

GET, POST /api/categories

PUT, DELETE /api/categories/{id}

GET, POST /api/accounts

PUT, DELETE /api/accounts/{id}

GET, POST /api/transactions

PUT, DELETE /api/transactions/{id}

GET, POST /api/budgets

PUT, DELETE /api/budgets/{id}

GET /api/dashboard

🔮 Roadmap (Próximos Passos)
[ ] Funcionalidades para Casal: Sistema de convites para partilhar contas e visualizar um dashboard conjunto.

[ ] Relatórios Avançados: Endpoints para filtrar transações por períodos de datas, categorias e contas.

[ ] Metas de Poupança: Módulo para criar e acompanhar o progresso de objetivos financeiros.

[ ] Notificações: Alertas por e-mail ou push quando um orçamento estiver perto do limite.

👨‍💻 Autor
Welington Machado Argolo

LinkedIn: www.linkedin.com/in/welington-argolo-2476861b9

Este projeto foi desenvolvido com o objetivo de aplicar conceitos avançados de desenvolvimento de software e criar uma solução de alto impacto para um problema do mundo real.
