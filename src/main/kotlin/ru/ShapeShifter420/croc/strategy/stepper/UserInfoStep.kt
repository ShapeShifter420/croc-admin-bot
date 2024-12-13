package ru.ShapeShifter420.croc.strategy.stepper

import org.springframework.stereotype.Component
import ru.ShapeShifter420.croc.enums.StepCode

@Component
class UserInfoStep : ChooseNextStep {

    override fun isAvailableForCurrentStep(stepCode: StepCode): Boolean {
        return stepCode == StepCode.USER_INFO
    }

    override fun getNextStep(chatId: Long): StepCode {
        return StepCode.BUTTON_REQUEST
    }

}