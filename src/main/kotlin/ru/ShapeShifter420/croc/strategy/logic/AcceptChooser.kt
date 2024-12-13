package ru.ShapeShifter420.croc.strategy.logic

import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.CallbackQuery
import ru.ShapeShifter420.croc.enums.ExecuteStatus
import ru.ShapeShifter420.croc.enums.StepCode
import ru.ShapeShifter420.croc.repository.UsersRepository

@Component
class AcceptChooser(private val usersRepository: UsersRepository) : CallbackChooser {

    override fun execute(chatId: Long, callbackQuery: CallbackQuery): ExecuteStatus {
        usersRepository.updateAccept(chatId, callbackQuery.data)
        return ExecuteStatus.FINAL
    }

    override fun isAvailableForCurrentStep(chatId: Long): Boolean {
        return usersRepository.getUser(chatId)!!.stepCode == StepCode.BUTTON_REQUEST.toString()
    }

    override fun isPermitted(chatId: Long): Boolean {
        return true
    }
}