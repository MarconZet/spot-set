package pl.marconzet.spotset.webapi

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource

@Configuration
@PropertySource("classpath:application.properties")
class SpotifyConfig {

    val baseUrl: String = """https://api.spotify.com/v1"""

    val clientName: String = "spotify"
}