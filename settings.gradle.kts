pluginManagement {
    repositories {
        google()
        mavenCentral()

        gradlePluginPortal()

    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {

        google()
        mavenCentral()
        maven (
                url = "https://maven.google.com"
        )

    }
}

rootProject.name = "medical_application"
include(":app")
 