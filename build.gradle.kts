import net.thebugmc.gradle.sonatypepublisher.PublishingType.USER_MANAGED

plugins {
    id("java")
    id("net.thebugmc.gradle.sonatype-central-portal-publisher") version "1.2.3"
}

description = "Library for minecraft plugin developers (Paper api only)"
group = "io.github.alejomc26"
version = "1.1"

signing {
    useGpgCmd()
}

centralPortal {

    publishingType = USER_MANAGED

    jarTask = tasks.jar

    sourcesJarTask = tasks.create<Jar>("sourcesEmptyJar"){
        archiveClassifier = "sources"
    }

    javadocJarTask = tasks.create<Jar>("javadocEmptyJar") {
        archiveClassifier = "javadoc"
    }

    pom {
        url = "https://github.com/Alejomc26/BossLibrary"
        licenses {
            license {
                name = "MIT License"
                url = "http://www.opensource.org/licenses/mit-license.php"
            }
        }
        developers {
            developer {
                name = "Alejo mc26"
                email = "alechavezjaja26@gmail.com"
            }
        }
        scm {
            connection = "scm:git:https://github.com/Alejomc26/BossLibrary"
            developerConnection = "scm:git:https://github.com/Alejomc26/BossLibrary"
            url = "https://github.com/Alejomc26/BossLibrary"
        }

    }
}

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
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