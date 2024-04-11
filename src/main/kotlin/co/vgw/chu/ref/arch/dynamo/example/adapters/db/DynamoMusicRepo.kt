package co.vgw.chu.ref.arch.dynamo.example.adapters.db

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable
import org.socialsignin.spring.data.dynamodb.repository.EnableScan
import org.springframework.data.repository.CrudRepository

@DynamoDBTable(tableName = "Music")
class Music(
    @field:DynamoDBHashKey(attributeName = "Artist")
    var Artist: String = "",

    @field:DynamoDBAttribute(attributeName = "SongTitle")
    var songTitle: String = ""
)

@EnableScan
interface DynamoMusicCrud : CrudRepository<Music, String> {
    fun getByArtist(artist: String): List<Music>
}
class DynamoMusicRepo(val crud: DynamoMusicCrud) : MusicRepo {
    override suspend fun readArtist(artist: String): Boolean {

        val result = crud.getByArtist(artist)

        return result.isEmpty()
    }

    override suspend fun insert(artist: String, song: String) {
        crud.save(Music(artist, song))
    }

}