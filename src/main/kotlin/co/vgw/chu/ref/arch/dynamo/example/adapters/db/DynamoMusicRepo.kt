package co.vgw.chu.ref.arch.dynamo.example.adapters.db

import aws.sdk.kotlin.runtime.auth.credentials.StaticCredentialsProvider
import aws.sdk.kotlin.services.dynamodb.DynamoDbClient
import aws.sdk.kotlin.services.dynamodb.model.*
import aws.smithy.kotlin.runtime.auth.awscredentials.Credentials
import aws.smithy.kotlin.runtime.net.url.Url
import kotlinx.coroutines.runBlocking

class DynamoMusicRepo(private val dynamoDbClient: DynamoDbClient) : MusicRepo {
    override suspend fun readArtist(artist: String): Boolean {

        dynamoDbClient.query(
            QueryRequest {
                tableName = "Music"
                scanIndexForward = false
                limit = 1
            }
        )

        return true
    }

    override suspend fun insert(artist: String, song: String) {
    }

}