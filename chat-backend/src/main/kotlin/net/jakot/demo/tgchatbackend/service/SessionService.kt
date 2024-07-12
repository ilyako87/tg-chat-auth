package net.jakot.demo.tgchatbackend.service

import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service

@Service
class SessionService {
    private val sessions = mutableMapOf<String, Authentication>()

    fun saveChatAuthSession(it: String, authentication: Authentication) {
        sessions[it] = authentication
    }

    fun getChatAuthSession(it: String): Authentication? = sessions[it]
}