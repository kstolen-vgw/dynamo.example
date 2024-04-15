package co.vgw.chu.ref.arch.dynamo.example.config

import aws.sdk.kotlin.runtime.auth.credentials.StaticCredentialsProvider
import aws.sdk.kotlin.services.dynamodb.DynamoDbClient
import aws.smithy.kotlin.runtime.auth.awscredentials.Credentials
import aws.smithy.kotlin.runtime.net.url.Url
import co.vgw.chu.ref.arch.dynamo.example.adapters.db.DynamoMusicRepo
import co.vgw.chu.ref.arch.dynamo.example.adapters.db.MusicRepo
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class ApplicationConfiguration {

    @Bean
    fun musicRepo(): MusicRepo {

        val dynamoDbClient = DynamoDbClient {
            region = "localhost"
            endpointUrl = Url.parse("http://localhost:8000")
            credentialsProvider = StaticCredentialsProvider(
                Credentials(
                    accessKeyId = "Id",
                    secretAccessKey = "key"
                )
            )
        }

        return DynamoMusicRepo(dynamoDbClient)
    }
}