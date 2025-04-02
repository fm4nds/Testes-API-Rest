<h1>Automação de Testes de API para dummyjson.com</h1>
Por: Amanda Galindo - linkedin.com/in/amandalaisgalindo/
<h2>Informações do Projeto</h2>
Este projeto implementa testes automatizados para a API dummyjson.com utilizando as seguintes tecnologias:


- RestAssured
- JUnit
- Allure
- Maven
  
O projeto cobre os seguintes endpoints:
- GET /test - Verificação do status da aplicação
- GET /users - Listagem de usuários
- POST /auth/login - Autenticação e geração de token
- GET /auth/products - Listagem de produtos com autenticação
- POST /products/add - Criação de novos produtos
- GET /products - Listagem de todos os produtos
- GET /products/{id} - Busca de produto por ID

<h2>Como executar</h2>
git clone https://github.com/fm4nds/testes-api-rest > cd testes-api-rest > mvn clean test 

O projeto utiliza um Makefile para facilitar a execução dos testes e gerenciamento de dependências. 
Basta executar make help para obter informações de comandos disponíveis.

<h2> Plano de Teste </h2>
<b>Dados do Projeto/Pessoas Envolvidas</b>

Esta seção destina-se a descrever os dados e as pessoas envolvidas durante todo o ciclo de desenvolvimento do projeto.

- Nome do Projeto: Projeto DB
- Product Owner: John Doe 
- Analista de Qualidade: Amanda Lais Galindo de Lima
- Desenvolvedor: John Doe

<b>Premissas</b>

Nesta seção estão descritas as premissas que deverão ser atendidas para a execução dos testes.

- P01: Endpoints acessíveis.
- P02: Documentação atualizada, com os métodos, parâmetros obrigatórios/opcionais, exemplos de request/response e critérios de aceitação.
- P03: Dados de teste preparados, massas de dados para diferentes cenários, bancos de dados com dados consistentes, ambiente isolado (Sem impacto em homologação ou produção).
- P04: Autenticação configurada, tokens de acesso gerados e válidos para testes.

<b>Riscos</b>

Nesta seção estão detalhados os riscos mapeados para os testes

- R01: Documentação estar desatualizada, dados incorretos nos requests/responses, que levem a falsos negativos. 

Contigência: Validar endpoints diretamente com o time de desenvolvimento e atualizar a documentação em paralelo.
- R02: Falha em testes devido a massas incorretas ou ausentes.

Contingência: Criar scripts para gerar dados sob demanda

<h2>Estratégias de Teste</h2>

A validação da API será conduzida de forma automatizada. Os testes serão executados em múltiplos 
ambientes (desenvolvimento, homologação e produção) e incluirão diferentes tipos de validação. 
Os critérios de sucesso envolvem aderência ao esquema de dados e regras definidas.

<b>Tipos de Teste</b>

- <b>Testes Funcionais</b>

Testes de Status Code, Contrato (Schema Validation) e Dados (Payload Validation)

- <b>Testes Não Funcionais</b>

Testes de Compatibilidade e Confiabilidade

- <b>Testes de Performance</b> 

Testes de Tempo de Resposta

- <b>Testes de Segurança</b> 

Testes de Autenticação, Autorização e Manipulação de Erros

<b>Ferramentas e Tecnologias Utilizadas</b>

- RestAssured: Para automação dos testes de API REST.
- JUnit: Para execução e estruturação dos testes.
- Allure Reports: Para geração de relatórios.

<b>Cronograma/Estimativa de Esforço</b>

Nesta seção é especificada a data de liberação do pacote para os testes, a data limite para finalização e a estimativa inicial para a execução.

Período das atividades de testes: 01/04/2025 – 05/04/2025
- Planejamento - Definição de escopo, priorização, alinhamento com stakeholders: 3h
- PR documentação - Revisão dos critérios de aceitação, documentação da API, validação com PO/Dev: 2h
- Modelagem - Preparação de dados de teste, configuração de automação, descrição das suítes e casos de testes: 8h
- Execução e Monitoramento - Execução dos casos de testes, análise de logs, status codes. Reteste de bugs corrigidos: 12h
- Relatório Final dos Testes - Documento de métricas dos Testes, que incluem quantidade de bugs, cobertura dos casos de teste e possíveis desvios: 2h

<h2>Bugs Encontrados</h2>
<b>[BUG 01] - Status Code Incorreto no Endpoint POST/auth/login</b></br>
Prioridade: Média</br>
Severidade: Médio</br>

Descrição: Discrepância entre Documentação da API e Implementação </br>

<b>Comportamento Esperado</b> > Deveria retornar status code 201 Created, conforme documentação</br>
<b>Comportamento Atual</b> > Retorna status code 200 OK

Reprodução:
1. Enviar requisição POST válida para /auth/login
2. Observar status code na resposta

<b>[BUG 02] - Código de Status Incorreto em GET /auth/products sem Token</b></br>
Prioridade: Alta</br>
Severidade: Alto</br>

Descrição: Resposta de autenticação diferente da documentação em casos que o token não é enviado</br>

<b>Comportamento Esperado</b> > Deveria retornar status code 403 Forbidden com mensagem "Authentication Problem"</br>
<b>Comportamento Atual</b> > Retorna status code 401 Unauthorized com mensagem "Access Token is required"

Reprodução:
1. Fazer requisição GET /auth/products SEM enviar o header Authorization
2. Observar status code e mensagem de retorno

<b>[BUG 03] - Inconsistência no Nome do Atributo do Token de Acesso no Endpoint POST/auth/login</b></br>
Prioridade: Alta</br>
Severidade: Alta</br>

Descrição: Discrepância no nome do atributo que retorna o token de acesso</br>

<b>Comportamento Esperado</b> > Deveria retornar o token no atributo "token" conforme documentação</br>
<b>Comportamento Atual</b> > Retorna o token no atributo "accessToken"

Reprodução:
1. Enviar requisição POST válida para /auth/login
2. Observar o nome do atributo que contém o token na resposta

<b>[BUG 04] - Falta do Atributo "name" em Respostas 401 Unauthorized para GET /auth/products</b></br>
Prioridade: Alta</br>
Severidade: Alto</br>

Descrição: Resposta de erro não inclui atributo "name" que está definida na Response da documentação.</br>

<b>Comportamento Esperado</b> > Deveria incluir atributo "name" e "message" no response body, conforme documentação</br>
<b>Comportamento Atual</b> > Retorna apenas o atributo "message" 

Reprodução:
1. Enviar requisição GET válida para /auth/products
2. Observar o nome do atributo que contém o token na resposta

<h2>Melhorias Encontradas</h2>

- Falta de Definição para Fluxos de Exceção/Alternativos: Não há a documentação adequada dos cenários de erro, como por exemplo: tipos de dados inválidos, limite de caracteres excedidos.
Dificulta a implementação de tratamentos de erro robustos nos clients e gera inconsistência nas respostas de erro, algumas retornam 400 Bad Request, ou 401 Unautorized onde deveria ser considerado outro status, ausencia de padrão de status code.

- Tempo de Resposta não documentado: Não há SLAs definidos para tempo máximo de resposta por endpoint. Dificulta a identificação de degradação de performance. Clients não têm parâmetros para definir timeouts adequados.

- Falha Grave de Segurança: Dados Sensíveis Não Codificados: A API expõe dados sensíveis, como senha em formato plain text. Violação de OWASP e possibilidade de ataques.

- Não ocorre validação dos campos ao criar um produto, a requisição pode ser enviada com os atributos vazios e retorna um 201 da mesma forma de um produto com todos os campos validos no endpoint POST /products/add
