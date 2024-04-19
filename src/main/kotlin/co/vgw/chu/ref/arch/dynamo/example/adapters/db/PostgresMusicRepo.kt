package co.vgw.chu.ref.arch.dynamo.example.adapters.db

import io.r2dbc.spi.ConnectionFactory
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.r2dbc.core.awaitRowsUpdated
import org.springframework.r2dbc.core.awaitSingleOrNull
import org.springframework.r2dbc.core.flow

class PostgresMusicRepo(val clientFactory: ConnectionFactory) : MusicRepo {
    override suspend fun findSong(artist: String, song: String): Song? {
        val client = DatabaseClient.create(clientFactory)

        val song = client.sql("SELECT artist, song_title FROM Music WHERE artist = :a AND song_title = :s")
            .bind("a", artist)
            .bind("s", song)
            .fetch().awaitSingleOrNull() ?: return null

        return song.let {it.toSong()}
    }

    override suspend fun findSongs(artist: String): List<Song> {
        val client = DatabaseClient.create(clientFactory)

     return client.sql("SELECT artist, song_title FROM Music WHERE artist = :a")
            .bind("a", artist).fetch().flow().map{it.toSong()}.toList()

    }

    override suspend fun songsBeginningWith(input: String): List<Song> {
        val client = DatabaseClient.create(clientFactory)

        return client.sql("SELECT artist, song_title FROM Music WHERE song_title LIKE :s")
            .bind("s", "$input%")
            .fetch().flow().map { it.toSong() }.toList()
    }

    override suspend fun insert(artist: String, song: String) {
        val client = DatabaseClient.create(clientFactory)

        val result = client.sql("INSERT INTO Music (artist, song_title) VALUES(:a, :s)")
            .bind("a", artist)
            .bind("s", song)
            .fetch().awaitRowsUpdated()

        println("rows updated: $result")
    }

    private fun MutableMap<String, Any>.toSong(): Song = Song(this["artist"].toString(), this["song_title"].toString())

}