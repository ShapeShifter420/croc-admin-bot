package ru.ShapeShifter420.croc.strategy.stepper

import org.springframework.stereotype.Component
import ru.ShapeShifter420.croc.enums.StepCode

@Component
class StartStep : ChooseNextStep {

    override fun isAvailableForCurrentStep(stepCode: StepCode): Boolean {
        return stepCode == StepCode.START
    }

    override fun getNextStep(chatId: Long): StepCode {
        return StepCode.USER_INFO
    }

}