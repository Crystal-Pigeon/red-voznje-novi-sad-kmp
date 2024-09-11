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
    var scheduleData by mutableStateOf<List<ScheduleStartDateResponse>>(emptyList())
    // Function to fetch schedule start dates
    fun fetchScheduleStartDates() {
        viewModelScope.launch {
            busScheduleRepository.getScheduleStartDate().collect { response ->
                when (response) {
                    is ApiResponse.Error<*> -> {}
                    is ApiResponse.Success<List<ScheduleStartDateResponse>> -> {
                        scheduleData = response.body
                        cache.scheduleStartDate = scheduleData.first().datum //TODO move to UI and cache it after user's choice
                    }
                }
            }
        }
    }
}