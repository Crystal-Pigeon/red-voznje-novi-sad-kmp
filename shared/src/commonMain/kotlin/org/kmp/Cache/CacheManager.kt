package org.kmp.Cache

import Cache
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

    var favourites: List<String>
        get() {
            val allStrings = cache.load<String>(CacheIds.FAVOURITES.id)
            return allStrings?.split(", ") ?: emptyList()
        }
        set(value){
            cache.save(CacheIds.FAVOURITES.id, value.joinToString(separator = ", "))
        }
}

enum class CacheIds(val id: String) {
    SCHEDULE_VALID_FROM("scheduleValidFrom"),
    FAVOURITES("favourites")
}