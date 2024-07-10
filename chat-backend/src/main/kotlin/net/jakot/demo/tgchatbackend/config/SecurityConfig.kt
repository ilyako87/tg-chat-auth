package net.jakot.demo.tgchatbackend.config

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import net.jakot.demo.tgchatbackend.CHAT_ID_SESSION_PARAMETER
import net.jakot.demo.tgchatbackend.service.SessionService
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer
import org.springframework.security.core.Authentication
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler

@Configuration
@EnableWebSecurity
class SecurityConfig(
    @Value("\${app.tg-bot.id}") private val botId: String,
    private val sessionService: SessionService
) {


    @Bean
    @Throws(Exception::class)
    fun web(
        http: HttpSecurity
    ): SecurityFilterChain {

        http
            .csrf { obj: CsrfConfigurer<HttpSecurity> -> obj.disable() }
            .cors { obj: CorsConfigurer<HttpSecurity> -> obj.disable() }
            .oauth2Login { oauth2Login ->
                oauth2Login.loginPage("/login")
                oauth2Login.successHandler(sessionSavingSuccessHandler())
            }
            .authorizeHttpRequests { authorize ->
                authorize
                    .anyRequest().permitAll()
            }

        return http.build()
    }

    private fun sessionSavingSuccessHandler(): SavedRequestAwareAuthenticationSuccessHandler {
        val successHandler = object : SavedRequestAwareAuthenticationSuccessHandler() {
            override fun onAuthenticationSuccess(
                request: HttpServletRequest,
                response: HttpServletResponse,
                authentication: Authentication
            ) {
                (request.session.getAttribute(CHAT_ID_SESSION_PARAMETER) as String).let {
                    sessionService.saveChatAuthSession(it, authentication)
                }
                super.onAuthenticationSuccess(request, response, authentication)
            }

        }
        successHandler.setDefaultTargetUrl("https://t.me/${botId}")
        successHandler.setAlwaysUseDefaultTargetUrl(true)
        return successHandler
    }

}
