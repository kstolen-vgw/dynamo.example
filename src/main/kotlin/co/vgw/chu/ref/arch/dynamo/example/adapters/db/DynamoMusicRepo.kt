package co.vgw.chu.ref.arch.dynamo.example.adapters.db

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIgnore
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable
import com.fasterxml.jackson.annotation.JsonIgnore
import org.socialsignin.spring.data.dynamodb.repository.EnableScan
import org.springframework.data.annotation.Id
import org.springframework.data.repository.CrudRepository
import java.io.Serializable

@DynamoDBDocument
class MusicId(
    @field:DynamoDBHashKey
    var Artist: String = "",

    @field:DynamoDBRangeKey
    var SongTitle: String = ""
): Serializable

@DynamoDBTable(tableName = "Music")
data class Music(
    @field:Id
    @field:DynamoDBIgnore
    @JsonIgnore
   val id: MusicId
) {
    @DynamoDBHashKey
    fun getArtist() = id.Artist

    fun setArtist(name: String) {
        id.Artist = name
    }

    @DynamoDBRangeKey
    fun getSongTitle() = id.SongTitle

    fun setSongTitle(name: String) {
        id.SongTitle = name
    }
}

@EnableScan
interface DynamoMusicCrud : CrudRepository<Music, MusicId> {
}
class DynamoMusicRepo(val crud: DynamoMusicCrud) : MusicRepo {
    override suspend fun readArtist(artist: String): Boolean {

        val result = crud.findAll().toList()

        return result.isEmpty()
    }

    override suspend fun insert(artist: String, song: String) {
        crud.save(Music(MusicId("testing", "hello")))
    }

}