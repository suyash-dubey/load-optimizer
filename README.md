# SmartLoad Optimization API

A stateless REST API that computes the optimal combination of shipment orders
for a truck while respecting weight, volume, hazmat, route, and time-window
constraints.

---

## Tech Stack
- Java 17
- Spring Boot
- Maven
- Docker (multi-stage build)

---

## How to Run

### Prerequisites
- Docker
- Docker Compose

### Start the service
```bash
git clone https://github.com/suyash-dubey/load-optimizer.git
cd load-optimizer
docker compose up --build
