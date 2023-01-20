import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("multiplatform") version "1.7.20"
    id("org.jetbrains.compose") version "1.2.2"
}

group = "com.dapadz"
version = "1.0-SNAPSHOT"

repositories {
    google()
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
}

kotlin {
    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = "11"
        }
        withJava()
    }
    sourceSets {
        val jvmMain by getting {
            dependencies {
                api(compose.foundation)
                api(compose.animation)
                api(compose.materialIconsExtended)
                api("moe.tlaster:precompose:1.3.13")
                implementation("io.ktor:ktor-client-core:2.2.1")
                implementation("io.ktor:ktor-client-cio:2.2.1")
                implementation("io.ktor:ktor-client-logging:2.2.1")
                implementation("io.ktor:ktor-client-gson:2.2.1")
                implementation("org.jsoup:jsoup:1.15.3")
                implementation(compose.desktop.currentOs)
            }
        }
        val jvmTest by getting
    }
}

compose.desktop {
    application {
        mainClass = "MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "JiraWorkLogDesktop"
            packageVersion = "1.0.0"
        }
    }
}
