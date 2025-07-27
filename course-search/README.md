# Course Search Application

## Setup
1. Start Elasticsearch: `docker-compose up -d`
2. Verify: `curl http://localhost:9208`
3. Build and run: `mvn spring-boot:run`

## Data Indexing
- The app auto-indexes `sample-courses.json` on startup.
- Verify: `curl http://localhost:9208/courses/_count`