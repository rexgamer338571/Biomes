plugins {
    id("java")
}

dependencies {
    compileOnly(project(":core"))
    compileOnly("org.spigotmc:spigot:1.19.3-R0.1-SNAPSHOT")
    compileOnly("com.google.guava:guava:30.2.0-jre")
    compileOnly("io.netty:netty:4.1.79.Final")
    compileOnly("org.yaml:snakeyaml:2.0")
    // fix vulnerabilities
}

configurations {
    all {
        exclude(group = "com.google.guava", module = "guava")
        exclude(group = "io.netty", module = "netty-codec")
        exclude(group = "io.netty", module = "netty-handler")
        exclude(group = "org.yaml", module = "snakeyaml")
    }
}

tasks {
    java {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(17))
        }
    }
}