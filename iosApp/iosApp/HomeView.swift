//
//  HomeView.swift
//  iosApp
//
//  Created by Marko Popic on 30.8.24..
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct HomeView: View {
    var body: some View {
        NavigationView(content: {
            NavigationLink(destination: BusLinesView()) {
                Text("Add bus lines")
            }
            .navigationTitle("Home")
        })
    }
}

#Preview {
    HomeView()
}
