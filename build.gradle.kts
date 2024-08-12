plugins {
    id("java")
    id("com.rikonardo.papermake") version "1.0.6"
    id("com.gradleup.shadow") version "8.3.0"
}

allprojects {
    group = "dev.ng5m"
    version = project.property("version")!!

    apply(plugin = "java")

    repositories {
        mavenCentral()
        maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
        maven("https://oss.sonatype.org/content/repositories/snapshots")
        maven("https://oss.sonatype.org/content/repositories/central")
        mavenLocal()
    }
}

dependencies {
    implementation(project(":core"))

    implementation(project(":v1_20_R3"))
}

tasks {
    jar {
        enabled = false
    }

    shadowJar {
        archiveBaseName.set("")
    }

    build {
        dependsOn(shadowJar)
    }
}



