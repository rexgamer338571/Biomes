plugins {
    id("java")
    id("com.rikonardo.papermake") version "1.0.6"
    id("com.gradleup.shadow") version "8.3.0"
}

version = "1.0.0"

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

    implementation(project(":v1_16_R3"))
    implementation(project(":v1_17_R1"))
    implementation(project(":v1_18_R2"))
    implementation(project(":v1_19_R1"))
    implementation(project(":v1_19_R2"))
    implementation(project(":v1_19_R3"))
    implementation(project(":v1_20_R1"))
    implementation(project(":v1_20_R2"))
    implementation(project(":v1_20_R3"))
}

tasks {
    jar {
        enabled = false
    }

    shadowJar {
        archiveBaseName.set("biomes")
    }

    build {
        dependsOn(shadowJar)
    }
}



