// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    val versions = de.honoka.gradle.buildsrc.Versions
    //plugins
    id("com.android.application") version versions.android apply false
    kotlin("android") version versions.kotlin apply false
}