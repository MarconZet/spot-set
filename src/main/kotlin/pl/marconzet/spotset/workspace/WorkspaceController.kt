package pl.marconzet.spotset.workspace

import org.springframework.security.access.prepost.PostAuthorize
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import pl.marconzet.spotset.data.api.SpotifyUserPrivate
import pl.marconzet.spotset.data.api.SpotifyUserPublic
import pl.marconzet.spotset.exception.WrongPrincipalException
import pl.marconzet.spotset.logger
import pl.marconzet.spotset.security.Spotify
import pl.marconzet.spotset.security.SpotifyOAuth2User

@Controller
class WorkspaceController(
    private val workspaceService: WorkspaceService,
) {
    val logger = logger()

    @GetMapping("/workspace")
    fun workspace(authentication: Authentication, model: Model): String {
        val principal = authentication.principal
        if (principal !is SpotifyOAuth2User)
            throw WrongPrincipalException()
        val dto = workspaceService.getWorkspace(principal)
        model.addAttribute("user", dto)
        return "workspace"
    }
}
