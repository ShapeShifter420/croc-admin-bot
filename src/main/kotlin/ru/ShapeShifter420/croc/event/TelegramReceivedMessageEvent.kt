package ru.ShapeShifter420.croc.event

import org.telegram.telegrambots.meta.api.objects.message.Message
import ru.ShapeShifter420.croc.enums.StepCode

class TelegramReceivedMessageEvent(
    val chatId: Long,
    val stepCode: StepCode,
    val message: Message
)
