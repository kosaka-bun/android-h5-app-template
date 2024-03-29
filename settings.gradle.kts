@file:Suppress("UnstableApiUsage")

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        maven("https://maven.aliyun.com/repository/public")
        google()
        mavenCentral()
        mavenLocal()
        maven("https://mirrors.honoka.de/maven-repo")
    }
}

pluginManagement {
    repositories {
        maven("https://maven.aliyun.com/repository/gradle-plugin")
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

rootProject.name = "android-h5-app-template"

include(":app")
include(":app-web")
