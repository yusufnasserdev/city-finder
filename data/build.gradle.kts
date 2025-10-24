import dev.yunas.buildsrc.AppConfig
import dev.yunas.buildsrc.getKey

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ktorfit)
}

android {
    namespace = "dev.yunas.data"
    compileSdk = AppConfig.Version.COMPILE_SDK

    defaultConfig {
        minSdk = AppConfig.Version.MIN_SDK

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

        buildConfigField("String", "API_KEY", getKey("API_KEY"))
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = AppConfig.Version.JVM
        targetCompatibility = AppConfig.Version.JVM
    }

    buildFeatures{
        buildConfig = true
    }
}

kotlin {
    jvmToolchain(17)
}

ksp {
    arg("KOIN_CONFIG_CHECK","true")
}

dependencies {

    implementation(projects.domain)
    implementation(libs.androidx.core.ktx)

    implementation(libs.koin.core)
    implementation(libs.koin.android)
    implementation(libs.koin.annotations)
    ksp(libs.koin.ksp.compiler)

    implementation(libs.kotlinx.serialization.json)
    implementation(libs.napier)
    implementation(libs.bundles.ktor)
    implementation(libs.bundles.ktorfit)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

}