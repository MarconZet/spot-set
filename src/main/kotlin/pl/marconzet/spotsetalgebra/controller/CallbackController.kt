package pl.marconzet.spotsetalgebra.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@Controller
class CallbackController {

    @RequestMapping("/callback")
    @ResponseBody
    fun callback(): String{
        return "Good job"
    }

}