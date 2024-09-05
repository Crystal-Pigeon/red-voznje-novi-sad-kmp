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
    
    // MARK: - State properties
    @State private var selectedPageIndex = 0
    @State private var underlineOffset: CGFloat = 0
    @State private var favoriteBuses: [FavoriteBusUI] = [FavoriteBusUI.dummy]
    
    // MARK: - Constants
    private var pages: [TabPage] {[
        TabPage(index: 0, name: SharedRes.strings().home_workday.localized),
        TabPage(index: 1, name: SharedRes.strings().home_saturday.localized),
        TabPage(index: 2, name: SharedRes.strings().home_sunday.localized)
    ]}
    
    private var underlineWidth: CGFloat {
        UIScreen.main.bounds.width / CGFloat(pages.count)
    }
    
    // MARK: - Layout
    var body: some View {
        NavigationView(content: {
            VStack(alignment: .trailing) {
                VStack(alignment: .leading, spacing: 0) {
                    HStack(spacing: 0) {
                        ForEach(pages, id: \.index) { page in
                            Button(action: {
                                selectedPageIndex = page.index
                                underlineOffset = underlineWidth * CGFloat(page.index)
                            }) {
                                Text(page.name)
                                    .foregroundColor(Color.white)
                                    .frame(maxWidth: .infinity)
                                    .font(.regular(16))
                                    .padding()
                            }
                        }
                    }
                    Rectangle()
                        .foregroundStyle(Color.white)
                        .frame(height: 2)
                        .frame(width: underlineWidth)
                        .offset(x: underlineOffset)
                        .animation(.easeInOut, value: underlineOffset)
                }
                .background(Color.brand)
                
                TabView(selection: $selectedPageIndex) {
                    ForEach(pages, id: \.index) { page in
                        FavoriteBusesListView(favoriteBuses: favoriteBuses)
                    }
                }
                .tabViewStyle(.page(indexDisplayMode: .never))
                .animation(.easeInOut, value: selectedPageIndex)
                .onChange(of: selectedPageIndex) { newPage in
                    selectedPageIndex = newPage
                    underlineOffset = underlineWidth * CGFloat(newPage)
                }
                
                NavigationLink(destination: BusLinesView()) {
                    Text("+")
                        .frame(width: 56, height: 56)
                        .background(Color.brand)
                        .foregroundStyle(.white)
                        .font(.regular(24))
                        .clipShape(.circle)
                        .padding(.trailing, 32)
                }
            }
            .background(Color.backgroundSecondary)
            .navigationTitle(SharedRes.strings().home_title.localized)
            .navigationBarTitleDisplayMode(.inline)
        })
        .accentColor(.white)
        .onAppear{
            getFavoriteBuses()
        }
    }
    
    private func getFavoriteBuses() {
        let repository = BusScheduleRepository()
        repository.getScheduleByLine { response, error in
            if let response = response {
                let scheduleA = response.first as? Array<Any> ?? []
                print(scheduleA)
            }
        }
    }
}

#Preview("Home") {
    HomeView()
}
