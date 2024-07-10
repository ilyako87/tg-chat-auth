package net.jakot.demo.tgchatbackend.bot

import net.jakot.demo.tgchatbackend.config.CHAT_ID_HEADER
import net.jakot.demo.tgchatbackend.dto.Order
import net.jakot.demo.tgchatbackend.service.AppService
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.exchange

@Service
class TgMessagingService(
    private val appService: AppService,
    private val resourceServerClient: RestTemplate
) {

    fun getLoginLinkMsg(chatId: Long): String {
        val loginLink = appService.getLoginLink(chatId)
        return "Use the following link to log in: <a href='$loginLink'>$loginLink</a>"
    }

    fun getResourceInfoMsg(chatId: Long): String {
        val headers = HttpHeaders()
        headers[CHAT_ID_HEADER] = "$chatId"
        val requestEntity = HttpEntity<Unit>(headers)
        resourceServerClient.exchange<List<Order>>("/orders", HttpMethod.GET, requestEntity).body?.let { orders ->
            println(orders.toString())
            return "Your orders:\n${orders.joinToString(separator = "\n") {"• ${it.name}(${it.id}): ${it.total}"}}"
        }
        return getLoginLinkMsg(chatId)
    }


}