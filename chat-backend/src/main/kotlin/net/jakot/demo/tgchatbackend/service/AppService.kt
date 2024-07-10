package net.jakot.demo.tgchatbackend.service

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class AppService(
    @Value("\${app.server-url}") val serverUrl: String) {

    fun getLoginLink(chatId: Long): String {
        return "$serverUrl/login?chatId=$chatId"
    }
}