package ru.ShapeShifter420.croc.event

import ru.ShapeShifter420.croc.enums.StepCode

class TelegramStepMessageEvent(
    val chatId: Long,
    val stepCode: StepCode
)
