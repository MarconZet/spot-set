package pl.marconzet.spotsetalgebra.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import pl.marconzet.spotsetalgebra.service.Spotify


@RestController
class WorkspaceController(
    private val spotify: Spotify
) {

    @GetMapping("/workspace")
    fun workspace2(): String {
        return spotify.getProfile()
    }

//    @GetMapping("/workspace")
//    fun workspace(@RegisteredOAuth2AuthorizedClient("spotify") authorizedClient: OAuth2AuthorizedClient): String {
//        val accessToken = authorizedClient.accessToken.tokenValue ?: throw RuntimeException()
//        val rest = Spotify(accessToken)
//        return rest.getProfile()
//    }
}
