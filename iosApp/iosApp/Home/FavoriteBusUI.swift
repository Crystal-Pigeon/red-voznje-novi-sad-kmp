//
//  FavoriteBusUI.swift
//  iosApp
//
//  Created by Marko Popic on 4.9.24..
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import Shared

struct FavoriteBusUI {
    let id: String
    let number: String
    let name: String
    let scheduleTitleA: String
    let scheduleTitleB: String?
    let scheduleA: [String:String]
    let scheduleB: [String:String]?

    var isOpened: Bool = false

    var shortScheduleA: [String:String] {
        return getShortSchedule(scheduleA)
    }

    var shortScheduleB: [String:String]? {
        guard let scheduleB = self.scheduleB else { return nil }
        return getShortSchedule(scheduleB)
    }
    
    private func getShortSchedule(_ schedule: [String:String]) -> [String:String] {
        if schedule.count <= 3 { return schedule }
        let currentTime = Date()
        let currentHours = [currentTime.hour - 1, currentTime.hour, currentTime.hour + 1]
        let shortSchedule = schedule.filter({
            currentHours.contains(Int($0.key) ?? -1)
        })
        let reducedSchedule = Dictionary(
            uniqueKeysWithValues: schedule.sorted(by: {
                return Int($0.key) ?? -1 < Int($1.key) ?? -1
            }).prefix(3).map { $0 }
        )
        return shortSchedule.count < 3 ? reducedSchedule : shortSchedule
    }

    static var dummy = FavoriteBusUI(
        id: "52",
        number: "52",
        name: "VETERNIK",
        scheduleTitleA: "ZA VETERNIK",
        scheduleTitleB: "ZA NOVI SAD",
        scheduleA: ["05":"00 30", "06":"00 40", "10":"00 40", "11":"00 40", "12":"00 40"],
        scheduleB: ["05":"25 55", "06":"10 50"]
    )
}

extension FavoriteBusUI {
    init(response: BusSchedule) {
        self.id = response.id
        self.number = response.number
        self.name = response.lineName
        self.scheduleTitleA = response.directionA
        self.scheduleTitleB = response.directionB
        self.scheduleA = response.scheduleA
        self.scheduleB = response.scheduleB
    }
}
