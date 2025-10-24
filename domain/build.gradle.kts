import dev.yunas.buildsrc.AppConfig

plugins {
    id("java-library")
    alias(libs.plugins.jetbrains.kotlin.jvm)
    alias(libs.plugins.ksp)
}

java {
    sourceCompatibility = AppConfig.Version.JVM
    targetCompatibility = AppConfig.Version.JVM
}

kotlin {
    compilerOptions {
        jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17
    }
}

ksp {
    arg("KOIN_CONFIG_CHECK", "true")
}

dependencies {
    implementation(libs.koin.core)
    implementation(libs.koin.annotations)
    ksp(libs.koin.ksp.compiler)

    implementation(libs.kotlinx.coroutines.core)
}