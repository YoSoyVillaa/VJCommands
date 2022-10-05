plugins {
    java
    `maven-publish`
}

repositories {
    mavenLocal()
    maven("https://jitpack.io")
    maven("https://repo.maven.apache.org/maven2/")
}

dependencies {
    implementation("net.dv8tion:JDA:5.0.0-alpha.20")
}

group = "com.yosoyvillaa"
version = "1.3"
description = "VJCommands"
java.sourceCompatibility = JavaVersion.VERSION_1_8

publishing {
    publications.create<MavenPublication>("maven") {
        from(components["java"])
    }
}

tasks.withType<JavaCompile>() {
    options.encoding = "UTF-8"
}
