package nl.hlopez.ignitesparkjdbc.spark.component

import org.apache.ignite.Ignition
import org.apache.ignite.cache.query.SqlFieldsQuery
import org.apache.ignite.spark.IgniteDataFrameSettings
import org.apache.spark.sql.Dataset
import org.apache.spark.sql.Row
import org.apache.spark.sql.SaveMode
import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import java.util.*

@Component
class WriteToJdbc(val df: Dataset<Row>, val igniteConfig: String) : CommandLineRunner {

    val logger = LoggerFactory.getLogger(javaClass)

    override fun run(vararg args: String?) {
        df.printSchema()
        df.show()

//        writeUsingSpringConfig(df)
        writeUsingJdbc(df)

        val ignite = Ignition.ignite()

        logger.info("-- Showing Data from Cache --")
        ignite.cache<String, String>(ignite.cacheNames().first())
                .query(SqlFieldsQuery("select * from comments"))
                .all
                .forEach { logger.info(it.toString()) }
        ignite.close()
    }

    private fun writeUsingJdbc(df: Dataset<Row>) {
        val prop = Properties()
        prop["driver"] = "org.apache.ignite.IgniteJdbcThinDriver"

        println("-- writing to ignite cluster using Jdbc --")
        df.write().apply {
            mode(SaveMode.Overwrite)
            option(IgniteDataFrameSettings.OPTION_CREATE_TABLE_PRIMARY_KEY_FIELDS(), "last_name")
        }.jdbc("jdbc:ignite:thin://127.0.0.1", "comments", prop)
    }

    private fun writeUsingSpringConfig(df: Dataset<Row>) {
        println("-- writing to ignite cluster using SpringConfig --")
        df.write().apply {
            mode(SaveMode.Overwrite)
            format(IgniteDataFrameSettings.FORMAT_IGNITE())
            option(IgniteDataFrameSettings.OPTION_CONFIG_FILE(), igniteConfig)
            option(IgniteDataFrameSettings.OPTION_TABLE(), "comments")
            option(IgniteDataFrameSettings.OPTION_CREATE_TABLE_PRIMARY_KEY_FIELDS(), "last_name")
        }.save()
    }

}