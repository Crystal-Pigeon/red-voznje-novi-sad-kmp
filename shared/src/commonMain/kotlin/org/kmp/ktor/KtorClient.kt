package org.kmp.ktor

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*

class KtorClient {
    private val client = HttpClient{
        expectSuccess = true
    }
    
    suspend fun getBusLines(area: Area, day: DayType?, date: String = "2024-09-01"): String{
        return client.get("http://www.gspns.co.rs/red-voznje/lista-linija"){
            url{
                parameters.append("rv", area.id)
                parameters.append("vaziod", date)
                parameters.append("dan", day?.id ?: "R")
            }
        }.bodyAsText()
    }
}