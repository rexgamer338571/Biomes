plugins {
    id("java")
}

dependencies {
    compileOnly(project(":core"))
    compileOnly("org.spigotmc:spigot:1.16.5-R0.1-SNAPSHOT")
}

tasks {
    java {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(17))
        }
    }
}