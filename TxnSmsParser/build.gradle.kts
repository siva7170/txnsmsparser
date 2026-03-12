import org.gradle.kotlin.dsl.getByName
import org.gradle.kotlin.dsl.publishing

plugins {
    alias(libs.plugins.android.library)
    id("maven-publish")
    id("signing")
}

// ── Publication info ──────────────────────────────────────────────
val publishGroupId    = "in.depthshow"    // ← change this
val publishArtifactId = "txnsmsparser"              // ← change this
val publishVersion    = "1.0.1"                     // ← change this

android {
    namespace = "in.depthshow.txnsmsparser"
    compileSdk = 36

    defaultConfig {
        minSdk = 26

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    // ── REQUIRED: tells Android to generate sources + javadoc JARs ──
    publishing {
        singleVariant("release") {
            withSourcesJar()
            withJavadocJar()
        }
    }
}

dependencies {
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}



// ── Maven publishing ──────────────────────────────────────────────
afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("release") {
                groupId    = publishGroupId
                artifactId = publishArtifactId
                version    = publishVersion

                from(components["release"])

                pom {
                    name.set("TxnSmsParser")
                    description.set("A library to parse transaction SMS messages")
                    url.set("https://github.com/siva7170/txnsmsparser")  // ← change

                    licenses {
                        license {
                            name.set("The Apache License, Version 2.0")
                            url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                        }
                    }

                    developers {
                        developer {
                            id.set("depthshow")       // ← change
                            name.set("Depthshow")   // ← change
                            email.set("depthshow@gmail.com") // ← change
                        }
                    }

                    scm {
                        connection.set("scm:git:github.com/siva7170/txnsmsparser.git")          // ← change
                        developerConnection.set("scm:git:ssh://github.com/siva7170/txnsmsparser.git") // ← change
                        url.set("https://github.com/siva7170/txnsmsparser/tree/main")           // ← change
                    }
                }
            }
        }
    }

    signing {
        sign(publishing.publications["release"])
    }
}