plugins {
    id("java")
    id("net.thebugmc.gradle.sonatype-central-portal-publisher") version "1.2.3"
}

group = "io.github.alejomc26"
version = "1.0"

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
}

signing {
    useGpgCmd()
}

centralPortal {
    pom {
        name = "Boss Library"
        description = "Library for minecraft plugin developers (Paper api only)"
        url = "https://github.com/Alejomc26/BossLibrary"
        licenses {
            license {
                name = "The Apache License, Version 2.0"
                url = "http://www.apache.org/licenses/LICENSE-2.0.txt"
            }
        }
    }
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
    compileOnly("io.papermc.paper:paper-api:1.20.4-R0.1-SNAPSHOT")
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}