package co.vgw.chu.ref.arch.dynamo.example.config

import aws.sdk.kotlin.runtime.auth.credentials.StaticCredentialsProvider
import aws.sdk.kotlin.services.dynamodb.DynamoDbClient
import aws.smithy.kotlin.runtime.auth.awscredentials.Credentials
import aws.smithy.kotlin.runtime.net.url.Url
import co.vgw.chu.ref.arch.dynamo.example.adapters.db.DynamoMusicRepo
import co.vgw.chu.ref.arch.dynamo.example.adapters.db.MusicRepo
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

data class DynamoDatabase(
    val host: String,
    val region: String,
    val accessKey: String,
    val accessId: String,
)
data class Databases(
    val dynamo: DynamoDatabase
)
@Configuration
class ApplicationConfiguration {

    @ConfigurationProperties(prefix = "dynamo-api")
    data class Properties(
        val enabled: Boolean,
        val database: Databases
    )

    @Bean
    fun musicRepo(props: Properties): MusicRepo {
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
}