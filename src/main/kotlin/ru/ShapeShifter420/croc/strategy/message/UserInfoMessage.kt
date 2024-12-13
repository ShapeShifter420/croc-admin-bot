package ru.ShapeShifter420.croc.strategy.message

import org.springframework.stereotype.Component
import ru.ShapeShifter420.croc.component.MessageWriter
import ru.ShapeShifter420.croc.dto.UserInfoDto
import ru.ShapeShifter420.croc.enums.StepCode
import ru.ShapeShifter420.croc.repository.UsersRepository

@Component
class UserInfoMessage(
    private val usersRepository: UsersRepository,
    private val messageWriter: MessageWriter
) : Message {

    override fun isAvailableForCurrentStep(chatId: Long): Boolean {
        return usersRepository.getUser(chatId)!!.stepCode == StepCode.USER_INFO.toString()
    }

    override fun getMessage(chatId: Long): String {
        return messageWriter.process(StepCode.USER_INFO, UserInfoDto(chatId))
    }
}