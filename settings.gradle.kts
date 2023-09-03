rootProject.name = "ToDo-Compose"

includeBuild("plugin")
include(":app")
include(":libraries:compose")
include(":libraries:extensions")

pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}

@Suppress("UnstableApiUsage")
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
include(":libraries:testing")
