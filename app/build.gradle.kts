import android.annotation.SuppressLint
import de.honoka.gradle.buildsrc.Versions

plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    namespace = "de.honoka.android.h5apptemplate"
    compileSdk = 33

    defaultConfig {
        applicationId = "de.honoka.android.h5apptemplate"
        minSdk = 26
        @SuppressLint("OldTargetApi")
        targetSdk = 33
        versionCode = 1
        versionName = "1.0.0-dev"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            @Suppress("UnstableApiUsage")
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    packaging {
        resources.excludes.addAll(listOf(
            "META-INF/INDEX.LIST",
            "META-INF/io.netty.versions.properties"
        ))
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = sourceCompatibility
    }

    kotlinOptions {
        jvmTarget = compileOptions.sourceCompatibility.toString()
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("de.honoka.sdk:honoka-android-utils:${Versions.honokaAndroidUtils}")
    implementation("cn.hutool:hutool-all:5.8.18")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}