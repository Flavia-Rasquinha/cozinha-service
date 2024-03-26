## Cozinha Service

**Descrição**

Conforme o [enunciado](https://github.com/Flavia-Rasquinha/cozinha-service#STATEMENT.md) foi desenvolvido duas apps. O cozinha-service 
é responsável por processar os pedidos recebidos do Pedido Service. Ele realiza validações, verificações no estoque e atualiza o status dos pedidos.

**Tecnologias Utilizadas**

- Spring Boot
- Docker
- MongoDB
- Apache Kafka

**Deploy localhost**

Rodando a aplicação localhost, após rodar o comando **docker-compose up -d**, irá subir as imagens do kafka e mongo, após
isso rodar a aplicação com o comando spring.profiles.active=local no environment para subir a aplicação com o profile local ativo.

**Servidores Implantados**

- [Render](https://render.com/) [pedido-service](https://pedido-service.onrender.com)
- [Render](https://render.com/) [cozinha-service](https://cozinha-service.onrender.com)
- [Upstash](https://upstash.com/) [kafka](https://console.upstash.com/kafka/37e3fc6c-191d-4ca3-ad46-fdba4fd44dd8/03268c8b-a670-4010-94d7-67b4c2a936e1)
- [Atlas mongo](https://cloud.mongodb.com/v2/65d3e1126f8bb92563f4e1c5#/clusters)

**Configurações**

* Porta da Aplicação: 8081
* URL do Kafka: choice-corgi-8216-us1-kafka.upstash.io:9092
* URL do MongoDB: mongodb+srv://flaviarask:<password>@cluster0.zd0tc3l.mongodb.net/restaurante

# Endpoints
Para documentação completa dos endpoints acessar o **SwaggerUI**:<br>
**Local:** http://localhost:8081/swagger-ui/<br>
**Online:** https://cozinha-service.onrender.com/swagger-ui/<br>

<br>

- `GET` /actuator/health · Status da app