package org.kmp.ktor

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*

class KtorClient {
    private val client = HttpClient{
        expectSuccess = true
    }
    
    suspend fun getBusLines(area: Area, day: DayType?, date: String = "2024-09-09"): String{
        return client.get("http://www.gspns.co.rs/red-voznje/lista-linija"){
            url{
                parameters.append("rv", area.id)
                parameters.append("vaziod", date)
                parameters.append("dan", day?.id ?: "R")
            }
        }.bodyAsText()
    }

    suspend fun getScheduleByLine(area: Area, day: DayType?, line: String, date: String = "2024-09-09"): String{
        return client.get("http://www.gspns.co.rs/red-voznje/ispis-polazaka"){
            url{
                parameters.append("rv", area.id)
                parameters.append("vaziod", date)
                parameters.append("dan", day?.id ?: "R")
                parameters.append("linija[]", line)
            }
        }.bodyAsText()
    }
}