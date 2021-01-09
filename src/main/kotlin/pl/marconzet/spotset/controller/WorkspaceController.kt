package pl.marconzet.spotset.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import pl.marconzet.spotset.logger
import pl.marconzet.spotset.security.Spotify


@RestController
class WorkspaceController(
    private val spotify: Spotify
) {
    val logger = logger()

    @GetMapping("/workspace")
    fun workspace2(): String {
        return spotify.principal.name
    }
}
