//
//  Font+Extension.swift
//  iosApp
//
//  Created by Marko Popic on 4.9.24..
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import Shared

extension SwiftUI.Font {
    static func regular(_ size: Double) -> Font {
        Font(SharedRes.fonts().inter_regular.uiFont(withSize: size))
    }
    
    static func bold(_ size: Double) -> Font {
        Font(SharedRes.fonts().inter_semibold.uiFont(withSize: size))
    }
}
