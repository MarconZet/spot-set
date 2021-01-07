package pl.marconzet.spotset.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import pl.marconzet.spotset.service.Spotify


@RestController
class WorkspaceController(
    private val spotify: Spotify
) {

    @GetMapping("/workspace")
    fun workspace2(): String {
        return spotify.getProfile()
    }
}
