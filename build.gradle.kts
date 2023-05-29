plugins {
    java
    `maven-publish`
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("net.dv8tion:JDA:5.0.0-beta.9")
}

group = "com.yosoyvillaa"
version = "1.6"
description = "VJCommands"
java.sourceCompatibility = JavaVersion.VERSION_1_8
java.targetCompatibility = JavaVersion.VERSION_1_8

publishing {
    publications.create<MavenPublication>("maven") {
        from(components["java"])
    }
}

java {
    withSourcesJar()
    withJavadocJar()
}

tasks.withType<JavaCompile>() {
    options.encoding = "UTF-8"
}

tasks.withType<Javadoc>() {
    setDestinationDir(file("docs/apidocs"))
    (options as StandardJavadocDocletOptions).links("https://docs.oracle.com/javase/8/docs/api/")
}