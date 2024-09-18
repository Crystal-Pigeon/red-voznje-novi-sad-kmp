//
//  AboutAppView.swift
//  iosApp
//
//  Created by Marko Popic on 13.9.24..
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import Shared

struct TitledSectionView: View {
    @State var title: String
    @State var description: String
    @State var actionTitle: String?
    @State var actionCompletion: (()->Void)?
    
    var body: some View {
        VStack(alignment: .leading, spacing: 12) {
            Text(title)
                .font(.bold(20))
                .frame(maxWidth: .infinity, alignment: .leading)
                .foregroundColor(Color.textPrimary)
            
            Text(description)
                .font(.regular(14))
                .frame(maxWidth: .infinity, alignment: .leading)
                .foregroundColor(Color.textPrimary)
            
            if let actionTitle = self.actionTitle, let actionCompletion = self.actionCompletion {
                Text(actionTitle)
                    .font(.bold(14))
                    .frame(maxWidth: .infinity, alignment: .leading)
                    .foregroundColor(Color.brand)
                    .onTapGesture {
                        actionCompletion()
                    }
            }
        }
    }
}

struct AboutAppView: View {
    var body: some View {
        VStack {
            VStack(alignment: .leading, spacing: 24) {
                TitledSectionView(
                    title: SharedRes.strings().about_app_about_app_section_title.localized,
                    description: SharedRes.strings().about_app_about_app_section_description.localized,
                    actionTitle: SharedRes.strings().about_app_about_app_section_action.localized,
                    actionCompletion: {
                        openWebsite()
                    }
                )
                TitledSectionView(
                    title: SharedRes.strings().about_app_updates_section_title.localized,
                    description: SharedRes.strings().about_app_updates_section_description.localized
                )
                TitledSectionView(
                    title: SharedRes.strings().about_app_language_section_title.localized,
                    description: SharedRes.strings().about_app_language_section_description.localized,
                    actionTitle: SharedRes.strings().about_app_language_section_action.localized,
                    actionCompletion: {
                        openAppSettings()
                    }
                )
                TitledSectionView(
                    title: SharedRes.strings().about_app_issue_section_title.localized,
                    description: SharedRes.strings().about_app_issue_section_description.localized,
                    actionTitle: SharedRes.strings().about_app_issue_section_action.localized,
                    actionCompletion: {
                        sendEmail()
                    }
                )
            }
            .padding(20)
            .navigationTitle(SharedRes.strings().about_app_title.localized)
            
            Spacer()
            
            Text(SharedRes.strings().about_app_footer.localized)
                .font(.regular(12))
                .frame(maxWidth: .infinity, alignment: .center)
                .foregroundColor(Color.textSecondary)
        }
        .background(Color.backgroundPrimary)
    }
    
    private func openWebsite() {
        if let url = URL(string: "http://www.gspns.co.rs/red-voznje/gradski") {
            if UIApplication.shared.canOpenURL(url) {
                UIApplication.shared.open(url)
            }
        }
    }
    
    private func openAppSettings() {
        if let url = URL(string: UIApplication.openSettingsURLString) {
            if UIApplication.shared.canOpenURL(url) {
                UIApplication.shared.open(url)
            }
        }
    }
    
    private func sendEmail(subject: String = SharedRes.strings().about_app_issue_section_email_subject.localized) {
        let subjectEncoded = subject.addingPercentEncoding(withAllowedCharacters: .urlQueryAllowed) ?? ""
        let mailtoURL = URL(string: "mailto:contact@crystalpigeon.com?subject=\(subjectEncoded)")!
        
        if UIApplication.shared.canOpenURL(mailtoURL) {
            UIApplication.shared.open(mailtoURL)
        }
    }
}

#Preview {
    AboutAppView()
}
