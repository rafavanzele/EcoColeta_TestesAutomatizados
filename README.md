
# EcoColeta — Testes Automatizados

## 1. Apresentação do Projeto

O EcoColeta é uma API REST desenvolvida em C# com .NET 8, voltada à gestão de resíduos e pontos de coleta, promovendo práticas relacionadas à sustentabilidade e à responsabilidade ambiental.

Este projeto implementa uma suíte de testes automatizados para a API EcoColeta, utilizando Java, Cucumber, RestAssured e JUnit 5.

A automação foi desenvolvida como parte da atividade avaliativa da disciplina de Testes Automatizados da FIAP, aplicando os conceitos de Behavior Driven Development (BDD) e a linguagem Gherkin.

Os testes verificam o comportamento das funcionalidades da aplicação, incluindo operações CRUD, códigos de resposta HTTP, conteúdo das respostas JSON, contratos de API com JSON Schema e cenários negativos.

### Objetivos

- Validar o funcionamento das principais operações da API EcoColeta.
- Automatizar os testes dos recursos TipoResiduo, PontoColeta e ColetaResiduo.
- Verificar os códigos de resposta HTTP e os dados retornados pela API.
- Validar os contratos das respostas utilizando JSON Schema.
- Executar cenários positivos e negativos escritos em Gherkin.
- Contribuir para a qualidade, confiabilidade e integridade dos dados relacionados à gestão de resíduos.

### Relação com ESG

O projeto está relacionado ao pilar ambiental (Environmental) do ESG, pois oferece funcionalidades para gerenciamento de resíduos e pontos de coleta.

A automação dos testes contribui para a confiabilidade dessas operações, verificando a integridade dos dados e o comportamento esperado da aplicação.


## 2. Tecnologias Utilizadas

O projeto utiliza as seguintes tecnologias e ferramentas:

- **Java 21:** linguagem utilizada na implementação dos testes automatizados.
- **Maven:** gerenciamento das dependências e execução do projeto de testes.
- **Cucumber 7.20.1:** implementação de testes BDD utilizando a linguagem Gherkin.
- **JUnit 5.11.4:** framework de testes utilizado em conjunto com o Cucumber.
- **RestAssured 5.5.0:** automação de requisições HTTP e validação das respostas da API.
- **JSON Schema:** validação dos contratos das respostas JSON.
- **IntelliJ IDEA:** ambiente de desenvolvimento e execução dos testes.
- **Git e GitHub:** versionamento e armazenamento do código-fonte.

### API Testada

A aplicação utilizada como base para os testes é a API EcoColeta, desenvolvida anteriormente em C# com .NET 8.

A API utiliza:

- ASP.NET Core Web API.
- Entity Framework Core.
- SQL Server.
- Autenticação HTTP Basic.
- Arquitetura organizada em Controllers, Services e Repositories.

Durante a execução local, a API é disponibilizada no endereço:

`https://localhost:7068`

Os testes automatizados realizam requisições HTTP para validar as funcionalidades dos recursos:

- `TipoResiduo`
- `PontoColeta`
- `ColetaResiduo`


## 3. Estrutura do Projeto

O projeto de testes automatizados está organizado da seguinte forma:

```text
EcoColeta_TestesAutomatizados/
|
|-- .mvn/
|-- src/
|   |-- main/
|   |-- test/
|       |-- java/
|       |   |-- br/com/fiap/ecocoleta/
|       |       |-- runner/
|       |       |   |-- RunCucumberTest.java
|       |       |-- steps/
|       |           |-- TipoResiduoSteps.java
|       |           |-- PontoColetaSteps.java
|       |           |-- ColetaResiduoSteps.java
|       |
|       |-- resources/
|           |-- features/
|           |   |-- tipo_residuo.feature
|           |   |-- ponto_coleta.feature
|           |   |-- coleta_residuo.feature
|           |
|           |-- schemas/
|               |-- tipo_residuo_schema.json
|               |-- tipo_residuo_lista_schema.json
|               |-- ponto_coleta_schema.json
|               |-- ponto_coleta_lista_schema.json
|               |-- coleta_residuo_schema.json
|               |-- coleta_residuo_lista_schema.json
|
|-- .gitignore
|-- pom.xml
|-- README.md
```

### Organização dos Arquivos

**Runner**

A classe `RunCucumberTest.java` é responsável por iniciar a execução dos cenários BDD utilizando Cucumber e JUnit.

**Steps**

As classes localizadas no pacote `steps` implementam as etapas descritas nos arquivos Gherkin.

Cada classe contém as requisições HTTP, validações e verificações correspondentes ao recurso testado.

**Features**

Os arquivos `.feature` descrevem os cenários de teste utilizando a linguagem Gherkin, com as palavras-chave `Dado`, `Quando` e `Então`.

Os cenários abrangem operações CRUD e validações de comportamentos positivos e negativos.

**Schemas**

Os arquivos JSON Schema definem a estrutura esperada das respostas da API.

São utilizados para validar os contratos das respostas JSON, verificando a estrutura e os tipos de dados retornados.

**pom.xml**

Arquivo responsável pelo gerenciamento das dependências Maven necessárias à compilação e execução dos testes.


## 4. Pré-requisitos

Para executar os testes automatizados, é necessário preparar o ambiente de desenvolvimento com as seguintes ferramentas:

- **Java JDK 21:** necessário para compilar e executar os testes automatizados.
- **Maven:** utilizado para gerenciar as dependências e executar os testes.
- **IntelliJ IDEA:** ambiente utilizado durante o desenvolvimento (opcional para execução via terminal).
- **.NET SDK 8:** necessário para executar a API EcoColeta localmente.
- **SQL Server:** banco de dados utilizado pela API EcoColeta.
- **Git:** utilizado para obter e versionar o código-fonte (opcional).

### Dependências do Projeto

As dependências Java são gerenciadas pelo Maven por meio do arquivo `pom.xml`.

Entre as principais dependências utilizadas estão:

- Cucumber Java.
- Cucumber JUnit Platform Engine.
- JUnit Platform Suite.
- RestAssured.
- JSON Schema Validator.

O Maven realiza o download das dependências declaradas no `pom.xml`, dispensando sua instalação individual.

### Requisitos para Execução

Antes de executar os testes, é necessário garantir que:

1. A API EcoColeta esteja em execução localmente.
2. O banco de dados SQL Server esteja disponível e configurado para a API.
3. A API esteja acessível pelo endereço `https://localhost:7068`.
4. As credenciais de autenticação HTTP Basic estejam configuradas no ambiente de execução dos testes.
5. O projeto Java esteja configurado para utilizar o JDK 21.


## 5. Configuração e Inicialização da API EcoColeta

Os testes automatizados deste projeto são executados contra a API REST EcoColeta, desenvolvida anteriormente em C# com ASP.NET Core e .NET 8.

A API deve estar em execução antes de iniciar a suíte de testes automatizados.

### Obtendo o Código-Fonte da API

O código-fonte da API EcoColeta está disponível no GitHub:

https://github.com/rafavanzele/EcoColeta_DotNet8

Para obter o projeto, é possível clonar o repositório utilizando o Git:

```bash
git clone https://github.com/rafavanzele/EcoColeta_DotNet8.git
```

Também é possível baixar o projeto diretamente pelo GitHub, utilizando a opção **Code > Download ZIP**.

### Preparando o Ambiente

Antes de iniciar a API:

1. Abra a solução EcoColeta no Visual Studio.
2. Verifique se o .NET SDK 8 está instalado.
3. Certifique-se de que o SQL Server esteja disponível.
4. Configure a conexão com o banco de dados conforme o ambiente local.
5. Verifique se as migrations do Entity Framework Core foram aplicadas.
6. Configure as credenciais utilizadas pela autenticação HTTP Basic.

### Inicializando a API

No Visual Studio:

1. Abra a solução `EcoColeta.Api`.
2. Selecione o projeto `EcoColeta.Api` como projeto de inicialização.
3. Selecione o perfil de execução HTTPS.
4. Inicie a aplicação utilizando o botão de execução do Visual Studio.

Após a inicialização, a API deverá estar acessível em:

`https://localhost:7068`

### Verificando a Disponibilidade

Antes de executar os testes automatizados, confirme que a API está em execução e respondendo às requisições HTTP.

Os testes utilizam autenticação HTTP Basic para acessar os endpoints protegidos.

As credenciais devem ser configuradas no ambiente de execução e não devem ser armazenadas diretamente no código-fonte ou publicadas no repositório GitHub.


## 6. Configuração e Execução dos Testes Automatizados

A suíte de testes automatizados do EcoColeta foi desenvolvida em Java, utilizando Maven, Cucumber, JUnit 5 e RestAssured.

Os testes são executados localmente e realizam requisições HTTP para a API EcoColeta, validando o comportamento dos endpoints.

### Obtendo o Projeto de Testes

O código-fonte dos testes automatizados está disponível no GitHub:

https://github.com/rafavanzele/EcoColeta_TestesAutomatizados

Para clonar o repositório:

```bash
git clone https://github.com/rafavanzele/EcoColeta_TestesAutomatizados.git
```

Também é possível baixar o projeto utilizando a opção **Code > Download ZIP** no GitHub.

### Abrindo o Projeto no IntelliJ IDEA

1. Abra o IntelliJ IDEA.
2. Selecione a opção **Open**.
3. Localize a pasta `EcoColeta_TestesAutomatizados`.
4. Selecione o arquivo `pom.xml` ou abra a pasta do projeto.
5. Aguarde o Maven carregar e sincronizar as dependências.
6. Verifique se o projeto está configurado para utilizar o Java JDK 21.

### Configurando as Credenciais de Autenticação

A API EcoColeta utiliza autenticação HTTP Basic.

As credenciais necessárias para acessar os endpoints protegidos devem ser configuradas como variáveis de ambiente na configuração de execução do IntelliJ IDEA.

Para configurar:

1. Acesse **Run > Edit Configurations**.
2. Selecione a configuração `RunCucumberTest`.
3. Localize o campo **Environment variables**.
4. Configure as variáveis de ambiente utilizadas pelo projeto para autenticação.
5. Salve as alterações clicando em **Apply** e **OK**.

Por segurança, não inclua usuários e senhas reais no repositório.

### Executando os Testes pelo IntelliJ IDEA

Antes de executar os testes, certifique-se de que a API EcoColeta esteja em execução em:

`https://localhost:7068`

Para executar a suíte:

1. Abra o arquivo `RunCucumberTest.java`, localizado no pacote `br.com.fiap.ecocoleta.runner`.
2. Selecione a configuração de execução `RunCucumberTest`.
3. Clique no botão **Run** do IntelliJ IDEA.
4. Aguarde a execução dos cenários automatizados.
5. Consulte os resultados na janela **Run**.

O IntelliJ IDEA apresenta os cenários executados, indicando quais passaram ou falharam.

### Executando os Testes pelo Maven

Também é possível executar os testes pelo terminal, desde que o Java 21 e o Maven estejam configurados corretamente.

Com a API EcoColeta em execução e as variáveis de ambiente configuradas, abra o terminal na raiz do projeto e execute:

```bash
mvn test
```

O Maven executará os testes configurados no projeto e apresentará os resultados no terminal.

### Resultados Esperados

Durante a execução, os testes verificam:

- Códigos de resposta HTTP.
- Conteúdo das respostas JSON.
- Contratos das respostas utilizando JSON Schema.
- Operações de consulta, cadastro, atualização e exclusão.
- Persistência dos dados.
- Cenários positivos e negativos.
- Comportamento da API diante de requisições não autorizadas.

A execução será considerada bem-sucedida quando todos os cenários automatizados forem concluídos sem falhas.


## 7. Cenários de Teste Implementados

A suíte de testes automatizados do EcoColeta utiliza Cucumber e a linguagem Gherkin para descrever e validar o comportamento dos endpoints da API.

Os cenários estão organizados em três arquivos `.feature`, correspondentes aos recursos `TipoResiduo`, `PontoColeta` e `ColetaResiduo`.

### TipoResiduo

Os testes deste recurso verificam as operações de consulta, cadastro, atualização e exclusão de tipos de resíduos.

### PontoColeta

Os testes deste recurso verificam as operações de consulta, cadastro, atualização e exclusão de pontos de coleta.

### ColetaResiduo

Os testes deste recurso verificam as operações de consulta, cadastro, atualização e exclusão de coletas de resíduos, além de cenários negativos de consulta e autenticação.

### Validações Realizadas

Durante a execução dos cenários, são realizadas verificações de códigos de resposta HTTP, conteúdo das respostas JSON, contratos definidos por JSON Schema e comportamento das operações de persistência.

A suíte também contempla verificações de comportamentos positivos e negativos da API.


## 8. Resultados da Execução

A suíte de testes automatizados foi executada utilizando o Maven integrado ao IntelliJ IDEA, com a API EcoColeta em execução localmente.

Os testes foram realizados utilizando Java 21, Cucumber, JUnit 5 e RestAssured.

### Resultado da Execução

A execução completa apresentou os seguintes resultados:

| Indicador | Resultado |
|---|---|
| Testes executados | 17 |
| Testes aprovados | 17 |
| Falhas | 0 |
| Erros | 0 |
| Testes ignorados | 0 |
| Resultado do build | BUILD SUCCESS |

### Evidência da Execução

O Maven apresentou o seguinte resumo:

```text
Tests run: 17, Failures: 0, Errors: 0, Skipped: 0

BUILD SUCCESS

Total time: 53.401 s
```

### Conclusão dos Testes

Todos os 17 testes automatizados foram executados com sucesso, sem falhas ou erros.

Os resultados demonstram que os cenários implementados foram validados com sucesso no ambiente local utilizado durante a execução.

A suíte contempla operações CRUD, validações de respostas HTTP, contratos JSON Schema e cenários positivos e negativos, contribuindo para a confiabilidade da API EcoColeta.


## 9. Considerações Finais

O projeto EcoColeta — Testes Automatizados foi desenvolvido como parte da atividade avaliativa da disciplina de Testes Automatizados da FIAP.

A solução utiliza Java 21, Maven, Cucumber, JUnit 5 e RestAssured para implementar testes automatizados de uma API REST desenvolvida em C# com .NET 8.

Foram implementados cenários BDD para os recursos TipoResiduo, PontoColeta e ColetaResiduo, contemplando operações CRUD, validações de respostas HTTP, contratos JSON Schema e cenários positivos e negativos.

A execução da suíte pelo Maven integrado ao IntelliJ IDEA apresentou 17 testes executados com sucesso, sem falhas ou erros.

O projeto demonstra a aplicação prática de conceitos de automação de testes, Behavior Driven Development (BDD), validação de contratos e integração entre diferentes tecnologias.

Os testes automatizados contribuem para a identificação de problemas, a verificação dos comportamentos esperados e a confiabilidade das funcionalidades implementadas na API EcoColeta.

### Repositórios do Projeto

**API EcoColeta (.NET 8):**

https://github.com/rafavanzele/EcoColeta_DotNet8

**Testes Automatizados (Java):**

https://github.com/rafavanzele/EcoColeta_TestesAutomatizados

  