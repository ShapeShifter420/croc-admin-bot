package ru.ShapeShifter420.croc.strategy


import org.springframework.stereotype.Component
import ru.ShapeShifter420.croc.dto.InlineKeyboardMarkupDto
import ru.ShapeShifter420.croc.dto.markup.DataModel
import ru.ShapeShifter420.croc.strategy.keyboard.InlineKeyboardMarkup

@Component
class MarkupContext<T : DataModel>(private val inlineKeyboardMarkup: List<InlineKeyboardMarkup<T>>) {

    fun getInlineKeyboardMarkupDto(
        chatId: Long
    ): InlineKeyboardMarkupDto? {
        return inlineKeyboardMarkup
            .firstOrNull { it.isAvailableForCurrentStep(chatId) }
            ?.let {
                val data = it.getData(chatId)
                InlineKeyboardMarkupDto(
                    it.message(chatId, data),
                    it.inlineButtons(chatId, data)
                )
            }
    }
}
