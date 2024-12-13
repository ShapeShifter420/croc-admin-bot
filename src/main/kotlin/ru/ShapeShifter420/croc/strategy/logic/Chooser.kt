package ru.ShapeShifter420.croc.strategy.logic

interface Chooser {

    fun isAvailableForCurrentStep(chatId: Long): Boolean

    fun isPermitted(chatId: Long): Boolean

}
