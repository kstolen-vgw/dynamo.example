package co.vgw.chu.ref.arch.dynamo.example.adapters.db

import org.springframework.stereotype.Component

class AltMusicRepo: MusicRepo {
    override suspend fun readArtist(artist: String): Boolean {
        return true
    }
}
