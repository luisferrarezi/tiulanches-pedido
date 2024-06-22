# TIU LANCHES
| :placard: Vitrine.Dev |     |
| -------------  | --- |
| :sparkles: Nome        | **Tiu Lanches - Pedido**
| :label: Tecnologias | Java, Maven, Spring, MySQL, Docker, Kubernetes, Kafka
| :rocket: URL         | 
| :fire: Desafio     | Tech Challenge FIAP

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT) ![Static Badge](https://img.shields.io/badge/any_text-Version-blue?label=latest)

# Detalhes do projeto
## Objetivo
Projeto criado para complementar o projeto principal [Tiu Lanches](https://github.com/luisferrarezi/tiulanches)

Este projeto representa o microsservice de pedido

# Arquitetura
## Infraestrutura
Para atender os requisitos da aplicação ser capaz de escalar foi implementado o Kubernetes onde é feito o deploy de forma automatizada via git actions

## Sonar
Foi implementado no através do git actions execução de testes e validação via sonar de todos os PRs e Main.

Segue imagem que ilustra o coverage e validação do sonar para este projeto:

![](https://github.com/luisferrarezi/tiulanches-pedido/blob/main/documentacao/imagens/coverage.jpg?table=block&id=ea599cfc-189c-4b5a-b3eb-21db292154fe&spaceId=62941c71-5c2d-41d6-8c4f-a5f5b14de56c&width=2000&userId=&cache=v2)

## Software
### Pattern
- Clean Architecture

### Linguagem
- Java - JDK 21

### Banco de dados
- MySql - 8.0

### Frameworks utilizados 
- Spring Framework
- Lombok
- Flyway
- Maven 
- Jackson Databind
- Log4j
- Kafka
- Jacoco
- JUnit
- Mockito

### Variáveis de Ambiente
Existem variáveis de ambiente na aplicação que estão cadastradas no docker compose e para kubernetes para serem apenas executadas.

Para poder funcionar em IDE é necessário cadastrar as seguintes variáveis de ambiente:
- DATASOURCE_URL=<DATASOURCE_URL>
- DATASOURCE_USERNAME=<DATASOURCE_USERNAME>
- DATASOURCE_PASSWORD=<DATASOURCE_PASSWORD>
- CONEXAO_KAFKA=<CONEXAO_KAFKA>

# Cloud
## Variáveis de Ambiente para Azure
Existe a variável de ambiente que é indispensável para que a function funcione corretamente:
- AZURE_CREDENTIALS=<AZURE_CREDENTIALS> - Credenciais necessárias do usuário github para que possa realizar o deploy
- CLUSTER_NAME=<CLUSTER_NAME> - O nome do cluster que foi criado para o AKS
- CONFIG_MAP_YAML=<CONFIG_MAP_YAML> - ConfigMap necessário com as variáveis de ambiente para o funcionamento da aplicação
- RESOURSE_GROUP=<RESOURSE_GROUP> - Resouce Group destinado na Azure para a aplicação
- SUBSCRIPTION=<SUBSCRIPTION> - Subscription ao qual o AKS criado pertence
- PASSWORD_ACR=<PASSWORD_ACR> - Senha para enviar imagem no ACR
- SERVER_ACR=<SERVER_ACR> - Server criado para enviar imagem no ACR
- USERNAME_ACR=<USERNAME_ACR> - Usuário para enviar imagem no ACR
- SONAR_TOKEN=<SONAR_TOKEN> - Token criado no sonar para que a aplicação seja validada por ele

## Automação
Atualmente para esta branch existem 2 níveis de automação, explicado abaixo:

- Pull Request: este é executado no momento que é criado o PR, e nele é realizado um build para garantir que a aplicação não foi quebrada após alteração, execução de todos os testes e validação via sonar cloud.
- Push: este é executado somente após o PR ter sido aprovado e executado o merge para a main, são realizados os seguintes processos:
    - execução de todos os testes 
    - validação via sonar cloud
    - build da aplicação, estando tudo ok é então logado no Container Registry da Azure, criado a imagem como latest e faz o upload dela para o ACR. 
    - Finalmente cria-se o ConfigMap com variáveis de ambiente necessárias para serem utilizadas pela aplicação no AKS, feito isso são utilizados os yamls configmap.yaml e app-deployment.yaml, para subir a aplicação no AKS.

# Local
## Execução
Antes de se executar qualquer comando deste projeto é necessário que esse sejam executados os comandos que estão no repositório principal para ter uma estrutura prévia. [Tiu Lanches](https://github.com/luisferrarezi/tiulanches).

Para executar o projeto localmente é necessário que antes seja criada uma base Mysql e configurar as variáveis de ambiente especificadas em "Variáveis de Ambiente", após isso vá até kubernetes\local onde tem o deployment necessário:

~~~Execute
kubectl apply -f tlpedido-deployment.yaml
~~~

# OWASP
## Relatórios
Neste projeto é prossível encontrar os relatórios Zap com a divulgação de ajustes necessários e o relatório após a correção.

[Criar pedidos antes correção](https://github.com/luisferrarezi/tiulanches-pedido/blob/main/documentacao/owasp/zap/ZAP-Criar-Pedidos.pdf)

[Criar pedidos após correção](https://github.com/luisferrarezi/tiulanches-pedido/blob/main/documentacao/owasp/zap/ZAP-Criar-Pedidos-Corrigido.pdf)
