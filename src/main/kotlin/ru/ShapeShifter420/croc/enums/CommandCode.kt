package ru.ShapeShifter420.croc.enums

enum class CommandCode(val command: String, val desc: String) {
    START("start", "Start work"),
    USER_INFO("user_info", "user info"),
    BUTTON("button", "button yes no")
}
