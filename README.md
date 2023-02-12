# Visualizador do Índice de Pobreza por país/região - dados do Banco Mundial

## Tecnologias
- Quarkus
- Angular 9

## Aplicação via Docker - antes de instalar o Docker compose

- Ir à pasta do projeto de Backend e rodar o comando: `./mvnw package`
- Ir à pasta do projeto de Frontend e rodar o comando: `ng build --prod`

Após isso, rodar o `docker compose build` e `docker compose create` para criar o container.
- A aplicação de frontend estará na porta **4200**.
- A aplicação de backend estará na porta **8080**.

## Swagger UI

Está disponível em `http://localhost:8080/q/swagger-ui` quando subir o backend via comando `quarkus dev`.

## Histórias-base

Estão no arquivo **Histórias.docx**.