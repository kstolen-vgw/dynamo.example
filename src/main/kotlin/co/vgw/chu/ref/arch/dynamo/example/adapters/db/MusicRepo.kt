package co.vgw.chu.ref.arch.dynamo.example.adapters.db


interface MusicRepo {
    suspend fun readArtist(artist: String): Boolean

    suspend fun insert(artist: String, song: String): Unit
}