package co.vgw.chu.ref.arch.dynamo.example.adapters.http.controllers

import co.vgw.chu.ref.arch.dynamo.example.adapters.db.MusicRepo
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
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
    suspend fun findArtist(@PathVariable name: String): ResponseEntity<Boolean> {
       var artistFound = music.readArtist("")

        return ResponseEntity.ok(artistFound)
    }
}