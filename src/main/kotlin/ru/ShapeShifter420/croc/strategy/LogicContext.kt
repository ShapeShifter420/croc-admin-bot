package ru.ShapeShifter420.croc.strategy

import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.CallbackQuery
import org.telegram.telegrambots.meta.api.objects.MessageEntity
import org.telegram.telegrambots.meta.api.objects.message.Message
import ru.ShapeShifter420.croc.enums.ExecuteStatus
import ru.ShapeShifter420.croc.strategy.logic.CallbackChooser
import ru.ShapeShifter420.croc.strategy.logic.Chooser
import ru.ShapeShifter420.croc.strategy.logic.MessageChooser

@Component
class LogicContext(private val chooser: List<Chooser>) {

    fun execute(chatId: Long, message: Message) {
        chooser.filter { it.isAvailableForCurrentStep(chatId) }
            .filter { it.isPermitted(chatId) }
            .forEach {
                (it as MessageChooser).execute(chatId = chatId, message = message)
            }
    }

    fun execute(chatId: Long, callbackQuery: CallbackQuery): ExecuteStatus {
        return chooser
            .filter { it.isAvailableForCurrentStep(chatId) }
            .filter { it.isPermitted(chatId) }
            .map { (it as CallbackChooser).execute(chatId = chatId, callbackQuery = callbackQuery) }
            .first()
    }
}
