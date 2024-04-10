package co.vgw.chu.ref.arch.dynamo.example.config

import co.vgw.chu.ref.arch.dynamo.example.adapters.db.AltMusicRepo
import co.vgw.chu.ref.arch.dynamo.example.adapters.db.InMemoryMusicRepo
import co.vgw.chu.ref.arch.dynamo.example.adapters.db.MusicRepo
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class ApplicationConfiguration {

    @Bean
    fun musicRepo(): MusicRepo = AltMusicRepo()
}