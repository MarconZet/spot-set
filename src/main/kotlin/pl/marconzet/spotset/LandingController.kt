package pl.marconzet.spotset

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import pl.marconzet.spotset.exception.SpotifyApiException
import pl.marconzet.spotset.webapi.RestTemplateErrorHandler
import pl.marconzet.spotset.webapi.api.Error
import pl.marconzet.spotset.webapi.api.ErrorResponse

@Controller
class LandingController {

    @GetMapping("/")
    fun landing(model: Model): String {
        Thread.dumpStack()
        return "index"
    }
}