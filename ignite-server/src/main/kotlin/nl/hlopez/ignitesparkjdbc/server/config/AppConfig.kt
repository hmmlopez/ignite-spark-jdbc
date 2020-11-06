package nl.hlopez.ignitesparkjdbc.server.config

import org.apache.ignite.IgniteSpringBean
import org.apache.ignite.configuration.IgniteConfiguration
import org.apache.ignite.logger.slf4j.Slf4jLogger
import org.apache.ignite.spi.discovery.tcp.TcpDiscoverySpi
import org.apache.ignite.spi.discovery.tcp.ipfinder.vm.TcpDiscoveryVmIpFinder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AppConfig {

    init {
        System.setProperty("IGNITE_QUIET", "false")
        System.setProperty("IGNITE_UPDATE_NOTIFIER", "false")
        System.setProperty("java.net.preferIPv4Stack", "true")
    }

    @Bean
    fun ignite(): IgniteSpringBean {
        val igniteSpringBean = IgniteSpringBean()
        igniteSpringBean.configuration = IgniteConfiguration().apply {
            metricsLogFrequency = 0
            gridLogger = Slf4jLogger()
            discoverySpi = TcpDiscoverySpi().apply {
                localAddress = "localhost"
                localPort = 47500
                ipFinder = TcpDiscoveryVmIpFinder().apply {
                    setAddresses(listOf("localhost:47500"))
                }
            }
        }
        return igniteSpringBean
    }
}