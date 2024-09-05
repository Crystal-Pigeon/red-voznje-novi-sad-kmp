//
//  StringResource+Extension.swift
//  iosApp
//
//  Created by Marko Popic on 3.9.24..
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import Shared

extension StringResource {
    var localized: String {
        Strings().get(id: self, args: [])
    }
}
