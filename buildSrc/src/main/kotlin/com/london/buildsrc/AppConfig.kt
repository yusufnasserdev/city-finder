package dev.yunas.buildsrc

import org.gradle.api.JavaVersion

object AppConfig {
    const val ENABLE_R8_FULL_MODE: Boolean = true
    const val ENABLE_R8_FOR_LIBRARIES: Boolean = false

    object Version {
        const val MIN_SDK = 21
        const val TARGET_SDK = 36
        const val COMPILE_SDK = 36
        val JVM = JavaVersion.VERSION_17
        const val BUILD_TOOLS = "35.0.0"
    }

    private const val APPLICATION_ID_GROUP = "dev.yunas"
    private const val APPLICATION_ID_SUFFIX = "cityfinder"
    const val APPLICATION_ID = "$APPLICATION_ID_GROUP.$APPLICATION_ID_SUFFIX"
    const val ANDROID_TEST_INSTRUMENTATION = "androidx.test.runner.AndroidJUnitRunner"

    object Namespace {
        const val APP = "$APPLICATION_ID_GROUP.$APPLICATION_ID_SUFFIX"
        const val DESIGN_SYSTEM = "$APPLICATION_ID_GROUP.designsystem"
        const val PRESENTATION = "$APPLICATION_ID_GROUP.presentation"
        const val DATA = "$APPLICATION_ID_GROUP.data"
        const val DOMAIN = "$APPLICATION_ID_GROUP.domain"
    }

    val freeCompilerArgs = listOf(
        "-opt-in=androidx.compose.material3.ExperimentalMaterial3Api",
        "-opt-in=androidx.compose.foundation.layout.ExperimentalLayoutApi",
    )
}
