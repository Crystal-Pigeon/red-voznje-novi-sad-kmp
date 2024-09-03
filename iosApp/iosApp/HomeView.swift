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
                Text("Add new bus lines")
//                Text(Strings().get(id: SharedRes.strings().general_tap_to_add, args: []))
            }
            .navigationTitle("Home")
        })
    }
}

#Preview {
    HomeView()
}
