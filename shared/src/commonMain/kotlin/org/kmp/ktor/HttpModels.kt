package org.kmp.ktor

import kotlinx.serialization.Serializable

enum class Area(val id: String){
    URBAN("rvg"), SUBURBAN("rvp")
}

enum class DayType(val id: String){
    WORKDAY("R"),SATURDAY("S"), SUNDAY("N")
}

data class BusLine(
    val id: String,
    val name: String
)

@Serializable
data class ScheduleStartDateResponse(
    val datum: String,
    val redv: String
)