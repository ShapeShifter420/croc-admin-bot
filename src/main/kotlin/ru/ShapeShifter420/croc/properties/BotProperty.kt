package ru.ShapeShifter420.croc.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "ru/ShapeShifter420/croc")
data class BotProperty(
    var username: String = "",
    var token: String = ""
)
