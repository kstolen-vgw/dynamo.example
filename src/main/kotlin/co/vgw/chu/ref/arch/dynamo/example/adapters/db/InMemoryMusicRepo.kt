package co.vgw.chu.ref.arch.dynamo.example.adapters.db


class InMemoryMusicRepo : MusicRepo {
    override suspend fun readArtist(artist: String): Song? {
        return Song(
            Artist = "inmemArtist",
            SongTitle = "inmemSong"
        )
    }

    override suspend fun insert(artist: String, song: String) {
        TODO("Not yet implemented")
    }
}
