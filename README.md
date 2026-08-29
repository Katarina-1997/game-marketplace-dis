# Game Marketplace — Distribuirani informacioni sistemi

Mikroservisni sistem za onlajn prodavnicu video igara, razvijen za potrebe predmeta
Distribuirani informacioni sistemi (master studije, FTN Novi Sad).

## Poslovna logika

Sistem omogućava pregled kataloga igara, kupovinu, obradu plaćanja i vođenje
biblioteke igara koje korisnik poseduje.

## Mikroservisi

| Servis | Uloga | Baza |
|---|---|---|
| catalog-service | Katalog igara | MongoDB |
| inventory-service | Biblioteka igrača | MongoDB |
| order-service | Porudžbine | MongoDB |
| payment-service | Obrada plaćanja | MongoDB |
| store-composite-service | Objedinjuje sve gore navedene servise | — |

## Komunikacija

- Sinhrona (REST): store-composite-service poziva catalog, inventory i order servise
- Asinhrona (Kafka/RabbitMQ): order-service → payment-service → inventory-service

## Tehnologije

- Spring Boot 4.0.8, Spring Cloud 2025.1.2
- Gradle (wrapper, verzija 9.7.1)
- MongoDB
- Docker / docker-compose

## Status

🚧 Projekat je u fazi razvoja. Mikroservisi se dodaju postepeno.