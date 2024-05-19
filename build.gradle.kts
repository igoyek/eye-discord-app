plugins {
    `java-library`

    id("com.github.johnrengelman.shadow") version "8.1.1"
    kotlin("jvm")
}

group = "dev.igoyek"
version = "1.1.0"

repositories {
    mavenCentral()
    mavenLocal()
    gradlePluginPortal()

    maven { url = uri("https://jitpack.io") }
    maven { url = uri("https://storehouse.okaeri.eu/repository/maven-public/") }
    maven { url = uri("https://repo.eternalcode.pl/snapshots") }
    maven { url = uri("https://repo.eternalcode.pl/releases") }
}

dependencies {
    // jda
    implementation("net.dv8tion:JDA:5.0.0-beta.23")

    // configs
    implementation("eu.okaeri:okaeri-configs-yaml-snakeyaml:5.0.1")

    // jda-utils
    implementation("pw.chew:jda-chewtils-command:2.0-SNAPSHOT")

    // panda utilities
    implementation("org.panda-lang:panda-utilities:0.5.2-alpha")

    // ORMLite
    implementation("com.j256.ormlite:ormlite-core:6.1")
    implementation("com.j256.ormlite:ormlite-jdbc:6.1")

    // hikaricp
    implementation("com.zaxxer:HikariCP:5.1.0")

    // database drivers
    implementation("mysql:mysql-connector-java:8.0.33")
    implementation("com.h2database:h2:2.2.224")
    implementation(kotlin("stdlib-jdk8"))
}

java {
}

tasks.shadowJar {
    archiveFileName.set("Eye ${project.version}.jar")

    manifest {
        attributes(
            "Main-Class" to "dev.igoyek.eye.DiscordApp"
        )
    }
}

kotlin {
    jvmToolchain(17)
}