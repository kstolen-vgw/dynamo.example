package co.vgw.chu.ref.arch.dynamo.example.adapters.db

class AltMusicRepo: MusicRepo {
    override suspend fun findSong(artist: String, song: String): Song? {

        return Song(
            Artist = "altArtist",
            SongTitle = "altSong"
        )
    }

    override suspend fun findSongs(artist: String): List<Song> {
        TODO("Not yet implemented")
    }

    override suspend fun insert(artist: String, song: String) {
        TODO("Not yet implemented")
    }
}
