package org.kmp.Cache

import Cache
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class CacheManager : KoinComponent {
    private val cache: Cache by inject()

    var scheduleValidFrom
        get() = cache.load<String>(CacheIds.SCHEDULE_VALID_FROM.id)
        set(value) = if (value != null) {
            cache.save(CacheIds.SCHEDULE_VALID_FROM.id, value)
        } else {
            cache.clear(CacheIds.SCHEDULE_VALID_FROM.id)
        }

    var favourites: Set<String>?
        get() = cache.load<Set<String>?>(CacheIds.FAVOURITES.id)
        set(value: Set<String>?) = if (value != null) {
            cache.save(CacheIds.FAVOURITES.id, value)
        } else {
            cache.clear(CacheIds.FAVOURITES.id)
        }
}

enum class CacheIds(val id: String) {
    SCHEDULE_VALID_FROM("scheduleValidFrom"),
    FAVOURITES("favourites")
}