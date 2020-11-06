package nl.hlopez.ignitesparkjdbc.spark.config

import org.apache.spark.sql.Dataset
import org.apache.spark.sql.Row
import org.apache.spark.sql.SparkSession
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource

@Configuration
class AppConfig {
    init {
        System.setProperty("IGNITE_QUIET", "false")
        System.setProperty("IGNITE_UPDATE_NOTIFIER", "false")
        System.setProperty("java.net.preferIPv4Stack", "true")
    }

    @Bean
    fun spark(): SparkSession {
        return SparkSession.Builder().apply {
            appName("Ignite JDBC")
            master("local")
        }.orCreate
    }

    @Bean
    fun inputData(spark: SparkSession): Dataset<Row> {
        return spark.read().option("header", true).csv(ClassPathResource("name_and_comments.txt").file.absolutePath)
    }

    @Bean
    fun igniteConfig() : String {
        return ClassPathResource("ignite-config.xml").file.absolutePath
    }

}