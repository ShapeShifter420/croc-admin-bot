package ru.ShapeShifter420.croc.strategy.keyboard

import ru.ShapeShifter420.croc.dto.MarkupDataDto
import ru.ShapeShifter420.croc.dto.markup.DataModel

interface InlineKeyboardMarkup<T: DataModel> {

    fun isAvailableForCurrentStep(chatId: Long): Boolean

    fun message(chatId: Long, data: T?): String

    fun inlineButtons(chatId: Long, data: T?): List<MarkupDataDto>

    fun getData(chatId: Long): T?
}
