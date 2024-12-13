package ru.ShapeShifter420.croc.strategy.stepper

import org.springframework.stereotype.Component
import ru.ShapeShifter420.croc.enums.StepCode

@Component
class ButtonRequestStep : ChooseNextStep {

    override fun isAvailableForCurrentStep(stepCode: StepCode): Boolean {
        return stepCode == StepCode.BUTTON_REQUEST
    }

    override fun getNextStep(chatId: Long): StepCode {
        return StepCode.BUTTON_RESPONSE
    }

}