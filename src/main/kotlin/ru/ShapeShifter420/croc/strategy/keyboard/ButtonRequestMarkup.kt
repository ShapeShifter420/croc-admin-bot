package ru.ShapeShifter420.croc.strategy.keyboard

import org.springframework.stereotype.Component
import ru.ShapeShifter420.croc.component.MessageWriter
import ru.ShapeShifter420.croc.dto.MarkupDataDto
import ru.ShapeShifter420.croc.dto.markup.ButtonRequestDto
import ru.ShapeShifter420.croc.dto.markup.DataModel
import ru.ShapeShifter420.croc.enums.StepCode
import ru.ShapeShifter420.croc.repository.UsersRepository

@Component
class ButtonRequestMarkup<T : DataModel>(
    private val usersRepository: UsersRepository,
    private val messageWriter: MessageWriter,
) : InlineKeyboardMarkup<ButtonRequestDto> {

    override fun isAvailableForCurrentStep(chatId: Long): Boolean {
        return usersRepository.getUser(chatId)!!.stepCode == StepCode.BUTTON_REQUEST.toString()
    }

    override fun message(chatId: Long, data: ButtonRequestDto?): String {
        if (data == null) {
            throw IllegalStateException("Not Yet Supported")
        }

        return messageWriter.process(StepCode.BUTTON_REQUEST)
    }

    override fun inlineButtons(chatId: Long, data: ButtonRequestDto?): List<MarkupDataDto> {
        val accept = data!!.accept
        return listOf(MarkupDataDto(0, accept.first()), MarkupDataDto(1, accept.last()))
    }

    override fun getData(chatId: Long): ButtonRequestDto {
        return ButtonRequestDto(listOf("YES", "NO"))
    }

}