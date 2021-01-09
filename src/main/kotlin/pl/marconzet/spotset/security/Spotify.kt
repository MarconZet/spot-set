package pl.marconzet.spotset.security

import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken
import org.springframework.stereotype.Service
import org.springframework.web.context.annotation.RequestScope
import pl.marconzet.spotset.configuration.SpotifyConfig

@Service
@RequestScope
class Spotify(
    authorizedClientService: OAuth2AuthorizedClientService,
    spotifyConfig: SpotifyConfig
) {
    private val authentication = SecurityContextHolder.getContext().authentication as OAuth2AuthenticationToken
    val principal = authentication.principal as SpotifyOAuth2User

    private val baseUrl = spotifyConfig.baseUrl
    private val restTemplate = ApiBinding(getAccessToken(authorizedClientService)).restTemplate

    fun getProfile(): String {
        return restTemplate.getForObject("$baseUrl/me", String::class.java) ?: throw RuntimeException()
    }

    private fun getAccessToken(authorizedClientService: OAuth2AuthorizedClientService) =
        authorizedClientService.loadAuthorizedClient<OAuth2AuthorizedClient>(
            authentication.authorizedClientRegistrationId,
            authentication.name
        ).accessToken.tokenValue
}