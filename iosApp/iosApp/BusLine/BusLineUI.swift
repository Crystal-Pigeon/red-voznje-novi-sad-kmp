//
//  BusLineUI.swift
//  iosApp
//
//  Created by Marko Popic on 4.9.24..
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import Shared

struct BusLineUI {
    let id: String
    let number: String
    let name: String
    let areaType: Area
    var isSelected: Bool
}

extension BusLineUI {
    init(response: BusLine) {
        self.id = response.id
        self.number = response.number
        self.name = response.name
        self.areaType = response.area
        self.isSelected = CacheManager().favourites.contains(response.id)
    }
}
