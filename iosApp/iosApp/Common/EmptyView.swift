//
//  EmptyView.swift
//  iosApp
//
//  Created by Marko Popic on 11.9.24..
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct EmptyView: View {
    var body: some View {
        VStack(alignment: .center, spacing: 16) {
            Image(systemName: "bus.fill")
                .font(.system(size: UIScreen.main.bounds.width * 0.3))
                .foregroundStyle(Color.textSecondary.opacity(0.5))
            
            Text("Currently, you don't have favorite buses.\nClick on the '+' button and choose them")
                .multilineTextAlignment(.center)
                .font(.regular(16))
                .foregroundStyle(Color.textSecondary)
                .frame(maxWidth: UIScreen.main.bounds.width * 0.8)
        }
    }
}

#Preview {
    EmptyView()
}
