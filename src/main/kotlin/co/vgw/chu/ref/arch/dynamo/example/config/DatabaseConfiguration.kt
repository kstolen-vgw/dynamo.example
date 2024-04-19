package co.vgw.chu.ref.arch.dynamo.example.config

import aws.sdk.kotlin.runtime.auth.credentials.StaticCredentialsProvider
import aws.sdk.kotlin.services.dynamodb.DynamoDbClient
import aws.smithy.kotlin.runtime.auth.awscredentials.Credentials
import aws.smithy.kotlin.runtime.net.url.Url
import co.vgw.chu.ref.arch.dynamo.example.adapters.db.DynamoMusicRepo
import co.vgw.chu.ref.arch.dynamo.example.adapters.db.MusicRepo
import co.vgw.chu.ref.arch.dynamo.example.adapters.db.PostgresMusicRepo
import io.r2dbc.spi.ConnectionFactories
import io.r2dbc.spi.ConnectionFactory
import io.r2dbc.spi.ConnectionFactoryOptions
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories
import org.springframework.r2dbc.core.DatabaseClient

@Configuration
class MusicDatabaseConfiguration(val config: ApplicationConfiguration) : AbstractR2dbcConfiguration() {

    @Bean
    override fun connectionFactory(): ConnectionFactory {
        val options = ConnectionFactoryOptions.builder()
            .option(ConnectionFactoryOptions.DRIVER, "postgresql")
            .option(ConnectionFactoryOptions.HOST, config.database.postgres.host)
            .option(ConnectionFactoryOptions.DATABASE, config.database.postgres.database)
            .option(ConnectionFactoryOptions.PORT, config.database.postgres.port)
            .option(ConnectionFactoryOptions.USER, config.database.postgres.user)
            .option(ConnectionFactoryOptions.PASSWORD, config.database.postgres.password)

        return ConnectionFactories.get(options.build())
    }

    fun dynamoMusicRepo(props: ApplicationConfiguration): DynamoMusicRepo {
        val dynamoDbClient = DynamoDbClient {
            region = props.database.dynamo.region
            endpointUrl = Url.parse(props.database.dynamo.host)
            credentialsProvider = StaticCredentialsProvider(
                Credentials(
                    accessKeyId = props.database.dynamo.accessId,
                    secretAccessKey = props.database.dynamo.accessKey
                )
            )
        }

        return DynamoMusicRepo(dynamoDbClient)
    }

    @Bean
    fun musicRepo(connectionFactory: ConnectionFactory): MusicRepo {

        return PostgresMusicRepo(connectionFactory)
    }
}