# Reward Points Application

This is a Spring Boot application that calculates reward points for customers based on their transactions over the last 3 months.

The application exposes REST APIs to:

* Fetch rewards for all customers
* Fetch rewards for an individual customer

## Tech Stack

* Java 17
* Spring Boot
* Spring Data JPA
* H2 Database
* Lombok
* JUnit & Mockito

## Project Structure
com.demo.rewards

 ├── controller
 ├── dto
 ├── exception
 ├── model
 ├── repository
 ├── service
 └── util

## APIs

### 1. Get All Rewards
GET /api/rewards

### 2. Get Rewards by Customer ID
GET /api/rewards/{customerId}

## Reward Calculation Logic

* For every dollar spent:
    * > $50 → 1 point
    * > $100 → 2 points

### Example:

* $120 → 90 points
  (50 * 1 + 20 * 2)

## Database

* In-memory H2 database is used
* Data is loaded using `data.sql`

## Testing

### Unit Tests

* Service Layer
* Controller Layer (MockMvc)

### Integration Test

* `RewardIntegrationTests`

## Exception Handling

* Global exception handler implemented
* Custom exception: `ResourceNotFoundException`

## Improvements Implemented

* Added proper API design
* Introduced Service & ServiceImpl layers
* Used entrySet for better performance
* Added unit & integration tests
* Implemented global exception handling

## Author
Navya Srivastava
