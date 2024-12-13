package ru.ShapeShifter420.croc.strategy.stepper

import ru.ShapeShifter420.croc.enums.StepCode

interface ChooseNextStep {

    fun isAvailableForCurrentStep(stepCode: StepCode): Boolean

    fun getNextStep(chatId: Long): StepCode?
}
