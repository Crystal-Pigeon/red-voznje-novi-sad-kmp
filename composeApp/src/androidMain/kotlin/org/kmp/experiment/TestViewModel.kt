package org.kmp.experiment

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.kmp.Cache.CacheManager
import org.kmp.Repositories.BusScheduleRepository
import org.kmp.ktor.ApiResponse
import org.kmp.ktor.ScheduleStartDateResponse

class TestViewModel(val busScheduleRepository: BusScheduleRepository, val cache: CacheManager) : ViewModel() {
    var scheduleData by
    mutableStateOf<ApiResponse<List<ScheduleStartDateResponse>>?>(null)
}