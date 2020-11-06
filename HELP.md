# Spark saving to Ignite using JDBC Thin Driver

## Usage
* Start a Ignite Server Node by runing `nl/hlopez/ignitesparkjdbc/server/ServerApplication.kt`
* Start the spark application that will save to Ignite `nl/hlopez/ignitesparkjdbc/spark/SparkApplication.kt`

### Code
The important code is in the class `nl/hlopez/ignitesparkjdbc/spark/component/WriteToJdbc.kt`
Here we see that the method `writeUsingSpringConfig(df)` works as expected, but when trying to write using
JDBC Driver `writeUsingJdbc(df)` (**IgniteJdbcThinDriver**) it fails with exception `java.sql.SQLException: No PRIMARY KEY defined for CREATE TABLE`.