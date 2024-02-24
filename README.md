# N+1 JPA Query Problem

- Run the spring boot app: ./mvnw spring-boot:run
- Run the angular frontend locally 

Open the UI at http://localhost:4200/
You will get a paginated list of 10 companies, performing 12 queries since this project reproduces the n+1 query problem.

The integration tests show how to solve the problem. 

# Database
By default, this project rely on h2 in mem DB. If you prefer, you can enable the spring profile called "postgres" to run the integration
tests against a testcontainer based on Postgresql DB 
See the application.properties, uncomment the first line in that case.

# References
- https://vladmihalcea.com/how-to-detect-the-n-plus-one-query-problem-during-testing/
- https://stackoverflow.com/questions/97197/what-is-the-n1-selects-problem-in-orm-object-relational-mapping
- https://dheerajgopinath.medium.com/the-issue-with-fetchmode-subselect-and-onetomany-mappings-in-hibernate-and-jpa-f79724068897
