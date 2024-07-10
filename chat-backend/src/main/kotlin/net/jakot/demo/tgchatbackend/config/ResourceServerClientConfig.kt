package net.jakot.demo.tgchatbackend.config

import net.jakot.demo.tgchatbackend.service.SessionService
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.ClientHttpRequestInterceptor
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService
import org.springframework.web.client.RestTemplate

const val RESOURCE_SERVER_SUFFIX = "/api/v1/resource"
const val CHAT_ID_HEADER = "X-ChatId"

@Configuration
class ResourceServerClientConfig(
    @Value("\${app.server-url}") private val serverUrl: String,
    private val sessionService: SessionService,
    private val authorizedClientService: OAuth2AuthorizedClientService
) {

    @Bean
    fun resourceServerClient(): RestTemplate {
        val rest = RestTemplateBuilder().rootUri(serverUrl + RESOURCE_SERVER_SUFFIX).build()
        rest.interceptors.add(ClientHttpRequestInterceptor { request, body, execution ->
            request.headers[CHAT_ID_HEADER]?.first()?.let { chatId ->
                sessionService.getChatAuthSession(chatId)?.let { principal ->
                    val authorizedClient =
                        authorizedClientService.loadAuthorizedClient<OAuth2AuthorizedClient>("keycloak", principal.name)
                    request.headers.setBearerAuth(authorizedClient.accessToken.tokenValue)
                }
            }
            execution.execute(request, body)
        })
        return rest
    }
}