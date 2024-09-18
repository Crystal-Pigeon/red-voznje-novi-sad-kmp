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

    @State private var selectedPageIndex = 0
    @State private var underlineOffset: CGFloat = 0

    @State private var workdayBuses: [FavoriteBusUI] = []
    @State private var saturdayBuses: [FavoriteBusUI] = []
    @State private var sundayBuses: [FavoriteBusUI] = []

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
                            EmptyView()
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
                            .onChange(of: selectedPageIndex) { newPage in
                                selectedPageIndex = newPage
                                underlineOffset = underlineWidth * CGFloat(newPage)
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
        let repository = BusScheduleRepository()
        repository.getFavourites { response, error in
            self.isLoading = false
            if let response = response {
                self.workdayBuses = (response[.workday] ?? []).map({ FavoriteBusUI(response: $0 as! BusSchedule) })
                self.saturdayBuses = (response[.saturday] ?? []).map({ FavoriteBusUI(response: $0 as! BusSchedule) })
                self.sundayBuses = (response[.sunday] ?? []).map({ FavoriteBusUI(response: $0 as! BusSchedule) })
            }  else if let error = error {
                print(error.localizedDescription)
            }
        }
    }
}

#Preview("Home") {
    HomeView()
}

struct testView: View {
    @State private var response: ApiResponse<ScheduleStartDateResponseList>? = nil

    var body: some View {
        VStack {
            if let result = response {
                if result.isSuccess() {
                        if let data = result.getSuccessData()?.list {
                            let a = data.first?.datum
                            let _ = print("bilo sta")
                            Text("Data: \(data)") // Use data.list to access the array
                        }
                    } else if result.isError() {
                        if let errorMessage = result.getErrorMessage() {
                            let _ = print("t error")
                            Text("Error: \(errorMessage)")
                        }
                    }
            } else {
                let _ = print("nista se ne desava")
                ProgressView()
            }
        }
        .onAppear {
            Task {
                Task {
                        let repository = BusScheduleRepository()
                        do {
                            // Call the API and update the response
                            let result = try await repository.getScheduleStartDate()
                            response = result // Update the response state
                        } catch let error {
                            print("test error: \(error)")
                            //response = ApiResponse.Error(message: error.localizedDescription)
                        }
                    }
            }
        }
    }
}
