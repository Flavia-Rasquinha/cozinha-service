## Cozinha Service

**Descrição**

O Cozinha Service é responsável por processar os pedidos recebidos do Pedido Service. Ele realiza validações, verificações no estoque e atualiza o status dos pedidos.

**Tecnologias Utilizadas**

- Spring Boot
- Docker
- MongoDB
- Apache Kafka

**Servidores Implantados**

- Render pedido-service
- Render cozinha-service
- Upstash kafka
- Atlas mongo

**Configurações**

* Porta da Aplicação: 8081
* URL do Kafka: choice-corgi-8216-us1-kafka.upstash.io:9092
* URL do MongoDB: mongodb+srv://flaviarask:<password>@cluster0.zd0tc3l.mongodb.net/restaurante

**Endpoints**

* Topico do Consumidor Kafka (para receber pedidos):
  * Servidor: kafka:9092 - pedidos
