package org.kmp.experiment

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform