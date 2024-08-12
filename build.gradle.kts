plugins {
    id("java")
    id("com.rikonardo.papermake") version "1.0.6"
    id("com.gradleup.shadow") version "8.3.0"
}

allprojects {
    group = "dev.ng5m"
    version = project.property("version")!!
}

dependencies {
    implementation(project(":core"))
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



