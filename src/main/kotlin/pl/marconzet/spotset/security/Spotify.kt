package pl.marconzet.spotset.security

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonTypeRef
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpMethod
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken
import org.springframework.stereotype.Service
import org.springframework.web.context.annotation.RequestScope
import pl.marconzet.spotset.configuration.ApiBinding
import pl.marconzet.spotset.configuration.SpotifyConfig
import pl.marconzet.spotset.data.api.PlaylistPaging
import pl.marconzet.spotset.data.api.PlaylistSimple
import pl.marconzet.spotset.data.api.SpotifyUserPrivate
import pl.marconzet.spotset.logger

@Service
@RequestScope
class Spotify(
    authorizedClientService: OAuth2AuthorizedClientService,
    spotifyConfig: SpotifyConfig
) {
    val logger = logger()

    private val authentication = SecurityContextHolder.getContext().authentication as OAuth2AuthenticationToken
    val principal = authentication.principal as SpotifyOAuth2User

    private val baseUrl = spotifyConfig.baseUrl
    private val restTemplate = ApiBinding(getAccessToken(authorizedClientService)).restTemplate

    fun getProfile(): SpotifyUserPrivate {
        return restTemplate.getForObject("$baseUrl/me", SpotifyUserPrivate::class.java) ?: throw RuntimeException()
    }

    fun getPlaylists(): List<PlaylistSimple> {
//        logger.info(restTemplate.getForObject("$baseUrl/me/playlists", String::class.java))
        return restTemplate.getForObject("$baseUrl/me/playlists", PlaylistPaging::class.java)?.items
            ?: throw RuntimeException()
    }

    private fun getAccessToken(authorizedClientService: OAuth2AuthorizedClientService) =
        authorizedClientService.loadAuthorizedClient<OAuth2AuthorizedClient>(
            authentication.authorizedClientRegistrationId,
            authentication.name
        ).accessToken.tokenValue
}