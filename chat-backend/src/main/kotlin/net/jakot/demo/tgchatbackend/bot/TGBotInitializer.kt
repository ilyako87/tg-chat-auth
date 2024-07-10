package net.jakot.demo.tgchatbackend.bot

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.context.event.EventListener
import org.telegram.telegrambots.meta.TelegramBotsApi
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession

@Configuration
class TGBotInitializer(@Value("\${app.tg-bot.enabled}") val botEnabled: Boolean,
    val tgBot: TGBot) {

    @EventListener(ContextRefreshedEvent::class)
    fun registerTelegramBot(event: ContextRefreshedEvent) {
        if (botEnabled) {
            TelegramBotsApi(DefaultBotSession::class.java).registerBot(tgBot)
            initializeBot()
        }
    }

    private fun initializeBot() {
        val commands = ArrayList<BotCommand>()
        commands.add(BotCommand("start", "Start bot"))
        commands.add(BotCommand("login", "Sign In"))
        commands.add(BotCommand("getresource", "Get protected data from resource server"))
        commands.add(BotCommand("support", "Support the project"))
        tgBot.execute(SetMyCommands(commands, BotCommandScopeDefault(), null))
    }

}