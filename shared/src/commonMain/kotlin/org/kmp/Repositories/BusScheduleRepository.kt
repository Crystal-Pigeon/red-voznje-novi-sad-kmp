package org.kmp.Repositories

import org.kmp.ktor.KtorClient
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import com.fleeksoft.ksoup.Ksoup
import com.fleeksoft.ksoup.nodes.Document
import com.fleeksoft.ksoup.nodes.Element
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.kmp.ktor.Area
import org.kmp.ktor.BusLine
import org.kmp.ktor.DayType

class BusScheduleRepository()/* : KoinComponent*/ {
    //private val ktorClient: KtorClient by inject()
    private val ktorClient = KtorClient()

    suspend fun getBusLines(areaType: Area = Area.URBAN, dayType: DayType = DayType.WORKDAY): List<BusLine> {
        val html = ktorClient.getBusLines(area = areaType, day = dayType)
        val document: Document = Ksoup.parse(html)

        val options = document.select("select#linija option")

        val busList = mutableListOf<BusLine>()
        for (option in options) {
            busList.add(BusLine(option.attr("value"), option.text()))
        }
        return busList
    }

    suspend fun getScheduleByLine(
        areaType: Area = Area.URBAN,
        dayType: DayType = DayType.WORKDAY,
        busLine: String = "2."
    ): BusSchedule {
        val html = ktorClient.getScheduleByLine(area = areaType, day = dayType, line = busLine)
        val document: Document = Ksoup.parse(html)

        val timeCells = document.select("td[valign='top']")

        val directionA = parseDirection(timeCells.getOrNull(0))
        val directionB = parseDirection(timeCells.getOrNull(1))

        // Select the <th> elements for direction names
        val directionHeaders = document.select("th")

        // Find the direction names in <th> tags (A and B)
        val directionAName =
            directionHeaders.firstOrNull { it.text().contains("Смер A") }?.text()?.substringAfter(":")?.trim()
        val directionBName =
            directionHeaders.firstOrNull { it.text().contains("Смер B") }?.text()?.substringAfter(":")?.trim()

        val lineInfoElement = document.selectFirst("div.table-title")

        // Check if the element exists and extract the text
        return with(lineInfoElement) {
            val lineInfoText = this?.text()?.trim() ?: ""

            // Extract the ID and line name from the text (e.g., "Линија: 2 CENTAR - NOVO NASELJE")
            val lineName = lineInfoText.substringAfter(" ").substringAfter(" ").trim()

            BusSchedule(
                id = busLine,
                number = busLine.removeSuffix("."),
                lineName = lineName,
                directionA = directionAName ?: "",
                directionB = directionBName,
                scheduleA = directionA,
                scheduleB = directionB.ifEmpty { null }
            )
        }

    }

    private fun parseDirection(cell: Element?): Map<String, String> {
        val times: MutableMap<String, String> = mutableMapOf()
        cell?.let {
            val elements = it.children()

            var currentHour = ""
            var minutes = ""

            for (element in elements) {
                when {
                    element.tagName() == "b" -> {
                        // If we have a new hour, save the previous one
                        if (currentHour.isNotEmpty()) {
                            minutes = ""
                        }
                        // Update the current hour
                        currentHour = element.text()
                    }

                    element.tagName() == "sup" -> {
                        // Collect minutes
                        minutes += " " + element.text()
                        times[currentHour] = minutes
                    }
                }
            }
        }
        return times
    }

    suspend fun getFavourites(busLines: List<String>) {
        busLines.forEach {
            getScheduleByLine(busLine = it)
        }
    }
}