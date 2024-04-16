package co.vgw.chu.ref.arch.dynamo.example.adapters.db

import aws.sdk.kotlin.services.dynamodb.DynamoDbClient
import aws.sdk.kotlin.services.dynamodb.model.AttributeValue
import aws.sdk.kotlin.services.dynamodb.model.QueryRequest
import aws.sdk.kotlin.services.dynamodb.model.TransactWriteItem
import aws.sdk.kotlin.services.dynamodb.transactWriteItems


class DynamoMusicRepo(private val dynamoDbClient: DynamoDbClient) : MusicRepo {

    override suspend fun findSong(artist: String, song: String): Song? {
        val result = dynamoDbClient.query(
            QueryRequest {
                tableName = "Music"
                scanIndexForward = false
                limit = 1
                keyConditionExpression = "Artist = :artist AND SongTitle = :title"
                expressionAttributeValues = mapOf(":artist" to AttributeValue.S(artist), ":title" to AttributeValue.S(song))
            }
        )

        val items = result.items
        if (items.isNullOrEmpty()) {
            println("result is ${if (items == null) "null" else "empty"}")
            return null
        }

        return items[0].let { AttributeValue.M(it).toSong() }
    }

    override suspend fun findSongs(artist: String): List<Song> {
        val result = dynamoDbClient.query(
            QueryRequest {
                tableName = "Music"
                scanIndexForward = false
                keyConditionExpression = "Artist = :name"
                expressionAttributeValues = mapOf(":name" to AttributeValue.S(artist))
            }
        )

        val items = result.items
        if (items.isNullOrEmpty()) {
            println("result is ${if (items == null) "null" else "empty"}")
            return emptyList()
        }

        return items.map { AttributeValue.M(it).toSong() }
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