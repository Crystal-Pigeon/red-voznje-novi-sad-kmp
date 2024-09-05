//
//  Color+Extension.swift
//  iosApp
//
//  Created by Marko Popic on 3.9.24..
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import Shared

extension SwiftUI.Color {
    static var brand = Color(SharedRes.colors().brand.getUIColor())
    static var textPrimary = Color(SharedRes.colors().primary_text.getUIColor())
    static var textSecondary = Color(SharedRes.colors().secondary_text.getUIColor())
    static var backgroundPrimary = Color(SharedRes.colors().primary_background.getUIColor())
    static var backgroundSecondary = Color(SharedRes.colors().secondary_background.getUIColor())
}
