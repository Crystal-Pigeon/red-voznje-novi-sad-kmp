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
    static var primary = Color(SharedRes.colors().orange.getUIColor())
    static var textPrimary = Color(SharedRes.colors().primary_text.getUIColor())
    static var backgroundPrimary = Color(SharedRes.colors().background.getUIColor())
}
