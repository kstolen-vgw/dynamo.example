package co.vgw.chu.ref.arch.dynamo.example.adapters.db

data class Song(
    val Artist: String,
    val SongTitle: String,
)

interface MusicRepo {
    suspend fun readArtist(artist: String): Song?

    suspend fun insert(artist: String, song: String): Unit
}