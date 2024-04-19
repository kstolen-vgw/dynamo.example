package co.vgw.chu.ref.arch.dynamo.example

import co.vgw.chu.ref.arch.dynamo.example.config.ApplicationConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(ApplicationConfiguration::class)
class Application

fun main(args: Array<String>) {
	runApplication<Application>(*args)
}
