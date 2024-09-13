package org.kmp.ktor

import io.ktor.client.*
import io.ktor.client.call.*
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

    suspend fun getBusLines(area: Area, day: DayType?, date: String): String? {
        return client.get("http://www.gspns.co.rs/red-voznje/lista-linija") {
            url {
                parameters.append("rv", area.id)
                parameters.append("vaziod", date)
                parameters.append("dan", day?.id ?: "R")
            }
        }.validatedResponse()
    }

    suspend fun getScheduleByLine(area: Area, day: DayType?, line: String, date: String = "2024-09-09"): String? {
        return client.get("http://www.gspns.co.rs/red-voznje/ispis-polazaka") {
            url {
                parameters.append("rv", area.id)
                parameters.append("vaziod", date)
                parameters.append("dan", day?.id ?: "R")
                parameters.append("linija[]", line)
            }
        }.validatedResponse()
    }

    suspend fun getScheduleStartDate(): ApiResponse<ScheduleStartDateResponseList> {
        return handleApiResponse<ScheduleStartDateResponseList> {
            val response: List<ScheduleStartDateResponse> = client.get("http://www.gspns.rs/feeds/red-voznje").body()
            ScheduleStartDateResponseList(response)
        }
    }
}

suspend fun HttpResponse.validatedResponse(): String? {
    HttpStatusCode
    return when (status.value) {
        in 200..299 -> bodyAsText()
        else -> null
    }
}