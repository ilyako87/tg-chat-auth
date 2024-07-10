package net.jakot.demo.tgchatbackend.bot

import org.telegram.telegrambots.meta.api.methods.ParseMode
import org.telegram.telegrambots.meta.api.methods.send.SendMessage

const val SUPPORT_PROMPT = "Support the project by following the link https://buymeacoffee.com/ilyako"
const val UNKNOWN_PROMPT = "Unknown command. Please select one of the commands from the menu!"

fun message(chatID: Long, msg: String): SendMessage {
    return SendMessage().apply {
        enableHtml(true)
        parseMode = ParseMode.HTML
        chatId = "$chatID"
        text = msg
    }
}

fun greeting(firstName: String, lastName: String?): String {
    return "Hi $firstName ${lastName ?: ""}. This is a demo of a telegram chat bot with the ability to authenticate and " +
            "perform an authorized request to the resource server. As the next step, sign in. /login"
}
