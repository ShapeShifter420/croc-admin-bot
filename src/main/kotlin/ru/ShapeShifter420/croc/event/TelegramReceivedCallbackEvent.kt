package ru.ShapeShifter420.croc.event

import org.telegram.telegrambots.meta.api.objects.CallbackQuery
import ru.ShapeShifter420.croc.enums.StepCode

class TelegramReceivedCallbackEvent(
    val chatId: Long,
    val stepCode: StepCode,
    val callback: CallbackQuery
)
