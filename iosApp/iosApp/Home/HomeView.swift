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
    @State private var isLoading = true
    @State private var errorMessage: String? = nil

    @State private var selectedPageIndex = 0
    @State private var underlineOffset: CGFloat = 0

    @State private var workdayBuses: [FavoriteBusUI] = []
    @State private var saturdayBuses: [FavoriteBusUI] = []
    @State private var sundayBuses: [FavoriteBusUI] = []

    // MARK: - Constants
    private var pages: [TabPage] = [
        TabPage(index: 0, name: SharedRes.strings().home_workday.localized),
        TabPage(index: 1, name: SharedRes.strings().home_saturday.localized),
        TabPage(index: 2, name: SharedRes.strings().home_sunday.localized)
    ]

    private var underlineWidth: CGFloat {
        UIScreen.main.bounds.width / CGFloat(pages.count)
    }

    // MARK: - Layout
    var body: some View {
        NavigationView(content: {
            ZStack(alignment: .bottomTrailing) {
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

                    if isLoading {
                        ProgressView()
                            .progressViewStyle(.circular)
                            .frame(maxWidth: .infinity, maxHeight: .infinity)
                            .scaleEffect(2)
                    } else {
                        if workdayBuses.isEmpty {
                            EmptyView(state: errorMessage == nil ? .emptyHome : .error(errorMessage!))
                                .frame(maxWidth: .infinity, maxHeight: .infinity)
                        } else {
                            TabView(selection: $selectedPageIndex) {
                                ForEach(pages, id: \.index) { page in
                                    let favoriteBuses = page.index == 0 ? $workdayBuses : page.index == 1 ? $saturdayBuses : $sundayBuses
                                    FavoriteBusesListView(favoriteBuses: favoriteBuses)
                                }
                            }
                            .tabViewStyle(.page(indexDisplayMode: .never))
                            .animation(.easeInOut, value: selectedPageIndex)
                            .onChange(of: selectedPageIndex) { oldValue, newValue in
                                selectedPageIndex = newValue
                                underlineOffset = underlineWidth * CGFloat(newValue)
                            }
                        }
                    }
                }

                NavigationLink(destination: BusLinesView()) {
                    Image(systemName: "plus")
                        .font(.regular(24))
                        .frame(width: 56, height: 56)
                        .background(Color.brand)
                        .foregroundStyle(.white)
                        .clipShape(.circle)
                        .padding(.trailing, 32)
                        .padding(.bottom, 16)
                }
            }
            .background(Color.backgroundSecondary)
            .navigationTitle(SharedRes.strings().home_title.localized)
            .navigationBarTitleDisplayMode(.inline)
            .toolbar {
                ToolbarItem(placement: .navigationBarTrailing) {
                    NavigationLink(destination: AboutAppView()) {
                        Image(systemName: "questionmark.circle")
                    }
                }
            }
            .onAppear {
                getFavoriteBuses()
            }
        })
        .accentColor(.white)
    }

    private func getFavoriteBuses() {
        self.isLoading = true
        let repository = BusScheduleRepository()
        repository.getFavourites { response, error in
            self.isLoading = false
            if let response = response {
                self.workdayBuses = (response[.workday] ?? [])
                    .filter {
                        $0 is BusSchedule
                    }.map{
                        FavoriteBusUI(response: $0 as! BusSchedule)
                    }
                self.saturdayBuses = (response[.saturday] ?? [])
                    .filter {
                        $0 is BusSchedule
                    }.map{
                        FavoriteBusUI(response: $0 as! BusSchedule)
                    }
                self.sundayBuses = (response[.sunday] ?? [])
                    .filter {
                        $0 is BusSchedule
                    }.map{
                        FavoriteBusUI(response: $0 as! BusSchedule)
                    }
            }  else if let error = error {
                errorMessage = error.localizedDescription
            }
        }
    }
}

#Preview("Home") {
    HomeView()
}
