//
//  EmptyView.swift
//  iosApp
//
//  Created by Marko Popic on 11.9.24..
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import Shared

enum EmptyViewState {
    case emptyHome
    case emptyLines
    case error(String)
    
    var imageName: String {
        switch self {
        case .emptyHome, .emptyLines:
            return "bus.fill"
        case .error:
            return "exclamationmark.octagon.fill"
        }
    }
    
    var message: String {
        switch self {
        case .emptyHome:
            return SharedRes.strings().home_no_data_message.localized
        case .emptyLines:
            return SharedRes.strings().bus_lines_no_data_message.localized
        case .error(let message):
            return message
        }
    }
}

struct EmptyView: View {
    
    var state: EmptyViewState
    
    var body: some View {
        VStack(alignment: .center, spacing: 16) {
            Image(systemName: state.imageName)
                .font(.system(size: UIScreen.main.bounds.width * 0.3))
                .foregroundStyle(Color.textSecondary.opacity(0.5))
            
            Text(state.message)
                .multilineTextAlignment(.center)
                .font(.regular(16))
                .foregroundStyle(Color.textSecondary)
                .frame(maxWidth: UIScreen.main.bounds.width * 0.8)
        }
    }
}

#Preview {
    EmptyView(state: .emptyHome)
}
