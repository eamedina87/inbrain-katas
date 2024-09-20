import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.jetbrainsKotlinAndroid) apply false
    alias(libs.plugins.jetbrainsKotlinCompiler) apply false
    alias(libs.plugins.jetbrainsKotlinKapt) apply false
}

subprojects {
    // Kapt
    apply(plugin = rootProject.libs.plugins.jetbrainsKotlinKapt.get().pluginId)

    // Kotlin Compile configuration
    tasks.withType<KotlinCompile> {
        compilerOptions {
            jvmTarget = JvmTarget.JVM_1_8
        }
    }

    // UnitTests
    tasks.withType<Test> {
        useJUnitPlatform()
    }
}
