package co.vgw.chu.ref.arch.dynamo.example.adapters.http.controllers

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping
class ApplicationController {

    @GetMapping("/healthcheck")
    fun healthcheck(): ResponseEntity<String> {
       return ResponseEntity.ok("HELLO \uD83D\uDC4B")
    }
}