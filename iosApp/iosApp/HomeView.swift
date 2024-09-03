//
//  HomeView.swift
//  iosApp
//
//  Created by Marko Popic on 30.8.24..
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import Shared

struct HomeView: View {
    var body: some View {
        NavigationView(content: {
            NavigationLink(destination: BusLinesView()) {
                Text(SharedRes.strings().general_tap_to_add.localized)
            }
            .navigationTitle("Home")
        })
    }
}

#Preview {
    HomeView()
}
