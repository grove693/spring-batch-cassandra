# spring-batch-cassandra
Spring Batch With Cassandra


Sample Project which combines Cassandra DB, Spring Batch & Spring Boot

It creates two spring batch jobs

Job1:
    - it simply reads a csv file and imports it into a Cassandra DB
Job2:
  - performs a join between two csv files (sample_movies.csv & sample_directors.csv)
  -the csv files are ordered by the  join column (id column, first column in each)
