package co.vgw.chu.ref.arch.dynamo.example.adapters.db

data class Song(
    val Artist: String,
    val SongTitle: String,
)

interface MusicRepo {

    suspend fun findSong(artist: String, song: String): Song?
    suspend fun findSongs(artist: String): List<Song>

    suspend fun insert(artist: String, song: String): Unit
}