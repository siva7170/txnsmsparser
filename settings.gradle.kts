pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }

}

plugins {
    // 1. ADD THE NMCP PLUGIN HERE
    id("com.gradleup.nmcp.settings") version "1.4.4"
}

// 3. ADD THE CONFIGURATION BLOCK
nmcpSettings {
    centralPortal {
    // FIX: Add .getOrElse("") to resolve the provider into a String immediately.
    // This allows the configuration cache to serialize the actual text value.
    username.set(providers.gradleProperty("mavenCentralUsername").getOrElse(""))
    password.set(providers.gradleProperty("mavenCentralPassword").getOrElse(""))

    publishingType.set("USER_MANAGED")

}
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "TxnSmsParserApp"
include(":app")
include(":TxnSmsParser")
