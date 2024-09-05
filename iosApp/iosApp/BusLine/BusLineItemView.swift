//
//  BusLineItemView.swift
//  iosApp
//
//  Created by Marko Popic on 30.8.24..
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import Shared

struct BusLineItemView: View {
    let busLine: BusLineUI
    
    var body: some View {
        HStack(spacing: 8, content: {
            Text(busLine.number)
                .font(.bold(15))
                .foregroundStyle(Color.brand)
            Text(busLine.name.uppercased())
                .font(.regular(15))
                .foregroundStyle(Color.textPrimary)
            Spacer()
            busLine.isSelected ?
            Image(systemName: "checkmark")
                .foregroundStyle(Color.brand) : nil
        })
        .background(Color.backgroundPrimary)
        .frame(maxWidth: .infinity, alignment: .leading)
        .padding(.vertical, 8)
    }
}

struct BusLineListView: View {
    @Binding var busLines: [BusLineUI]
    
    var body: some View {
        List($busLines, id: \.id) { $busLine in
            BusLineItemView(busLine: busLine)
                .contentShape(.rect)
                .listRowBackground(Color.backgroundPrimary)
                .onTapGesture {
                    busLine.isSelected.toggle()
                }
        }
        .listStyle(.plain)
        .listRowSpacing(12)
        .background(Color.backgroundSecondary)
    }
}

#Preview {
    BusLineListView(busLines: .constant([
        BusLineUI(id: "52", number: "52", name: "Veternik"),
        BusLineUI(id: "52", number: "52", name: "Veternik"),
        BusLineUI(id: "52", number: "52", name: "Veternik")
    ]))
}
