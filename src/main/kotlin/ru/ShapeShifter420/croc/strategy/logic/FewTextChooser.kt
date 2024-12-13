package ru.ShapeShifter420.croc.strategy.logic

import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.message.Message
import ru.ShapeShifter420.croc.enums.StepCode
import ru.ShapeShifter420.croc.repository.UsersRepository

@Component
class FewTextChooser(private val usersRepository: UsersRepository) : MessageChooser {

    override fun isAvailableForCurrentStep(chatId: Long): Boolean {
        return usersRepository.getUser(chatId)!!.stepCode == StepCode.USER_INFO.toString()
    }

    override fun isPermitted(chatId: Long): Boolean {
        return true
    }

    override fun execute(chatId: Long, message: Message) {
        usersRepository.updateText(chatId, message.text)
    }
}