package org.kmp.ktor

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