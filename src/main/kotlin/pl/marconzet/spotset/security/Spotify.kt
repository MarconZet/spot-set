package pl.marconzet.spotset.security

import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken
import org.springframework.stereotype.Service
import org.springframework.web.context.annotation.RequestScope
import org.springframework.web.util.UriComponentsBuilder
import pl.marconzet.spotset.configuration.ApiBinding
import pl.marconzet.spotset.configuration.SpotifyConfig
import pl.marconzet.spotset.data.api.*
import pl.marconzet.spotset.logger
import kotlin.reflect.KClass

@Service
@RequestScope
class Spotify(
    authorizedClientService: OAuth2AuthorizedClientService,
    spotifyConfig: SpotifyConfig
) {
    private val authentication = SecurityContextHolder.getContext().authentication as OAuth2AuthenticationToken
    private val principal = authentication.principal as SpotifyOAuth2User

    private val baseUrl = spotifyConfig.baseUrl
    private val restTemplate = ApiBinding(getAccessToken(authorizedClientService)).restTemplate

    private val market = principal.attributes["country"] as String

    fun getUserPlaylists(): List<Playlist> {
        val params = mapOf(
            "limit" to "50",
        )

        val uri = buildUri("$baseUrl/me/playlists", params)
        return acc(uri, UserPlaylistsPaging::class).flatMap { it.items }
    }

    fun getPlaylistsTracks(playlistId: String): List<Track> {
        val params = mapOf(
            "market" to market,
            "additional_types" to "track",
            "limit" to "100",
            "fields" to "items(track(name,id)),next"
        )

        val uri = buildUri("$baseUrl/playlists/$playlistId/tracks", params)
        return acc(uri, TrackListPaging::class).flatMap { it.items }.map { it.track }
    }

    fun getAllLikedSongs(): List<Track> {
        val params = mapOf(
            "limit" to "50",
        )

        val uri = buildUri("$baseUrl/me/tracks", params)
        return acc(uri, TrackListPaging::class).flatMap { it.items }.map { it.track }
    }

    private fun buildUri(baseUrl: String, params: Map<String, String>): String {
        return UriComponentsBuilder.fromUriString(baseUrl).apply {
            params.entries.forEach {
                queryParam(it.key, it.value)
            }
        }.toUriString()
    }

    private fun <T : Paging> acc(url: String, clazz: KClass<T>): List<T> {
        val res = restTemplate.getForObject(url, clazz.java)
            ?: throw RuntimeException()
        return listOf(res) + (res.next?.let { s -> acc(s, clazz) } ?: emptyList())
    }

    private fun getAccessToken(authorizedClientService: OAuth2AuthorizedClientService) =
        authorizedClientService.loadAuthorizedClient<OAuth2AuthorizedClient>(
            authentication.authorizedClientRegistrationId,
            authentication.name
        ).accessToken.tokenValue
}