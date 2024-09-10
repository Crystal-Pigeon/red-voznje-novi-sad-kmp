package org.kmp.experiment

import androidx.lifecycle.ViewModel
import org.kmp.Cache.CacheManager
import org.kmp.Repositories.BusScheduleRepository

class TestViewModel(val busScheduleRepository: BusScheduleRepository, val cache: CacheManager) : ViewModel() {

}