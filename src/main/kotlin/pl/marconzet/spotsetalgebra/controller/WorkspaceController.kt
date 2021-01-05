package pl.marconzet.spotsetalgebra.controller

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import pl.marconzet.spotsetalgebra.data.dto.CallbackDTO

@Controller
class WorkspaceController {
    @GetMapping("/")
    fun landing(model: Model) : String {
        return "index"
    }

    @GetMapping("/workspace")
    @ResponseBody
    fun callback(): String {
        return "  "
    }
}