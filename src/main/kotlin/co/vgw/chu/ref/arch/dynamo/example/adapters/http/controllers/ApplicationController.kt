package co.vgw.chu.ref.arch.dynamo.example.adapters.http.controllers

import co.vgw.chu.ref.arch.dynamo.example.adapters.db.MusicRepo
import co.vgw.chu.ref.arch.dynamo.example.adapters.db.Song
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping
class ApplicationController(
    val music: MusicRepo
) {

    @GetMapping("/healthcheck")
    fun healthcheck(): ResponseEntity<String> {
       return ResponseEntity.ok("HELLO \uD83D\uDC4B")
    }

    @GetMapping("/find/artist/{name}")
    suspend fun findArtist(@PathVariable name: String): ResponseEntity<Song> {
       var artistFound = music.readArtist(name) ?: return ResponseEntity.notFound().build()

        return ResponseEntity.ok(artistFound)
    }

    @PostMapping("/artist/{artist}/{song}")
    suspend fun addArtist(@PathVariable artist: String, @PathVariable song: String): ResponseEntity<Boolean> {
        music.insert(artist, song)

        return ResponseEntity.ok(true)
    }
}