//
//  BusLinesView.swift
//  iosApp
//
//  Created by Marko Popic on 30.8.24..
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import Shared

internal struct BusLineList {
    var index: Int
    var name: String
}

struct BusLineListView: View {
    @Binding var busLines: [BusLineUI]
    
    var body: some View {
        List($busLines, id: \.id) { $busLine in
            BusLineItemView(busLine: busLine)
                .contentShape(.rect)
                .listRowBackground(Color.white)
                .onTapGesture {
                    busLine.isSelected.toggle()
                }
        }
        .listStyle(.insetGrouped)
    }
}

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
    private var pages: [BusLineList] {[
        BusLineList(index: 0, name: "Gradski"),
        BusLineList(index: 1, name: "Prigradski")
    ]}
    
    private var underlineWidth: CGFloat {
        UIScreen.main.bounds.width / CGFloat(pages.count)
    }

    // MARK: - Layout
    var body: some View {
        VStack(alignment: .leading, spacing: 0) {
            HStack(spacing: 0) {
                ForEach(pages, id: \.index) { page in
                    Button(action: {
                        selectedPageIndex = page.index
                        underlineOffset = underlineWidth * CGFloat(page.index)
                    }) {
                        Text(page.name)
                            .foregroundColor(Color.primary)
                            .padding()
                            .frame(maxWidth: .infinity)
                            .background(Color.backgroundPrimary)
                            .cornerRadius(0)
                    }
                    .background(Color.backgroundPrimary)
                }
            }
            
            Rectangle()
                .foregroundStyle(Color.primary)
                .frame(height: 1)
                .frame(width: underlineWidth)
                .offset(x: underlineOffset)
                .animation(.easeInOut, value: underlineOffset)
            
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
        .background(Color.backgroundPrimary)
        .navigationTitle("Linije")
        .navigationBarTitleDisplayMode(.inline)
        .onAppear {
            getBusLines()
        }
        .onDisappear {
            print("Izabrane gradske linije \(self.selectedUrbanLines.map{ $0.id })")
            print("Izabrane prigradske linije \(self.selectedSubrbanLines.map{ $0.id })")
        }
    }
}

// MARK: - Getting the data
extension BusLinesView {
    private func getBusLines() {
        Greeting().getBusLines { busLines, error in
            if let busLines = busLines {
                self.urbanLines = busLines.map {
                    BusLineUI(response: $0)
                }
            }
        }
    }
}

#Preview("Bus Lines") {
    BusLinesView()
}

