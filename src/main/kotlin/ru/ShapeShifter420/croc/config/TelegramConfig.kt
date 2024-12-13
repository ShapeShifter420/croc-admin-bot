package ru.ShapeShifter420.croc.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient
import org.telegram.telegrambots.meta.generics.TelegramClient
import ru.ShapeShifter420.croc.properties.BotProperty

@Configuration
class TelegramConfig(private val botProperty: BotProperty) {
    @Bean
    fun telegramClient(): TelegramClient {
        return OkHttpTelegramClient(botProperty.token)
    }
}