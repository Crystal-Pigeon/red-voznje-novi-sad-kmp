//
//  BusLinesView.swift
//  iosApp
//
//  Created by Marko Popic on 30.8.24..
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import Shared

struct BusLinesView: View {
    
    // MARK: - State properties
    @State private var selectedPageIndex = 0
    @State private var underlineOffset: CGFloat = 0
    
    @State private var urbanLines: [BusLineUI] = []
    @State private var subrbanLines: [BusLineUI] = []
    
    private var selectedUrbanLines: [BusLineUI] {
        urbanLines.filter{ $0.isSelected }
    }
    
    private var selectedSubrbanLines: [BusLineUI] {
        subrbanLines.filter{ $0.isSelected }
    }
    
    // MARK: - Constants
    private var pages: [TabPage] {[
        TabPage(index: 0, name: SharedRes.strings().bus_lines_urban.localized),
        TabPage(index: 1, name: SharedRes.strings().bus_lines_suburban.localized)
    ]}
    
    private var underlineWidth: CGFloat {
        UIScreen.main.bounds.width / CGFloat(pages.count)
    }

    // MARK: - Layout
    var body: some View {
        VStack(alignment: .leading, spacing: 0) {
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
                    BusLineListView(busLines: page.index == 0 ? $urbanLines : $subrbanLines)
                }
            }
            .tabViewStyle(.page(indexDisplayMode: .never))
            .animation(.easeInOut, value: selectedPageIndex)
            .onChange(of: selectedPageIndex) { newPage in
                selectedPageIndex = newPage
                underlineOffset = underlineWidth * CGFloat(newPage)
            }
        }
        .background(Color.backgroundSecondary)
        .navigationTitle(SharedRes.strings().bus_lines_title.localized)
        .navigationBarTitleDisplayMode(.inline)
        .onAppear {
            getBusLines()
        }
        .onDisappear {
            let favoriteLines = self.selectedUrbanLines.map{ $0.id } + self.selectedSubrbanLines.map{ $0.id }
            CacheManager().favourites = favoriteLines
        }
    }
}

// MARK: - Getting the data
extension BusLinesView {
    private func getBusLines() {
        let repository = BusScheduleRepository()
        repository.getBusLines(areaType: .urban, dayType: .workday) { busLines, error in
            if let busLines = busLines {
                self.urbanLines = busLines.map {
                    BusLineUI(response: $0)
                }
            }
        }
        repository.getBusLines(areaType: .suburban, dayType: .workday) { busLines, error in
            if let busLines = busLines {
                self.subrbanLines = busLines.map {
                    BusLineUI(response: $0)
                }
            }
        }
    }
}

#Preview("Bus Lines") {
    BusLinesView()
}

