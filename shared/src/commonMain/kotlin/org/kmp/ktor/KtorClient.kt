package org.kmp.ktor

import io.ktor.client.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.*
import kotlinx.serialization.json.*
import io.ktor.serialization.kotlinx.json.json


class KtorClient {
    private val client = HttpClient {
        install(ContentNegotiation) {
            register(ContentType.Text.Html, KotlinxSerializationConverter(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true

            }))
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            }) // Set up kotlinx.serialization with JSON
        }
        expectSuccess = true
    }

    suspend fun getBusLines(area: Area, day: DayType?, date: String = "2024-09-09"): String {
        return client.get("http://www.gspns.co.rs/red-voznje/lista-linija") {
            url {
                parameters.append("rv", area.id)
                parameters.append("vaziod", date)
                parameters.append("dan", day?.id ?: "R")
            }
        }.bodyAsText()
    }

    suspend fun getScheduleByLine(area: Area, day: DayType?, line: String, date: String = "2024-09-09"): String {
        return client.get("http://www.gspns.co.rs/red-voznje/ispis-polazaka") {
            url {
                parameters.append("rv", area.id)
                parameters.append("vaziod", date)
                parameters.append("dan", day?.id ?: "R")
                parameters.append("linija[]", line)
            }
        }.bodyAsText()
    }

    suspend fun getScheduleStartDate(): ApiResponse<List<ScheduleStartDate>, ErrorBody> {
        //val response: String = client.get("http://www.gspns.rs/feeds/red-voznje").bodyAsText()
        return client.safeRequest {
            method = HttpMethod.Get
            url("http://www.gspns.rs/feeds/red-voznje")
            //contentType(ContentType.Text.Html)
        }
    //Json.decodeFromString(response)//TODO response handling
    }
}