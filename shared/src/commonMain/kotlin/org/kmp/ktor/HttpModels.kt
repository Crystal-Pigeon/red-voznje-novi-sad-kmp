package org.kmp.ktor

import dev.icerock.moko.resources.desc.StringDesc
import getString
import kotlinx.serialization.Serializable
import org.kmp.experiment.SharedRes

enum class Area(val id: String){
    URBAN("rvg"), SUBURBAN("rvp");

    fun getStringId(): StringDesc {
        return when(this){
            URBAN -> getString(SharedRes.strings.bus_lines_urban)
            SUBURBAN -> getString(SharedRes.strings.bus_lines_suburban)
        }
    }
}

enum class DayType(val id: String){
    WORKDAY("R"),SATURDAY("S"), SUNDAY("N");

    fun getStringId(): StringDesc{
        return when(this){
            WORKDAY -> getString(SharedRes.strings.home_workday)
            SATURDAY -> getString(SharedRes.strings.home_saturday)
            SUNDAY -> getString(SharedRes.strings.home_sunday)
        }
    }
}

data class BusLine(
    val id: String,
    val number: String,
    val name: String,
    val area: Area,
    var isFavourite: Boolean = false
)

@Serializable
data class ScheduleStartDateResponse(
    val datum: String,
    val redv: String
)

@Serializable
data class ScheduleStartDateResponseList(val list: List<ScheduleStartDateResponse>)