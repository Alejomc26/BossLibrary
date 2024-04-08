import io.papermc.hangarpublishplugin.model.Platforms
import net.thebugmc.gradle.sonatypepublisher.PublishingType.USER_MANAGED

plugins {
    id("java")
    id("net.thebugmc.gradle.sonatype-central-portal-publisher") version "1.2.3"
    id("io.papermc.hangar-publish-plugin") version "0.1.2"
}

description = "Library for minecraft plugin developers (Paper api only)"
group = "io.github.alejomc26"
version = "1.41"

signing {
    useGpgCmd()
}

hangarPublish {
    publications.register("plugin") {
        version.set(project.version as String)
        channel.set("Snapshot") // We're using the 'Snapshot' channel
        // TODO: Edit the project name to match your Hangar project
        id.set("BossLibrary")
        apiKey.set(System.getProperty("HANGAR_API_TOKEN"))
        platforms {
            // TODO: Use the correct platform(s) for your plugin
            register(Platforms.PAPER) {
                // TODO: If you're using ShadowJar, replace the jar lines with the appropriate task:
                //   jar.set(tasks.shadowJar.flatMap { it.archiveFile })
                // Set the jar file to upload
                jar.set(tasks.jar.flatMap { it.archiveFile })

                // Set platform versions from gradle.properties file
                val versions: kotlin.collections.List<kotlin.String> = (property("paperVersion") as kotlin.String)
                        .split(",")
                        .map { it.trim() }
                platformVersions.set(versions)

                // TODO: Configure your plugin dependencies, if any
                dependencies {
                    // Example for a dependency found on Hangar
                    hangar("Maintenance") {
                        required.set(false)
                    }
                    // Example for an external dependency
                    url("Debuggery", "https://github.com/PaperMC/Debuggery") {
                        required.set(true)
                    }
                }
            }
        }
    }
}

centralPortal {

    publishingType = USER_MANAGED

    jarTask = tasks.jar

    sourcesJarTask = tasks.sourcesJar

    javadocJarTask = tasks.javadocJar

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