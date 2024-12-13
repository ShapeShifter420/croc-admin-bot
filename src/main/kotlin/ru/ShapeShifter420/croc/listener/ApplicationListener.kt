package ru.ShapeShifter420.croc.listener

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Lazy
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import ru.ShapeShifter420.croc.enums.ExecuteStatus
import ru.ShapeShifter420.croc.event.TelegramReceivedCallbackEvent
import ru.ShapeShifter420.croc.event.TelegramReceivedMessageEvent
import ru.ShapeShifter420.croc.event.TelegramStepMessageEvent
import ru.ShapeShifter420.croc.repository.UsersRepository
import ru.ShapeShifter420.croc.service.MessageService
import ru.ShapeShifter420.croc.strategy.LogicContext
import ru.ShapeShifter420.croc.strategy.NextStepContext

@Component
class ApplicationListener(
    private val logicContext: LogicContext,
    private val nextStepContext: NextStepContext,
    private val usersRepository: UsersRepository,
    private val messageService: MessageService
) {

    inner class Message {
        @EventListener
        fun onApplicationEvent(event: TelegramReceivedMessageEvent) {
            logicContext.execute(chatId = event.chatId, message = event.message)
            val nextStepCode = nextStepContext.next(event.chatId, event.stepCode)
            if (nextStepCode != null) {
                stepMessageBean().onApplicationEvent(
                    TelegramStepMessageEvent(
                        chatId = event.chatId,
                        stepCode = nextStepCode
                    )
                )
            }
        }
    }

    inner class StepMessage {
        @EventListener
        fun onApplicationEvent(event: TelegramStepMessageEvent) {
            usersRepository.updateUserStep(event.chatId, event.stepCode)
            messageService.sendMessageToBot(event.chatId, event.stepCode)
        }
    }

    inner class CallbackMessage {
        @EventListener
        fun onApplicationEvent(event: TelegramReceivedCallbackEvent) {
            val nextStepCode = when (logicContext.execute(event.chatId, event.callback)) {
                ExecuteStatus.FINAL -> {
                    nextStepContext.next(event.chatId, event.stepCode)
                }
                ExecuteStatus.NOTHING -> throw IllegalStateException("Не поддерживается")
            }
            if (nextStepCode != null) {
                stepMessageBean().onApplicationEvent(
                    TelegramStepMessageEvent(
                        chatId = event.chatId,
                        stepCode = nextStepCode
                    )
                )
            }
        }
    }

    @Bean
    @Lazy
    fun messageBean(): Message = Message()

    @Bean
    @Lazy
    fun stepMessageBean(): StepMessage = StepMessage()

    @Bean
    @Lazy
    fun callbackMessageBean(): CallbackMessage = CallbackMessage()

}