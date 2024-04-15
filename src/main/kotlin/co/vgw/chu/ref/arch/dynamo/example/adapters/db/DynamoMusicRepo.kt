package co.vgw.chu.ref.arch.dynamo.example.adapters.db

import aws.sdk.kotlin.services.dynamodb.DynamoDbClient
import aws.sdk.kotlin.services.dynamodb.model.AttributeValue
import aws.sdk.kotlin.services.dynamodb.model.QueryRequest
import aws.sdk.kotlin.services.dynamodb.model.TransactWriteItem
import aws.sdk.kotlin.services.dynamodb.model.TransactWriteItemsRequest
import aws.sdk.kotlin.services.dynamodb.transactWriteItems


class DynamoMusicRepo(private val dynamoDbClient: DynamoDbClient) : MusicRepo {
    override suspend fun readArtist(artist: String): Song? {

        val result = dynamoDbClient.query(
            QueryRequest {
                tableName = "Music"
                scanIndexForward = false
                limit = 1
                keyConditionExpression = "Artist = :name"
                expressionAttributeValues = mapOf(":name" to AttributeValue.S(artist))
            }
        )

        val items = result.items
        if (items.isNullOrEmpty()) {
            println("result is ${if (items == null) "null" else "empty"}")
            return null
        }

        val song =  items[0].let { AttributeValue.M(it)}.toSong()

        println(song)

        return song
    }

    private fun AttributeValue.toSong(): Song {
        val entryMap = this.asM()

        return Song(
            Artist = entryMap.getValue("Artist").asS(),
            SongTitle = entryMap.getValue("SongTitle").asS()
        )
     }

    override suspend fun insert(artist: String, song: String) {
        val row =
            mapOf(
                "Artist" to AttributeValue.S(artist),
                "SongTitle" to AttributeValue.S(song),
            )

        dynamoDbClient.transactWriteItems {
            transactItems =
                listOf(
                    TransactWriteItem {
                        put {
                            tableName = "Music"
                            item = row
                            conditionExpression = "attribute_not_exists(Artist) AND attribute_not_exists(SongTitle)"
                        }
                    }
                )
        }
    }

}