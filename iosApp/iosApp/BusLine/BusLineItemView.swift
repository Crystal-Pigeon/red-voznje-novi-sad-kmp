//
//  BusLineItemView.swift
//  iosApp
//
//  Created by Marko Popic on 30.8.24..
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct BusLineUI {
    let id: String = UUID().uuidString
    let number: String
    let name: String
    
    var isSelected: Bool = false
    
    var spacing: CGFloat {
        return 8.0
    }
    
    static var urban = [
        BusLineUI(number: "7", name: "LIMAN - NOVO NASELJE"),
        BusLineUI(number: "12", name: "TELEP"),
        BusLineUI(number: "14", name: "SAJLOVO"),
        BusLineUI(number: "15", name: "IND. ZONA SEVER")
    ]
    
    static var suburban = [
        BusLineUI(number: "52", name: "VETERNIK"),
        BusLineUI(number: "53", name: "FUTOG STARI"),
        BusLineUI(number: "54", name: "FUTOG GRMEČKA")
    ]
}

struct BusLineItemView: View {
    let busLine: BusLineUI
    
    var body: some View {
        HStack(spacing: busLine.spacing, content: {
            Text(busLine.number)
                .fontWeight(.bold)
                .foregroundStyle(.blue)
            Text(busLine.name.uppercased())
            Spacer()
            busLine.isSelected ?
            Image(systemName: "checkmark")
                .foregroundStyle(.blue) : nil
        })
        .frame(maxWidth: .infinity, alignment: .leading)
        .padding(.vertical, 8)
    }
}

#Preview {
    BusLineItemView(busLine: BusLineUI.urban[0])
}
