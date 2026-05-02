package rs.neozoic.reservation.auth.api.controller

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
class LoginController {

    @GetMapping("/login")
    fun loginPage(
        @RequestParam(required = false) activated: String?,
        @RequestParam(required = false) error: String?,
        model: Model
    ): String {
        model.addAttribute("activated", activated != null)
        model.addAttribute("activationError", error == "activation_failed")
        model.addAttribute("activationExpired", error == "activation_expired")
        model.addAttribute("loginError", error == "true")
        return "login"
    }
}
