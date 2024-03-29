@file:Suppress("PropertyName")

import java.util.Properties
import java.io.FileInputStream

loadGradleProperties()
val kotlin_version: String by project

plugins {
    // Support convention plugins written in Kotlin. Convention plugins are build scripts in 'src/main' that automatically become available as plugins in the main build.
    `kotlin-dsl`
    `java-gradle-plugin`
    kotlin("plugin.serialization") version "1.6.21"
}

repositories {
    // Use the plugin portal to apply community plugins in convention plugins.
    gradlePluginPortal()
}

gradlePlugin {
    plugins {
        create("projectInfoPlugin") {
            id = "uk.co.xprl.project-info"
            implementationClass = "uk.co.xprl.gradle.ProjectInfoPlugin"
        }
        create("mavenArtifactPlugin") {
            id = "uk.co.xprl.maven-artifact"
            implementationClass = "uk.co.xprl.gradle.MavenArtifactPlugin"
        }
    }
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version")
    implementation("org.jetbrains.kotlin:kotlin-serialization:1.6.21")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-hocon:1.2.2")
}


/**
 * Load gradle.properties file
 */
fun Project.loadGradleProperties() {
    val props = Properties()
    val propsFile = file("../gradle.properties")
    FileInputStream(propsFile).use {
        props.load(it)
    }

    for((key, value) in props.entries) {
        extra[key.toString()] = value.toString()
    }
}