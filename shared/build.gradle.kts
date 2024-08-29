import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    id("dev.icerock.mobile.multiplatform-resources")
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
            baseName = "Shared"
            isStatic = true
        }
    }
    
    sourceSets {
        commonMain.dependencies {
            api(libs.resources)
            implementation(libs.resources.test)
            // put your Multiplatform dependencies here
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

multiplatformResources {
    resourcesPackage.set("org.kmp.experiment") // required
    resourcesClassName.set("SharedRes") // optional, default MR
    //resourcesVisibility.set(MRVisibility.Internal) // optional, default Public
    //iosBaseLocalizationRegion.set("en") // optional, default "en"
    //iosMinimalDeploymentTarget.set("11.0") // optional, default "9.0"
}
