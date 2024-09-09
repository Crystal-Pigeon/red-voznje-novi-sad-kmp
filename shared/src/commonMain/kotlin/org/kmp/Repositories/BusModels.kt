package org.kmp.Repositories

data class BusSchedule(
    val id: String,
    val number: String,
    val lineName: String,
    val directionA: String,
    val directionB: String?,
    val scheduleA: Map<String, String>,
    val scheduleB: Map<String, String>?
)