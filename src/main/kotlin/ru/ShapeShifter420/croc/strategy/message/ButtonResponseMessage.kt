package ru.ShapeShifter420.croc.strategy.message

import org.springframework.stereotype.Component
import ru.ShapeShifter420.croc.component.MessageWriter
import ru.ShapeShifter420.croc.dto.ButtonResponseDto
import ru.ShapeShifter420.croc.dto.UserInfoDto
import ru.ShapeShifter420.croc.enums.StepCode
import ru.ShapeShifter420.croc.repository.UsersRepository

@Component
class ButtonResponseMessage(
    private val usersRepository: UsersRepository,
    private val messageWriter: MessageWriter
) : Message {

    override fun isAvailableForCurrentStep(chatId: Long): Boolean {
        return usersRepository.getUser(chatId)!!.stepCode == StepCode.BUTTON_RESPONSE.toString()
    }

    override fun getMessage(chatId: Long): String {
        val user = usersRepository.getUser(chatId)
        return messageWriter.process(
            StepCode.BUTTON_RESPONSE,
            ButtonResponseDto(chatId = chatId, text = user?.text, accept = user?.accept)
        )
    }
}