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
    @State private var isLoading = true
    @State private var errorMessage: String? = nil
    
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

            if isLoading {
                ProgressView()
                    .progressViewStyle(.circular)
                    .frame(maxWidth: .infinity, maxHeight: .infinity)
                    .scaleEffect(2)
            } else {
                if urbanLines.isEmpty && subrbanLines.isEmpty {
                    EmptyView(state: errorMessage == nil ? .emptyLines : .error(errorMessage!))
                        .frame(maxWidth: .infinity, maxHeight: .infinity)
                } else {
                    TabView(selection: $selectedPageIndex) {
                        ForEach(pages, id: \.index) { page in
                            BusLineListView(busLines: page.index == 0 ? $urbanLines : $subrbanLines)
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
        .background(Color.backgroundSecondary)
        .navigationTitle(SharedRes.strings().bus_lines_title.localized)
        .navigationBarTitleDisplayMode(.inline)
        .onAppear {
            getBusLines()
        }
    }
}

// MARK: - Getting the data
extension BusLinesView {
    private func getBusLines() {
        let repository = BusScheduleRepository()
        repository.getBusLines() { busLines, error in
            self.isLoading = false
            if let busLines = busLines {
                self.urbanLines = busLines
                    .filter { $0.area == .urban }
                    .map { BusLineUI(response: $0) }
                self.subrbanLines = busLines
                    .filter { $0.area == .suburban }
                    .map { BusLineUI(response: $0) }
            } else if let error = error {
                errorMessage = error.localizedDescription
            }
        }
    }
}

#Preview("Bus Lines") {
    BusLinesView()
}

