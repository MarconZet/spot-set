package pl.marconzet.spotsetalgebra.controller

import org.springframework.security.oauth2.client.OAuth2AuthorizedClient
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.client.HttpClientErrorException
import pl.marconzet.spotsetalgebra.configuration.SpotifyApi


@Controller
class WorkspaceController {
    @GetMapping("/")
    fun landing(model: Model) : String {
        return "index"
    }

    @GetMapping("/workspace")
    @ResponseBody
    fun workspace(@RegisteredOAuth2AuthorizedClient("spotify") authorizedClient: OAuth2AuthorizedClient): String {
        val accessToken = authorizedClient.accessToken.tokenValue ?: throw RuntimeException()
        val rest = SpotifyApi(accessToken)
        return rest.getProfile()
    }
}