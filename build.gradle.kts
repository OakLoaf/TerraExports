plugins {
    `java-library`
    id("com.gradleup.shadow") version("8.3.0")
    id("xyz.jpenilla.run-paper") version("2.2.4")
}

group = "org.lushplugins"
version = "1.0.0"

repositories {
    mavenLocal()
    mavenCentral()
    maven("https://oss.sonatype.org/content/groups/public/")
    maven("https://repo.papermc.io/repository/maven-public/") // Paper
    maven("https://repo.codemc.org/repository/maven-public/") // Terra
}

dependencies {
    // Dependencies
    compileOnly("io.papermc.paper:paper-api:1.21.6-R0.1-SNAPSHOT")

    compileOnly("com.dfsek.terra:api:6.6.5-BETA+8cfa2e146")
    compileOnly("com.dfsek.terra:manifest-addon-loader:1.0.0-BETA+fd6decc70")
    compileOnly("com.dfsek.terra:v1_21_6:6.6.5-BETA+8cfa2e146")

    // Libraries
    implementation("com.fasterxml.jackson.core:jackson-core:2.19.0") // Jackson
    implementation("com.fasterxml.jackson.core:jackson-annotations:2.19.0") // Jackson
    implementation("com.fasterxml.jackson.core:jackson-databind:2.19.0") // Jackson
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))

    registerFeature("optional") {
        usingSourceSet(sourceSets["main"])
    }

    withSourcesJar()
}

tasks {
    withType<JavaCompile> {
        options.encoding = "UTF-8"
    }

    shadowJar {
        minimize()

        // Destination for runServer task
//        destinationDirectory.set(file("run/plugins/Terra/addons/"))

        archiveFileName.set("${project.name}-${project.version}.jar")
    }

    processResources{
        filesMatching("terra.addon.yml") {
            expand(project.properties)
        }

        inputs.property("version", rootProject.version)
        filesMatching("terra.addon.yml") {
            expand("version" to rootProject.version)
        }
    }

    runServer {
        minecraftVersion("1.21.6")

        downloadPlugins {
            modrinth("bluemap", "5.7-paper")
            modrinth("terra", "6.6.5-BETA-bukkit")
        }
    }
}
