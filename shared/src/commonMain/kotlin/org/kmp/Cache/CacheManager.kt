package org.kmp.Cache

import Cache
import org.kmp.ktor.Area
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class CacheManager : KoinComponent {
    //private val cache: Cache by inject()
    private val cache = Cache()

    var scheduleValidFrom
        get() = cache.load<String>(CacheIds.SCHEDULE_VALID_FROM.id)
        set(value) = if (value != null) {
            cache.save(CacheIds.SCHEDULE_VALID_FROM.id, value)
        } else {
            cache.clear(CacheIds.SCHEDULE_VALID_FROM.id)
        }

    var urbanFavourites: List<String>
        get() {
            val allStrings = cache.load<String>(CacheIds.URBAN_FAVOURITES.id)
            return allStrings?.split(", ") ?: emptyList()
        }
        set(value) {
            cache.save(CacheIds.URBAN_FAVOURITES.id, value.joinToString(separator = ", "))
        }

    var suburbanFavourites: List<String>
        get() {
            val allStrings = cache.load<String>(CacheIds.SUBURBAN_FAVOURITES.id)
            return allStrings?.split(", ") ?: emptyList()
        }
        set(value) {
            cache.save(CacheIds.SUBURBAN_FAVOURITES.id, value.joinToString(separator = ", "))
        }

    val favourites: List<String>
        get() = urbanFavourites + suburbanFavourites

    fun removeFromFavourites(id: String) {
        if (urbanFavourites.contains(id)) {
            urbanFavourites = urbanFavourites.filter { it != id }//keeping the list immutable
        } else if (suburbanFavourites.contains(id)) {
            suburbanFavourites = suburbanFavourites.filter { it != id }
        }
    }

    fun addToFavourites(id: String, areaType: Area) {
        when (areaType) {
            Area.URBAN -> if(!urbanFavourites.contains(id)) urbanFavourites = urbanFavourites + listOf(id)//keeping the list immutable
            Area.SUBURBAN -> if(!suburbanFavourites.contains(id)) suburbanFavourites = suburbanFavourites + listOf(id)
        }
    }

    var scheduleStartDate: String?
    get() = cache.load<String>(CacheIds.SCHEDULE_START_DATE.id)
    set(value: String?) = cache.save(CacheIds.SCHEDULE_START_DATE.id, value)
}

enum class CacheIds(val id: String) {
    SCHEDULE_VALID_FROM("scheduleValidFrom"),
    URBAN_FAVOURITES("urban_favourites"),
    SUBURBAN_FAVOURITES("suburban_favourites"),
    SCHEDULE_START_DATE("schedule_start_date")
}