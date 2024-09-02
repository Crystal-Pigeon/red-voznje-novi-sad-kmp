//
//  BusLinesView.swift
//  iosApp
//
//  Created by Marko Popic on 30.8.24..
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import Shared

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
        .background(Color.white)
    }
}

struct BusLinesView: View {
    
    struct BusLineList {
        var index: Int
        var name: String
    }
    
    private var pages = [
        BusLineList(index: 0, name: "Gradski"),
        BusLineList(index: 1, name: "Prigradski")
    ]
    
    @State private var urbanLines = BusLineUI.urban
    @State private var subrbanLines = BusLineUI.suburban
    
    @State private var selectedPageIndex = 0
    @State private var underlineOffset: CGFloat = 0
    
    private var underlineWidth: CGFloat {
        UIScreen.main.bounds.width / CGFloat(pages.count)
    }
    
    private var selectedUrbanLines: [BusLineUI] {
        urbanLines.filter{ $0.isSelected }
    }
    
    private var selectedSubrbanLines: [BusLineUI] {
        subrbanLines.filter{ $0.isSelected }
    }

    var body: some View {
        VStack(alignment: .leading, spacing: 0) {
            HStack(spacing: 0) {
                ForEach(pages, id: \.index) { page in
                    Button(action: {
                        selectedPageIndex = page.index
                        underlineOffset = underlineWidth * CGFloat(page.index)
                    }) {
                        Text(page.name)
                            .foregroundColor(.blue)
                            .padding()
                            .frame(maxWidth: .infinity)
                            .background(Color.white)
                            .cornerRadius(0)
                    }
                }
            }
            
            Rectangle()
                .foregroundStyle(.blue)
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
        .navigationTitle("Linije")
        .navigationBarTitleDisplayMode(.inline)
        .toolbarBackground(Color.black, for: .navigationBar)
        .onDisappear {
            print("Izabrane gradske linije \(self.selectedUrbanLines.map{ $0.number })")
            print("Izabrane prigradske linije \(self.selectedSubrbanLines.map{ $0.number })")
        }
    }
}

#Preview("Bus Lines") {
    BusLinesView()
}

