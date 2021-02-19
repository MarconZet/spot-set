package pl.marconzet.spotset.workspace

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import pl.marconzet.spotset.data.api.Track
import pl.marconzet.spotset.data.dto.QueryDTO
import pl.marconzet.spotset.exception.InterpretationException
import pl.marconzet.spotset.exception.WrongPrincipalException
import pl.marconzet.spotset.logger
import pl.marconzet.spotset.security.Spotify
import pl.marconzet.spotset.security.SpotifyOAuth2User

@Controller
@RequestMapping("/workspace")
class WorkspaceController(
    private val workspaceService: WorkspaceService,
    private val queryService: QueryService
) {
    val logger = logger()

    @GetMapping("")
    fun index(authentication: Authentication, model: Model): String {
        val principal = authentication.principal
        if (principal !is SpotifyOAuth2User)
            throw WrongPrincipalException()
        val dto = workspaceService.getWorkspace(principal)
        model.addAttribute("workspace", dto)
        model.addAttribute("form", QueryDTO())
        return "workspace"
    }

    @PostMapping("query")
    fun query(@ModelAttribute("form") queryDTO: QueryDTO, errors: BindingResult, model: Model): String {
        logger.info(queryDTO.query)
        if (errors.hasErrors()) {
            TODO()
        }
        val query = queryDTO.query ?: ""
        try {
            val res = queryService.processQuery(query)
            model.addAttribute("result", res)
        } catch (ex: InterpretationException) {
            TODO()
        }

        return "result"
    }
}
