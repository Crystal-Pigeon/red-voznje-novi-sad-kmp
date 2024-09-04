import SwiftUI
import Shared

@main
struct iOSApp: App {
    init() {
        let appearance = UINavigationBarAppearance()
        appearance.configureWithOpaqueBackground()
        appearance.backgroundColor = UIColor(SwiftUI.Color.brand) // Set the navigation bar background color
        appearance.titleTextAttributes = [.foregroundColor: UIColor.white] // Set the title color
        appearance.largeTitleTextAttributes = [.foregroundColor: UIColor.white] // Set the large title color
        appearance.shadowColor = nil
        appearance.shadowImage = UIImage()
        
        UINavigationBar.appearance().standardAppearance = appearance
        UINavigationBar.appearance().scrollEdgeAppearance = appearance
        UINavigationBar.appearance().compactAppearance = appearance
        UINavigationBar.appearance().tintColor = .white
    }
    
    var body: some Scene {
        WindowGroup {
            HomeView()
        }
    }
}
