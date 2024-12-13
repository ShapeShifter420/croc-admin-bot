package ru.ShapeShifter420.croc.strategy.logic

import org.telegram.telegrambots.meta.api.objects.CallbackQuery
import ru.ShapeShifter420.croc.enums.ExecuteStatus

interface CallbackChooser : Chooser {
    fun execute(chatId: Long, callbackQuery: CallbackQuery): ExecuteStatus
}