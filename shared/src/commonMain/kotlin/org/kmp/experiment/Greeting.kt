package org.kmp.experiment

import com.fleeksoft.ksoup.Ksoup
import com.fleeksoft.ksoup.nodes.Document
import org.kmp.ktor.Area
import org.kmp.ktor.BusLine
import org.kmp.ktor.DayType
import org.kmp.ktor.KtorClient

class Greeting {
    private val platform = getPlatform()
    private val ktorClient = KtorClient()

    fun greet(): String {
        return "Hello, ${platform.name}!"
    }

    suspend fun getBusLines(): List<BusLine>{
        val html = ktorClient.getBusLines(area = Area.URBAN, day = DayType.WORKDAY)
        val document: Document = Ksoup.parse(html)

        val options = document.select("select#linija option")

        val busList = mutableListOf<BusLine>()
        for (option in options) {
            busList.add(BusLine(option.attr("value"), option.text()))
        }
        return busList
    }
}