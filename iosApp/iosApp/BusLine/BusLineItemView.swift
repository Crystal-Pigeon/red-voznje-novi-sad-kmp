//
//  BusLineItemView.swift
//  iosApp
//
//  Created by Marko Popic on 30.8.24..
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import Shared

struct BusLineUI {
    let id: String
    let number: String
    let name: String
    
    var isSelected: Bool = false
}

extension BusLineUI {
    init(response: BusLine) {
        self.id = response.id
        self.number = response.name.components(separatedBy: " ").first ?? ""
        self.name = response.name
    }
}

struct BusLineItemView: View {
    let busLine: BusLineUI
    
    var body: some View {
        HStack(spacing: 8, content: {
            Text(busLine.number)
                .fontWeight(.bold)
                .foregroundStyle(Color.primary)
            Text(busLine.name.uppercased())
            Spacer()
            busLine.isSelected ?
            Image(systemName: "checkmark")
                .foregroundStyle(Color.primary) : nil
        })
        .frame(maxWidth: .infinity, alignment: .leading)
        .padding(.vertical, 8)
        .background(Color.backgroundPrimary)
    }
}

#Preview {
    BusLineItemView(busLine: BusLineUI(id: "52", number: "52", name: "Veternik"))
}
