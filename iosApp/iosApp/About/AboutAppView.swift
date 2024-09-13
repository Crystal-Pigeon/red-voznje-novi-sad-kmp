//
//  AboutAppView.swift
//  iosApp
//
//  Created by Marko Popic on 13.9.24..
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

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
                TitledSectionView(title: "O Aplikaciji", description: "Aplikaciju smo razvili kako bismo građanima Novog Sada i turistima koji posete Novi Sad omogućili da pregledaju red vožnje.\n\nSvi podaci koji se nalaze u aplikaciji su sa javnog sajta JGSP.")
                TitledSectionView(title: "Ažuriranje", description: "Uvek se prikazuje najaktuelniji red vožnje koji je dostupan i na sajtu JGSP-a.")
                TitledSectionView(title: "Jezik", description: "Aplikacija je dostupna na Srpskom i Engleskom jeziku.", actionTitle: "Promeni jezik", actionCompletion: {
                    openAppSettings()
                })
                TitledSectionView(title: "Prijavi gresku", description: "Ukoliko uočite bilo kakvu grešku u radu aplikacije budite slobodni da nam istu prijavite putem email-a.\n\nNeke greške mogu biti prouzrokovane zbog neispravnosti na samom sajtu JGSP-a, a mi ćemo nastojati da greške koje su do naše aplikacije rešimo u što kraćem roku.", actionTitle: "Prijavi grešku", actionCompletion: {
                    sendEmail()
                })
            }
            .padding(20)
            .navigationTitle("O Aplikaciji")
            
            Spacer()
            
            Text("Powered by Crystal Pigeon")
                .font(.regular(12))
                .frame(maxWidth: .infinity, alignment: .center)
                .foregroundColor(Color.textSecondary)
        }
        .background(Color.backgroundPrimary)
    }
    
    private func openAppSettings() {
        if let url = URL(string: UIApplication.openSettingsURLString) {
            if UIApplication.shared.canOpenURL(url) {
                UIApplication.shared.open(url)
            }
        }
    }
    
    private func sendEmail(subject: String = "Red Vožnje Novi Sad: Greška") {
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
