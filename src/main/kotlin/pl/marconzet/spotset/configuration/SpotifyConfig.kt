package pl.marconzet.spotset.configuration

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource

@Configuration
@PropertySource("classpath:application.properties")
class SpotifyConfig {

    @Value("\${spotset.api.url}")
    lateinit var baseUrl: String
}