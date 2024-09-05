//
//  FavoriteBusView.swift
//  iosApp
//
//  Created by Marko Popic on 3.9.24..
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct BusHeaderView: View {
    
    @State var number: String
    @State var title: String
    
    var body: some View {
        HStack(alignment: .center, spacing: 8) {
            Text(number)
                .frame(width: 32, height: 32)
                .background(Color.brand)
                .foregroundStyle(.white)
                .font(.bold(16))
                .clipShape(.circle)
            
            Text(title)
                .foregroundStyle(Color.textPrimary)
                .font(.bold(16))
            
            Spacer()
        }
    }
}

struct BusScheduleView: View {
    
    @State var title: String
    @State var timetable: [String:String]
    
    var body: some View {
        VStack(alignment: .leading, spacing: 8) {
            Text(title)
                .frame(maxWidth: .infinity, alignment: .leading)
                .foregroundStyle(Color.textSecondary)
                .font(.regular(14))
                .padding(.bottom, 4)
            
            ForEach(timetable.sorted(by: <), id: \.key) { time in
                HStack(alignment: .top, spacing: 4) {
                    Text(time.key + ":")
                        .foregroundStyle(Int(time.key) == Date().hour ? Color.brand : Color.textPrimary)
                        .font(.bold(14))
                    Text(time.value)
                        .frame(maxWidth: .infinity, alignment: .leading)
                        .foregroundStyle(Color.textPrimary)
                        .font(.regular(14))
                }
            }
        }
    }
}

struct FavoriteBusView: View {
    let bus: FavoriteBusUI
    
    var body: some View {
        VStack(alignment: .leading, spacing: 16) {
            BusHeaderView(number: bus.number, title: bus.name)
            HStack(alignment: .top, spacing: 0) {
                BusScheduleView(title: bus.scheduleTitleA, timetable: bus.scheduleA)
                    .frame(maxWidth: .infinity, alignment: .leading)
                BusScheduleView(title: bus.scheduleTitleB, timetable: bus.scheduleB)
                    .frame(maxWidth: .infinity, alignment: .leading)
            }
        }
        .padding(4)
    }
}

struct FavoriteBusesListView: View {
    
    @State var favoriteBuses: [FavoriteBusUI]
    
    var body: some View {
        List($favoriteBuses, id: \.id) { $bus in
            FavoriteBusView(bus: bus)
                .padding(12)
                .listRowSeparator(.hidden)
                .listRowBackground(
                    Rectangle()
                        .cornerRadius(8)
                        .foregroundStyle(Color.backgroundPrimary)
                        .shadow(color: Color.black.opacity(0.15), radius: 8)
                        .padding(16)
                )
        }
        .padding(2)
        .listStyle(.plain)
        .background(Color.backgroundSecondary)
    }
}

#Preview {
    FavoriteBusesListView(favoriteBuses: [FavoriteBusUI.dummy, FavoriteBusUI.dummy])
}
