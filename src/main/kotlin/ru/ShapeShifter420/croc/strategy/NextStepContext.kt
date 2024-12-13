package ru.ShapeShifter420.croc.strategy

import org.springframework.stereotype.Component
import ru.ShapeShifter420.croc.enums.StepCode
import ru.ShapeShifter420.croc.strategy.stepper.ChooseNextStep

@Component
class NextStepContext(private val chooseNextStep: List<ChooseNextStep>) {

    fun next(chatId: Long, stepCode: StepCode): StepCode? {
        return chooseNextStep.firstOrNull { it.isAvailableForCurrentStep(stepCode) }?.getNextStep(chatId)
    }

}
