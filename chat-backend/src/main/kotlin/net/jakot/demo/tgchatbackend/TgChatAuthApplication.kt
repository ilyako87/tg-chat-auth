package net.jakot.demo.tgchatbackend

import jakarta.servlet.http.HttpSession
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

const val CHAT_ID_SESSION_PARAMETER = "chatId"

@SpringBootApplication
class TgChatAuthApplication

fun main(args: Array<String>) {
    runApplication<TgChatAuthApplication>(*args)
}

@Controller
class TgChatAuthController {

    @RequestMapping("/login")
    fun index(@RequestParam(CHAT_ID_SESSION_PARAMETER) chatId: String, session: HttpSession): String {
        session.setAttribute(CHAT_ID_SESSION_PARAMETER, chatId)
        return "redirect:/oauth2/authorization/keycloak"
    }
}