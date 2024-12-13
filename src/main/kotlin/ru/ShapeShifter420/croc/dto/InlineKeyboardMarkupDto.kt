package ru.ShapeShifter420.croc.dto

data class InlineKeyboardMarkupDto(
    /** Сообщение */
    val message: String,
    /** Кнопки под сообщением */
    val inlineButtons: List<MarkupDataDto>
)
