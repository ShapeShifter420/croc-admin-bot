package ru.ShapeShifter420.croc.command

import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Component
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand
import org.telegram.telegrambots.meta.api.objects.chat.Chat
import org.telegram.telegrambots.meta.api.objects.User
import org.telegram.telegrambots.meta.generics.TelegramClient
import ru.ShapeShifter420.croc.enums.CommandCode
import ru.ShapeShifter420.croc.enums.StepCode
import ru.ShapeShifter420.croc.event.TelegramStepMessageEvent
import ru.ShapeShifter420.croc.repository.UsersRepository

@Component
class UserInfoCommand(
    private val usersRepository: UsersRepository,
    private val applicationEventPublisher: ApplicationEventPublisher
) : BotCommand(CommandCode.USER_INFO.command, CommandCode.USER_INFO.desc) {

    companion object {
        private val USER_INFO = StepCode.USER_INFO
    }

    override fun execute(telegramClient: TelegramClient, user: User, chat: Chat, arguments: Array<out String>) {
        val chatId = chat.id

        usersRepository.updateUserStep(chatId, USER_INFO)

        applicationEventPublisher.publishEvent(
            TelegramStepMessageEvent(chatId = chatId, stepCode = USER_INFO)
        )
    }

}
