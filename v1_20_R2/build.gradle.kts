plugins {
    id("java")
}

dependencies {
    compileOnly(project(":core"))
    compileOnly("org.spigotmc:spigot:1.20.2-R0.1-SNAPSHOT")
}

tasks {
    java {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(17))
        }
    }
}