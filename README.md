# Backend Challenge - Itaú Unibanco (Study)

This repository contains my study implementation of the technical challenge proposed by Itaú Unibanco, focused on backend development using **Java** and **Spring Boot**.

> This project was developed solely for learning and practice purposes. It is not an official submission to the challenge.

## Challenge Description

The challenge consists of developing a **REST API** capable of:
- Receiving transactions with value and date
- Storing the data **in memory**
- Returning statistics based on transactions from the last **60 seconds**

## Technologies Used

- Java 21+ 
- Spring Boot 3.4.4
- Maven
- Postman / Insomnia (API testing)

## API Features

### POST `/transaction`

Receives a new transaction with the following fields:

```json
{
  "value": 123.45,
  "dateTime": "2020-08-07T12:34:56.789-03:00"
}
```

**Constraints:**
- `value` must be ≥ 0
- `dateTime` must be in the past (never in the future)
- JSON must be properly formatted

**Responses:**
- `201 Created`: Valid transaction registered
- `422 Unprocessable Entity`: Invalid data
- `400 Bad Request`: Malformed JSON

### DELETE `/transaction`

Removes all transactions stored in memory.

**Response:**
- `200 OK`

### GET `/statistics`

Returns statistics based on transactions from the last **60 seconds**:

```json
{
  "count": 10,
  "sum": 1234.56,
  "avg": 123.45,
  "min": 12.34,
  "max": 234.56
}
```

**Response:**
- `200 OK`: Always returns all 5 fields (even if all values are 0)
