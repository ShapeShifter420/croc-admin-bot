package ru.ShapeShifter420.croc.service

import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import org.telegram.telegrambots.meta.api.methods.ParseMode
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow
import org.telegram.telegrambots.meta.generics.TelegramClient
import ru.ShapeShifter420.croc.dto.MarkupDataDto
import ru.ShapeShifter420.croc.dto.markup.DataModel
import ru.ShapeShifter420.croc.enums.StepCode
import ru.ShapeShifter420.croc.enums.StepType.*
import ru.ShapeShifter420.croc.event.TelegramStepMessageEvent
import ru.ShapeShifter420.croc.strategy.MarkupContext
import ru.ShapeShifter420.croc.strategy.MessageContext
import ru.ShapeShifter420.croc.strategy.NextStepContext

@Service
class MessageService(
    private val telegramClient: TelegramClient,
    private val messageContext: MessageContext,
    private val applicationEventPublisher: ApplicationEventPublisher,
    private val markupContext: MarkupContext<DataModel>,
    private val nextStepContext: NextStepContext
) {

    fun sendMessageToBot(
        chatId: Long,
        stepCode: StepCode
    ) {
        when (stepCode.type) {
            SIMPLE_TEXT -> telegramClient.execute(simpleTextMessage(chatId))
            INLINE_KEYBOARD_MARKUP -> telegramClient.sendInlineKeyboardMarkup(chatId)
        }

        if (!stepCode.botPause) {
            applicationEventPublisher.publishEvent(
                TelegramStepMessageEvent(
                    chatId = chatId,
                    stepCode = nextStepContext.next(chatId, stepCode)!!
                )
            )
        }
    }


    private fun simpleTextMessage(chatId: Long): SendMessage {
        val sendMessage = SendMessage(chatId.toString(),  messageContext.getMessage(chatId))
        sendMessage.enableHtml(true)

        val replyKeyboardRemove = ReplyKeyboardRemove(true)

        sendMessage.replyMarkup = replyKeyboardRemove
        return sendMessage
    }

    private fun TelegramClient.sendInlineKeyboardMarkup(chatId: Long) {
        val inlineKeyboardMarkup: InlineKeyboardMarkup
        val messageText: String

        val inlineKeyboardMarkupDto = markupContext.getInlineKeyboardMarkupDto(chatId)!!
        messageText = inlineKeyboardMarkupDto.message
        inlineKeyboardMarkup = inlineKeyboardMarkupDto.inlineButtons.getInlineKeyboardMarkup()

        this.execute(sendMessageWithMarkup(chatId, messageText, inlineKeyboardMarkup))
    }

    private fun sendMessageWithMarkup(
        chatId: Long, messageText: String, inlineKeyboardMarkup: InlineKeyboardMarkup
    ): SendMessage {
        val sendMessage = SendMessage(chatId.toString(), messageText)

        sendMessage.replyMarkup = inlineKeyboardMarkup
        sendMessage.parseMode = ParseMode.HTML
        return sendMessage
    }

    private fun List<MarkupDataDto>.getInlineKeyboardMarkup(): InlineKeyboardMarkup {

        val inlineKeyboardButtonsInner: MutableList<InlineKeyboardButton> = mutableListOf()
        val inlineKeyboardButtons: MutableList<MutableList<InlineKeyboardButton>> = mutableListOf()
        this.sortedBy { it.rowPos }.forEach { markupDataDto ->
            val button = InlineKeyboardButton(markupDataDto.text)
            button.callbackData = markupDataDto.text
            inlineKeyboardButtonsInner.add(button)
        }
        inlineKeyboardButtons.add(inlineKeyboardButtonsInner)
        val rows = inlineKeyboardButtons.map { InlineKeyboardRow(it) }
        return InlineKeyboardMarkup(rows)
    }
}