## Cozinha Service

**Descrição**

O Cozinha Service é responsável por processar os pedidos recebidos do Pedido Service. Ele realiza validações, verificações no estoque e atualiza o status dos pedidos.

**Tecnologias Utilizadas**

- Spring Boot
- Docker
- MongoDB
- Apache Kafka

**Instruções de Instalação**

1. Certifique-se de ter o Docker instalado.
2. Clone o repositório.
3. Execute `docker-compose up -d` para iniciar o MongoDB e o Kafka.
4. Execute a aplicação Spring Boot.

**Configurações**

* Porta da Aplicação: 8081
* URL do Kafka: localhost:29092
* URL do MongoDB: mongodb://localhost:27017/local

**Endpoints**

* Endpoint do Consumidor Kafka (para receber pedidos):
  * URL: kafka:29092
