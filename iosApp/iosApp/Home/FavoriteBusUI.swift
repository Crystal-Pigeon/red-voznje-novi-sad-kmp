//
//  FavoriteBusUI.swift
//  iosApp
//
//  Created by Marko Popic on 4.9.24..
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation

struct FavoriteBusUI {
    let id: String
    let number: String
    let name: String
    let scheduleTitleA: String
    let scheduleTitleB: String
    let scheduleA: [String:String]
    let scheduleB: [String:String]
    
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
