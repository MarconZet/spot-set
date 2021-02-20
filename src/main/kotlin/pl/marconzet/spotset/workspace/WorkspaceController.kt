package pl.marconzet.spotset.workspace

import org.springframework.security.core.Authentication
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import pl.marconzet.spotset.data.dto.QueryDTO
import pl.marconzet.spotset.data.dto.WorkspaceDTO
import pl.marconzet.spotset.exception.InterpretationException
import pl.marconzet.spotset.exception.WrongPrincipalException
import pl.marconzet.spotset.logger
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
        val dto = workspaceService.getWorkspace(authentication)
        model.addAttribute("workspace", dto)
        model.addAttribute("form", QueryDTO())
        return "workspace"
    }

    @PostMapping("query")
    fun query(
        authentication: Authentication,
        @ModelAttribute("form") queryDTO: QueryDTO,
        errors: BindingResult,
        model: Model
    ): String {
        logger.info(queryDTO.query)
        if (errors.hasErrors()) {
            val message = errors.allErrors.mapNotNull { it.defaultMessage }.first()
            model.addAttribute("form_error", message)
        } else {
            val query = queryDTO.query ?: ""
            try {
                val res = queryService.processQuery(query)
                model.addAttribute("result", res)
                return "result"
            } catch (ex: InterpretationException) {
                model.addAttribute("form_error", ex.message)
            }
        }
//        model.addAttribute("form", queryDTO)
        model.addAttribute("workspace", workspaceService.getWorkspace(authentication))
        return "workspace"

    }
}
