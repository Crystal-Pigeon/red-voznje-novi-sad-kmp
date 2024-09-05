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
        Font(SharedRes.fonts().manrope_regular.uiFont(withSize: size))
    }
    
    static func bold(_ size: Double) -> Font {
        Font(SharedRes.fonts().manrope_bold.uiFont(withSize: size))
    }
}
