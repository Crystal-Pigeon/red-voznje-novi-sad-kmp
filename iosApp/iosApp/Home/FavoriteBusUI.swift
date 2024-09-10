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
        let currentHours = [Date().hour - 1, Date().hour, Date().hour + 1]
        return scheduleA.filter({
            currentHours.contains(Int($0.key) ?? -1)
        })
    }
    
    var shortScheduleB: [String:String]? {
        guard let scheduleB = self.scheduleB else { return nil }
        let currentHours = [Date().hour - 1, Date().hour, Date().hour + 1]
        return scheduleB.filter({
            currentHours.contains(Int($0.key) ?? -1)
        })
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
