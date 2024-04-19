package co.vgw.chu.ref.arch.dynamo.example.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("dynamo-api")
class ApplicationConfiguration(
    val enabled: Boolean,
    val database: Databases
)

data class DynamoDatabase(
    val host: String,
    val region: String,
    val accessKey: String,
    val accessId: String,
)
data class PostgresDatabase(
    val host: String,
    val port: Int,
    val database: String,
    val user: String,
    val password: String,
)
data class Databases(
    val dynamo: DynamoDatabase,
    val postgres: PostgresDatabase,
)

