package ru.ShapeShifter420.croc.config

import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties
@ConfigurationPropertiesScan("ru.ShapeShifter420.croc.properties")
class PropertyConfig {
}
