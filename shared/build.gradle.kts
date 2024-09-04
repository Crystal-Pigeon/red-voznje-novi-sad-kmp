import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    id("dev.icerock.mobile.multiplatform-resources")
}

object Versions {
    const val koin = "4.0.0-RC2"
}

object Deps {

    object Koin {
        const val core = "io.insert-koin:koin-core:${Versions.koin}"
        const val test = "io.insert-koin:koin-test:${Versions.koin}"
        const val android = "io.insert-koin:koin-android:${Versions.koin}"
    }

}

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            export("dev.icerock.moko:resources:0.24.2")
            export("dev.icerock.moko:graphics:0.9.0")
            baseName = "Shared"
            isStatic = true
        }
    }
    
    sourceSets {
        commonMain.dependencies {
            api(libs.resources)
            implementation(libs.resources.test)
            implementation(libs.ktor.client.core)
            implementation(libs.kotlinx.coroutines.core)
            implementation("com.fleeksoft.ksoup:ksoup-ktor2:0.1.6-alpha1")
            implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.6.1")
            implementation(Deps.Koin.core)
            // put your Multiplatform dependencies here
        }

        androidMain.dependencies {
            implementation(libs.ktor.client.okhttp)
            implementation(Deps.Koin.android)
        }

        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
            implementation(Deps.Koin.core)
        }
    }
}
task("testClasses")

android {
    namespace = "org.kmp.experiment.shared"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
}
/*dependencies {
    implementation(libs.ksoup.ktor2.android)
}*/

multiplatformResources {
    resourcesPackage.set("org.kmp.experiment") // required
    resourcesClassName.set("SharedRes") // optional, default MR
    //resourcesVisibility.set(MRVisibility.Internal) // optional, default Public
    //iosBaseLocalizationRegion.set("en") // optional, default "en"
    //iosMinimalDeploymentTarget.set("11.0") // optional, default "9.0"
}
