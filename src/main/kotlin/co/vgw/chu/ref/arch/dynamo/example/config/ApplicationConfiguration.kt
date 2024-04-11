package co.vgw.chu.ref.arch.dynamo.example.config

import co.vgw.chu.ref.arch.dynamo.example.adapters.db.AltMusicRepo
import co.vgw.chu.ref.arch.dynamo.example.adapters.db.DynamoMusicCrud
import co.vgw.chu.ref.arch.dynamo.example.adapters.db.DynamoMusicRepo
import co.vgw.chu.ref.arch.dynamo.example.adapters.db.InMemoryMusicRepo
import co.vgw.chu.ref.arch.dynamo.example.adapters.db.MusicRepo
import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.client.builder.AwsClientBuilder
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig
import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary


@Configuration
@EnableDynamoDBRepositories(basePackages = ["co.vgw.chu.ref.arch.dynamo.example.adapters.db"])
class ApplicationConfiguration {

    @Bean
    fun musicRepo(crud: DynamoMusicCrud): MusicRepo {
        return DynamoMusicRepo(crud)
    }

    @Primary
    @Bean
    fun dynamoDBMapper(dynamoDB: AmazonDynamoDB): DynamoDBMapper {
        return DynamoDBMapper(dynamoDB, DynamoDBMapperConfig.DEFAULT)
    }

    @Bean
    fun amazonDynamoDB(): AmazonDynamoDB {
        val builder = AmazonDynamoDBClientBuilder.standard()

        builder
            .withEndpointConfiguration(
            AwsClientBuilder.EndpointConfiguration("http://dynamo-db:8000","ap-northeast-2")
        )
            .withCredentials(AWSStaticCredentialsProvider(BasicAWSCredentials("hello", "world")))

        return builder.build()
    }

    @Bean
    fun awsCredentials() = BasicAWSCredentials("hello" ,"world")
}