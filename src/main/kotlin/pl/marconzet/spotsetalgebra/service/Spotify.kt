package pl.marconzet.spotsetalgebra.service

import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken
import org.springframework.stereotype.Service
import org.springframework.web.context.annotation.RequestScope
import kotlin.RuntimeException

@Service
@RequestScope
class Spotify(authorizedClientService: OAuth2AuthorizedClientService){
    private val baseUrl = "https://api.spotify.com/v1"

    private fun getAccessToken(authorizedClientService: OAuth2AuthorizedClientService) =
        try {
            val authentication = SecurityContextHolder.getContext().authentication
            val token: OAuth2AuthenticationToken = authentication as OAuth2AuthenticationToken
            val client = authorizedClientService.loadAuthorizedClient<OAuth2AuthorizedClient>(
                token.authorizedClientRegistrationId,
                token.name
            )
            client.accessToken.tokenValue
        } catch (e: Throwable) {
            null
        }

    val binding = ApiBinding(getAccessToken(authorizedClientService))

    fun getProfile() : String{
        return binding.restTemplate.getForObject("$baseUrl/me", String::class.java) ?: throw RuntimeException()
    }
}