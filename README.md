# Smart-NR - NR-13 API


***

## Criação do Container do PostgreSQL

* Primeiramente, criar uma network dentro do Docker

```docker network create --driver bridge smartnr-network```

* Em seguida, criar o container dentro da rede

```docker run --name nr13-api-db -p 5432:5432 --network smartnr-network -d -e POSTGRES_DB=nr13_api -e POSTGRES_USER=admin -e POSTGRES_PASSWORD=123456 postgres:13```

## Build da imagem e container da API

* Antes de fazer o build da imagem, é preciso fazer o build da aplicação, para gerar o .jar atualizado.

```./mvnw clean package```

* Em seguida, fazer o build da imagem

```docker image build smartnr-nr13-api .```

* Para gerar o container, passar as variáveis de ambiente como parâmetro.

```docker run --name smartnr-nr13-api -p 8080:8080 --network smartnr-network -e DB_HOSTNAME=nr13-api-db -e DB_PORT=5432 smartnr-nr13-api```
