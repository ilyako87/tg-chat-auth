package net.jakot.demo.tgchatbackend.bot

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.objects.Update

@Component
class TGBot(
    @Value("\${app.tg-bot.token}") val token: String,
    @Value("\${app.tg-bot.name}") val botName: String,
    val messagingService: TgMessagingService
) : TelegramLongPollingBot(token) {

    override fun onUpdateReceived(update: Update?) {
        update?.let { upd ->
            upd.message?.let { msg ->
                when (msg.text) {
                    "/start" -> {
                        message(msg.chatId, greeting(msg.from.firstName, msg.from.lastName)).let {
                            this.execute(it)
                        }
                    }

                    "/login" -> {
                        message(msg.chatId, messagingService.getLoginLinkMsg(msg.chatId)).let {
                            this.execute(it)
                        }
                    }

                    "/getresource" -> {
                        message(msg.chatId, messagingService.getResourceInfoMsg(msg.chatId)).let {
                            this.execute(it)
                        }
                    }

                    "/support" -> {
                        message(msg.chatId, SUPPORT_PROMPT).let {
                            this.execute(it)
                        }
                    }

                    else -> {
                        message(msg.chatId, UNKNOWN_PROMPT).let {
                            this.execute(it)
                        }
                    }
                }
            }
        }
    }

    override fun getBotUsername(): String {
        return botName
    }
}
