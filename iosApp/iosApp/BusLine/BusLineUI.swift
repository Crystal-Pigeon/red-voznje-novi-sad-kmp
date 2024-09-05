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
    
    var isSelected: Bool = false
}

extension BusLineUI {
    init(response: BusLine) {
        var nameArray = response.name.components(separatedBy: " ")
        self.id = response.id
        self.number = nameArray.removeFirst()
        self.name = nameArray.joined(separator: " ")
    }
}
