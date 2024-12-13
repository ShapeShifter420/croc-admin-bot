package ru.ShapeShifter420.croc.strategy

import org.springframework.stereotype.Component
import ru.ShapeShifter420.croc.strategy.message.Message

@Component
class MessageContext(private val message: List<Message>) {

    fun getMessage(chatId: Long): String {
        return message
            .filter { it.isAvailableForCurrentStep(chatId) }
            .map { it.getMessage(chatId) }
            .first()
    }
}
