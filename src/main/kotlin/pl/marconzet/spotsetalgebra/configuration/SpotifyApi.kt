package pl.marconzet.spotsetalgebra.configuration

import kotlin.RuntimeException

open class SpotifyApi(accessToken: String?) : ApiBinding(accessToken) {
    private val baseUrl = "https://api.spotify.com/v1"

    fun getProfile() : String{
        return restTemplate.getForObject("$baseUrl/me", String::class.java) ?: throw RuntimeException()
    }
}